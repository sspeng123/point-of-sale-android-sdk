package com.pengshi.wokx.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pengshi.wokx.bean.Menu;
import com.pengshi.wokx.bean.MenuType;
import com.pengshi.wokx.bean.OrderItem;
import com.pengshi.wokx.manager.MenuManager;
import com.pengshi.wokx.manager.MenuTypeManager;
import com.pengshi.wokx.manager.OrderDetailManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.pengshi.wokx.R;
import com.pengshi.wokx.util.MyApplication;
import com.pengshi.wokx.util.ScrollLayout;

/**
 * @author shipeng
 * 1/11/24
 */
public class MenuActivity extends Activity {
    /** 总页数. */
    private int PageCount;
    /** 当前页码. */
    private int PageCurrent;
    /** 被选中的. */
    private int gridID = -1;
    /** 每页显示的数量，Adapter保持一致. */
    private static final float PAGE_SIZE = 12.0f;
    /** GridView. */
    private GridView gridView;
    /** 自定义的左右滑动. */
    private ScrollLayout curPage;
    /** 页码条布局、菜品类型. */
    private LinearLayout layoutBottom,menuTypes;
    /** 页码条. */
    private ImageView imgCur;
    /** 数据集. */
    private List<MenuType> lstDate_MenuType = new ArrayList<MenuType>();
    private List<Menu> lstDate = new ArrayList<Menu>();
    private List<Button> button_menuTypes = new ArrayList<Button>();
    /** 菜品描述. */
    private TextView textview_menuDesc;
    /** 跳转到订单页面按钮. */
    private Button button_toOrder;
    /** 呼叫前台. */
    private ImageButton imageButton_call;
    /** 选中的菜单类型ID. */
    private String type_id;
    private List<OrderItem> mData;
    private int total_num = 0, total_prices = 0;
    private TextView textview_total_num, textview_total_prices;
    private ListView listview_orderDetail;
    private Button button_order_submit, button_order_pay, button_order_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        MyApplication.getInstance().addActivity(this);//添加当前Activity到Activity容器

        findViews();
        initViews();

        curPage.setPageListener(new ScrollLayout.PageListener() {
            @Override
            public void page(int page) {
                setCurPage(page);
            }
        });

        //字体闪烁动画，使用线程和Timer实现
        spark();
        bindOrderDetails();
    }

    //字体闪烁动画，使用线程和Timer实现
    private int clo = 0;
    public void spark() {
        final Button touchScreen = (Button) findViewById(R.id.button_toOrder);// 获取页面textview对象
        Timer timer = new Timer();
        TimerTask taskcc = new TimerTask(){

            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (clo == 0) {
                            clo = 1;
                            touchScreen.setTextColor(Color.RED); // 透明Color.TRANSPARENT
                        } else {
                            clo = 0;
                            touchScreen.setTextColor(Color.BLUE);
                        }
                    }
                });
            }
        };
        timer.schedule(taskcc, 1, 2000); // 参数分别是delay（多长时间后执行），duration（执行间隔）
    }

    /**
     * 初始化
     */
    private void findViews() {
        curPage = (ScrollLayout) findViewById(R.id.scr);
        layoutBottom = (LinearLayout) findViewById(R.id.layout_scr_bottom);
        textview_menuDesc=(TextView) findViewById(R.id.textview_menuDesc);
        menuTypes = (LinearLayout) findViewById(R.id.menuTypes);
        curPage.getLayoutParams().height = this.getWindowManager().getDefaultDisplay().getHeight() * 7 / 8;
        button_toOrder = (Button) findViewById(R.id.button_toOrder);
        imageButton_call = (ImageButton) findViewById(R.id.imageButton_call);

        textview_total_num = (TextView) findViewById(R.id.listview_textview_total_num);
        textview_total_prices = (TextView) findViewById(R.id.listview_textview_total_prices);

    }

    /**
     * 初始化
     */
    private void initViews() {
        // 初始化菜品类型
        initViews_MenuType();

        //初始化GridView
        initViews_Menu(type_id);

        //呼叫前台
        imageButton_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setMessage("你确定要呼叫服务台吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // TODO 呼叫前台
                                Toast.makeText(MenuActivity.this, "已向服务台发送呼叫，请稍等.....",
                                        Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        //跳转订单页面
        button_toOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				/*Intent intent = new Intent(MenuActivity.this, OrderActivity.class);
				intent.putExtra("type_id", type_id);
				startActivityForResult(intent, 0);*/
            }
        });
    }
    /**
     * 初始化菜品类型
     */
    private void initViews_MenuType() {
        // 获取菜品类型数据
        lstDate_MenuType = new MenuTypeManager(this).queryMenuTypes();
        // 添加菜品类型
        for (int i = 0; i < lstDate_MenuType.size(); i++) {
            final Button button = new Button(this);
            button.setTag(lstDate_MenuType.get(i).getId());
            if (i == 0) {
                button.setTextColor(Color.RED);
            }
            button.setHeight(200);
            button.setWidth(250);

            /*button.setBackground(this.getResources().getDrawable(R.drawable.button));*/
            //button.setBackgroundColor(Color.parseColor("#E78E29"));
            button.setText(lstDate_MenuType.get(i).getName());
            button.setTextSize(12);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String position = ((String) button.getTag());
                    type_id = position;
                    // 为GridView绑定数据
                    initViews_Menu(position);
                    for (int j = 0; j < button_menuTypes.size(); j++) {
                        button_menuTypes.get(j).setTextColor(Color.BLACK);
                    }
                    button.setTextColor(Color.RED);
                }
            });

            type_id = lstDate_MenuType.get(0).getId();
            button_menuTypes.add(button);
            // 将组件对象放置在布局管理器中
            menuTypes.addView(button, i);
        }
    }
    /**
     * 添加GridView
     */
    private void initViews_Menu(String type_id) {
        lstDate = new MenuManager(this).queryMenus(type_id);
        PageCount = (int) Math.ceil(lstDate.size() / PAGE_SIZE);
        if (gridView != null) {
            curPage.removeAllViews();
        }

        for (int i = 0; i < PageCount; i++) {
            gridView = new GridView(MenuActivity.this);
            gridView.setAdapter(new DateAdapter(MenuActivity.this, lstDate, i,textview_menuDesc));
            gridView.setNumColumns(4);
            gridView.setHorizontalSpacing(8);
            gridView.setVerticalSpacing(8);
            // 去掉点击时的黄色背景
            gridView.setSelector(R.drawable.bg_grid_item);
            gridView.setOnItemClickListener(gridListener);
            curPage.addView(gridView);
        }
        curPage.ScreenToZero(0);
        setCurPage(0);
    }

    /**
     * GridView的监听事件
     */
    public AdapterView.OnItemClickListener gridListener = new AdapterView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            PageCurrent = curPage.getCurScreen();
            gridID = arg2 + PageCurrent * 12;

			/*if (((GridView) arg0).getTag() != null) {
				((View) ((GridView) arg0).getTag()).setBackgroundResource(R.drawable.bg_grid_item);
			}
			((GridView) arg0).setTag(arg1);
			arg1.setBackgroundResource(R.drawable.bg_grid_item_false);*/
        }
    };

    /**
     * 更新当前页码
     */
    public void setCurPage(int page) {
        layoutBottom.removeAllViews();
        for (int i = 0; i < PageCount; i++) {
            imgCur = new ImageView(MenuActivity.this);
            imgCur.setBackgroundResource(R.drawable.bg_img_item);
            imgCur.setId(i);
            // 判断当前页码来更新
            if (imgCur.getId() == page) {
                imgCur.setBackgroundResource(R.drawable.bg_img_item_true);
            }
            layoutBottom.addView(imgCur);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b=data.getExtras();  //data为B中回传的Intent
                //Integer type_id=b.getInt("type_id");//str即为回传的值"Hello, this is B speaking"
                String type_id = b.getString("type_id");
                initViews_Menu(type_id);
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    // 捕捉按键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 建立菜单选项
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        //getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 处理菜单选项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		/*switch (item.getItemId()) {
		case R.id.quit:
			// 取得自定义View
			LayoutInflater layoutInflater = LayoutInflater.from(MenuActivity.this);
			final View logout_View = layoutInflater.inflate(R.layout.logout, null);

			Dialog alertDialog = new AlertDialog.Builder(MenuActivity.this)
					.setTitle("确认退出吗？").setIcon(R.drawable.call).setView(logout_View)
					.setPositiveButton("确认", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//TODO 检查用户密码是否正确
							// 获得服务员工号,查找本地用户信息
							String account = findUserMsg();
							// 获得密码
							String password = ((TextView)logout_View.findViewById(R.id.edittext_logout_password)).getText().toString();

							User user=new UserManager(MenuActivity.this).query(account, password);
							if(user!=null){
								//清空订单信息
								new OrderDetailManager(MenuActivity.this).truncate();
								//删除本地用户信息
								deleteUserMsg();
								//关闭背景音乐
								stopService(new Intent("com.liuwang.meal.MUSIC"));
								//关闭所有Activity
								MyApplication.getInstance().exit();
							}else{
								MyUtil.showDialog(MenuActivity.this, "密码错误，退出失败");
							}
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					}).create();
			alertDialog.show();

			break;

		default:
			break;
		}*/
        return super.onOptionsItemSelected(item);
    }


    // 查找配置文件中的用户信息
    private String findUserMsg(){
        // 共享信息
        SharedPreferences sp = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
        return sp.getString("account", "");
    }
    // 清空配置文件中的用户信息
    private void deleteUserMsg(){
        // 共享信息
        SharedPreferences sp = getSharedPreferences("user_msg", MODE_WORLD_WRITEABLE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("account", "");
        editor.commit();
    }


    private void bindOrderDetails() {
        // 通过订单详细管理类获取订单信息
        mData = new OrderDetailManager(this).queryOrderItems();
        MyAdapter adapter = new MyAdapter(this);
        //
        total_num = 0;
        total_prices = 0;
        // 为ListView绑定数据
        listview_orderDetail = (ListView) findViewById(R.id.listview_orderDetail);
        listview_orderDetail.setAdapter(adapter);

        // 设置下单、结账、返回三个
	/*	if (new OrderDetailManager(MenuActivity.this).queryByState(0)) {
			button_order_submit.setVisibility(View.VISIBLE);
			button_order_pay.setVisibility(View.GONE);
		} else {
			button_order_submit.setVisibility(View.GONE);
			if (new OrderDetailManager(MenuActivity.this).queryByState(1)) {
				button_order_pay.setVisibility(View.VISIBLE);
			}else {
				button_order_pay.setVisibility(View.GONE);
			}
		}
		if (new OrderDetailManager(MenuActivity.this).queryByState(4)) {
			button_order_back.setVisibility(View.GONE);
		} else {
			button_order_back.setVisibility(View.VISIBLE);
		}*/

        // 绑定总订单价格、总订单数量
        int total[] = new OrderDetailManager(this).queryTotal();
        textview_total_prices.setText(total[0] + "元");
        textview_total_num.setText(total[1] + "");
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        public MyAdapter(Context context) {

            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /*
         * (non-Javadoc)
         *
         * @see android.widget.Adapter#getView(int, android.view.View,
         * android.view.ViewGroup)
         */
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row_listview_content, null);
            }
            // 给控件赋值
            ((TextView) convertView.findViewById(R.id.listview_textview_id)).setText((position + 1)
                    + "");
            ((TextView) convertView.findViewById(R.id.listview_textview_name)).setText(mData.get(
                    position).getName());
            ((TextView) convertView.findViewById(R.id.listview_textview_price)).setText(mData.get(
                    position).getPrice()
                    + "元/" + mData.get(position).getUnit());
            ((TextView) convertView.findViewById(R.id.listview_textview_num)).setText(mData
                    .get(position).getNum().toString());
            ((TextView) convertView.findViewById(R.id.listview_textview_prices)).setText((mData
                    .get(position).getPrice() * mData.get(position).getNum()) + "");
            ((TextView) convertView.findViewById(R.id.listview_textview_remark)).setText(mData.get(
                    position).getRemark());



            final Button button_add = ((Button) convertView.findViewById(R.id.button_add));
            // 已结账，不显示
            if( new OrderDetailManager(MenuActivity.this).queryState(mData.get(position).get_id())>=4 ){
                button_add.setVisibility(View.INVISIBLE);
            }
            button_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(new OrderDetailManager(MenuActivity.this).query(mData.get(position).getMenuID())&&0==new OrderDetailManager(MenuActivity.this).queryState(mData.get(position).get_id())){//已存在、状态为0
                        // 数量加1
                        new OrderDetailManager(MenuActivity.this).updateNum(mData.get(position).get_id(),1);
                    }else{
                        int id;
                        if((id=new OrderDetailManager(MenuActivity.this).query(mData.get(position).getMenuID(),0))>-1){
                            // 数量加1
                            new OrderDetailManager(MenuActivity.this).updateNum(id,1);
                        }else{
                            // 订单项加1
                            new OrderDetailManager(MenuActivity.this).addOrderItem(mData.get(position).getMenuID());
                        }
                    }
                    // 重新绑定数据
                    bindOrderDetails();

                }
            });
            final Button button_sub = ((Button) convertView.findViewById(R.id.button_sub));
            //小于2 、 已下单，不显示
            if( (mData.get(position).getNum()<2) || (0!=new OrderDetailManager(MenuActivity.this).queryState(mData.get(position).get_id()))){
                button_sub.setVisibility(View.INVISIBLE);
            }
            button_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 数量减1
                    new OrderDetailManager(MenuActivity.this).updateNum(mData.get(position).get_id(),-1);
                    // 重新绑定数据
                    bindOrderDetails();
                }
            });

            ImageView imageview_remark = ((ImageView) convertView.findViewById(R.id.listview_imageview_remark));
            imageview_remark.setBackgroundResource(android.R.drawable.ic_input_add);
            imageview_remark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 取得自定义View
//					LayoutInflater layoutInflater = LayoutInflater.from(MenuActivity.this);
//					final View order_remark_View = layoutInflater.inflate(R.layout.order_remark,
//							null);

//					Dialog alertDialog = new AlertDialog.Builder(MenuActivity.this)
//							.setTitle("给菜添加说明注释").setIcon(R.drawable.call)
//							.setView(order_remark_View)
//							.setPositiveButton("提交", new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//                                // 获取选中的单选按钮的值
//                                String radio_checkValue = ((RadioButton) order_remark_View
//                                        .findViewById(((RadioGroup) order_remark_View
//                                                .findViewById(R.id.radioGroup_Spicy))
//                                                .getCheckedRadioButtonId())).getText()
//                                        .toString();
//                                // 获取输入框中的值
//                                String editText_orderRemark = ((TextView) order_remark_View
//                                        .findViewById(R.id.editText_orderRemark)).getText()
//                                        .toString();
//                                // 向数据库修改备注信息
//                                new OrderDetailManager(OrderActivity.this).updateRemark(mData
//                                        .get(position).get_id(), radio_checkValue + ","
//                                        + editText_orderRemark);
//									// 重新绑定数据
//									bindOrderDetails();
//								}
//							}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//
//								}
//							}).create();
//					alertDialog.show();
                }
            });
            ImageView imageview_delete = ((ImageView) convertView
                    .findViewById(R.id.listview_imageview_delete));
            imageview_delete.setBackgroundResource(android.R.drawable.ic_delete);
            imageview_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                    builder.setTitle("删除订单项").setMessage("你确定要删除吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // 删除数据库中的订单项
                                    new OrderDetailManager(MenuActivity.this).delete(mData.get(
                                            position).get_id());
                                    // mData.remove(position);
                                    // notifyDataSetChanged();
                                    // 重新绑定数据
                                    bindOrderDetails();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            });
            imageview_remark.setVisibility(View.INVISIBLE);
            imageview_delete.setVisibility(View.INVISIBLE);
            String State = "";
            switch (mData.get(position).getState()) {
                case 0:
                    State = "待提交";
                    imageview_remark.setVisibility(View.VISIBLE);
                    imageview_delete.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    State = "已下单";
                    break;
                case 2:
                    State = "烹饪中";
                    break;
                case 3:
                    State = "出锅";
                    break;
                case 4:
                    State = "结账中";
                    break;
                case 5:
                    State = "已结账";
                    break;
                default:
                    State = "无";
                    break;
            }
            ((TextView) convertView.findViewById(R.id.listview_textview_state)).setText(State);

            return convertView;
        }
    }

    public class DateAdapter extends BaseAdapter {

        private Context context;
        /** 列表. */
        private List<Menu> lstDate;
        private List<TextView> lstDate_textview=new ArrayList<TextView>();
        /** 描述. */
        private TextView textview_menuDesc;

        // 每页显示的Item个数
        public static final int SIZE = 12;

        public DateAdapter(Context mContext, List<Menu> list, int page, TextView textview_menuDesc) {
            this.context = mContext;
            this.textview_menuDesc = textview_menuDesc;
            lstDate = new ArrayList<Menu>();
            int i = page * SIZE;
            int iEnd = i + SIZE;
            while ((i < list.size()) && (i < iEnd)) {
                lstDate.add(list.get(i));
                i++;
            }
        }

        @Override
        public int getCount() {
            return lstDate.size();
        }

        @Override
        public Object getItem(int position) {
            return lstDate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                // 实例化图片视图
                convertView = LayoutInflater.from(context).inflate(R.layout.item_menu, null);
                // 设置图片视图属性
                convertView.setPadding(8, 8, 8, 8);
            }
            /** 添加按钮、删除按钮. */
            /*	Button submitOrder_add;*/
            /** 菜品图片. */
            ImageView imageView;
            final TextView menuName,menuPrice,menuNum;
            //获取Menu
            final Menu menu = lstDate.get(position);

            // 实例化控件
            menuName = (TextView) convertView.findViewById(R.id.textview_menuName);
            menuPrice = (TextView) convertView.findViewById(R.id.textview_menuPrice);
            /*menuNum = (TextView) convertView.findViewById(R.id.textview_menuNum);*/
            /*submitOrder_add = (Button) convertView.findViewById(R.id.button_submitOrder_add);*/
            /*imageView = (ImageView) convertView.findViewById(R.id.imageview_menu);*/
		/*try {
			//获取图片
			imageView.setImageDrawable(Drawable.createFromStream(context.getAssets().open(menu.getPic()), null));
			//TODO 给菜谱图片绑定单击事件
		} catch (IOException e) {
			e.printStackTrace();
		}*/
            // 显示菜品描叙
		/*imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 按住事件发生后执行代码的区域
					textview_menuDesc.setVisibility(View.VISIBLE);
					//textview_menuDesc.setText(menu.getDescription());
					textview_menuDesc.setText("ni hao ");
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 松开事件发生后执行代码的区域
					textview_menuDesc.setVisibility(View.GONE);
				}
				return false;
			}
		});*/
            // 为TextView设置菜名
            menuName.setText(menu.getName());
            // 为TextView设置菜的价格
            //menuPrice.setText(menu.getPrice() + "元/" + menu.getUnit());
            menuPrice.setText("$ " + menu.getPrice() + "");
	/*	int num = new OrderDetailManager(context).queryNum(menu.get_id());
		if(num > 0 ){
			menuNum.setBackgroundResource(R.drawable.correct);
			submitOrder_add.setBackgroundResource(R.drawable.add_gray);
			submitOrder_add.setEnabled(false);
		}*/
            //添加到集合中
            /*lstDate_textview.add(menuNum);*/

            // 给下单按钮绑定单击事件
		/*submitOrder_add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					if(new OrderDetailManager(context).query(menu.get_id())){//已下单
						MyUtil.showToast(context, "此商品以下单...");
					}else{//未下单
						//加1
						new OrderDetailManager(context).addOrderItem(menu.get_id());
						//更新数量
						int num = new OrderDetailManager(context).queryNum(menu.get_id());
						lstDate_textview.get(position).setText(num > 0 ? "" + num : "");
						notifyDataSetChanged();
					}
				}
		});*/
            menuName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new OrderDetailManager(context).addOrderItem(menu.get_id());
                    bindOrderDetails();
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

    }
}

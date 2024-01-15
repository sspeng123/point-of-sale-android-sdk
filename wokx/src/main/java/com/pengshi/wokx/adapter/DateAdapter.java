package com.pengshi.wokx.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pengshi.wokx.R;
import com.pengshi.wokx.bean.*;
import com.pengshi.wokx.manager.OrderDetailManager;

import java.util.ArrayList;
import java.util.List;


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
        menuPrice.setText("$ "+menu.getPrice() + "" );
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
        menuName.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                new OrderDetailManager(context).addOrderItem(menu.get_id());

            }
        });
        return convertView;
    }

}
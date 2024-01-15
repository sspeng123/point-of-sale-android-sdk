package com.pengshi.wokx.ui;

import android.accounts.Account;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import org.apache.http.client.HttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.pengshi.wokx.R;
import com.pengshi.wokx.bean.Menu;
import com.pengshi.wokx.manager.MenuManager;
import com.pengshi.wokx.manager.MenuTypeManager;
import com.pengshi.wokx.manager.OrderDetailManager;
import com.pengshi.wokx.manager.OrderManager;
import com.pengshi.wokx.util.NetworkUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author shipeng
 * 1/11/24
 */
public class LodingActivity extends Activity {

    // 声明控件
    private ProgressBar loading_progress_bar;
    BroadcastReceiverHelper rhelper;
    //private SocketUtil client;
    private Context context = null;
    private Account mAccount;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_anim);
        this.context = this;
        // 判断网络是否可用
        // CheckNetworkConnection();

        // 开启服务
//		startService(new Intent("com.liuwang.meal.Socket"));

        loading_progress_bar = (ProgressBar) findViewById(R.id.loading_progress_bar);
		/*mAccount = CloverAccount.getAccount(this);
		if (mAccount == null) {
			return;
		}*/

        // loading_progress_bar.setProgress(20);

        // ProgressDialog mypDialog = new ProgressDialog(this);
        // // 实例化
        // mypDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // // 设置进度条风格，风格为长形，有刻度的
        // mypDialog.setTitle("同步数据");
        // // 设置ProgressDialog 标题
        // mypDialog.setMessage("正在同步服务器数据...");
        // // 设置ProgressDialog 提示信息
        // // mypDialog.setIcon(R.drawable.ic_launcher);
        // // 设置ProgressDialog 标题图标
        // mypDialog.setProgress(59);
        // // 设置ProgressDialog 进度条进度
        // // mypDialog.setButton("退出",
        // (android.content.DialogInterface.OnClickListener) this);
        // // 设置ProgressDialog 的一个Button
        // mypDialog.setIndeterminate(false);
        // // 设置ProgressDialog 的进度条是否不明确
        // mypDialog.setCancelable(true);
        // // 设置ProgressDialog 是否可以按退回按键取消
        // mypDialog.show();
        // // 让ProgressDialog显示

//		 //资源数据更新
//		try {
//			client = new SocketUtil("192.168.1.107",8898);
//		} catch (Exception e) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setMessage("服务器连接失败,请检查服务器是否开启...")
//			       .setCancelable(false)
//			       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//			           public void onClick(DialogInterface dialog, int id) {
//			        	   finish();
//			           }
//			       });
//			AlertDialog alert = builder.create();
//			alert.show();
//			return;
//		}

//		//TODO 服务器的当前时间
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Log.i("test", "返回的消息：" + client.sendMsg("0,synchronous,0,15,null"));
//				} catch (IOException e) {
//					MyUtil.showDialog(LodingActivity.this, "发送消息失败....");
//				}// /
//				client.closeSocket();
//			}
//		}).start();





        // TODO 字段数据更新
        // try {
        // client = new SocketUtil("192.168.1.107",8898);
        // } catch (Exception e) {
        // Toast.makeText(LoginActivity.this, "服务器连接失败,请检查服务器是否开启...",
        // 0).show();
        // }
        //
        // new Thread(new Runnable() {
        // @Override
        // public void run() {
        // Log.i("test","返回的消息："+client.sendMsg("0,login,[{account:account,password:password,table_no:table_no,customer_count:customer_count,machine_code:machine_code}]"));///
        // client.closeSocket();
        // }
        // }).start();
        queryWebService();


    }


    private void queryWebService() {
        new AsyncTask<Void, String, Void>() {

            @Override
            protected void onProgressUpdate(String... values) {
                String logString = values[0];
                //log(logString);
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Log.d("sql", "sql-------------------doInBackground");
                    publishProgress("Requesting auth token");
                    String baseUrl = "https://connect.squareup.com";
                    String authToken = "EAAAEa5cfkIKS-oP-uxqUTNGiSMBCek9Kvmi8USlEm28xZCRZDeBuP2s3gZ7T6ir";
					/*CloverAuth.AuthResult authResult = CloverAuth.authenticate(LodingActivity.this, mAccount);
					publishProgress("Successfully authenticated as " + mAccount + ".  authToken=" + authResult.authToken + ", authData=" + authResult.authData);
					authToken = authResult.authToken;*/
                    String merchants_id = "QHC20T9HAK922";
                    /*if (authResult.authToken != null && authResult.baseUrl != null) {*/
                    if(authToken != null){
                        if (CheckNetworkConnection()) {
                            Log.d("sql", "sql-------------------CheckNetworkConnection");
                            String getNameUri1 = "/v2/merchants";

//
                            String url = baseUrl + getNameUri1;
                            CustomOKHttpClient httpClient = CustomOKHttpClient.getHttpClient();

                            String jsonData = httpClient.get(url);
                            publishProgress("requesting merchant id using: " + url);

                            //CustomHttpClient httpClient = CustomHttpClient.getHttpClient();

                            Log.d("sql", "sql-------------------CheckNetworkConnection1");


                            //String result = httpClient.get(url);

                            JSONTokener jsonTokener = new JSONTokener(jsonData);
                            JSONObject root = (JSONObject) jsonTokener.nextValue();

                            String geLocationList = "/v2/locations";
                            String locationUrl = baseUrl + geLocationList;
                            String locationResult = httpClient.get(locationUrl);
                            JSONTokener locationsJson = new JSONTokener(locationResult);
                            JSONObject locationRoot = (JSONObject) locationsJson.nextValue();
                            JSONArray locations = locationRoot.getJSONArray("locations");
                            Log.d("sql", "sql-------------------elements" + locations.length());
                            if(locations.length() == 0)
                            {
                                return null;
                            }

                            JSONObject jsonObj = (JSONObject) locations.opt((locations.length()-1));
                            String _id = jsonObj.getString("id");
                            Log.d("sql", "sql-------------------elements" + _id);
//                            merchants_id = root.getString("merchantId");
//                            publishProgress("received merchant id: " + merchants_id);
//                            Log.d("sql", "sql-------------------merchants_id" + merchants_id);
//
//                            String getNameUri = "/v3/merchants/" + merchants_id + "/categories";
//                            String url1 = baseUrl + getNameUri + "?access_token=" + authToken;
//                            Log.d("sql", "sql-------------------url1:" + url1);
//
//                            publishProgress("requesting merchant id using: " + url1);
//                            String result1 = httpClient.get(url1);
//                            Log.d("sql", "sql-------------------result1" + result1);
//                            JSONTokener jsonTokener1 = new JSONTokener(result1);
//                            JSONObject root1 = (JSONObject) jsonTokener1.nextValue();
//
//                            JSONArray elementsList = root1.getJSONArray("elements");
//
//                            Log.d("sql", "sql-------------------elements" + elementsList.length());
//
                            String getCategoriesUri = "/v2/catalog/list?types=CATEGORY";
                            String getCategoriesurl = baseUrl + getCategoriesUri ;
                            String categoriesResult = httpClient.get(getCategoriesurl);
                            JSONTokener categoriesJson = new JSONTokener(categoriesResult);
                            JSONObject categoriesRoot = (JSONObject) categoriesJson.nextValue();
                            JSONArray objects = categoriesRoot.getJSONArray("objects");
                            Log.d("sql", "sql-------------------objects" + objects.length());

                            MenuTypeManager m = new MenuTypeManager(context);
                            m.delete();

                            for (int i = 0; i < objects.length(); i++) {
                                JSONObject categoriyobj = (JSONObject) objects.opt(i);
                                String _cid = categoriyobj.getString("id");
                                JSONObject category_data = categoriyobj.getJSONObject("category_data");
                                String _name = category_data.getString("name");
                                Log.d("sql", "sql-------------------categoriyobj" + _cid + "-----_name" + _name);
                                m.add(_cid, _name);
                            }

//                            for (int i = 0; i < elementsList.length(); i++) {
//                                JSONObject jsonObj = (JSONObject) elementsList.opt(i);
//                                String _id = jsonObj.getString("id");
//                                String _name = jsonObj.getString("name");
//                                Log.d("sql", "sql-------------------_id" + _id + "-----_name" + _name);
//                                m.add(_id, _name);
//                            }
//
                            String getItemsUri = "/v2/catalog/search-catalog-items";
                            String url2 = baseUrl + getItemsUri ;
                            JSONObject obj = new JSONObject();
                            JSONArray las = new JSONArray();
                            las.put("LT2TZWT72QE01");
                            obj.put("enabled_location_ids", las);
                            String result2 = httpClient.post(url2,obj);
                            JSONTokener jsonTokener2 = new JSONTokener(result2);
                            JSONObject root2 = (JSONObject) jsonTokener2.nextValue();
                            JSONArray elementsList1 = root2.getJSONArray("items");
                            List<Menu> lmenu = new ArrayList<Menu>();
                            for (int j = 0; j < elementsList1.length(); j++) {
                                JSONObject jsonmenuObj = (JSONObject) elementsList1.opt(j);
                                Menu menu = new Menu();
                                menu.setId(jsonmenuObj.getString("id"));
                                menu.setHidden(false);
                                JSONObject menudataObj = jsonmenuObj.getJSONObject("item_data");
                                menu.setName(menudataObj.getString("name"));
                                Log.d("loading", "doInBackground:menu name "+menudataObj.getString("name"));
//                                if (jsonObj.getString("code") != null) {
//                                    menu.setCode(jsonObj.getString("code"));
//                                }


                                menu.setPriceType("FIXED");
//                                menu.setPrice((jsonObj.getDouble("price") / 100));
                                menu.setPrice(1.00);
                                menu.setTaxRates_id("1");
                                menu.setTaxRates(0.875);
                                menu.setCATEGORIES_ID("W45WW2KFJTP64P3VRGPTXIYA");
                                menu.setModifierGroups_id("1");
                                menu.setModifierGroups_Name("zhongg");
//                                JSONObject jsonob = jsonObj.getJSONObject("taxRates");
//                                if (jsonob.getJSONArray("elements").length() > 0) {
//                                    JSONObject jsonTax = (JSONObject) jsonob.getJSONArray("elements").opt(0);
//                                    menu.setTaxRates_id(jsonTax.getString("id"));
//                                    menu.setTaxRates(jsonTax.getDouble("rate") / 10000);
//                                }
//                                JSONObject jsoncate = jsonObj.getJSONObject("categories");
//                                if (jsoncate.getJSONArray("elements").length() > 0) {
//                                    JSONObject jsoncategories = (JSONObject) jsoncate.getJSONArray("elements").opt(0);
//                                    menu.setCATEGORIES_ID(jsoncategories.getString("id"));
//                                }
//                                JSONObject jsonmodifierGroups = jsonObj.getJSONObject("modifierGroups");
//                                if (jsonmodifierGroups.getJSONArray("elements").length() > 0) {
//                                    JSONObject jsonmodifierGroupselements = (JSONObject) jsonmodifierGroups.getJSONArray("elements").opt(0);
//                                    menu.setModifierGroups_id(jsonmodifierGroupselements.getString("id"));
//                                    menu.setModifierGroups_Name(jsonmodifierGroupselements.getString("name"));
//                                }
                                lmenu.add(menu);

                            }
//                            String getItemsUri = "/v3/merchants/" + merchants_id + "/items?expand=categories%2CtaxRates%2CmodifierGroups";
//                            String url2 = baseUrl + getItemsUri + "&access_token=" + authToken;
//                            Log.d("sql", "sql-------------------url2:" + url2);
//
//                            String result2 = httpClient.get(url2);
//                            Log.d("sql", "sql-------------------result1" + result2);
//                            JSONTokener jsonTokener2 = new JSONTokener(result2);
//                            JSONObject root2 = (JSONObject) jsonTokener2.nextValue();
//                            JSONArray elementsList1 = root2.getJSONArray("elements");
//
//                            List<Menu> lmenu = new ArrayList<Menu>();
//
//                            for (int j = 0; j < elementsList1.length(); j++) {
//                                JSONObject jsonObj = (JSONObject) elementsList1.opt(j);
//                                Menu menu = new Menu();
//                                menu.setId(jsonObj.getString("id"));
//                                menu.setHidden(jsonObj.getBoolean("hidden"));
//                                menu.setName(jsonObj.getString("name"));
//
//                                if (jsonObj.getString("code") != null) {
//                                    menu.setCode(jsonObj.getString("code"));
//                                }
//                                menu.setPriceType(jsonObj.getString("priceType"));
//                                menu.setPrice((jsonObj.getDouble("price") / 100));
//                                JSONObject jsonob = jsonObj.getJSONObject("taxRates");
//                                if (jsonob.getJSONArray("elements").length() > 0) {
//                                    JSONObject jsonTax = (JSONObject) jsonob.getJSONArray("elements").opt(0);
//                                    menu.setTaxRates_id(jsonTax.getString("id"));
//                                    menu.setTaxRates(jsonTax.getDouble("rate") / 10000);
//                                }
//                                JSONObject jsoncate = jsonObj.getJSONObject("categories");
//                                if (jsoncate.getJSONArray("elements").length() > 0) {
//                                    JSONObject jsoncategories = (JSONObject) jsoncate.getJSONArray("elements").opt(0);
//                                    menu.setCATEGORIES_ID(jsoncategories.getString("id"));
//                                }
//                                JSONObject jsonmodifierGroups = jsonObj.getJSONObject("modifierGroups");
//                                if (jsonmodifierGroups.getJSONArray("elements").length() > 0) {
//                                    JSONObject jsonmodifierGroupselements = (JSONObject) jsonmodifierGroups.getJSONArray("elements").opt(0);
//                                    menu.setModifierGroups_id(jsonmodifierGroupselements.getString("id"));
//                                    menu.setModifierGroups_Name(jsonmodifierGroupselements.getString("name"));
//                                }
//                                lmenu.add(menu);
//
//                            }
//                            Log.d("sql", "sql-------------------lmenu.size()" + lmenu.size());
                            MenuManager mm = new MenuManager(context);
                            mm.add(lmenu);

                            new OrderDetailManager(context).truncate();
                            new OrderManager(context).truncate();
                        }
                    }
                } catch (Exception e) {
                    Log.d("sql", "sql-------------------getMessage"+e.getMessage());
                    publishProgress("Error retrieving merchant info from server" + e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //mButton.setEnabled(true);
                Log.d("sql", "sql-------------------onPostExecute");
                Intent intent = new Intent(LodingActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        }.execute();
    }

    // 判断网络是否可用
    private boolean CheckNetworkConnection() {
        if (!NetworkUtil.isNetworkConnected(this)) {
            return false;
        }
        return  true;
    }

    class BroadcastReceiverHelper extends BroadcastReceiver {

        NotificationManager mn = null;
        Notification notification = null;
        Context ct = null;
        BroadcastReceiverHelper receiver;

        public BroadcastReceiverHelper(Context c) {
            ct = c;
            receiver = this;
        }

        // 注册
        public void registerAction(String action) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(action);
            ct.registerReceiver(receiver, filter , RECEIVER_EXPORTED);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("msg");
            if (intent.getAction().equals("com.liuwang.sendMsg_lodingActivity")) {

                //MyUtil.showDialog(LodingActivity.this, "onReceive:" + msg);
            }
        }
    }

    @Override
    protected void onStart() {
        // 注册广播接收器

        rhelper = new BroadcastReceiverHelper(this);
        rhelper.registerAction("com.liuwang.sendMsg_lodingActivity");
        super.onStart();
    }

    @Override
    protected void onStop() {
        // 取消广播接收器
        unregisterReceiver(rhelper);
        super.onStop();
    }

    static class CustomOKHttpClient extends OkHttpClient {
        private static final MediaType MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
        public static CustomOKHttpClient getHttpClient() {
            CustomOKHttpClient httpClient = new CustomOKHttpClient();

            return httpClient;
        }

        public String post(String url, JSONObject jsonObject) throws IOException, Exception {
            RequestBody body = RequestBody.create(MEDIA_TYPE,jsonObject.toString());
            Request request = new Request.Builder().url(url)
                    .addHeader("Square-Version","2023-12-13")
                    .addHeader("Authorization","Bearer EAAAEa5cfkIKS-oP-uxqUTNGiSMBCek9Kvmi8USlEm28xZCRZDeBuP2s3gZ7T6ir")
                    .addHeader("Content-Type","application/json")
                    .post(body)
                    .build();
            String result;

            Response response = null;

            response = newCall(request).execute();
            int statusCode =  response.code();
            if (statusCode == HttpStatus.SC_OK) {
                ResponseBody entity = response.body();
                if (entity != null) {
                    result = response.body().string();
                } else {
                    throw new Exception("Received empty body from HTTP response");
                }
            } else {
                throw new Exception("Received non-OK status from server: " + response.code());
            }
            return result;
        }

        public String get(String url) throws IOException, Exception {
            String result;
            Request request = new Request.Builder().url(url)
                    .addHeader("Square-Version","2023-12-13")
                    .addHeader("Authorization","Bearer EAAAEa5cfkIKS-oP-uxqUTNGiSMBCek9Kvmi8USlEm28xZCRZDeBuP2s3gZ7T6ir")
                    .addHeader("Content-Type","application/json")
                    .build();
            Response response = null;

            response = newCall(request).execute();


           // HttpResponse response = execute(request);
            int statusCode =  response.code();
            if (statusCode == HttpStatus.SC_OK) {
                ResponseBody entity = response.body();
                //HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = response.body().string();
                    //result = EntityUtils.toString(entity);
                } else {
                    throw new Exception("Received empty body from HTTP response");
                }
            } else {
                throw new Exception("Received non-OK status from server: " + response.code());
            }
            return result;
        }

    }

    static class CustomHttpClient extends DefaultHttpClient {
        private static final int CONNECT_TIMEOUT = 60000;
        private static final int READ_TIMEOUT = 60000;
        private static final int MAX_TOTAL_CONNECTIONS = 5;
        private static final int MAX_CONNECTIONS_PER_ROUTE = 3;
        private static final int SOCKET_BUFFER_SIZE = 8192;
        private static final boolean FOLLOW_REDIRECT = false;
        private static final boolean STALE_CHECKING_ENABLED = true;
        private static final String CHARSET = HTTP.UTF_8;
        private static final HttpVersion HTTP_VERSION = HttpVersion.HTTP_1_1;
        private static final String USER_AGENT = "CustomHttpClient"; // + version

        public static CustomHttpClient getHttpClient() {
            CustomHttpClient httpClient = new CustomHttpClient();
            final HttpParams params = httpClient.getParams();

            HttpProtocolParams.setUserAgent(params, USER_AGENT);
            HttpProtocolParams.setContentCharset(params, CHARSET);
            HttpProtocolParams.setVersion(params, HTTP_VERSION);
            HttpClientParams.setRedirecting(params, FOLLOW_REDIRECT);
            HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, READ_TIMEOUT);
            HttpConnectionParams.setSocketBufferSize(params, SOCKET_BUFFER_SIZE);
            HttpConnectionParams.setStaleCheckingEnabled(params, STALE_CHECKING_ENABLED);
            ConnManagerParams.setTimeout(params, CONNECT_TIMEOUT);
            ConnManagerParams.setMaxTotalConnections(params, MAX_TOTAL_CONNECTIONS);
            ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(MAX_CONNECTIONS_PER_ROUTE));

            return httpClient;
        }

        public String get(String url) throws IOException, HttpException {
            String result;
            HttpGet request = new HttpGet(url);
            request.setHeader("Square-Version","2023-12-13");
            request.setHeader("Authorization","Bearer EAAAEa5cfkIKS-oP-uxqUTNGiSMBCek9Kvmi8USlEm28xZCRZDeBuP2s3gZ7T6ir");
            request.setHeader("Content-Type","application/json");
            HttpResponse response = execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                } else {
                    throw new HttpException("Received empty body from HTTP response");
                }
            } else {
                throw new HttpException("Received non-OK status from server: " + response.getStatusLine());
            }
            return result;
        }

        @SuppressWarnings("unused")
        public String post(String url, String body) throws IOException, HttpException {
            String result;
            HttpPost request = new HttpPost(url);
            HttpEntity bodyEntity = new StringEntity(body);
            request.setEntity(bodyEntity);
            HttpResponse response = execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                } else {
                    throw new HttpException("Received empty body from HTTP response");
                }
            } else {
                throw new HttpException("Received non-OK status from server: " + response.getStatusLine());
            }
            return result;
        }
    }
}

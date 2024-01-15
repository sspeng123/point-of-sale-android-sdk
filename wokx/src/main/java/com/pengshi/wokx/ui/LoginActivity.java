package com.pengshi.wokx.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.pengshi.wokx.R;
import sdk.android.baidulibrary.com.googlelibrary.bean.Employer;
import com.pengshi.wokx.db.SubOrderItem;
import com.pengshi.android.sdk.abcpos.manager.ClockManager;
import com.pengshi.android.sdk.abcpos.manager.EmployerManager;
import com.pengshi.android.sdk.abcpos.util.AppUtils;
import com.pengshi.android.sdk.abcpos.util.MyApplication;
import com.pengshi.android.sdk.abcpos.util.MyUtil;
import com.pengshi.android.sdk.abcpos.util.PSException;
import com.pengshi.android.sdk.abcpos.util.SPUtils;
import com.pengshi.android.sdk.abcpos.util.ScreenUtils;

import java.util.Locale;

/**
 * Created by karlp on 5/27/2017.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,SurfaceHolder.Callback {
    SurfaceHolder surfaceHolder = null;
    private EditText editText ;
    private TextView txtappName,txtappVersion;
    private Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,buttondot,buttondel,buttonenter,buttonClock;
    private ImageView btnExit,btnSettings,btnEnglish,btnChinese;
    private AlertDialog mDialog;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.login);
    }
    @Override
    protected void findViews(){
        editText   = (EditText) findViewById(R.id.login_pin);
        button0    = (Button)findViewById(R.id.button0);
        button1    = (Button)findViewById(R.id.button1);
        button2    = (Button)findViewById(R.id.button2);
        button3    = (Button)findViewById(R.id.button3);
        button4    = (Button)findViewById(R.id.button4);
        button5    = (Button)findViewById(R.id.button5);
        button6    = (Button)findViewById(R.id.button6);
        button7    = (Button)findViewById(R.id.button7);
        button8    = (Button)findViewById(R.id.button8);
        button9    = (Button)findViewById(R.id.button9);
        buttondel  = (Button)findViewById(R.id.button_delete);
        buttonenter=(Button)findViewById(R.id.button_yes);
        buttondot  = (Button)findViewById(R.id.buttonX);
        btnExit    = (ImageView)findViewById(R.id.btnExit_Go);
        /*txtappName = (TextView)findViewById(R.id.app_name);
        txtappVersion = (TextView)findViewById(R.id.app_versions);*/
        buttonClock   =  (Button)findViewById(R.id.buttonClock);
        btnEnglish    =   (ImageView)findViewById(R.id.btnEnglish);
        btnChinese    =   (ImageView)findViewById(R.id.btnChinese);
        SurfaceView surfaceView = (SurfaceView)findViewById(R.id.sview);
        surfaceHolder  = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

       /* Button b = (Button)findViewById(R.id.button_yes);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });*/


    }
    @Override
    protected void initViews(){
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttondel.setOnClickListener(this);
        buttonenter.setOnClickListener(this);
        buttondot.setOnClickListener(this);
        btnExit.setOnClickListener(this);
     /*   txtappName.setText(AppUtils.getAppName(this ));
        txtappVersion.setText("Version: "+ AppUtils.getVersionName(this));*/
        buttonClock.setOnClickListener(this);
        btnChinese.setOnClickListener(this);
        btnEnglish.setOnClickListener(this);



    }



    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawBackGround(surfaceHolder);
    }

    private void drawBackGround(SurfaceHolder holder) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float  mViewWidth = displayMetrics.widthPixels;

        //float  mViewHeight = displayMetrics.heightPixels;
        float  mViewHeight =  ScreenUtils.getDpi(this);;
        Canvas canvas = holder.lockCanvas();
        Bitmap bitmap =  getDrawBitmap(this,mViewWidth,mViewHeight);
        canvas.drawBitmap(bitmap, 0, 0, null);
        bitmap.recycle();
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    private Bitmap getDrawBitmap(Context context, float width, float height) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        Bitmap resultBitmap = MyUtil.zoomImage(bitmap, width, height);
        return resultBitmap;
    }

    public void alert(int message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.alert);
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editText.setText("");
                dialogInterface.dismiss();
            }
        });
        builder.setCancelable(true);
        mDialog=builder.create();

        /*mDialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mDialog.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        //布局位于状态栏下方
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        //全屏
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        //隐藏导航栏
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                if (Build.VERSION.SDK_INT >= 19) {
                    uiOptions |= 0x00001000;
                } else {
                    uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                }
                mDialog.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
            }
        });*/
        mDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonClock:
                try{
                    int i =  new ClockManager(this).checkClock(editText.getText().toString());
                    if(i == 1){
                        alert(R.string.Clockin);
                    }else {
                        alert(R.string.Clockout);
                    }
                }catch (Exception ex){
                    if(ex instanceof PSException){
                        alert(Integer.parseInt(ex.getMessage()));
                    }
                }
                break;
            case R.id.button_yes:
                try {
                    if (editText.getText().toString().equals("")) {
                        alert(R.string.Password1);
                        break;
                    }

                    Employer employer = new EmployerManager(this).queryByPin(editText.getText().toString());
                    if (employer == null) {
                        alert(R.string.Password);
                    } if(!employer.isActivity()){
                        alert(R.string.Active);
                    } else{
                        SPUtils.setUserMsg(this, employer.get_id() + "");
                        SPUtils.setNameMsg(this, employer.getFirstName() + " " +  employer.getLastName());
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception ex){
                    Log.d("sss", "onClick: " + ex.getMessage().toString());
                }
                break;
            case R.id.button0:
                editText.setText(editText.getText() + "0");
                break;
            case R.id.button1:
                editText.setText(editText.getText() + "1");
                break;
            case R.id.button2:
                editText.setText(editText.getText() + "2");
                break;
            case R.id.button3:
                editText.setText(editText.getText() + "3");
                break;
            case R.id.button4:
                editText.setText(editText.getText() + "4");
                break;
            case R.id.button5:
                editText.setText(editText.getText() + "5");
                break;
            case R.id.button6:
                editText.setText(editText.getText() + "6");
                break;
            case R.id.button7:
                editText.setText(editText.getText() + "7");
                break;
            case R.id.button8:
                editText.setText(editText.getText() + "8");
                break;
            case R.id.button9:
                editText.setText(editText.getText() + "9");
                break;
            case R.id.button_delete:
                if(editText.getText().length() > 0) {
                    editText.setText(editText.getText().subSequence(0, editText.getText().length() - 1));
                }
                break;
            case R.id.btnExit_Go:
                MyApplication.getInstance().exit();
                //finish();
                break;
            case  R.id.buttonX:
                editText.setText("");
                break;
            case R.id.btnChinese:

                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration config = res.getConfiguration();
                // 应用用户选择语言
                config.locale = Locale.SIMPLIFIED_CHINESE;
                res.updateConfiguration(config, dm);
                Intent refresh = new Intent(this, LoginActivity.class);
                startActivity(refresh);
                finish();
                break;
            case R.id.btnEnglish:

                DisplayMetrics dm1 = res.getDisplayMetrics();
                Configuration config1 = res.getConfiguration();
                // 应用用户选择语言
                config1.locale = Locale.ENGLISH;
                res.updateConfiguration(config1, dm1);
                Intent refresh1 = new Intent(this, LoginActivity.class);
                startActivity(refresh1);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

package com.smartcourse.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcourse.MyApplication;
import com.example.smartcourse.R;
import com.smartcourse.db.DBHelper;
import com.smartcourse.net.HttpRequestManager;
import com.smartcourse.net.HttpRequestTask;
import com.smartcourse.net.HttpTaskListenner;
import com.smartcourse.parser.ClassTbParser;
import com.smartcourse.view.TitleBarView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class ConfirmActivity extends Activity implements View.OnClickListener {
    private TextView area, depart, pro, grade, mclass;
    private Button submit;
    private TitleBarView titleBarView;
    private String[] params;
    private NameValuePair nvDepart, nvPro, nvGrade, nvClass;
    private HttpRequestTask checkHttpRequestTask;
    private HttpRequestTask loginHttpRequestTask;
    private ProgressDialog dialog;
    private static final String loginUrl = "http://pkxt.cuit.edu.cn/showfunction.asp";
    private static final String checkUrl = "http://pkxt.cuit.edu.cn/showclasstb.asp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        area = (TextView) findViewById(R.id.area);
        depart = (TextView) findViewById(R.id.depart);
        grade = (TextView) findViewById(R.id.grade);
        pro = (TextView) findViewById(R.id.pro);
        mclass = (TextView) findViewById(R.id.myclass);
        submit = (Button) findViewById(R.id.submit);
        titleBarView = (TitleBarView) findViewById(R.id.id_add_title);

        titleBarView.setTitle("添加课程");
        titleBarView.setLeftImageOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String[] params = getIntent().getStringArrayExtra("params");
        if (params != null) {
            this.params = params;
            area.setText(params[0]);
            depart.setText(params[1]);
            pro.setText(params[2]);
            grade.setText(params[3]);
            mclass.setText(params[4]);
            nvDepart = new BasicNameValuePair("depart", params[1]);
            nvPro = new BasicNameValuePair("pro", params[2]);
            nvGrade = new BasicNameValuePair("grade", params[3]);
            nvClass = new BasicNameValuePair("class", params[4]);
        }

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ContentValues values = new ContentValues();
        values.put("depart", params[1]);
        values.put("pro", params[2]);
        values.put("grade", params[3]);
        values.put("class", params[4]);

        DBHelper.getInstance(MyApplication.getMyApplication()).delAll(DBHelper.USER_TBL_NAME);
        DBHelper.getInstance(MyApplication.getMyApplication()).insert(values, DBHelper.USER_TBL_NAME);
        doCheck();
    }

    private void doCheck() {
        dialog = ProgressDialog.show(ConfirmActivity.this, "正在获取", "正在拼命加载课程信息，请稍等...");
        loginHttpRequestTask = new HttpRequestTask(loginUrl, new HttpTaskListenner() {

            @Override
            public void onSuccess(String result) {
                // TODO Auto-generated method stub
                if (nvDepart != null && nvPro != null && nvGrade != null && nvClass != null) {
                    doSubmit();
                } else {
                    Toast.makeText(ConfirmActivity.this, "请完善需要查询课表的信息", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed() {
                // TODO Auto-generated method stub
                ConfirmActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "加载课程表失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        loginHttpRequestTask.addParams("user", "guest");
        loginHttpRequestTask.addParams("passwd", "guest");
        HttpRequestManager.request(loginHttpRequestTask);

    }

    public void doSubmit() {
        checkHttpRequestTask = new HttpRequestTask(checkUrl, new HttpTaskListenner() {

            @Override
            public void onSuccess(String result) {
                // TODO Auto-generated method stub
                String classtbid = result.substring(result.indexOf("?id="), result.indexOf("&mode="));
                String classTbUrl = "http://pkxt.cuit.edu.cn/show_sub_table.asp" + classtbid + "&mode=1";
                Log.d("url", classTbUrl);
                HttpRequestTask catchCourse = new HttpRequestTask(classTbUrl, new HttpTaskListenner() {

                    @Override
                    public void onSuccess(String result) {
                        // TODO Auto-generated method stub
                        ClassTbParser parser = new ClassTbParser(getApplicationContext());
                        parser.parse(result);
                        dialog.dismiss();

                        Intent intent = new Intent(ConfirmActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed() {
                        // TODO Auto-generated method stub
                        ConfirmActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "加载课程表失败", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });

                    }
                });
                HttpRequestManager.request(catchCourse);

            }

            @Override
            public void onFailed() {
                // TODO Auto-generated method stub

            }
        });
        checkHttpRequestTask.addParams("Submit", "查询");
        checkHttpRequestTask.addParams("mode", "1");
        checkHttpRequestTask.addNameValuePair(nvClass);
        checkHttpRequestTask.addNameValuePair(nvDepart);
        checkHttpRequestTask.addNameValuePair(nvGrade);
        checkHttpRequestTask.addNameValuePair(nvPro);
        HttpRequestManager.request(checkHttpRequestTask);
    }

}

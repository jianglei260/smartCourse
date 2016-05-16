package com.smartcourse.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.smartcourse.AppManager;
import com.example.smartcourse.MyApplication;
import com.example.smartcourse.R;
import com.example.smartcourse.R.array;
import com.example.smartcourse.R.id;
import com.example.smartcourse.R.layout;
import com.smartcourse.db.DBHelper;
import com.smartcourse.net.HttpRequestManager;
import com.smartcourse.net.HttpRequestTask;
import com.smartcourse.net.HttpTaskListenner;
import com.smartcourse.parser.ClassTbParser;
import com.smartcourse.view.TitleBarView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddCourse extends Activity {
    private static final String loginUrl = "http://pkxt.cuit.edu.cn/showfunction.asp";
    ;
    private static final String checkUrl = "http://pkxt.cuit.edu.cn/showclasstb.asp";
    private TextView tv;
    private Button submit;
    private Spinner area, depart, pro, grade, mclass;
    private TitleBarView titleBarView;
    private int areaId = 1;
    private String[] departItems, proItems, gradeItems, mclassItems;
    private List<String> departList, proList;
    private ArrayAdapter<String> areaAdapter, departAdapter, proAdapter, gradeAdapter, mclassAdapter;
    private NameValuePair nvDepart, nvPro, nvGrade, nvClass;

    private HttpRequestTask checkHttpRequestTask;
    private HttpRequestTask loginHttpRequestTask;

    private ProgressDialog dialog;
    private ContentValues values;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcourse);

        String[] areaItems = getResources().getStringArray(R.array.area);
        String[] gradeItems = getResources().getStringArray(R.array.grade);
        String[] mclassItems = getResources().getStringArray(R.array.mclass);

        area = (Spinner) findViewById(R.id.area);
        depart = (Spinner) findViewById(R.id.depart);
        grade = (Spinner) findViewById(R.id.grade);
        pro = (Spinner) findViewById(R.id.pro);
        mclass = (Spinner) findViewById(R.id.myclass);
        submit = (Button) findViewById(R.id.submit);
        titleBarView = (TitleBarView) findViewById(id.id_add_title);

        titleBarView.setTitle("添加课程");
        titleBarView.setRightImageVisible(false);
        departList = new ArrayList<String>();
        proList = new ArrayList<String>();

        titleBarView.setLeftImageVisible(true);
        titleBarView.setLeftImageOnClick(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCourse.super.onBackPressed();
            }
        });
        areaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areaItems);
        area.setAdapter(areaAdapter);
        gradeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gradeItems);
        grade.setAdapter(gradeAdapter);
        mclassAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mclassItems);
        mclass.setAdapter(mclassAdapter);

        loginHttpRequestTask = new HttpRequestTask(loginUrl, new HttpTaskListenner() {

            @Override
            public void onSuccess(String result) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFailed() {
                // TODO Auto-generated method stub
                AddCourse.this.runOnUiThread(new Runnable() {
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

        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (nvDepart != null && nvPro != null && nvGrade != null && nvClass != null) {
                    dialog = ProgressDialog.show(AddCourse.this, "正在获取", "正在拼命加载课程信息，请稍等...");
                    doSubmit();
                } else {
                    Toast.makeText(AddCourse.this, "请完善需要查询课表的信息", Toast.LENGTH_SHORT).show();
                }
            }
        });

        area.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                updateDepartSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        depart.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                updateProSpinner(position);
                String departInfo = parent.getItemAtPosition(position).toString();
                nvDepart = new BasicNameValuePair("depart", departInfo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        pro.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String proInfo = parent.getItemAtPosition(position).toString();
                nvPro = new BasicNameValuePair("pro", proInfo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        grade.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String gradeInfo = parent.getItemAtPosition(position).toString();
                nvGrade = new BasicNameValuePair("grade", gradeInfo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        mclass.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String classInfo = parent.getItemAtPosition(position).toString();
                nvClass = new BasicNameValuePair("class", classInfo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

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
                        DBHelper.getInstance(MyApplication.getMyApplication()).delAll(DBHelper.USER_TBL_NAME);
                        DBHelper.getInstance(MyApplication.getMyApplication()).insert(values, DBHelper.USER_TBL_NAME);
                        parser.parse(result);
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setClass(AddCourse.this, MainActivity.class);
                        startActivity(intent);
                        AddCourse.this.finish();
                    }

                    @Override
                    public void onFailed() {
                        // TODO Auto-generated method stub
                        AddCourse.this.runOnUiThread(new Runnable() {
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
        values = new ContentValues();
        values.put("depart", nvDepart.getValue());
        values.put("pro", nvPro.getValue());
        values.put("grade", nvGrade.getValue());
        values.put("class", nvClass.getValue());
        HttpRequestManager.request(checkHttpRequestTask);
    }

    private void updateDepartSpinner(int id) {
        if (id == 0) {
            departItems = getResources().getStringArray(R.array.depart_native);
            departAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departItems);
            depart.setAdapter(departAdapter);
        }
        if (id == 1) {
            departItems = getResources().getStringArray(R.array.depart);
            departAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, departItems);
            depart.setAdapter(departAdapter);
        }
    }

    private void updateProSpinner(int id) {
        proAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, proList);
        pro.setAdapter(proAdapter);
        NativePro nativePro = new NativePro();
        switch (id) {
            case 0:
                proList.clear();
                for (int i = 0; i < nativePro.str0.length; i++) {
                    proList.add(nativePro.str0[i]);
                }
                break;
            case 1:
                proList.clear();
                for (int i = 0; i < nativePro.str1.length; i++) {
                    proList.add(nativePro.str1[i]);
                }
                break;
            case 2:
                proList.clear();
                for (int i = 0; i < nativePro.str2.length; i++) {
                    proList.add(nativePro.str2[i]);
                }
                break;
            case 3:
                proList.clear();
                for (int i = 0; i < nativePro.str3.length; i++) {
                    proList.add(nativePro.str3[i]);
                }
                break;
            case 4:
                proList.clear();
                for (int i = 0; i < nativePro.str4.length; i++) {
                    proList.add(nativePro.str4[i]);
                }
                break;
            case 5:
                proList.clear();
                for (int i = 0; i < nativePro.str5.length; i++) {
                    proList.add(nativePro.str5[i]);
                }
                break;
            case 6:
                proList.clear();
                for (int i = 0; i < nativePro.str6.length; i++) {
                    proList.add(nativePro.str6[i]);
                }
                break;
            case 7:
                proList.clear();
                for (int i = 0; i < nativePro.str7.length; i++) {
                    proList.add(nativePro.str7[i]);
                }
                break;
            case 8:
                proList.clear();
                for (int i = 0; i < nativePro.str8.length; i++) {
                    proList.add(nativePro.str8[i]);
                }
                break;
            case 9:
                proList.clear();
                for (int i = 0; i < nativePro.str9.length; i++) {
                    proList.add(nativePro.str9[i]);
                }
                break;
            case 10:
                proList.clear();
                for (int i = 0; i < nativePro.str10.length; i++) {
                    proList.add(nativePro.str10[i]);
                }
                break;
            case 11:
                proList.clear();
                for (int i = 0; i < nativePro.str11.length; i++) {
                    proList.add(nativePro.str11[i]);
                }
                break;
            case 12:
                proList.clear();
                for (int i = 0; i < nativePro.str12.length; i++) {
                    proList.add(nativePro.str12[i]);
                }
                break;
            case 13:
                proList.clear();
                for (int i = 0; i < nativePro.str13.length; i++) {
                    proList.add(nativePro.str13[i]);
                }
                break;
            case 14:
                proList.clear();
                for (int i = 0; i < nativePro.str14.length; i++) {
                    proList.add(nativePro.str14[i]);
                }
                break;
        }
    }
}

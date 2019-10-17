package co.id.gmedia.travelocscanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import co.id.gmedia.travelocscanner.utils.APIvolley;
import co.id.gmedia.travelocscanner.utils.AppSharedPreferences;
import co.id.gmedia.travelocscanner.utils.URL;

public class ActivityLogin extends AppCompatActivity {
    EditText pswd,usrusr;
    TextView sup,btnlog;
    private Context context;
    private static String TAG = "ActivityLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        if
        (AppSharedPreferences.isLoggedIn(this)) {
            startActivity(new Intent(context, ActivityScan2.class));
            finish();
        }

        initUI();
        initEvent();
        initData();
    }

    private void initUI() {

        btnlog = (TextView) findViewById(R.id.btnlog);
        usrusr= (EditText) findViewById(R.id.usrusr);
        pswd = (EditText) findViewById(R.id.pswrdd);
    }


    private void initEvent() {

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();

            }
        });

    }

    private void initData() {

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("username", usrusr.getText().toString());
            jsonObject.put("password", pswd.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new APIvolley(context, jsonObject, "POST", URL.dologin, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject response = new JSONObject(result);

                    //2. Pastikan response 200 dari metadata
                    String message = response.getJSONObject("metadata").getString("message");
                    String status = response.getJSONObject("metadata").getString("status");

                    if(status.equals("200")){

                        String token = response.getJSONObject("response").getString("token");
                        AppSharedPreferences.Login(context,token);
                        startActivity(new Intent(ActivityLogin.this, ActivityScan2.class));
                        finish();
                        Log.d(TAG, "token " + token);
                        Log.d(TAG, "Login Berhasil" +message);
                        Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.d(TAG, "Login Gagal" +message);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(context, "Login gagal", Toast.LENGTH_SHORT).show();

            }
        }) ;

    }


}

package co.id.gmedia.travelocscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import co.id.gmedia.travelocscanner.utils.APIvolley;
import co.id.gmedia.travelocscanner.utils.URL;

public class MainActivity extends AppCompatActivity {

    private EditText edtName, editPass;
    private Button btnsimpan;
    private Context context;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        initUI();
        initEvent();

    }

    private void initUI() {

        edtName = (EditText) findViewById(R.id.editTextName);
        editPass= (EditText) findViewById(R.id.editTextpassword);
        btnsimpan= (Button) findViewById(R.id.buttonAdd);
    }



    private void initEvent() {
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initData();
                startActivity(new Intent(getApplicationContext(),ActivityScan2.class));
            }
        });
    }

    private void initData() {

        JSONObject jbody=new JSONObject();
        try {
            jbody.put("username", edtName.getText().toString());
            jbody.put("password", editPass.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }



        new APIvolley(context, jbody, "POST", URL.dotransaksi, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                /*{
                    "response": {
                            "id": "2",
                            "nik": "897492",
                            "nama": "Promo",
                            "username": "promo",
                            "no_telp": "234567",
                            "email": "123@gmail.com",
                            "level": "1",
                            "ket_level": "Sales Promo",
                            "insert_at": "2019-09-09 14:20:01",
                            "fcm_id": "",
                            "foto_profil": "http://localhost/gmedia/yia/assets/uploads/user/kjdklasjd.png"
                },
                    "metadata": {
                            "status": 200,
                            "message": "Berhasil"
                }
                }*/

                // 1. Ubah ke JSON
                try {
                    JSONObject response = new JSONObject(result);

                    //2. Pastikan response 200 dari metadata
                    String status = response.getJSONObject("metadata").getString("status");

                        if(status.equals("200")){

                        String nama = response.getJSONObject("response").getString("nama");

                        Log.d(TAG, "onSuccess: " + nama);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {

            }
        });
    }


}

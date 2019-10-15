package co.id.gmedia.travelocscanner;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import co.id.gmedia.travelocscanner.utils.APIvolley;
import co.id.gmedia.travelocscanner.utils.URL;

public class ActivityTampilDataBox extends AppCompatActivity {

    private TextView edtBox_id;
    private TextView editNama;
    private TextView edtEmail;
    private TextView edtPhone;
    private Context context;
    private String TAG = "ActivityTampilDataBox";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_databox);

        context = this;
        initUI();
        //initEvent();

    }

    private void initUI() {

        edtBox_id = (TextView) findViewById(R.id.id);
        editNama= (TextView) findViewById(R.id.nama);
        edtEmail=(TextView) findViewById(R.id.email);
        edtPhone=(TextView)findViewById(R.id.phone);

    }

    /*private void initEvent() {
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initData();
                startActivity(new Intent(getApplicationContext(),ActivityRegister.class));
            }
        });
    }*/

    private void initData() {

        JSONObject jbody=new JSONObject();
        try {
            jbody.put("box_id", edtBox_id.getText().toString());
            jbody.put("nama", editNama.getText().toString());
            jbody.put("email", edtEmail.getText().toString());
            jbody.put("phone", edtPhone.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        new APIvolley(context, jbody, "POST", URL.dotransaksi, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {


                // 1. Ubah ke JSON
                try {
                    JSONObject response = new JSONObject(result);

                    //2. Pastikan response 200 dari metadata
                    String status = response.getJSONObject("metadata").getString("status");

                    if(status.equals("200")){

                        String box_id = response.getJSONObject("response").getString("box_id");
                        String nama = response.getJSONObject("response").getString("nama");

                        Log.d(TAG, "onSuccess: " + box_id);
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

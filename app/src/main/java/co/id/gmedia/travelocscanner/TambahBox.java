package co.id.gmedia.travelocscanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.id.gmedia.travelocscanner.utils.APIvolley;
import co.id.gmedia.travelocscanner.utils.AppController;
import co.id.gmedia.travelocscanner.utils.URL;

public class TambahBox extends AppCompatActivity  {

    EditText editNama, editEmail, editPhone;
    TextView editBoxId;
    Button buttonAdd, buttonView;
   static ProgressDialog pd;
    private int update;

    public static APIvolley apivolley;


    @Override
protected void onCreate (Bundle savedInstanceState){
super.onCreate (savedInstanceState);
setContentView(R.layout.activity_box);

    Intent data = getIntent();
    String intent_boxid = data.getStringExtra("box_id");

editBoxId = (TextView) findViewById(R.id.TextIdbox);
editNama = (EditText) findViewById(R.id.editTextNama);
editEmail = (EditText) findViewById(R.id.editTextEmail);
editPhone = (EditText) findViewById(R.id.editTextPhone);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        //buttonView = (Button) findViewById(R.id.buttonView);

editBoxId.setText(intent_boxid);
buttonAdd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SimpanData();
    }
});
}


    /*private void Update_data()
    {
        pd.setMessage("Update Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST, URL.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(TambahBox.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity( new Intent(TambahBox.this,MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(TambahBox.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("box_id",editBoxId.getText().toString());
                map.put("nama",editNama.getText().toString());
                map.put("email",editEmail.getText().toString());
                map.put("phone",editPhone.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(updateReq);
    }*/

   /* private void simpanData()
    {

        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, URL.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(TambahBox.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity( new Intent(TambahBox.this,MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(TambahBox.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("box_id",editBoxId.getText().toString());
                map.put("nama",editNama.getText().toString());
                map.put("email",editEmail.getText().toString());
                map.put("phone",editPhone.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }*/

    public void SimpanData(){
        JSONObject body = new JSONObject();
        try {
            body.put("box_id", editBoxId.getText().toString());
            body.put("nama", editNama.getText().toString());
            body.put("email", editEmail.getText().toString());
            body.put("phone",editPhone.getText().toString());
            Log.d("_log", body.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("body.error", e.getMessage());
        }

        apivolley = new APIvolley(TambahBox.this, body, "POST", URL.dotransaksi, new APIvolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("Response", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int status = object.getJSONObject("metadata").getInt("status");
                    String message;
                    if (status==200){
                        message = object.getJSONObject("metadata").getString("message");
                    } else {
                        message = object.getJSONObject("metadata").getString("message");
                    }
                    Log.d("OnSuccess", message);
                    Toast.makeText(TambahBox.this, message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TambahBox.this, MainMenu.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("response", e.getMessage());
                }

            }

            @Override
            public void onError(String result) {
                Log.d("Error.Response", result);
            }
        });
    }

}

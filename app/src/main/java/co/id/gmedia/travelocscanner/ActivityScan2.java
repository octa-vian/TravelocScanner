package co.id.gmedia.travelocscanner;

        import android.Manifest;
        import android.app.Activity;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import com.budiyev.android.codescanner.CodeScanner;
        import com.budiyev.android.codescanner.CodeScannerView;
        import com.budiyev.android.codescanner.DecodeCallback;
        import com.google.zxing.Result;
        import com.google.zxing.integration.android.IntentIntegrator;
        import com.karumi.dexter.Dexter;
        import com.karumi.dexter.PermissionToken;
        import com.karumi.dexter.listener.PermissionDeniedResponse;
        import com.karumi.dexter.listener.PermissionGrantedResponse;
        import com.karumi.dexter.listener.PermissionRequest;
        import com.karumi.dexter.listener.single.PermissionListener;

        import co.id.gmedia.travelocscanner.utils.AppSharedPreferences;
        import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ActivityScan2 extends AppCompatActivity  {
    private ImageView ivBgContent;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private ZXingScannerView mScannerView;
    private ImageView btnexit;
    static String idBox="";
    private Context context;
    private static String TAG = "ActivityScan2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner2);
        context = this;
        ivBgContent = findViewById(R.id.ivBgContent);
        scannerView = findViewById(R.id.scannerView);
        ivBgContent.bringToFront();
        //Btn logout
        btnexit = findViewById(R.id.btnback);


        //membaca dan memindahkan ID box
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String message = "Box ID :\n" + result.getText();
                        //showAlertDialog(message);
                        //mengambil id box
                        idBox = result.getText();
                        Intent intent= new Intent(ActivityScan2.this, TambahBox.class);
                        intent.putExtra("box_id", idBox);
                        Log.d(TAG, "ID Box berhasil di ambil" +message );
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        checkCameraPermission();
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppSharedPreferences.Logout(context);
                startActivity(new Intent(context, ActivityLogin.class));
                finish();

            }
        });
    }





    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();

    }

    private void checkCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mCodeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
                                                                   PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "SCAN LAGI",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        mCodeScanner.startPreview();
                    }
                });

        builder.setPositiveButton(
                "INPUT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent= new Intent(ActivityScan2.this, TambahBox.class);
                        intent.putExtra("box_id", idBox);
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
package co.id.gmedia.travelocscanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuutama);
    }

    public void kescanner (View view) {
        Intent intent = new Intent(MainMenu.this,ActivityScan2.class);
        startActivity(intent);

    }
    public void ke (View view) {
        Intent intent = new Intent(MainMenu.this,TambahBox.class);
        startActivity(intent);
    }

   public void kescan3 (View view) {
        Intent intent = new Intent(MainMenu.this,TambahBox.class);
        startActivity(intent);

    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(MainMenu.this,ActivityScan2.class);
        startActivity(intent);
        finish();

    }
}


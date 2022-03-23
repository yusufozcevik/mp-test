package localstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.yzm5242.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SharedPrefsActivity extends AppCompatActivity {
    String  SHARED_PREFS_NAME = "my_app_shared_prefs";
    String  PREFS_NAME_SURNAME = "name_surname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);

        String adSoyad = getSharedPreference(SharedPrefsActivity.this, SHARED_PREFS_NAME, PREFS_NAME_SURNAME,"empty");

        EditText etAdSoyad = findViewById(R.id.et_ad_soyad);

        TextView tvAdSoyad = findViewById(R.id.tv_ad_soyad);
        tvAdSoyad.setText(adSoyad);

        Button btnKaydet = findViewById(R.id.btn_kaydet);
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adSoyad = etAdSoyad.getText().toString();
                //clearSharedPreference(SharedPrefsActivity.this, SHARED_PREFS_NAME);
                setSharedPreference(SharedPrefsActivity.this,SHARED_PREFS_NAME, getResources().getString(R.string.sp_key),adSoyad);
                //removeSharedPreference(SharedPrefsActivity.this, "name_surmane");
            }
        });
    }

    public void setSharedPreference(Context context, String fileName, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public String getSharedPreference(Context context, String fileName, String key, String defaultValue) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE).getString(key, defaultValue);
    }

    public void clearSharedPreference(Context context, String dosyaadi){
        SharedPreferences sharedPref = context.getSharedPreferences(dosyaadi, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.clear();
        edit.apply();
    }

    public void removeSharedPreference(Context context, String key){
        SharedPreferences sharedPref = context.getSharedPreferences("my_app_shared_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.remove(key);
        edit.apply();
    }
}
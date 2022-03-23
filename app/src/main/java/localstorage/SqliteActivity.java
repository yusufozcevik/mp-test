package localstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yzm5242.R;

import java.util.List;

public class SqliteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);

        SQLiteDB db = new SQLiteDB(SqliteActivity.this);

        TextView tvAdSoyad = findViewById(R.id.tv_ad_soyad);

        List<String> veriler = db.Read();
        if(veriler.isEmpty())
            tvAdSoyad.setText("DB Bos");
        else{
            String icerik = "";
            for(int i = 0; i < veriler.size(); i++){
                icerik += veriler.get(i);
                icerik += "\n";
            }
            tvAdSoyad.setText(icerik);
        }


        EditText etAdSoyad = findViewById(R.id.et_ad_soyad);

        Button btnKaydet = findViewById(R.id.btn_kaydet);
        btnKaydet.setText("Guncelle");
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adSoyad = etAdSoyad.getText().toString();
                //db.Insert(adSoyad);
                //db.Remove(2);
                db.UPDATE(3, adSoyad);
            }
        });

        db.getReadableDatabase();
    }
}
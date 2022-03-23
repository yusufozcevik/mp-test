package jsonparser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.yzm5242.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonParserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackson_parser);

        TextView tv = findViewById(R.id.tv);

        final ObjectMapper mapper = new ObjectMapper();
        try {
            String personJsonStr = "{\"firstname\":\"John\",\"lastname\":\"Doe\",\"age\":35}";
            Person person = mapper.readValue(personJsonStr, Person.class);                               // read from json string
            tv.setText(person.getFirstname() + " " + person.getLastname() + " " + person.age);
        } catch (IOException e) {

        }
    }
}
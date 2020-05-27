package ru.startandroid.p0331_shaedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSave, btnLoad;
    EditText etText, etKey;

    SharedPreferences sPref;

//    final String SAVED_TEXT = "saved_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        etText = findViewById(R.id.etText);
        etKey = findViewById(R.id.etKey);

        btnSave.setOnClickListener(this);
        btnLoad.setOnClickListener(this);

//        loadText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        saveText();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnSave:
                saveText();
                break;
            case R.id.btnLoad:
                loadText();
                break;
        }
    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(etKey.getText().toString(), etText.getText().toString());
        ed.apply();
        Toast.makeText(this, "Text saved", Toast.LENGTH_LONG).show();
        etKey.setText("");
        etText.setText("");
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
//        String savedText = sPref.getString(etText.getText().toString(), "");
        String savedKey = sPref.getString(etKey.getText().toString(), "");
        etKey.setText(savedKey);
        etText.setText("");
        Toast.makeText(this, "Text loaded", Toast.LENGTH_LONG).show();
    }
}

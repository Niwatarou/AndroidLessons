package ru.startandroid.p0341_simplesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final String LOG = "myLogs";

    Button btnAdd, btnRead, btnClear, btnUpd, btnDel;
    EditText etName, etEmail, etID;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        btnUpd = findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);

        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etID = findViewById(R.id.etID);

        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        // создаем объект для данных
        ContentValues cv = new ContentValues();
//        Log.d(LOG, "DEBUG: ContentValues created.");

        // получаем данные из полей ввода
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String id = etID.getText().toString();
//        Log.d(LOG, "DEBUG: Variables initialized.");
        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Log.d(LOG, "DEBUG: dbHelper.getWritableDatabase()");

        switch(v.getId()) {
            case R.id.btnAdd:
                Log.d(LOG, "--- Insert in mytable: ---");

                // подготовим данные для вставки в виде пар: наименование столбца - значение
                cv.put("name", name);
                cv.put("email", email);

                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG, "row inserted, ID = " + rowID);
                etID.setText("");
                etName.setText("");
                etEmail.setText("");
                break;
            case R.id.btnRead:
                Log.d(LOG, "--- Rows in mytable: ---");

                // делаем запрос всех данных из таблицы mytable, получаем Cursor
                Cursor c = db.query("mytable", null, null,
                        null, null, null, null);

                // ставим позицию курсора на первую строку выборки
                // если в выборке нет строк, вернется false
                if(c.moveToFirst()) {

                    // определяем номера столбцов по имени в выборке
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int emailColIndex = c.getColumnIndex("email");

                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG,
                                "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", email = " + c.getString(emailColIndex));
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while(c.moveToNext());
                } else {
                    Log.d(LOG, "0 rows");
                }

                c.close();
                break;
            case R.id.btnClear:
                Log.d(LOG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);
                Log.d(LOG, "deleted rows count = " + clearCount);
                etID.setText("");
                etName.setText("");
                etEmail.setText("");
                break;
            case R.id.btnUpd:
                if(id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG, "--- Update mytable: ---");
                // подготовим значения для обновления
                cv.put("name", name);
                cv.put("email", email);
                // обновляем по id
                int updCount = db.update("mytable", cv, "id = ?",
                        new String[] { id });
                Log.d(LOG, "updated rows count = " + updCount);
                etID.setText("");
                etName.setText("");
                etEmail.setText("");
                break;
            case R.id.btnDel:
                if(id.equalsIgnoreCase("")) {
                    break;
                }
                Log.d(LOG, "--- Delete from mytable: ---");
                // удаляем по id
                int delCount = db.delete("mytable", "id = " + id, null);
                Log.d(LOG, "deleted rows count = " + delCount);
                etID.setText("");
                etName.setText("");
                etEmail.setText("");
                break;
        }
        // закрываем подключение к БД
        dbHelper.close();
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "myDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG, "--- onCreate database ---");
            // создаем таблицу с полями
            db.execSQL("create table mytable (id integer primary key autoincrement, " +
                    "name text, email text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}

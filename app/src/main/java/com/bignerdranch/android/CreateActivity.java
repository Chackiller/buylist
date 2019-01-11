package com.bignerdranch.android;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class CreateActivity extends AppCompatActivity {

    SQLiteDatabase db;
    SQLiteOpenHelper sqLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    public void onCheckButtonClick(View view) {
        EditText name = findViewById(R.id.editName);
        EditText description = findViewById(R.id.editDescription);
        Spinner spinner = findViewById(R.id.spinner);
        String nameText = name.getText().toString();
        String descriptionText = description.getText().toString();
        String lol = spinner.getSelectedItem().toString();
        int i = 0;
        if (lol.equals("Їжа") ){
            i = 1;
        } else if(lol.equals("Різне")){
            i = 2;
        }
        sqLiteOpenHelper = new ListViewItemsDataBase(this);
        try {
            db = sqLiteOpenHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("NAME",nameText);
            contentValues.put("DESCRIPTION",descriptionText);
            contentValues.put("TYPE",i);
            db.insert("TODO",null,contentValues);
            db.close();
        } catch (SQLiteException e){
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
        setResult(RESULT_OK);
        finish();
    }
}

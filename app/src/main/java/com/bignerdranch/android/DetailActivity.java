package com.bignerdranch.android;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Cursor cursor;
    SQLiteOpenHelper sqLiteOpenHelper;
    String name;
    String description;
    int type;
    int checkedDetail;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        pos = getIntent().getIntExtra("ID", 0);

        sqLiteOpenHelper = new ListViewItemsDataBase(this);
        try {
            db = sqLiteOpenHelper.getWritableDatabase();
            cursor = db.query("TODO",
                    new String[]{"_id","NAME","ISCHECKED","TYPE","DESCRIPTION"},
                    "_id = ?",new String[]{Integer.toString(pos)},null,null, null);

            TextView textViewName = findViewById(R.id.nameDetail);
            TextView textViewDescription = findViewById(R.id.descriptionDetail);
            TextView textViewType = findViewById(R.id.typeDetail);
            CheckBox checkBox = findViewById(R.id.checkBoxDetail);
            cursor.moveToFirst();
            type = cursor.getInt(cursor.getColumnIndex("TYPE"));
            description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
            name = cursor.getString(cursor.getColumnIndex("NAME"));
            checkedDetail = cursor.getInt(cursor.getColumnIndex("ISCHECKED"));

            textViewName.setText(name);
            textViewDescription.setText(description);
            String typeString = "lol";
            if(type == 1){
                typeString = "Їжа";
            } else if (type == 2){
                typeString = "Різне";
            }
            textViewType.setText(typeString);
            checkBox.setClickable(false);
            if(checkedDetail == 1){
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }

            cursor.close();
        } catch (SQLiteException e){
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDeleteButtonClick(View view) {
        db.delete("TODO","_id = ?",new String[]{Integer.toString(pos)});
        db.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}

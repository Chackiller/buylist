package com.bignerdranch.android;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class CustomCursorAdapter extends SimpleCursorAdapter {

    public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView name = view.findViewById(R.id.textView);
        final String text = cursor.getString(cursor.getColumnIndex("NAME"));
        Integer check = cursor.getInt(cursor.getColumnIndex("ISCHECKED"));
        name.setText(text);
        final int pos = cursor.getInt(cursor.getColumnIndex("_id"));
        final CheckBox checkBox = view.findViewById(R.id.checkBox2);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SQLiteOpenHelper sqLiteOpenHelper = new ListViewItemsDataBase(context);
                SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                if(isChecked) {
                    contentValues.put("ISCHECKED", 1);
                } else {
                    contentValues.put("ISCHECKED",0);
                }

                db.update("TODO",contentValues,"_id = ?",new String[]{Integer.toString(pos)});
                db.close();
            }
        });
        boolean isChecked;
        if(check == 1){
           isChecked = true;
        } else {
            isChecked = false;
        }
        checkBox.setChecked(isChecked);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("ID",pos);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_items, parent, false);
        return view;
    }
}

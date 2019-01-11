package com.bignerdranch.android;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainFragment extends Fragment {

    SQLiteDatabase db;
    SQLiteDatabase db2;
    Cursor cursor;
    View rootView;
     SQLiteOpenHelper sqLiteOpenHelper;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView mListView = rootView.findViewById(R.id.main_list_view);
        sqLiteOpenHelper = new ListViewItemsDataBase(rootView.getContext());
       try {
            db = sqLiteOpenHelper.getWritableDatabase();
            cursor = db.query("TODO",
                    new String[]{"_id","NAME","ISCHECKED"},
                    null,null,null,null, null);

            CustomCursorAdapter listAdapter = new CustomCursorAdapter(container.getContext(),
                    R.layout.list_items,cursor,
                    new String[]{"NAME"},
                    new int[]{R.id.textView},0);
                
           mListView.setAdapter(listAdapter);
        } catch (SQLiteException e){
        Toast.makeText(getContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
    }
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onStart() {
        super.onStart();
        Cursor cursorNew = db.query("TODO",
                new String[]{"_id","NAME","ISCHECKED"},
                null,null,null,null, null);
        ListView mListView = rootView.findViewById(R.id.main_list_view);
        CustomCursorAdapter cursorAdapter = (CustomCursorAdapter) mListView.getAdapter();
        cursorAdapter.changeCursor(cursorNew);
        cursor = cursorNew;
    }
}

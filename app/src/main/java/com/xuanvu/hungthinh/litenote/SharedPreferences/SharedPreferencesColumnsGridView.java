package com.xuanvu.hungthinh.litenote.SharedPreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesColumnsGridView {
    private SharedPreferences preferences;
    private Context context;
    private SharedPreferences.Editor editor;

    private int MODE_PRIVATE = 1;

    private static final String NAME = "GirdViewColumn";
    private static final String COLUMN = "column";


    @SuppressLint("WrongConstant")
    public SharedPreferencesColumnsGridView(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(NAME,context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public boolean checkExist(Context context){
        preferences = context.getSharedPreferences(NAME,context.MODE_PRIVATE);
        if(preferences.contains(COLUMN)){
            return true;
        }
        else{
            return false;
        }
    }

    public void setGridViewColumn(int column){
        editor.putInt(COLUMN,column);
        editor.commit();
    }

    public int getGridViewColumns(){
        return preferences.getInt(COLUMN,2);
    }


}

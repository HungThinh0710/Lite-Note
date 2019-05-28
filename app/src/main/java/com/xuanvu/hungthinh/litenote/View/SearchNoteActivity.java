package com.xuanvu.hungthinh.litenote.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.xuanvu.hungthinh.litenote.Adapter.GridViewAdapter;
import com.xuanvu.hungthinh.litenote.Database.MyDatabase;
import com.xuanvu.hungthinh.litenote.MainActivity;
import com.xuanvu.hungthinh.litenote.Model.Note;
import com.xuanvu.hungthinh.litenote.R;

import java.util.ArrayList;
import java.util.List;

public class SearchNoteActivity extends AppCompatActivity {
    private EditText edtSearch;
    private Button btnBack;
    private MyDatabase myDatabase;
    private List<Note> listNote;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_note);
        init();
        onTextChange();
    }

    private void init() {
        edtSearch = findViewById(R.id.edtSearchNote);
        btnBack = findViewById(R.id.btnBack);
        gridView = findViewById(R.id.gridViewSearch);
        myDatabase = new MyDatabase(this);

        edtSearch.setShowSoftInputOnFocus(true);

        gridView.setNumColumns(2);







        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void onTextChange() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String keyword = edtSearch.getText().toString();
                    List<Note> listNote = getDataSearchResult(keyword);
                    gridViewAdapter = new GridViewAdapter(SearchNoteActivity.this,(ArrayList) listNote);
                    if(listNote != null){
                        gridView.setAdapter(gridViewAdapter);
                        gridViewAdapter.notifyDataSetChanged();
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private List<Note> getDataSearchResult(String keyword) {
        keyword = edtSearch.getText().toString();
        listNote = new ArrayList<>();
        listNote = myDatabase.searchNote(keyword);
        return listNote;
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}

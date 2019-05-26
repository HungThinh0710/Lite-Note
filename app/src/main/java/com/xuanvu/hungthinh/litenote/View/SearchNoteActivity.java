package com.xuanvu.hungthinh.litenote.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xuanvu.hungthinh.litenote.MainActivity;
import com.xuanvu.hungthinh.litenote.R;

public class SearchNoteActivity extends AppCompatActivity {
        private EditText edtSearch;
        private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_note);
        init();
    }

    private void init() {
        edtSearch = findViewById(R.id.edtSearchNote);
        btnBack = findViewById(R.id.btnBack);

        edtSearch.setShowSoftInputOnFocus(true);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();
    }
}

package com.xuanvu.hungthinh.litenote.View;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xuanvu.hungthinh.litenote.Model.Note;
import com.xuanvu.hungthinh.litenote.R;

import java.text.SimpleDateFormat;

public class DetailNoteActivity extends AppCompatActivity {
    private Note note;
    public EditText edtTitle, edtContent;
    private TextView txtTimeModify;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        init();
        back();
        receiveDataBundle();
        setData();
    }

    private void init() {
        edtTitle = findViewById(R.id.edt_detail_title);
        edtContent = findViewById(R.id.edt_detail_content);
        txtTimeModify = findViewById(R.id.txt_detail_time_modify);
        btnBack = findViewById(R.id.btn_detail_note_back);
    }

    private void receiveDataBundle() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("NoteObject");
//        Log.d("In4",note.getmContent());
    }

    private void setData() {
        edtTitle.setText(note.getmTitle());
        edtContent.setText(note.getmContent());
        txtTimeModify.setText(getString(R.string.detail_time_modify,note.getmCreated_at()));
    }

    private void back() {
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

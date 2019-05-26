package com.xuanvu.hungthinh.litenote.View;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xuanvu.hungthinh.litenote.Model.Note;
import com.xuanvu.hungthinh.litenote.R;

public class DetailNoteActivity extends AppCompatActivity {
    private Note note;
    public EditText edtTitle;
    public EditText edtContent;
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

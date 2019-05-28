package com.xuanvu.hungthinh.litenote.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanvu.hungthinh.litenote.Model.Note;
import com.xuanvu.hungthinh.litenote.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailNoteActivity extends AppCompatActivity {
    private Note note;
    public EditText edtTitle, edtContent;
    private TextView txtTimeModify;
    private Button btnBack;

    BottomSheetBehavior sheetBehaviorThreeDot, sheetBehaviorAdd;

    private Button btnThreeDot,btnAdd;
    /*
     * Bind Controls from Bottom Sheet
     * */
    @BindView(R.id.layout_bottom_sheet_threedote_note)
    LinearLayout layoutBottomSheetThreeDot;
    @BindView(R.id.layout_bottom_sheet_add_note)
    LinearLayout layoutBottomSheetAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);
        init();
        toggleBottomSheet();
        receiveDataBundle();
        setData();
        back();
    }

    private void init() {
        //init ButterKnife
        ButterKnife.bind(this);

        //init Control
        edtTitle = findViewById(R.id.edt_detail_title);
        edtContent = findViewById(R.id.edt_detail_content);
        txtTimeModify = findViewById(R.id.txt_detail_time_modify);
        btnBack = findViewById(R.id.btn_detail_note_back);

        //init Button for react Bottom sheet
        btnThreeDot = findViewById(R.id.btn_detail_note_threedot);
        btnAdd = findViewById(R.id.btn_detail_note_add);

        //init Bottom Sheet
        bottomSheetInit();
    }

    private void bottomSheetInit() {
        sheetBehaviorThreeDot = BottomSheetBehavior.from(layoutBottomSheetThreeDot);
        sheetBehaviorThreeDot.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        btnThreeDot.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        btnThreeDot.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        sheetBehaviorAdd = BottomSheetBehavior.from(layoutBottomSheetAdd);
        sheetBehaviorAdd.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newStateAddButton) {
                switch (newStateAddButton) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        btnThreeDot.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        btnThreeDot.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    public void toggleBottomSheet() {
        btnThreeDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehaviorThreeDot.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehaviorThreeDot.setState(BottomSheetBehavior.STATE_EXPANDED);
                    if(sheetBehaviorAdd.getState() == BottomSheetBehavior.STATE_EXPANDED){
                        sheetBehaviorAdd.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                } else {
                    sheetBehaviorThreeDot.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehaviorAdd.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehaviorAdd.setState(BottomSheetBehavior.STATE_EXPANDED);
                    if(sheetBehaviorThreeDot.getState() == BottomSheetBehavior.STATE_EXPANDED){
                        sheetBehaviorThreeDot.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                } else {
                    sheetBehaviorAdd.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

    }

    private void receiveDataBundle() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("NoteObject");
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
        if(sheetBehaviorThreeDot.getState() == BottomSheetBehavior.STATE_EXPANDED){
            sheetBehaviorThreeDot.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(sheetBehaviorAdd.getState() == BottomSheetBehavior.STATE_EXPANDED){
            sheetBehaviorAdd.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        else{
            supportFinishAfterTransition();
        }
    }

    /*
     * Handle BottomSheet ThreeDot
     * */
    @OnClick(R.id.btn_bottom_sheet_threedot_delete)
    public void bottomSheetThreeDotDelete(){
        Toast.makeText(this, "Clicked for delete Button", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_bottom_sheet_threedot_make_a_copy)
    public void bottomSheetThreeDotMakeACopy(){

    }

    @OnClick(R.id.btn_bottom_sheet_threedot_share)
    public void bottomSheetThreeDotShare(){

    }

    @OnClick(R.id.btn_bottom_sheet_threedot_label)
    public void bottomSheetThreeDotLabel(){

    }

    /*
     * Handle BottomSheet AddPlus
     * */
    @OnClick(R.id.btn_bottom_sheet_add_capture)
    public void bottomSheetAddCapture(){

    }
    @OnClick(R.id.btn_bottom_sheet_add_select_picture)
    public void bottomSheetAddSelectPicture(){

    }
    @OnClick(R.id.btn_bottom_sheet_add_sound_recoder)
    public void bottomSheetAddSoundRecoder(){

    }

}

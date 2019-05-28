package com.xuanvu.hungthinh.litenote.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanvu.hungthinh.litenote.Database.MyDatabase;
import com.xuanvu.hungthinh.litenote.Model.Note;
import com.xuanvu.hungthinh.litenote.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateNoteActivity extends AppCompatActivity{
    private Button btnBack;
    private EditText edtTitle, edtContent;
    private MyDatabase myDatabase;

    BottomSheetBehavior sheetBehaviorThreeDot, sheetBehaviorAdd;

    private Button btnThreeDot,btnAdd;
//    private Button btnDeleteBottomSheet, btnCopyBottomSheet, btnShareBottomSheet, btnLabelBottomSheet;
//    private Button btnCaptureBottomSheet, btnSelectPictureBottomSheet, btnSoundRecoderBottomSheet;

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
        setContentView(R.layout.activity_create_note);

        init();
        toggleBottomSheet();
        back();

    }

    private void init() {

        //init ButterKnife
        ButterKnife.bind(this);

        //init Database
        myDatabase = new MyDatabase(this);

        //init Control
        edtTitle = findViewById(R.id.edt_create_note_title);
        edtContent = findViewById(R.id.edt_create_note_content);
        TextView txtTimeModify = findViewById(R.id.txt_create_time_modify);
        btnBack = findViewById(R.id.btn_create_note_back);

        //init Button for react Bottom sheet
        btnThreeDot = findViewById(R.id.btn_create_note_threedot);
        btnAdd = findViewById(R.id.btn_create_note_add);

        //init TimeModified
        String date_n = new SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault()).format(new Date());
        txtTimeModify.setText(getString(R.string.detail_time_modify,date_n));

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

    /**
     * manually opening / closing bottom sheet on button click
     */

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

    private void autoSave() {
        Intent intent = new Intent();
        if(edtTitle.getText().toString().equals("") && edtContent.getText().toString().equals("")){
            intent.putExtra("MESSAGE",false);
            setResult(200,intent);
            supportFinishAfterTransition();
        }
        else{
//            currentTime = Calendar.getInstance().getTime();
            String date_n = new SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Note note = new Note(edtTitle.getText().toString(),edtContent.getText().toString(),"#FFFFF",date_n);
            myDatabase.addNote(note);
            Toast.makeText(this, "Saved your note", Toast.LENGTH_SHORT).show();
            intent.putExtra("MESSAGE",true);
            setResult(200,intent);
            supportFinishAfterTransition();
        }

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
            autoSave();
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

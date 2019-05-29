package com.xuanvu.hungthinh.litenote.View;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanvu.hungthinh.litenote.Color.DefineColor;
import com.xuanvu.hungthinh.litenote.Database.MyDatabase;
import com.xuanvu.hungthinh.litenote.MainActivity;
import com.xuanvu.hungthinh.litenote.Model.Note;
import com.xuanvu.hungthinh.litenote.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailNoteActivity extends AppCompatActivity {
    private Note note;
    public EditText edtTitle, edtContent;
    private TextView txtTimeModify;
    private Button btnBack;
    private ImageView imageView;

    private MyDatabase myDatabase;

    ClipboardManager clipboardManager;

    BottomSheetBehavior sheetBehaviorThreeDot, sheetBehaviorAdd;

    private Button btnThreeDot, btnAdd, btnSaveDetailNote;
    private String hexColorChanged;
    private DefineColor defineColor = new DefineColor();



    /*
     * Bind Controls from Bottom Sheet
     * */

    @BindView(R.id.layout_bottom_sheet_threedote_note)
    LinearLayout layoutBottomSheetThreeDot;
    @BindView(R.id.layout_bottom_sheet_add_note)
    LinearLayout layoutBottomSheetAdd;
    @BindView(R.id.layout_detail_note)
    LinearLayout layoutFrameDetailNote;
    @BindView(R.id.layout_detail_note_bottom)
    LinearLayout layoutFrameDetailNoteBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail_note );
        init();
        receiveDataBundle();
        toggleBottomSheet();
        setData();
        back();
    }

    private void init() {
        //init ButterKnife
        ButterKnife.bind( this );

        //init Database
        myDatabase = new MyDatabase( this );

        //init Control
        edtTitle = findViewById( R.id.edt_detail_title );
        edtContent = findViewById( R.id.edt_detail_content );
        txtTimeModify = findViewById( R.id.txt_detail_time_modify );
        btnBack = findViewById( R.id.btn_detail_note_back );
        btnSaveDetailNote = findViewById( R.id.save_detail_note );
        imageView = findViewById( R.id.imageView );
        clipboardManager =(ClipboardManager) getSystemService( CLIPBOARD_SERVICE );

        //init Button for react Bottom sheet
        btnThreeDot = findViewById( R.id.btn_detail_note_threedot );
        btnAdd = findViewById( R.id.btn_detail_note_add );

        //init Bottom Sheet
        bottomSheetInit();

        btnSaveDetailNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoUpdate();
            }
        });
    }

    private void autoUpdate() {
        Intent intent = new Intent(this,MainActivity.class);
        if(edtTitle.getText().toString().equals("") && edtContent.getText().toString().equals("")){
            intent.putExtra("MESSAGE",false);
            setResult(Activity.RESULT_CANCELED,intent);
            finishActivity(200);
        }
        else{
//            currentTime = Calendar.getInstance().getTime();
            String date_n = new SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Note noteObjForUpdate = new Note(edtTitle.getText().toString(),edtContent.getText().toString(),hexColorChanged,date_n);
            myDatabase.updateNote(noteObjForUpdate,note.getID());
            Toast.makeText(this, "Saved your note", Toast.LENGTH_SHORT).show();
            intent.putExtra("MESSAGE",true);
            setResult(Activity.RESULT_OK,intent);
//            finish();
            finishActivity(200);
        }
    }

    private void bottomSheetInit() {
        sheetBehaviorThreeDot = BottomSheetBehavior.from( layoutBottomSheetThreeDot );
        sheetBehaviorThreeDot.setBottomSheetCallback( new BottomSheetBehavior.BottomSheetCallback() {
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
        } );


        sheetBehaviorAdd = BottomSheetBehavior.from( layoutBottomSheetAdd );
        sheetBehaviorAdd.setBottomSheetCallback( new BottomSheetBehavior.BottomSheetCallback() {
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
        } );
    }

    public void toggleBottomSheet() {
        btnThreeDot.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehaviorThreeDot.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehaviorThreeDot.setState( BottomSheetBehavior.STATE_EXPANDED );
                    if (sheetBehaviorAdd.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehaviorAdd.setState( BottomSheetBehavior.STATE_COLLAPSED );
                    }
                } else {
                    sheetBehaviorThreeDot.setState( BottomSheetBehavior.STATE_COLLAPSED );
                }
            }
        } );

        btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehaviorAdd.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehaviorAdd.setState( BottomSheetBehavior.STATE_EXPANDED );
                    if (sheetBehaviorThreeDot.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                        sheetBehaviorThreeDot.setState( BottomSheetBehavior.STATE_COLLAPSED );
                    }
                } else {
                    sheetBehaviorAdd.setState( BottomSheetBehavior.STATE_COLLAPSED );
                }
            }
        } );

    }

    private void receiveDataBundle() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra( "NoteObject" );
        hexColorChanged = note.getmColor();
    }

    private void setData() {
        edtTitle.setText( note.getmTitle() );
        edtContent.setText( note.getmContent() );
        txtTimeModify.setText( getString( R.string.detail_time_modify, note.getmCreated_at() ) );

        layoutFrameDetailNote.setBackgroundColor(Color.parseColor(note.getmColor()));
        layoutFrameDetailNoteBottom.setBackgroundColor(Color.parseColor(note.getmColor()));
        layoutBottomSheetAdd.setBackgroundColor(Color.parseColor(note.getmColor()));
        layoutBottomSheetThreeDot.setBackgroundColor(Color.parseColor(note.getmColor()));
    }

    private void setColorFrameLayout(int drawableRes){
        layoutFrameDetailNote.setBackgroundResource(drawableRes);
        layoutFrameDetailNoteBottom.setBackgroundResource(drawableRes);
        layoutBottomSheetAdd.setBackgroundResource(drawableRes);
        layoutBottomSheetThreeDot.setBackgroundResource(drawableRes);
    }

    private void back() {
        btnBack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        } );
    }

    @Override
    public void onBackPressed() {
        if (sheetBehaviorThreeDot.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehaviorThreeDot.setState( BottomSheetBehavior.STATE_COLLAPSED );
        }
        if (sheetBehaviorAdd.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehaviorAdd.setState( BottomSheetBehavior.STATE_COLLAPSED );
        } else {
            autoUpdate();
            supportFinishAfterTransition();
        }
    }

    /*
     * Handle BottomSheet ThreeDot
     * */
    @OnClick(R.id.btn_bottom_sheet_threedot_delete)
    public void bottomSheetThreeDotDelete() {
        final AlertDialog.Builder box = new AlertDialog.Builder( this );
        //box.setTitle("Delete");
        box.setMessage( "Delete note !" );

        box.setPositiveButton( "Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean isDeleted = myDatabase.deleteNote(note);

                if (isDeleted) {
                    Toast.makeText(getApplicationContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailNoteActivity.this,MainActivity.class );
//                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY );
                    overridePendingTransition( 0,0 );

                    intent.putExtra("REFRESH_STATUS",true);
                    startActivityForResult( intent, 200 );
//                    supportFinishAfterTransition();
                } else {
                    Toast.makeText(getApplicationContext(), "Error : Note not deleted", Toast.LENGTH_SHORT).show();
                }
            }
        } );
        box.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        } );
        AlertDialog alertDialog = box.create();
        alertDialog.show();
    }

    @OnClick(R.id.btn_bottom_sheet_threedot_make_a_copy)
    public void bottomSheetThreeDotMakeACopy() {
        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        if (!title.equals( "" )|| !content.equals( "" ))
        {
            ClipData clipData = ClipData.newPlainText( "",title + "\n" + content );
            clipboardManager.setPrimaryClip( clipData );
            Toast.makeText( this, "Copied", Toast.LENGTH_SHORT ).show();
        }

    }

    @OnClick(R.id.btn_bottom_sheet_threedot_share)
    public void bottomSheetThreeDotShare() {
        Intent intent = new Intent( Intent.ACTION_SEND );
        intent.setType("text/plain");
        intent.putExtra( Intent.EXTRA_TEXT, note );
        startActivity(Intent.createChooser(intent, "Select app"));


    }

    /*
     * Handle BottomSheet AddPlus
     * */
    @OnClick(R.id.btn_bottom_sheet_add_capture)
    public void bottomSheetAddCapture() {
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        startActivityForResult( intent, 1000 );

    }

    @OnClick(R.id.btn_bottom_sheet_add_select_picture)
    public void bottomSheetAddSelectPicture() {
        Intent intent = new Intent( Intent.ACTION_GET_CONTENT );
        intent.setType( "image/*" );
        startActivityForResult( intent, 2000 );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {
                Bitmap bitmap = (Bitmap) data.getExtras().get( "data" );
                imageView.setImageBitmap( bitmap );
            } else if (requestCode == 2000) {
                Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    /* imageView.setImageURI(data.getData()  );*/
                    bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), uri );
                    imageView.setImageBitmap( bitmap );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 3000){
               /* Uri uri = data.getData();
                MediaPlayer mediaPlayer = MediaPlayer.create( this,uri );*/
            }

        }

    }

    @OnClick(R.id.btn_bottom_sheet_add_sound_recoder)
    public void bottomSheetAddSoundRecoder() {
        Intent intent = new Intent (MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult (intent, 3000);

    }

    /*
     * Handle set color for note
     * */
    @OnClick(R.id.img_bottom_sheet_circle_white)
    public void setWhiteColorForNote(){
        hexColorChanged = defineColor.WHITE;
        setColorFrameLayout(R.color.circle_white);
    }

    @OnClick(R.id.img_bottom_sheet_circle_red_light)
    public void setRedLightColorForNote(){
        hexColorChanged = defineColor.RED_LIGHT;
        setColorFrameLayout(R.color.circle_red_light);
    }

    @OnClick(R.id.img_bottom_sheet_circle_yellow_heavy)
    public void setYellowHeavyColorForNote(){
        hexColorChanged = defineColor.YELLOW_HEAVY;
        setColorFrameLayout(R.color.circle_yellow_heavy);
    }

    @OnClick(R.id.img_bottom_sheet_circle_yellow_light)
    public void setYellowLightColorForNote(){
        hexColorChanged = defineColor.YELLOW_LIGHT;
        setColorFrameLayout(R.color.circle_yellow_light);
    }

    @OnClick(R.id.img_bottom_sheet_circle_green_light)
    public void setGreenLightColorForNote(){
        hexColorChanged = defineColor.GREEN_LIGHT;
        setColorFrameLayout(R.color.circle_green_light);
    }

    @OnClick(R.id.img_bottom_sheet_circle_cyan_light)
    public void setCyanLightColorForNote(){
        hexColorChanged = defineColor.CYAN_LIGHT;
        setColorFrameLayout(R.color.circle_cyan_light);
    }

    @OnClick(R.id.img_bottom_sheet_circle_cyan_light_two)
    public void setCyanLightTwoColorForNote(){
        hexColorChanged = defineColor.CYAN_LIGHT_TWO;
        setColorFrameLayout(R.color.circle_cyan_light_two);
    }

    @OnClick(R.id.img_bottom_sheet_circle_blue_light)
    public void setBlueLightColorForNote(){
        hexColorChanged = defineColor.BLUE_LIGHT;
        setColorFrameLayout(R.color.circle_blue_light);
    }

    @OnClick(R.id.img_bottom_sheet_circle_violet_light)
    public void setVioletLightColorForNote(){
        hexColorChanged = defineColor.VIOLET_LIGHT;
        setColorFrameLayout(R.color.circle_violet_light);
    }

    @OnClick(R.id.img_bottom_sheet_circle_pink_light)
    public void setPinkLightColorForNote(){
        hexColorChanged = defineColor.PINK_LIGHT;
        setColorFrameLayout(R.color.circle_pink_light);
    }

    @OnClick(R.id.img_bottom_sheet_circle_brown_light)
    public void setBrownLightColorForNote(){
        hexColorChanged = defineColor.CYAN_LIGHT_TWO;
        setColorFrameLayout(R.color.circle_brown_light);
    }

    @OnClick(R.id.img_bottom_sheet_circle_greylight)
    public void setGreyLightColorForNote(){
        hexColorChanged = defineColor.GREY_LIGHT;
        setColorFrameLayout(R.color.circle_grey);
    }

}

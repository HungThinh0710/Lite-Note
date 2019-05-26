package com.xuanvu.hungthinh.litenote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xuanvu.hungthinh.litenote.Adapter.GridViewAdapter;
import com.xuanvu.hungthinh.litenote.Database.MyDatabase;
import com.xuanvu.hungthinh.litenote.Model.Note;
import com.xuanvu.hungthinh.litenote.SharedPreferences.SharedPreferencesColumnsGridView;
import com.xuanvu.hungthinh.litenote.View.CreateNoteActivity;
import com.xuanvu.hungthinh.litenote.View.DetailNoteActivity;
import com.xuanvu.hungthinh.litenote.View.SearchNoteActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GridView gridView;
    private Button btnSwitchColumn;
    private EditText edtSearchNote;
    private SharedPreferencesColumnsGridView sharedPreferencesColumnsGridView ;
    private MyDatabase myDatabase;
    public GridViewAdapter gridViewAdapter;
    private List<Note> listNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//
                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, (View)findViewById(R.id.fab), "Create_note_cre_to_back");
//                startActivity(intent, options.toBundle());
                startActivityForResult(intent,200,options.toBundle());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
        searchNote();
        switchColumnsGridView();
        setOnItemClick();
    }

    private void init() {
        gridView = findViewById(R.id.gridView);
        btnSwitchColumn = findViewById(R.id.btnSwitchColumn);
        edtSearchNote = findViewById(R.id.edtSearchNote);
//        String Title []  = {"Title 1","Title 2", "Title 3", "Title 4", "Title 5"};
//        String Content[] = {"Content 1","Content 2", "Content 3 day ne ", "Content 4", "Content5"};

        listNote = new ArrayList<Note>();
        myDatabase = new MyDatabase(this);
        listNote = myDatabase.getAllNotes();

        gridViewAdapter = new GridViewAdapter(this, (ArrayList) listNote);
        gridView.setAdapter(gridViewAdapter);

        //Check and get girdView columns
        sharedPreferencesColumnsGridView = new SharedPreferencesColumnsGridView(this);
        if(sharedPreferencesColumnsGridView.checkExist(this) == false){
            btnSwitchColumn.setBackgroundResource(R.drawable.ic_gridview_2col);
            sharedPreferencesColumnsGridView.setGridViewColumn(2);
        }

        gridView.setNumColumns(sharedPreferencesColumnsGridView.getGridViewColumns());
        if(sharedPreferencesColumnsGridView.getGridViewColumns() == 2) btnSwitchColumn.setBackgroundResource(R.drawable.ic_gridview_2col);
        else btnSwitchColumn.setBackgroundResource(R.drawable.ic_gridview_1col);



        /*
        * Demo Add Note to Database
        * */
//        Note note = new Note("Nho di SG","SG dep lam do ai oiiiii","#NHTNOKIA","26622");
//        Note note2 = new Note("Quang Ngai","QBDBDFBDF DFGDssfDfg sd sdfsDfSdfDFSDfi","#NHTNOKIA","26622");
//        Note note3 = new Note("NsdfsdfsdfG","SGsdfsdfo ai dfsdfsdfsdfsdfoiiiii","#NHTNOKIA","26622");
//        MyDatabase myDatabase = new MyDatabase(this);
//        myDatabase.addNote(note);
//        myDatabase.addNote(note2);
//        myDatabase.addNote(note3);
    }

    private void switchColumnsGridView() {
        btnSwitchColumn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPreferencesColumnsGridView.getGridViewColumns() == 2){
                    gridView.setNumColumns(1);
                    btnSwitchColumn.setBackgroundResource(R.drawable.ic_gridview_1col);
                    sharedPreferencesColumnsGridView.setGridViewColumn(1);
                }
                else{
                    gridView.setNumColumns(2);
                    btnSwitchColumn.setBackgroundResource(R.drawable.ic_gridview_2col);
                    sharedPreferencesColumnsGridView.setGridViewColumn(2);
                }
//                gridView.deferNotifyDataSetChanged();
                gridViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void searchNote() {
        edtSearchNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchNoteActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, (View)findViewById(R.id.frameSearchNote), "frameTransSearchNote");
                startActivity(intent, options.toBundle());

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setOnItemClick() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View convertViewDetailNote = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_detail_note,null);
                LinearLayout linearDetailLayout = convertViewDetailNote.findViewById(R.id.layout_detail_note);
                linearDetailLayout.setTransitionName("layout_detail_trans"+ position );
                View convertViewGridNote = LayoutInflater.from(MainActivity.this).inflate(R.layout.gridview_row,null);
                LinearLayout linearGridLayout = convertViewGridNote.findViewById(R.id.layout_gridview_note);
                linearGridLayout.setTransitionName("layout_detail_trans"+ position );
                Log.d("debn_acti_detail_n_tran",linearDetailLayout.getTransitionName());
                Log.d("debn_acti_grid_n_tran",linearGridLayout.getTransitionName());




                Intent intent = new Intent(MainActivity.this, DetailNoteActivity.class);
                //Get information
                Note note = new Note();
                note = listNote.get(position);
                intent.putExtra("NoteObject", note);

//                linearGridLayout.setTransitionName("layout_detail_trans"+ position) ;
                //Debug full log
//                Log.d("debn_acti_grid_n_tran",linearGridLayout.getTransitionName());
//
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(MainActivity.this, (View)findViewById(R.id.layout_gridview_note), linearDetailLayout.getTransitionName());
                startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200){
            boolean resultCreate = data.getBooleanExtra("MESSAGE",false);
            Log.d("info", String.valueOf(resultCreate));

            if(resultCreate == true){
                listNote = new ArrayList<Note>();
                myDatabase = new MyDatabase(this);
                listNote = myDatabase.getAllNotes();

                gridViewAdapter = new GridViewAdapter(this, (ArrayList) listNote);
                gridView.setAdapter(gridViewAdapter);
                gridViewAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(this, "Note has been deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

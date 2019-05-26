package com.xuanvu.hungthinh.litenote.Adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xuanvu.hungthinh.litenote.MainActivity;
import com.xuanvu.hungthinh.litenote.Model.Note;
import com.xuanvu.hungthinh.litenote.R;
import com.xuanvu.hungthinh.litenote.View.DetailNoteActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Note> arrayListNote;

    public GridViewAdapter(Context context, ArrayList<Note> arrayListNote ) {
        this.context = context;
        this.arrayListNote = arrayListNote;
    }

    @Override
    public int getCount() {
        return arrayListNote.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.gridview_row,null);
        LinearLayout linearLayout = convertView.findViewById(R.id.layout_gridview_note);
        TextView txtTitle = convertView.findViewById(R.id.GridView_TitleText);
        TextView txtContent = convertView.findViewById(R.id.GridView_ContentText);
        EditText edt = convertView.findViewById(R.id.edt_detail_title);

        View cvDetailLayout = layoutInflater.inflate(R.layout.activity_detail_note,null);
        LinearLayout linearLayout1 = cvDetailLayout.findViewById(R.id.layout_detail_note);

//        linearLayout.setTransitionName("s");
//        linearLayout1.setTransitionName("s");
        linearLayout.setTransitionName("layout_detail_trans"+ position );
        txtTitle.setText(arrayListNote.get(position).getmTitle());
        txtContent.setText(arrayListNote.get(position).getmContent());

        Log.d("debn_acti_adapte_n_tran",linearLayout.getTransitionName());

        return convertView;

    }


}

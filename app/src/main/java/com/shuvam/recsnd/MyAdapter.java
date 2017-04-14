package com.shuvam.recsnd;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<File> files = new ArrayList<>();
    MyAdapter(ArrayList<File> list)
    {
        this.files = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.file_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder itemViewHolder, int position) {


        itemViewHolder.fileName.setText(""+files.get(position).getName());
        itemViewHolder.fileSize.setText(""+(files.get(position).length()/(long)1024)+" KB");
        //Here you can fill your row view
    }

    @Override
    public int getItemCount() {

        return files.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView fileName;
        private TextView fileSize;
        private LinearLayout ll;
        public ViewHolder(final View itemView) {
            super(itemView);
            fileName = (TextView)itemView.findViewById(R.id.fileName);
            fileSize = (TextView)itemView.findViewById(R.id.fileSize);
            ll = (LinearLayout)itemView.findViewById(R.id.parentLinearLayout);
        }

        @Override
        public void onClick(View view) {
            Log.d("Position:",""+getLayoutPosition());
        }
    }
}

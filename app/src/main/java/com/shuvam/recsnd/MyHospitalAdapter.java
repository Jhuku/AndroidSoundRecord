package com.shuvam.recsnd;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class MyHospitalAdapter extends RecyclerView.Adapter<MyHospitalAdapter.ViewHolder> {

    ArrayList<Hospital> hps = new ArrayList<>();

    MyHospitalAdapter(ArrayList<Hospital> h){

        this.hps= h;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hospital_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder itemViewHolder, int position) {


        itemViewHolder.tvHospitalName.setText(hps.get(position).getName());
        itemViewHolder.tvHospitalVicinity.setText(hps.get(position).getVicinity());
        //Here you can fill your row view
    }

    @Override
    public int getItemCount() {

        return hps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHospitalName;
        TextView tvHospitalVicinity;

        public ViewHolder(View itemView) {
            super(itemView);

            tvHospitalName = (TextView)itemView.findViewById(R.id.hospitalName);
            tvHospitalVicinity = (TextView)itemView.findViewById(R.id.hospitalVicinity);
        }
    }
}

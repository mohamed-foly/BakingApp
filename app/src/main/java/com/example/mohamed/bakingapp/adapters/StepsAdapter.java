package com.example.mohamed.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamed.bakingapp.R;
import com.example.mohamed.bakingapp.model.Step;

import java.util.List;


public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Step> steps;
    private final StepsAdapter.OnItemClickListener listener;

    public StepsAdapter(Context context, List<Step> steps, StepsAdapter.OnItemClickListener listener){
        this.context = context;
        this.steps = steps;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.step_row,parent,false);

        return new ItemRow(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

       // new Image().Image(context, "http://"+ServerIp+"/smva/recipes/"+recipes.get(position).img,((ItemRow)holder).img_tv);
        ((ItemRow)holder).name_tv.setText(steps.get(position).getShortDescription());
        //Log.e("desc:",steps.get(position).getShortDescription());
        //Log.e("Picasso",ServerIp);
        ((ItemRow)holder).bind(steps.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class ItemRow extends RecyclerView.ViewHolder{

        TextView name_tv ;
        ItemRow(View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.step_desc);
        }


        void bind(final Step item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Step item);
    }


}
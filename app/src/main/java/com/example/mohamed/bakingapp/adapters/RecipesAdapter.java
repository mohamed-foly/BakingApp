package com.example.mohamed.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.bakingapp.R;
import com.example.mohamed.bakingapp.model.Recipe;

import java.util.ArrayList;


public class RecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private ArrayList<Recipe> recipes;
    private final RecipesAdapter.OnItemClickListener listener;

    public RecipesAdapter(Context context, ArrayList<Recipe> recipes, RecipesAdapter.OnItemClickListener listener){
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.recipe_row,parent,false);

        return new ItemRow(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

       // new Image().Image(context, "http://"+ServerIp+"/smva/recipes/"+recipes.get(position).img,((ItemRow)holder).img_tv);
        ((ItemRow)holder).name_tv.setText(recipes.get(position).getName());

        //Log.e("Picasso",ServerIp);
        ((ItemRow)holder).bind(recipes.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ItemRow extends RecyclerView.ViewHolder{

        TextView name_tv ;
        ImageView img_tv ;
        ItemRow(View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.recipe_name);
            img_tv = itemView.findViewById(R.id.recipe_image);
        }


        void bind(final Recipe item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Recipe item);
    }


}
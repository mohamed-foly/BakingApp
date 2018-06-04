package com.example.mohamed.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamed.bakingapp.R;
import com.example.mohamed.bakingapp.model.Ingredient;
import com.example.mohamed.bakingapp.model.Step;

import java.util.List;


public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Ingredient> ingredients;
    private final IngredientAdapter.OnItemClickListener listener;

    public IngredientAdapter(Context context, List<Ingredient> ingredients, IngredientAdapter.OnItemClickListener listener){
        this.context = context;
        this.ingredients = ingredients;
        this.listener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.ingredient_row,parent,false);

        return new ItemRow(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

       // new Image().Image(context, "http://"+ServerIp+"/smva/recipes/"+recipes.get(position).img,((ItemRow)holder).img_tv);
        ((ItemRow)holder).name_tv.setText(ingredients.get(position).getIngredient());
        ((ItemRow)holder).quantity_tv.setText(String.valueOf(ingredients.get(position).getQuantity()));
        ((ItemRow)holder).measure_tv.setText(ingredients.get(position).getMeasure());

        ((ItemRow)holder).bind(ingredients.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ItemRow extends RecyclerView.ViewHolder{

        TextView name_tv ;
        TextView quantity_tv ;
        TextView measure_tv ;
        ItemRow(View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.ingredient_desc);
            quantity_tv = itemView.findViewById(R.id.quantity);
            measure_tv = itemView.findViewById(R.id.measure);
        }


        void bind(final Ingredient item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Ingredient item);
    }


}
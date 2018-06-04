package com.example.mohamed.bakingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mohamed.bakingapp.adapters.IngredientAdapter;
import com.example.mohamed.bakingapp.adapters.RecipesAdapter;
import com.example.mohamed.bakingapp.adapters.StepsAdapter;
import com.example.mohamed.bakingapp.model.Ingredient;
import com.example.mohamed.bakingapp.model.Recipe;
import com.example.mohamed.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;


public class Recipe_detailsFragment extends Fragment {
View masterView;
List<Step> steps;
    List<Ingredient> ingredients;
    StepsAdapter stepsAdapter;
    IngredientAdapter ingredientAdapter;
    public Recipe_detailsFragment() {
    }
    @SuppressLint("ValidFragment")
    public Recipe_detailsFragment(List<Step> steps , List<Ingredient> ingredients) {
        this.steps = steps;
        this.ingredients= ingredients;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        masterView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        StepsAdapter.OnItemClickListener onItemClickListener = new StepsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Step item) {
                //Toast.makeText(getContext(), "not Finished Yet", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),step_instructionActivity.class);
                ArrayList<Step> steps_Array = new ArrayList<>(steps);
                intent.putExtra("step",steps.indexOf(item));


                intent.putParcelableArrayListExtra("steps",steps_Array);
                startActivity(intent);
            }
        };

        stepsAdapter = new StepsAdapter(getContext(),steps,onItemClickListener);


        RecyclerView recyclerView = masterView.findViewById(R.id.steps_viewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(stepsAdapter);

        IngredientAdapter.OnItemClickListener onItemClickListener1 = new IngredientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Ingredient item) {
                Toast.makeText(getContext(), "not Finished Yet", Toast.LENGTH_SHORT).show();
            }
        };

        ingredientAdapter= new IngredientAdapter(getContext(),ingredients,onItemClickListener1);
        RecyclerView recyclerView1 = masterView.findViewById(R.id.ingredient_viewer);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView1.setAdapter(ingredientAdapter);

        return masterView;
    }
}

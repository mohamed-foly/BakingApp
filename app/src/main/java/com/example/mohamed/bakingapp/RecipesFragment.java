package com.example.mohamed.bakingapp;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamed.bakingapp.adapters.RecipesAdapter;
import com.example.mohamed.bakingapp.interfaces.OnRecipeSelected;
import com.example.mohamed.bakingapp.model.Recipe;

import java.util.ArrayList;


public class RecipesFragment extends Fragment {
    ArrayList<Recipe> recipes;
    View masterView;
OnRecipeSelected onRecipeSelected;
    public void setRecipes(ArrayList<Recipe> recipes, OnRecipeSelected onRecipeSelected) {
        this.recipes = recipes;
        this.onRecipeSelected = onRecipeSelected;
        RecipesAdapter recipesAdapter = new RecipesAdapter(getContext(),recipes,onRecipeSelected);
        RecyclerView recyclerView = masterView.findViewById(R.id.recipe_viewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recipesAdapter);

    }

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        masterView = inflater.inflate(R.layout.fragment_recipes, container, false);
        return masterView;
    }

}

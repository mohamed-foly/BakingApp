package com.example.mohamed.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.mohamed.bakingapp.interfaces.OnGrab_Recipes;
import com.example.mohamed.bakingapp.interfaces.OnRecipeSelected;
import com.example.mohamed.bakingapp.model.Recipe;
import com.example.mohamed.bakingapp.tasks.Grab_Recipes;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnGrab_Recipes,OnRecipeSelected {
    private String Recipes_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    RecipesFragment recipesFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager() ;
        recipesFragment = new RecipesFragment();
        fragmentManager.beginTransaction().add(R.id.Master_fragment_container,recipesFragment).commit();


        new Grab_Recipes(this).execute(Recipes_URL);


    }

    @Override
    public void onGrab_Recipes_listener(ArrayList<Recipe> recipes) {

        if (recipes != null){

            recipesFragment.setRecipes(recipes,this);

            for (Recipe x :recipes) {
                Log.d("RecipeGrabbedRow",x.getName());
            }
        }else{
            Log.e("RecipesArray","Null !");

        }


    }


    @Override
    public void onRecipeSelected_listener(Recipe recipe) {
        Toast.makeText(getApplicationContext(), recipe.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),Recipe_details.class);
        intent.putExtra("foly",recipe);
        startActivity(intent);
    }
}

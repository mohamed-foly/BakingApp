package com.example.mohamed.bakingapp.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mohamed.bakingapp.interfaces.OnGrab_Recipes;
import com.example.mohamed.bakingapp.model.Ingredient;
import com.example.mohamed.bakingapp.model.Recipe;
import com.example.mohamed.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Grab_Recipes extends AsyncTask<String, Void, ArrayList<Recipe>> {
    private OnGrab_Recipes onGrab_recipes;
    public Grab_Recipes(OnGrab_Recipes onGrab_recipes) {
        this.onGrab_recipes = onGrab_recipes;
    }

    @Override
    protected ArrayList<Recipe> doInBackground(String... strings) {
        InputStream inputStream;
        ArrayList<Recipe> recipes ;
        // HTTP
        try {
            URL mURL = new URL(strings[0]);
            HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("GET");

            inputStream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            inputStream.close();

            JSONArray jsonArray;
            JSONArray ingredients;
            JSONArray steps;

            jsonArray = new JSONArray(sb.toString());

            recipes = new ArrayList<>();



            JSONObject temp ;
            Recipe temp_recipe;

            for(int c =0 ; c<jsonArray.length() ; c++){
                temp_recipe = new Recipe();
                Log.d("JsonRow",jsonArray.get(c).toString());
                temp = jsonArray.optJSONObject(c);
                temp_recipe.setId(temp.optInt("id"));
                temp_recipe.setName(temp.optString("name"));
                temp_recipe.setServings(temp.optInt("servings"));
                temp_recipe.setImage(temp.optString("image"));
                ingredients = temp.getJSONArray("ingredients");
                List<Ingredient> temp_ingredient_list  =  new ArrayList<>();
                JSONObject temp_ingredient_json ;
                Ingredient temp_ingredient ;
                for (int cc = 0 ; cc < ingredients.length();cc++){
                    temp_ingredient = new Ingredient();
                    temp_ingredient_json = ingredients.optJSONObject(cc);
                    temp_ingredient.setQuantity(temp_ingredient_json.optInt("quantity"));
                    temp_ingredient.setMeasure(temp_ingredient_json.optString("measure"));
                    temp_ingredient.setIngredient(temp_ingredient_json.optString("ingredient"));
                    temp_ingredient_list.add(temp_ingredient);
                }
                temp_recipe.setIngredients(temp_ingredient_list);

                steps = temp.getJSONArray("steps");
                List<Step> temp_steps_list  =  new ArrayList<>();
                JSONObject temp_steps_json ;
                Step temp_step ;

                for (int s = 0 ; s < steps.length();s++){

                    temp_step = new Step();
                    temp_steps_json = steps.optJSONObject(s);
                    temp_step.setId(temp_steps_json.optInt("id"));
                    temp_step.setShortDescription(temp_steps_json.optString("shortDescription"));
                    temp_step.setDescription(temp_steps_json.optString("description"));
                    temp_step.setVideoURL(temp_steps_json.optString("videoURL"));
                    temp_step.setThumbnailURL(temp_steps_json.optString("thumbnailURL"));
                    temp_steps_list.add(temp_step);
                }
                temp_recipe.setSteps(temp_steps_list);

                recipes.add(temp_recipe);
            }

            for (Recipe r:recipes) {
                Log.d("RecipeRow",r.getName());
            }


        } catch(Exception e) {
            Log.e("RecipesTask",e.getMessage());
            return null;
        }
        return recipes;
    }

    @Override
    protected void onPostExecute(ArrayList<Recipe> recipes) {
        super.onPostExecute(recipes);
        onGrab_recipes.onGrab_Recipes_listener(recipes);
    }
}
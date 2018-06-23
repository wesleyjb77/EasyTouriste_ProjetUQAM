package com.ingwesley.www.easytouriste_true;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ingwesley.www.easytouriste_true.All_Adapters.FavoritesAdapter;
import com.ingwesley.www.easytouriste_true.All_Adapters.Listing_All_Adapter;
import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FavoritesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper myDb;
    private List<ModelEndroits> listEndroitFav;
    FavoritesAdapter adapter;
    MaterialSearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_listing);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        recyclerView = findViewById(R.id.endroit_rec);
        setSupportActionBar(toolbar);
        searchView = (MaterialSearchView)findViewById(R.id.search_view);
        myDb = new DatabaseHelper(this);
listEndroitFav=new ArrayList<>();
        load_data_from_server("");
        recyclerView = (RecyclerView) findViewById(R.id.endroit_rec);
        adapter = new FavoritesAdapter(this, listEndroitFav);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    public  void sortArray(){

        for (ModelEndroits en : listEndroitFav) {
            //System.out.println (en.getId());
            if(myDb.checkUser(en.getId())){
                ModelEndroits data = new ModelEndroits(
                        en.getId(),
                        en.getNom(),
                        en.getIllustration(),
                        en.getDescription(),
                        en.getAdresse(),
                        en.getTelephone(),
                        en.getEmail(),
                        en.getStars(),
                        en.getPrix(),
                        en.getId_cat()
                );
                listEndroitFav.add(data);


            }
        }
    }
    public void onResume() {
        super.onResume();
        resetSearch();

    }


    public void resetSearch() {
        listEndroitFav.clear();
        load_data_from_server("");
        adapter = new FavoritesAdapter(this, listEndroitFav);
        recyclerView.setAdapter(adapter);

    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void load_data_from_server(final String id) {

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> task2 = new AsyncTask<Void, Void, Void>() {
            ProgressDialog pdLoading = new ProgressDialog(FavoritesActivity.this);

            @Override
            protected Void doInBackground(Void... voids) {
                String path = getString(R.string.path_fr_all);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path)
                        .build();
                //?id="+integers[0]
                //listEndroit.removeAll(listEndroit);
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {


                        JSONObject object = array.getJSONObject(i);
                        if (myDb.checkUser(object.getString("id"))) {
                            ModelEndroits data = new ModelEndroits(
                                    object.getString("id"),
                                    object.getString("nom"),
                                    object.getString("url"),
                                    object.getString("description"),
                                    object.getString("adresse"),
                                    object.getString("telephone"),
                                    object.getString("email"),
                                    object.getString("stars"),
                                    object.getString("prix"),
                                    object.getString("id_cat")
                            );
                            listEndroitFav.add(data);
                        }

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                //onProgressUpdate();
                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
                pdLoading.dismiss();
                if (listEndroitFav == null) {

                    Toast.makeText(FavoritesActivity.this, "no data found", Toast.LENGTH_LONG);
                }

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //this method will be running on UI thread
                pdLoading.setMessage(getString(R.string.load));
                pdLoading.setCancelable(false);
                pdLoading.show();

            }
        };

        task2.execute();

    }
}
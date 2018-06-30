package com.ingwesley.www.easytouriste_true.gui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ingwesley.www.easytouriste_true.adapters.ListingAdapter;
import com.ingwesley.www.easytouriste_true.R;
import com.ingwesley.www.easytouriste_true.models.ModelEndroits;
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


public class MainListing extends AppCompatActivity {

    String TAG1 = "MainListing";
    String key;
    RecyclerView recyclerView;
    ListingAdapter adapter;
    MaterialSearchView searchView;
    String suggestion[];
    private  static ArrayList<ModelEndroits> listEndroit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_listing);
        Log.e(TAG1, "the probleme");
        Toolbar toolbar = findViewById(R.id.toolbar2);
        recyclerView = findViewById(R.id.endroit_rec);
        setSupportActionBar(toolbar);
        searchView = (MaterialSearchView)findViewById(R.id.search_view);
       //searchView.setOnQueryTextListener((MaterialSearchView.OnQueryTextListener) this);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
     // toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        listEndroit=new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        key=getIntent().getExtras().getString("key");
        load_data_from_server(key);
        //searchView.setSuggestions(suggestion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ListingAdapter(this, listEndroit);
        recyclerView.setAdapter(adapter);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {

                //If closed Search View , lstView will return default
                adapter = new ListingAdapter(MainListing.this, listEndroit);
                recyclerView.setAdapter(adapter);

            }
        });
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText == null || newText.trim().isEmpty()) {
                    resetSearch();
                    return false;
                }
                else {
                    List<ModelEndroits> filteredList = new ArrayList<>();
                    filteredList.addAll(listEndroit);
                    for (ModelEndroits row : listEndroit) {
                        if (!row.getNom().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.remove(row);
                        }
                    }

                    adapter = new ListingAdapter(MainListing.this, filteredList);
                    recyclerView.setAdapter(adapter);
                }
                return false;
            }

        });

    }

    private void load_data_from_server(final String id) {

        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Void> task2 = new AsyncTask<String, Void, Void>() {
            ProgressDialog pdLoading = new ProgressDialog(MainListing.this);
            @Override
            protected Void doInBackground(String... strings) {
                String path=getString(R.string.path_fr);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path+strings[0])
                        .build();
                //?id="+integers[0]
                //listEndroit.removeAll(listEndroit);
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());
                    suggestion=new String[array.length()];
                    for (int i = 0; i < array.length(); i++) {


                        JSONObject object = array.getJSONObject(i);
                        //suggestion[i]=object.getString("nom");
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
                                object.getString("nom_cat")
                        );
                        listEndroit.add(data);

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
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this method will be running on UI thread
                pdLoading.setMessage("\tLoading...");
                pdLoading.setCancelable(false);
                pdLoading.show();

            }
        };

        task2.execute(id);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String isFavChange = data.getStringExtra("isFavChange");
                Toast.makeText(this, isFavChange, Toast.LENGTH_SHORT).show();
                if (isFavChange.equals("1")){

                    resetSearch();
                }
            }
        }


        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    public void onResume() {
        super.onResume();
       // resetSearch();

    }


    public void resetSearch() {
        adapter = new ListingAdapter(this, listEndroit);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
package com.ingwesley.www.easytouriste_true;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.ingwesley.www.easytouriste_true.All_Adapters.Listing_All_Adapter;
import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;
import com.ingwesley.www.easytouriste_true.R;
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
    Listing_All_Adapter adapter;
    MaterialSearchView searchView;
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
       // searchView.setOnQueryTextListener((MaterialSearchView.OnQueryTextListener) this);
        //toolbar.setTitleTextColor(Color.parseColor("#696969"));
      toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        listEndroit=new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        key=getIntent().getExtras().getString("key");
        load_data_from_server(key);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Listing_All_Adapter(this, listEndroit);
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
                adapter = new Listing_All_Adapter(MainListing.this, listEndroit);
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

                    adapter = new Listing_All_Adapter(MainListing.this, filteredList);
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

                    for (int i = 0; i < array.length(); i++) {


                        JSONObject object = array.getJSONObject(i);
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
              if(listEndroit==null){

                  Toast.makeText(MainListing.this,"no data found", Toast.LENGTH_LONG);
              }

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
    private void loadFav(final String id) {

        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Void> task2 = new AsyncTask<String, Void, Void>() {
            ProgressDialog pdLoading = new ProgressDialog(MainListing.this);
            @Override
            protected Void doInBackground(String... strings) {
                String path="https://api.myjson.com/bins/13fbde";
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path+strings[0])
                        .build();
                //?id="+integers[0]
                //listEndroit.removeAll(listEndroit);
                try {
                    Response response = client.newCall(request).execute();
                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {


                        JSONObject object = array.getJSONObject(i);
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
                pdLoading.dismiss();
                adapter.notifyDataSetChanged();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    public void onResume() {
        super.onResume();
        resetSearch();

    }


    public void resetSearch() {
        adapter = new Listing_All_Adapter(this, listEndroit);
        recyclerView.setAdapter(adapter);

    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }




}
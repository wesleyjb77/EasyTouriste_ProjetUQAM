package com.ingwesley.www.easytouriste_true.All_Fragments;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.ingwesley.www.easytouriste_true.All_Adapters.FavoritesAdapter;
import com.ingwesley.www.easytouriste_true.All_Adapters.Listing_All_Adapter;
import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;
import com.ingwesley.www.easytouriste_true.DatabaseHelper;
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

public class FragmentFavorites extends Fragment  implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{
    private MaterialSearchView searchView;
    // private Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseHelper myDb;
    private List<ModelEndroits> listEndroitFav;
    FavoritesAdapter adapter;
    FragmentActivity c;
    String key;
// screen not refresh on resume but in case we change the stared assignment
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_all, container, false);

        c = getActivity();
        myDb = new DatabaseHelper(c);
        // toolbar = view.findViewById(R.id.toolbar1);
        setHasOptionsMenu(true);
        // ((AppCompatActivity) c).setSupportActionBar(toolbar);
//        ((AppCompatActivity) c).getSupportActionBar().setDisplayShowHomeEnabled(true);
        //((AppCompatActivity) c).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setTitleTextColor(Color.parseColor("#696969"));
        // toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //searchView = view.findViewById(R.id.search_view);
        listEndroitFav = new ArrayList<>();
      key=c.getIntent().getExtras().getString("key");

        recyclerView = (RecyclerView) view.findViewById(R.id.endroit_rec);

        load_data_from_server(key);
        adapter = new FavoritesAdapter(c, listEndroitFav);

        recyclerView.setLayoutManager(new LinearLayoutManager(c));

        recyclerView.setAdapter(adapter);
//rate_bar=view.findViewById(R.id.staredBar);
        // rate_bar.setNumStars (3);

        return view;
    }
/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        ((AppCompatActivity) c).getMenuInflater().inflate(R.menu.menu_item, menu);
        //SearchManager searchManager = (SearchManager) ((AppCompatActivity) c).getSystemService(c.SEARCH_SERVICE);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        //
       // SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

       // searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setMaxWidth(Integer.MAX_VALUE);



        ((AppCompatActivity) c).getMenuInflater().inflate(R.menu.location_item, menu);
        super.onCreateOptionsMenu(menu,inflater);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
    }
*/


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        searchView.setQueryHint("Search");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public void onResume(){
        super.onResume();
        //listEndroit = new ArrayList<>();
        load_data_from_server(key);
        //searchView.setIconified(true);
        resetSearch();
    }


    private void load_data_from_server(final String id) {

        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Void> task1 = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                String path=getString(R.string.path_fr);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path+id)
                        .build();
                listEndroitFav.removeAll(listEndroitFav);
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {


                        JSONObject object = array.getJSONObject(i);
                        for (int j = 0; j < myDb.getAllEndroitsId().length; j++) {
                            //String ids= (String) listIds.get(j);
                            //boolean var =(myDb.checkUser(object.getString("id_end"))) ? true : false;
                            if (object.getString("id").equals(myDb.getAllEndroitsId()[j])) {

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
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    System.out.println("End of content");
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task1.execute(id);
    }


    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String newtext) {
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
            filteredList.addAll(listEndroitFav);
            for (ModelEndroits row : listEndroitFav) {
                if (!row.getNom().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.remove(row);
                }
            }

            adapter = new FavoritesAdapter(c, filteredList);
            recyclerView.setAdapter(adapter);
        }
        return false;
    }

    public void resetSearch() {
        adapter = new FavoritesAdapter(c, listEndroitFav);
        recyclerView.setAdapter(adapter);
    }


}

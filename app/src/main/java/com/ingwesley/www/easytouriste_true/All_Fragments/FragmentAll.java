package com.ingwesley.www.easytouriste_true.All_Fragments;

import android.annotation.SuppressLint;

import android.app.SearchManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.ingwesley.www.easytouriste_true.All_Adapters.Listing_All_Adapter;
import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;
import com.ingwesley.www.easytouriste_true.R;
import com.ingwesley.www.easytouriste_true.splash;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import android.view.MenuItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FragmentAll extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

   // private Toolbar toolbar;
    RecyclerView recyclerView;

    private List<ModelEndroits> listEndroit;
    Listing_All_Adapter adapter;
    FragmentActivity c;
    //SearchView searchView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_all, container, false);

        c = getActivity();
       // toolbar = view.findViewById(R.id.toolbar1);
        setHasOptionsMenu(true);

        listEndroit = new ArrayList<>();


        recyclerView = (RecyclerView) view.findViewById(R.id.endroit_rec);

         load_data_from_server(0);
         adapter = new Listing_All_Adapter(c, listEndroit);

           recyclerView.setLayoutManager(new LinearLayoutManager(c));

        recyclerView.setAdapter(adapter);
//rate_bar=view.findViewById(R.id.staredBar);
 // rate_bar.setNumStars (3);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);

    }



    private void load_data_from_server(int id) {

        @SuppressLint("StaticFieldLeak") AsyncTask<Integer, Void, Void> task2 = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                String path=getString(R.string.path_fr);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path)
                        .build();
                listEndroit.removeAll(listEndroit);
                try {
                    Response response = client.newCall(request).execute();

                    JSONArray array = new JSONArray(response.body().string());

                    for (int i = 0; i < array.length(); i++) {


                        JSONObject object = array.getJSONObject(i);

                        ModelEndroits data = new ModelEndroits(
                                object.getString("id_end"),
                                object.getString("nom_end"),
                                object.getString("illustration_End"),
                                object.getString("description_end"),
                                object.getString("adresse_end"),
                                object.getString("Telephone1"),
                                object.getString("Email"),
                                object.getString("ville"));
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
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Toast.makeText(c, "Starting programme ", Toast.LENGTH_SHORT).show();
            }
        };

        task2.execute(id);
    }


    @Override
    public void onResume(){
        super.onResume();
        //listEndroit = new ArrayList<>();
        //listEndroit.removeAll(listEndroit);
        //load_data_from_server(0);
        //searchView.setIconified(true);
        resetSearch();
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
            filteredList.addAll(listEndroit);
            for (ModelEndroits row : listEndroit) {
                if (!row.getNom().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.remove(row);
                }
            }

            adapter = new Listing_All_Adapter(c, filteredList);
            recyclerView.setAdapter(adapter);
        }
        return false;
    }

    public void resetSearch() {
        adapter = new Listing_All_Adapter(c, listEndroit);
        recyclerView.setAdapter(adapter);
    }
}

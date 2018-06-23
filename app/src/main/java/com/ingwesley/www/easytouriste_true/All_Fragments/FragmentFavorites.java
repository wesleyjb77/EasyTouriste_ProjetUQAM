package com.ingwesley.www.easytouriste_true.All_Fragments;
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
import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;
import com.ingwesley.www.easytouriste_true.DatabaseHelper;
import com.ingwesley.www.easytouriste_true.R;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

//import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
public class FragmentFavorites extends Fragment  implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{
    private MaterialSearchView searchView;

    RecyclerView recyclerView;
    DatabaseHelper myDb;
    private List<ModelEndroits> listEndroitFav;
    private List<ModelEndroits> listEndroit;
    FavoritesAdapter adapter;
    FragmentActivity c;
// screen not refresh on resume but in case we change the stared assignment
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_fav, container, false);

        c = getActivity();
        myDb = new DatabaseHelper(c);
        setHasOptionsMenu(true);

        listEndroitFav = new ArrayList<>();
        listEndroit = new ArrayList<>();

        //listEndroit = Parcels.unwrap(getArguments().getParcelable("endroits"));
            sortArray();

        recyclerView = (RecyclerView) view.findViewById(R.id.endroit_recfav);
        adapter = new FavoritesAdapter(c, listEndroitFav);

        recyclerView.setLayoutManager(new LinearLayoutManager(c));

        recyclerView.setAdapter(adapter);
        return view;
    }


    public  void sortArray(){

        for (ModelEndroits en : listEndroit) {
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
        listEndroitFav.clear();
        sortArray();
        resetSearch();

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

/*
    private void load_data_from_server(final String id) {

        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Void> task1 = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                String path=getString(R.string.path_fr);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(path+strings[0])
                        .build();
                //listEndroitFav.removeAll(listEndroitFav);
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
    */
///Before Android 4.1, method int android.support.v7.widget.DropDownListView.lookForSelectablePosition(int, boolean) would have incorrectly overridden the package-private method in android.widget.ListView
    @Override
    public void onDetach() {
        super.onDetach();
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

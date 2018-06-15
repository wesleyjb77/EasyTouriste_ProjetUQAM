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
import android.view.View;
import android.view.ViewGroup;

import com.ingwesley.www.easytouriste_true.All_Adapters.Listing_All_Adapter;
import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;
import com.ingwesley.www.easytouriste_true.R;
import android.view.MenuItem;

import org.parceler.Parcels;

import android.widget.SearchView;
import java.util.ArrayList;
import java.util.List;


public class FragmentAll extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{


    RecyclerView recyclerView;

    private List<ModelEndroits> listEndroit;
    Listing_All_Adapter adapter;
    FragmentActivity c;
    //SearchView searchView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycle_all, container, false);
        setHasOptionsMenu(true);
        c = getActivity();
        listEndroit = new ArrayList<>();

        recyclerView = (RecyclerView) view.findViewById(R.id.endroit_rec);
        listEndroit = Parcels.unwrap(getArguments().getParcelable("endroits"));
           recyclerView.setLayoutManager(new LinearLayoutManager(c));
        adapter = new Listing_All_Adapter(c, listEndroit);
        recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        super.onCreateOptionsMenu(menu, inflater);

    }

    public void onResume() {
        super.onResume();
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

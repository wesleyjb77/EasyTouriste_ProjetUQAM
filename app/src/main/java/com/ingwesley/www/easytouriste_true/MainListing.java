package com.ingwesley.www.easytouriste_true;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.EventLogTags;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ingwesley.www.easytouriste_true.All_Adapters.SectionsPageAdapter;
import com.ingwesley.www.easytouriste_true.All_Fragments.FragmentAll;
import com.ingwesley.www.easytouriste_true.All_Fragments.FragmentFavorites;
import com.ingwesley.www.easytouriste_true.All_Fragments.FragmentMostVisited;
import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;
import com.ingwesley.www.easytouriste_true.Helpers.BottomNavigationViewHelper;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainListing extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    private Menu menu;
    String TAG1 = "MainListing";

    private List<ModelEndroits> listEndroit= new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_listing);
        Log.e(TAG1, "the probleme");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#696969"));
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        menu = bottomNavigationView.getMenu();
        menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        String key=getIntent().getExtras().getString("key");
        listEndroit=load_data_from_server(key);
        replaceFragment(new FragmentAll());




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.display_all:
                        //replaceFragment(tab1);
                        replaceFragment(new FragmentAll());
                        item.setChecked(true);
                        break;

                    case R.id.display_most_visited:
                        replaceFragment(new FragmentMostVisited());
                        item.setChecked(true);
                        break;

                    case R.id.display_favorite:
                        // replaceFragment(new Tab3Fragment());
                        replaceFragment(new FragmentFavorites());
                        item.setChecked(true);
                        break;

                }


                return false;
            }
        });

    }
    private ArrayList<ModelEndroits> load_data_from_server(final String id) {

        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Void> task2 = new AsyncTask<String, Void, Void>() {
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
               // adapter.notifyDataSetChanged();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Toast.makeText(c, "Starting programme ", Toast.LENGTH_SHORT).show();
            }
        };

        task2.execute(id);
        return (ArrayList<ModelEndroits>) listEndroit;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ObtainData(fragment);
        fragmentTransaction.replace(R.id.fram, fragment, "FragmentThree");
        //create first framelayout with id fram in the activity where fragments will be displayed
        fragmentTransaction.commit();
    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



public void ObtainData(Fragment fragment){

    Bundle args = new Bundle();
    args.putParcelable("endroits", Parcels.wrap(listEndroit));
    fragment.setArguments(args);

    /////


}

static class InternetConnection {

        /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
        public static boolean checkConnection(Context context) {
            final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    return true;
                }
            }
            return false;
        }
    }


}
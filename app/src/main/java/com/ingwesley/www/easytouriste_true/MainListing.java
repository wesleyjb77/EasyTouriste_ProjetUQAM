package com.ingwesley.www.easytouriste_true;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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




public class MainListing extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    private Menu menu;
    String TAG1 = "MainListing";
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
        FragmentAll tab1=new FragmentAll();
        replaceFragment(tab1);

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
    @Override
    public void onStart() {
        super.onStart();
        // splash t=new splash();
        // Dismiss the toast
        Toast.makeText(this, "Starting listing", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.fram, fragment, "FragmentThree");
        //create first framelayout with id fram in the activity where fragments will be displayed
        fragmentTransaction.commit();
    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
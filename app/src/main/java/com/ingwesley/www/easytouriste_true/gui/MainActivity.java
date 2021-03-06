package com.ingwesley.www.easytouriste_true.gui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ingwesley.www.easytouriste_true.adapters.ExpandableAdapter;
import com.ingwesley.www.easytouriste_true.adapters.SectionsPageAdapter;
import com.ingwesley.www.easytouriste_true.adapters.SlideAdapter;
import com.ingwesley.www.easytouriste_true.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,ExpandableListView.OnGroupClickListener {

    public String hotel;
    String acceuil;
    String hebergement;
    String a_visiter;
    String deplacement;
    //String aide =getString(R.string.aide);
    String contact_nous;
    String a_faire;
    String restauration;
    String meteo;
    String suivez_nous;
    String auberge;
    String site_touristique;
    String sites_naturels;
    String musee;
    String location;
    String ligne_aerienne;
    String restaurant;
    String patisserie;
    String plage;
    String art;
    String chute;
    private DrawerLayout mDrawerLayout;
    ExpandableListView mListView;
    RelativeLayout rl_menu;
    private ExpandableAdapter mlistAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHash;
    String TAG = "MainActivity";
    private ViewPager viewPager;
    private SlideAdapter myadapter;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private Button mprev;
    private Button mNext;
    public int mCurPg;
    SectionsPageAdapter sAdapter;
   Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "Destroy");
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setNavigationIcon(R.drawable.menu_icon);
        // getSupportActionBar().hide();
        mListView = findViewById(R.id.activity_expandable_list_view);
        initData();
        mlistAdapter = new ExpandableAdapter(MainActivity.this, listDataHeader, listHash);
        mListView.setAdapter(mlistAdapter);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mprev = findViewById(R.id.prev);
        mNext = findViewById(R.id.next);
        viewPager = findViewById(R.id.viewpager);
        mDotLayout = findViewById(R.id.dotslayout);
        myadapter = new SlideAdapter(this);
        viewPager.setAdapter(myadapter);
        eventData();
        addDotsIndicator(0);


        viewPager.addOnPageChangeListener(viewListener);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewPager.setCurrentItem(mCurPg + 1);
            }
        });
        mprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewPager.setCurrentItem(mCurPg - 1);
            }
        });
    }

    private void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
       /* if (height < 10)
            height = 200;*/
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_item,menu);
        getMenuInflater().inflate(R.menu.location_item,menu);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        return true;
    }




    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        //cinema
        acceuil = getResources().getString(R.string.acceuil);
        hebergement = getString(R.string.hebergement);
        a_visiter = getString(R.string.a_visiter);
        deplacement = getString(R.string.deplacement);
        //String aide =getString(R.string.aide);
        contact_nous = getString(R.string.contact_nous);
        a_faire = getString(R.string.a_faire);
        restauration = getString(R.string.restauration);
        meteo = getString(R.string.meteo);
        suivez_nous = getString(R.string.suivez_nous);
        hotel = getString(R.string.hotel);
        auberge = getString(R.string.auberge);
        site_touristique = getString(R.string.site_touristique);
        sites_naturels = getString(R.string.sites_naturels);
        musee = getString(R.string.musee);
        location = getString(R.string.location);
        ligne_aerienne = getString(R.string.ligne_aerienne);
        restaurant = getString(R.string.restaurant);
        patisserie = getString(R.string.patisserie);
        plage = getString(R.string.plage);
        art = getString(R.string.art);
        chute = getString(R.string.chute);

        listDataHeader.add(acceuil);
        listDataHeader.add(hebergement);
        listDataHeader.add(restauration);
        listDataHeader.add(deplacement);
        listDataHeader.add(a_visiter);
        listDataHeader.add(a_faire);
        listDataHeader.add(meteo);
        listDataHeader.add(contact_nous);

        listDataHeader.add(suivez_nous);


        List<String> Acceuil = new ArrayList<>();
        //Acceuil.add("This is Expandable ListView");
        List<String> suivezNous = new ArrayList<>();
        List<String> Contact_nous = new ArrayList<>();
        List<String> Meteo = new ArrayList<>();


        List<String> afaire = new ArrayList<>();
        afaire.add(plage);
        afaire.add(chute);

        List<String> avisiter = new ArrayList<>();
        avisiter.add(site_touristique);
        avisiter.add(sites_naturels);
        avisiter.add(musee);
        avisiter.add(art);

        List<String> Hebergement = new ArrayList<>();
        Hebergement.add(hotel);
        Hebergement.add(auberge);

        List<String> Restoration = new ArrayList<>();
        Restoration.add(restaurant);
        Restoration.add(patisserie);

        List<String> Deplacement = new ArrayList<>();
        Deplacement.add(location);
        Deplacement.add(ligne_aerienne);


        listHash.put(listDataHeader.get(0), Acceuil);
        listHash.put(listDataHeader.get(1), Hebergement);
        listHash.put(listDataHeader.get(2), Restoration);
        listHash.put(listDataHeader.get(3), Deplacement);
        listHash.put(listDataHeader.get(4), avisiter);
        listHash.put(listDataHeader.get(5), afaire);
        listHash.put(listDataHeader.get(6), Meteo);
        listHash.put(listDataHeader.get(7), suivezNous);
        listHash.put(listDataHeader.get(8), Contact_nous);


    }

    public void eventData() {

        mListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        mListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });
        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                final String item = (String) listHash.get(listDataHeader.get(groupPosition)).get(childPosition);

                String cat = null;

                if (item.equals(hotel)) {
                    cat = "1";
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
    /*
    Toast.makeText(getApplicationContext(),
            listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

*/
                } else if (item.equals(auberge)) {
                    cat = "1";
                    mDrawerLayout.closeDrawer(Gravity.LEFT);


                } else if (item.equals(site_touristique)) {
                    cat = "6";
                    mDrawerLayout.closeDrawer(Gravity.LEFT);


                } else if (item.equals(sites_naturels)) {

                    cat = "6";
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else if (item.equals(musee)) {

                    cat = "6";
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else if (item.equals(art)) {
                    cat = "6";
                    mDrawerLayout.closeDrawer(Gravity.LEFT);

                } else if (item.equals(plage)) {
                    cat = "4";
                    mDrawerLayout.closeDrawer(Gravity.LEFT);

                } else if (item.equals(restaurant)) {

                    cat = "3";
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else if (item.equals(patisserie)) {

                    cat = "3";
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else if (item.equals(ligne_aerienne)) {


                } else if (item.equals(location)) {


                } else {


                }
                Intent myIntent = new Intent(MainActivity.this, MainListing.class);
                myIntent.putExtra("key", cat); //Optional parameters
                MainActivity.this.startActivity(myIntent);

/*
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " > "
                + listHash.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

*/


                return false;

            }
        });

        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);

               // String group = (String) listHash.get(listDataHeader.get(groupPosition)).toString();
                if (groupPosition==8){

                    Intent favIntent = new Intent(MainActivity.this, FavoritesActivity.class);
                    //favIntent.putExtra("key", cat); //Optional parameters
                    MainActivity.this.startActivity(favIntent);

                }
                if (groupPosition==7){

                  // Intent wtIntent = new Intent(MainActivity.this, MeteoActivity.class);
                    //favIntent.putExtra("key", cat); //Optional parameters
                    //MainActivity.this.startActivity(wtIntent);

                }
               // if()


                return false;
            }
        });


    }

    @Override
    public void onRestart() {
        super.onRestart();
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }

    public void addDotsIndicator(int position) {

        mDots = new TextView[3];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            // mDots[i].setText("0");
            mDots[i].setTextSize(25f);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);

        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurPg = position;
            if (position == 0) {
                mprev.setEnabled(false);
                mNext.setEnabled(true);
                mprev.setVisibility(View.INVISIBLE);
            } else if (position == mDots.length - 1) {
                mprev.setEnabled(true);
                mNext.setEnabled(false);
                mNext.setVisibility(View.INVISIBLE);

            } else {
                mprev.setEnabled(true);
                mNext.setEnabled(true);
                mprev.setVisibility(View.VISIBLE);
                mNext.setVisibility(View.VISIBLE);

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

        return false;
    }
}

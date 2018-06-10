package com.ingwesley.www.easytouriste_true;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ingwesley.www.easytouriste_true.All_Adapters.ExpandableAdapter;
import com.ingwesley.www.easytouriste_true.All_Adapters.SlideAdapter;
import com.ingwesley.www.easytouriste_true.All_Models.MenuNameProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "Destroy");

       // getSupportActionBar().hide();
       rl_menu = findViewById(R.id.rl_menu);
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



        rl_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
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

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        String acceuil =getResources().getString(R.string.acceuil);
        String hebergement = getString(R.string.hebergement);
        String a_visiter =getString(R.string.a_visiter);
        String deplacement =getString(R.string.deplacement);
        //String aide =getString(R.string.aide);
        String contact_nous =getString(R.string.contact_nous);
        String a_faire =getString(R.string.a_faire);
        String restauration=getString(R.string.restauration);
        String meteo = getString(R.string.meteo);
        String suivez_nous = getString(R.string.suivez_nous);
        String hotel = getString(R.string.hotel);
        String auberge = getString(R.string.auberge);
        String site_touristique = getString(R.string.site_touristique);
        String sites_naturels = getString(R.string.sites_naturels);
        String musee = getString(R.string.musee);
        String location=getString(R.string.location);
        String ligne_aerienne = getString(R.string.ligne_aerienne);
        String restaurant=getString(R.string.restaurant);
        String patisserie = getString(R.string.patisserie);
        String plage = getString(R.string.plage);
        String art=getString(R.string.art);
        String chute =getString(R.string.chute);

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
        List<String> suivezNous  = new ArrayList<>();
        List<String> Contact_nous  = new ArrayList<>();
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


                Intent myIntent = new Intent(MainActivity.this,MainListing.class);
                //myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);

                mDrawerLayout.closeDrawer(Gravity.LEFT);

                return false;

            }
        });

        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void addDotsIndicator(int position) {

        mDots = new TextView[2];
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
            } else if (position == mDots.length-1) {
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

}

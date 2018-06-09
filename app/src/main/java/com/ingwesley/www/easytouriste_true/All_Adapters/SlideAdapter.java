package com.ingwesley.www.easytouriste_true.All_Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ingwesley.www.easytouriste_true.R;

import java.util.Objects;

// for display the slide
public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    // list of images

    public int[] lst_images = {
            R.drawable.paper_plane,
            R.drawable.paper_plane
    };

    // list of titles
    public String[] lst_title = {
            "MONUMENT",
            "CULTURE"
    }   ;
    // list of descriptions
    public String[] lst_description = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,"
    };
    // list of background colors
    public int[]  lst_backgroundcolor = {
            R.drawable.back2,
            R.drawable.back3
    };


    public SlideAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst_title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view== object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide,container,false);
        LinearLayout layoutslide = view.findViewById(R.id.slidelinearlayout);
        ImageView imgslide = view.findViewById(R.id.slideimg);
        TextView txttitle= view.findViewById(R.id.txttitle);
        //TextView description = view.findViewById(R.id.txtdescription);
       //layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        layoutslide.setBackgroundResource(lst_backgroundcolor[position]);
       imgslide.setImageResource(lst_images[position]);
        txttitle.setText(lst_title[position]);
       // description.setText(lst_description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}

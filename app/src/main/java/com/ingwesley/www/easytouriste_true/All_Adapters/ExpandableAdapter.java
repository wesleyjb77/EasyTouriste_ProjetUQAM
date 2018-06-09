package com.ingwesley.www.easytouriste_true.All_Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ingwesley.www.easytouriste_true.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class ExpandableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;
    private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET = { android.R.attr.state_expanded };
    private static final int[][] GROUP_STATE_SETS = { EMPTY_STATE_SET, // 0
            GROUP_EXPANDED_STATE_SET // 1
    };

    public ExpandableAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1); // i = Group Item , i1 = ChildItem
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group,null);
        }


        View ind = view.findViewById(R.id.img_list);

        if (ind != null)
        {
            ImageView indicator = (ImageView) ind;
            if (getChildrenCount(i) == 0)
            {
                indicator.setVisibility(View.INVISIBLE);
            }
            else
            {
                indicator.setVisibility(View.VISIBLE);

                int stateSetIndex = (b ? 1 : 0);
                Drawable drawable = indicator.getDrawable();
                drawable.setState(GROUP_STATE_SETS[stateSetIndex]);
            }
        }
        TextView lblListHeader = view.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
/*
        ImageView im=view.findViewById(R.id.img_icon);
       for(int j=0; j<getGroupCount(); j++){
           switch (j){

               case 1: {
                   im.setImageResource(R.drawable.ic_location);
               }
               break;
               case 2: {
                   im.setImageResource(R.drawable.ic_mail);
               } break;
               case 3: {
                   im.setImageResource(R.drawable.ic_call);

               }break;
               case 4: {
                   im.setImageResource(R.drawable.ic_call);

               } break;
            default:
                    im.setImageResource(R.drawable.ic_home);
           }
/

       }
       */

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String)getChild(i,i1);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =inflater.inflate(R.layout.list_item,null);
        }

        TextView txtListChild = view.findViewById(R.id.lblListItem);
        txtListChild.setText(childText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}

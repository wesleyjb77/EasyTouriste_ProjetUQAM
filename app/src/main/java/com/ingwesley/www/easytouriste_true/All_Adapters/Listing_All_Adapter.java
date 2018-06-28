package com.ingwesley.www.easytouriste_true.All_Adapters;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;
import com.ingwesley.www.easytouriste_true.Helpers.DatabaseHelper;
import com.ingwesley.www.easytouriste_true.GUI.DescActivity;
import com.ingwesley.www.easytouriste_true.R;
import java.util.List;

public class Listing_All_Adapter extends RecyclerView.Adapter<Listing_All_Adapter.ViewHolder> {
    private Context context;
    private List<ModelEndroits> endroits;
    DatabaseHelper myDb;

    public Listing_All_Adapter(Context context, List<ModelEndroits> endroits) {
        this.endroits = endroits;
        this.context = context;
        myDb = new DatabaseHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item,parent,false);
        final ViewHolder holder = new ViewHolder(itemView) ;
        holder.view_container.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int position=holder.getAdapterPosition();
                Intent intent = new Intent(context, DescActivity.class);
                intent.putExtra("id", endroits.get(position).getId());
                intent.putExtra("nom", endroits.get(position).getNom());
                intent.putExtra("img", endroits.get(position).getIllustration());
                intent.putExtra("adresse", endroits.get(position).getAdresse());
                intent.putExtra("telephone", endroits.get(position).getTelephone());
                intent.putExtra("mail", endroits.get(position).getEmail());
                intent.putExtra("description", endroits.get(position).getDescription());
                intent.putExtra("nom_cat", endroits.get(position).getNom_cat());

                context.startActivity(intent);


            }


        });
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();

                if (!myDb.checkUser(endroits.get(position).getId())) {
                    holder.fav.setImageResource(R.drawable.ic_favorite);
                    myDb.insertData(endroits.get(position).getId());
                    Toast.makeText(context, "add " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                }
                else {
                    holder.fav.setImageResource(R.drawable.ic_unfavorite);
                    myDb.deleteData(endroits.get(position).getId());
                    Toast.makeText(context, "Remove " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                }
            }



        });
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nom.setText(endroits.get(position).getNom());
        holder.adresse.setText(endroits.get(position).getAdresse());

        Glide.with(context).load(endroits.get(position).getIllustration()).into(holder.img_end);
        if (myDb.checkUser(endroits.get(position).getId())) {
            holder.fav.setImageResource(R.drawable.ic_favorite);
        }
        else {
            holder.fav.setImageResource(R.drawable.ic_unfavorite);
        }


    }



    @Override
    public int getItemCount() {
        return endroits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nom;
        public TextView id;
        public ImageView img_end;
        public TextView description;
        public TextView adresse;
        public TextView email;
        public TextView ville;
        public ImageView fav;

        LinearLayout view_container;
        public ViewHolder(View itemView) {
            super(itemView);
            nom=itemView.findViewById(R.id.nom_end);
            img_end=itemView.findViewById(R.id.img_end);
            adresse=itemView.findViewById(R.id.adresse);
            fav=itemView.findViewById(R.id.fav);
            view_container=itemView.findViewById(R.id.view_container);

        }


    }

}


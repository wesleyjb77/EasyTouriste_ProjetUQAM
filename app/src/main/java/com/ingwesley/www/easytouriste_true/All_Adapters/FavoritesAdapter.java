package com.ingwesley.www.easytouriste_true.All_Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;
import com.ingwesley.www.easytouriste_true.DatabaseHelper;
import com.ingwesley.www.easytouriste_true.DescActivity;
import com.ingwesley.www.easytouriste_true.R;

import org.parceler.Parcel;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private Context context;
    String path;
    private List<ModelEndroits> endroits;
    private List<ModelEndroits> listEndroitFiltered;
    DatabaseHelper myDb;

  //  private Listing_All_AdapterListener listener;
    public FavoritesAdapter(Context context, List<ModelEndroits> endroits) {
        this.context = context;
        this.endroits = endroits;
        this.listEndroitFiltered = endroits;

        myDb = new DatabaseHelper(context);
       // getFilter();
           // path="http://192.168.15.232/Easytouriste_mobile/images/endroits/";

         path="";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.each_item,parent,false);
        return new ViewHolder(itemView,context,endroits);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.id.setText(endroits.get(position).getId());
        holder.nom.setText(endroits.get(position).getNom());
        holder.adresse.setText(endroits.get(position).getAdresse());
        holder.Telephone.setText(endroits.get(position).getTelephone());
        holder.description.setText(endroits.get(position).getDescription());
        holder.email.setText(endroits.get(position).getEmail());
        holder.ville.setText(endroits.get(position).getVille());

        Glide.with(context).load(path+endroits.get(position).getIllustration()).into(holder.img_end);
        if (myDb.checkUser(endroits.get(position).getId())) {
            holder.fav.setImageResource(R.drawable.ic_favorite);
        }
        else {
            holder.fav.setImageResource(R.drawable.ic_unfavorite);
        }


    }

    @Override
    public int getItemCount() {
        return listEndroitFiltered.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nom;
        public TextView id;
        public ImageView img_end;
        public TextView description;
        public TextView adresse;
        public TextView Telephone;
        public TextView email;
        public TextView ville;
        public ImageView fav;
        public Context context;
        List<ModelEndroits> endroit;
        public ViewHolder(final View itemView, final Context context, final List<ModelEndroits> endroits) {
            super(itemView);
            this.context=context;
            this.endroit=endroits;
            id=itemView.findViewById(R.id.id_end);
            nom=itemView.findViewById(R.id.nom_end);
            img_end=itemView.findViewById(R.id.img_end);
            adresse=itemView.findViewById(R.id.adresse);
            description=itemView.findViewById(R.id.description);
            Telephone=itemView.findViewById(R.id.telephone);
            email=itemView.findViewById(R.id.email);
            ville=itemView.findViewById(R.id.ville);
            fav=itemView.findViewById(R.id.fav);
            //action

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                   // Toast.makeText(context, "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    if (!myDb.checkUser(endroits.get(position).getId())) {
                        fav.setImageResource(R.drawable.ic_favorite);
                        //endroit.get(position).getId();
                        myDb.insertData(endroits.get(position).getId());
                        Toast.makeText(context, "add " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        fav.setImageResource(R.drawable.ic_unfavorite);
                        myDb.deleteData(endroits.get(position).getId());
                        removeItem(position);
                        Toast.makeText(context, "Remove " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    }
                    }



            });
            itemView.setOnClickListener(this);

        }

        public void removeItem(int position){
            endroits.remove(position);
            notifyItemRemoved(position);


            }

        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();
            Intent intent = new Intent(context, DescActivity.class);
            intent.putExtra("id", endroit.get(position).getId());
            intent.putExtra("nom", endroit.get(position).getNom());
            intent.putExtra("img", endroit.get(position).getIllustration());
            intent.putExtra("adresse", endroit.get(position).getAdresse());
            intent.putExtra("telephone", endroit.get(position).getTelephone());
            intent.putExtra("mail", endroit.get(position).getEmail());
            intent.putExtra("description", endroit.get(position).getDescription());

            context.startActivity(intent);


        }

    }

}


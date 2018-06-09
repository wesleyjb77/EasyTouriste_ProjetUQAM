package com.ingwesley.www.easytouriste_true.All_Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;
import com.ingwesley.www.easytouriste_true.R;


import java.util.List;

public class MostVisitedAdapter extends RecyclerView.Adapter<MostVisitedAdapter.ViewHolder> {
    private Context context;
    String path;
    private List<ModelEndroits> endroits;

    public MostVisitedAdapter(Context context, List<ModelEndroits> endroits) {
        this.context = context;
        this.endroits = endroits;
        path="http://192.168.15.210/Easytouriste_mobile/images/endroits/";

        //path="http://192.168.43.66/Easytouriste_mobile/images/endroits/";
        // path="http://192.168.43.66/Easytouriste_mobile/images/endroits/";
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.each_most_visited,parent,false);
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
    }

    @Override
    public int getItemCount() {
        return endroits.size();
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
        public Context context;
        List<ModelEndroits> endroit;
        public ViewHolder(View itemView, Context context, List<ModelEndroits> endroits) {
            super(itemView);
            this.context=context;
            this.endroit=endroits;
            id=itemView.findViewById(R.id.id_end1);
            nom=itemView.findViewById(R.id.nom_end1);
            img_end=itemView.findViewById(R.id.img_end1);
            adresse=itemView.findViewById(R.id.adresse1);
            description=itemView.findViewById(R.id.description1);
            Telephone=itemView.findViewById(R.id.telephone1);
            email=itemView.findViewById(R.id.email1);
            ville=itemView.findViewById(R.id.ville1);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            /*
            int position=getAdapterPosition();
            Intent intent = new Intent(context, EndroitShow.class);
            intent.putExtra("id", endroit.get(position).getId());
            intent.putExtra("nom", endroit.get(position).getNom());
            intent.putExtra("img", endroit.get(position).getIllustration());
            intent.putExtra("adresse", endroit.get(position).getAdresse());
            intent.putExtra("telephone", endroit.get(position).getTelephone());
            intent.putExtra("mail", endroit.get(position).getEmail());
            intent.putExtra("description", endroit.get(position).getDescription());

            context.startActivity(intent);
            */


        }

    }
}


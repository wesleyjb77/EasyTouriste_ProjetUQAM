package com.ingwesley.www.easytouriste_true;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static java.net.Proxy.Type.HTTP;

public class DescActivity extends AppCompatActivity {
  // String path="http://192.168.15.210/Easytouriste_mobile/images/endroits/";
  String path="";
   String TAG3="lolll";
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG3, "Destroy");
        setContentView(R.layout.activity_desc_endroit);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        final String id  = getIntent().getExtras().getString("id");
        myDb = new DatabaseHelper(this);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (myDb.checkUser(id)) {
            fab.setImageResource(R.drawable.ic_favorite);
        }
        else {
            fab.setImageResource(R.drawable.ic_unfavorite);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               boolean bool=myDb.checkUser(id);
                // Snackbar.make(view, "Remove "+bool, Snackbar.LENGTH_LONG).setAction("Action", null).show();

                if (bool==false) {
                    myDb.insertData(id);
                    Snackbar.make(view, "add"+id, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    fab.setImageResource(R.drawable.ic_favorite);
                }
                else {
                    myDb.deleteData(id);
                    Snackbar.make(view, "Remove", Snackbar.LENGTH_LONG).setAction("Action1", null).show();
                    fab.setImageResource(R.drawable.ic_unfavorite);

                }

            }
        });

        ImageButton btMail=findViewById(R.id.bt_mail);
        ImageButton btCall=findViewById(R.id.bt_call);
        ImageButton btDirection=findViewById(R.id.bt_direction);


        // hide the default actionbar
       // getSupportActionBar().hide();

        String nom = getIntent().getExtras().getString("nom");
        final String adresse = getIntent().getExtras().getString("adresse") ;
        //String categorie = getIntent().getExtras().getString("categorie");
        final String mail = getIntent().getExtras().getString("mail");
        String img = getIntent().getExtras().getString("img");
        String description = getIntent().getExtras().getString("description") ;
        final String telephone = getIntent().getExtras().getString("telephone") ;
        //final String telephone = "37726106" ;

        //action
        btMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mail!=null){
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
// The intent does not have a URI, so declare the "text/plain" MIME type
                    emailIntent.setType("text/html");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, mail);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                    startActivity(Intent.createChooser(emailIntent, "Send Email"));

                }

            }
        });
        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (telephone!=null) {
                    Uri number = Uri.parse("tel:"+telephone);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);
                }
            }
        });
        btDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adresse!=null) {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+adresse+"+");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });

        // ini views //
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        // setting values to each view
        TextView tv_desc=findViewById(R.id.description_endroit);
        TextView tv_cat=findViewById(R.id.categorie_endroit);
        TextView tv_mail=findViewById(R.id.email_endroit);
        TextView tv_tel=findViewById(R.id.telephone_endroits);
        TextView tv_ad=findViewById(R.id.adresse_endroit);
        ImageView img_end=findViewById(R.id.image_endroit);

        //tv_desc.setText(nom);
        //tv_categorie.setText(categorie);

        tv_desc.setText(description);
        tv_mail.setText(mail);
        tv_tel.setText(telephone);
        tv_ad.setText(adresse);
        Glide.with(this).load(path+img).into(img_end);
        collapsingToolbarLayout.setTitle(nom);






    }
}

package com.ingwesley.www.easytouriste_true;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.ingwesley.www.easytouriste_true.All_Models.ModelEndroits;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AppServices {

    ArrayList<ModelEndroits> listEndroit=new ArrayList<>();
    public AppServices(){


    }




    public ArrayList<ModelEndroits> ServiceData(final String path) {

        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Void> task2 = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                //String path=getString(R.string.path_fr);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(strings[0])
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
                //adapter.notifyDataSetChanged();
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Toast.makeText(c, "Starting programme ", Toast.LENGTH_SHORT).show();
            }
        };

        task2.execute(path);
        return listEndroit;
    }


}

package com.ahmedmansour.accent10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ahmedmansour.accent10.Database.DBAccent;

import java.util.ArrayList;

public class Movies_Libary extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_libary);
        getSupportActionBar().setTitle("Movies Library");

  /*      DBAccent dbAccent =new DBAccent(this);
        ArrayList<String> arrayList =dbAccent.getAllMovies();
        ArrayAdapter arrayAdapter= new ArrayAdapter(this,R.layout.activity_movies_item,R.id.txt_ItemName,arrayList);
*/
        ListView ls = (ListView) findViewById(R.id.ls_MoviesLibary);
        ls.setAdapter(new MyCustonAdapter());
/*        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }

        });*/

    }



   private class MyCustonAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            DBAccent dbAccent=new DBAccent(getBaseContext());
            ArrayList<String> arrayList =dbAccent.getAllMovies();

            LayoutInflater mInflater =getLayoutInflater();
            View mView =mInflater.inflate(R.layout.activity_movies_item,null);
            TextView textView =(TextView)mView.findViewById(R.id.txt_ItemName);

           // ArrayAdapter arrayAdapter= new ArrayAdapter(this,,arrayList);

            textView.setText(arrayList.get(position));


            //ArrayAdapter arrayAdapter= new ArrayAdapter(this,R.layout.activity_movies_item,R.id.txt_ItemName,arrayList);

            return mView;
        }
    }

}

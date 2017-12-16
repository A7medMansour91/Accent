package com.ahmedmansour.accent10;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class FileInfo {
        public  String Path;
        public  String File_Name;

        public FileInfo(String Path,String File_Name){
            this.Path=Path;
            this.File_Name=File_Name;
        }
    }

    public class Movies_List extends AppCompatActivity {
        String but;
        ListView ls;
        EditText TSI ;
        ArrayList<FileInfo> fullpath=new ArrayList<>();
        ArrayList<String> SearchItem=new ArrayList<>();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movies_list);
            Bundle extras = getIntent().getExtras();
            but=extras.getString("Key");



       /*     String tital=new Asynctask().execute("").get();

            MenuItem giornataItem = menu.findItem(R.id.giornata);
            giornataItem.setTitle(tital);

            String tital=new Asynctask().execute("").get();
            setTitle(tital);

            setContentView(R.layout.MainActivity);*/

            // videoview =(VideoView)findViewById(R.id.videoView);




            //list all media
            getFile();
            //full  songspath.add(new SongsInfo("ss","ss","ddd","ddd"));
            ls =(ListView)findViewById(R.id.ls_MoviesList);
            ls.setAdapter(new MyCustonAdapter());
            ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    try{
                      //videoview.setVideoPath(fullsongpath.get(position).Path);
                      //videoview.start();

                          //  Toast.makeText(getBaseContext(), fullpath.get(position).Path,Toast.LENGTH_LONG).show();

                        if (but.equals("srt")) {
                            Intent intent=new Intent();
                            intent.putExtra("PathSRT",fullpath.get(position).Path);
                            setResult(RESULT_FIRST_USER,intent);
                          //  Toast.makeText(getBaseContext(), "SRT",Toast.LENGTH_LONG).show();
                        }else if (but.equals("mp4")){
                            Intent intent=new Intent();
                            intent.putExtra("Title",fullpath.get(position).File_Name);
                            intent.putExtra("PathMP4",fullpath.get(position).Path);
                            setResult(RESULT_OK,intent);
                            //Toast.makeText(getBaseContext(), fullpath.get(position).File_Name,Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e){e.printStackTrace();}
                    finish();

                }

            });
            TSI =(EditText)findViewById(R.id.txt_SearchItem);
            TSI.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = TSI.getText().toString().toLowerCase(Locale.getDefault());
                    listadaptor.filter(text);

                }
            });
        }

        @Override
        public void onBackPressed() {
            Intent intent=new Intent();
            setResult(RESULT_CANCELED,intent);
            finish();
        }


        public  void getFile(){
            Cursor cursor;
            Uri path = MediaStore.Files.getContentUri("external"); // ( "internal","content", "external", "")

            String[] proj ={ MediaStore.Files.FileColumns._ID,
                 MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE, MediaStore.Files.FileColumns.MIME_TYPE} ;

            /*String[] args = {  "ABC.En","ABC.Ar" };
            String mimeType ="TITLE=? OR TITLE=?";*/
            cursor=getContentResolver().query(path,proj,null,null,"title ASC");


            if (cursor!= null){
                if (cursor.moveToFirst()){
                    do {

                        String filePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)).toLowerCase();
                        String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));

                        String REGEX = "(.)(\\."+but+")";
                        Pattern pat = Pattern.compile(REGEX);
                        Matcher mat = pat.matcher(filePath);

                        if (mat.find()) {
                            fullpath.add(new FileInfo(filePath,title));
                        }

                    }while(cursor.moveToNext());
                }
                cursor.close();
            }

        }



        private class  MyCustonAdapter extends BaseAdapter {

            @Override
            public int getCount(){
                return fullpath.size();
            }

            @Override
            public String getItem(int position){
                return null;
            }

            @Override
            public long getItemId(int position){
                return position;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent){

                LayoutInflater mInflater =getLayoutInflater();
                View mView =mInflater.inflate(R.layout.activity_movies_item,null);
                TextView textView =(TextView)mView.findViewById(R.id.txt_ItemName);
                ImageView imageView=(ImageView)mView.findViewById(R.id.imageView2);
                FileInfo f=fullpath.get(position);
                textView.setText(f.File_Name);

                if (but.equals("srt")) {
                    getSupportActionBar().setTitle("Select The Subtitle File");
                    imageView.setImageResource(R.mipmap.ic_video_srt);

                }else if (but.equals("mp4")){
                    getSupportActionBar().setTitle("Choose Your Favorite Movie");
                    imageView.setImageResource(R.mipmap.ic_video_list);
                    }

                return mView;
            }
        }
        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            fullpath.clear();
            if (charText.length() == 0) {
                fullpath.addAll(arraylist);
            } else {
                for (ApplicationInfo wp : arraylist) {
                    if (wp.loadLabel(packageManager).toString().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        fullpath.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }
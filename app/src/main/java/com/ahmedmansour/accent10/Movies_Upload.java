package com.ahmedmansour.accent10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ahmedmansour.accent10.Database.DBAccent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

public class Movies_Upload extends AppCompatActivity {
    public static final int REQUEST_CODE_GetName = 01;
    public static final int REQUEST_CODE_GetStr = 02;
    EditText editText_Title ;
    Button but_ChooseVideo,but_FileSRT;
    String PathSRT,PathMP4,Title,data;

    File FileIN ;
    FileReader FReader ;
    BufferedReader Breader ;
    StringBuilder SBuilder ;



    ArrayList<String> Translate =new ArrayList<>();
    ArrayList<String> Secvideo =new ArrayList<>();
    ArrayList<Integer> StartTime =new ArrayList<>();
    ArrayList<Integer> EndTime =new ArrayList<>();

    ArrayList<String> Time =new ArrayList<>();

    Set<String> Vocabulary =new HashSet<>();
    Vector vocabularyx =new Vector ();

    String regPartNum ="(\\d+)";                       //(\d+) All Num only
    Pattern patPartNum = Pattern.compile(regPartNum);

    String regTimeViduo ="(\\d+)(.*)";                 //(.*?) Text only
    Pattern patTimeViduo =Pattern.compile(regTimeViduo);

    String regTranslate ="(.*)";                      //(.*) Text and syubol
    Pattern patTranslate =Pattern.compile(regTranslate);

    String regNewLine ="(^)";                         //(^) new line
    Pattern patNewLine =Pattern.compile(regNewLine);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_uplode);
        getIntent().getSerializableExtra("MyClass");
        getSupportActionBar().setTitle("Add a Movie");

        but_ChooseVideo = (Button)findViewById(R.id.but_ChooseVideo);
        but_ChooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(),Movies_List.class);
                Bundle extras = new Bundle();
                extras.putString("Key", "mp4");
                intent.putExtras(extras);
                startActivityForResult(intent, REQUEST_CODE_GetName);

            }
        });

        editText_Title = (EditText)findViewById(R.id.editText_Title);
        editText_Title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Title= editText_Title.getText().toString().trim();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//-------------------------------------------------------------------------------------------------------------------------------------------------
       /* String regPartNum ="(\\d+)";                       //(\d+) All Num only
        Pattern patPartNum = Pattern.compile(regPartNum);

        String regTimeViduo ="(\\d+)(.*)";                 //(.*?) Text only
        Pattern patTimeViduo =Pattern.compile(regTimeViduo);

        String regTranslate ="(.*)";                      //(.*) Text and syubol
        Pattern patTranslate =Pattern.compile(regTranslate);

        String regNewLine ="(^)";                         //(^) new line
        Pattern patNewLine =Pattern.compile(regNewLine);

        try {
            data="";
            sb = new StringBuilder(); // بناء سترنج متعدد اي يحتوي على قيم عديدة او سلسلة متغيرة
            //is = this.getAssets().open("ahmed.srt"); // الوصول الى الملف النصي
            fis  = new FileInputStream(PathSRT);
            reader = new BufferedReader(new InputStreamReader(fis, "UTF8")); // قارئ بفرد وهو يستخدم عادة لقراءة الملفات الضخمة, وسوف يقرأ من الملف النصي
            if (fis!=null) { // اذا تم ايجاد الملف
                while ((data = reader.readLine()) != null) { // قم بقراءة البفرد المدخل سطر سطر اي بشكل سليم ووضعه داخل سترنج str
                    data = data.replaceAll("<i>|</i>", "") ; //  "-|--"
                    sb.append(data + "\n" ); // السترنج الذي تم بنائه في البداية قم بإضافة داخله الـسترنج str الذي تخزن داخله الملف النصي


                    Matcher partNum    =patPartNum.matcher(data);
                    Matcher timeViduo  =patTimeViduo.matcher(data);
                    Matcher translate  =patTranslate.matcher(data);
                    Matcher newLine    =patNewLine.matcher(data);


                    if (partNum.matches()) {            // String regexNum ="(\\d+)";

                        Secvideo.add(partNum.group(0));
                        //  System.out.println("PartNum:"+partNum.group(0));
                    }

                    else if (newLine.matches()){              // String regex4 ="(^)";

                        //   System.out.println("NewLine:"+newLine.group(0));                 //result newLine
                    }

                    else if (timeViduo.matches()) {    // String regex2 ="(\\d+)(.*)";

                        Time.add(timeViduo.group(0));
                        String StartEndTime = timeViduo.group(0);

                        String[] parts = StartEndTime.split("-->");

                        String SplitStartTime = parts[0].trim();

                        String[] StartTimeHM = SplitStartTime.split(":");
                        String Hs = StartTimeHM[0];
                        Integer hs =(Integer.parseInt(Hs)*60)*(60*1000);
                        String Ms = StartTimeHM[1];
                        Integer ms =(Integer.parseInt(Ms))*(60*1000);

                        String[] StartTimeSMS = StartTimeHM[2].split(",");
                        String Ss = StartTimeSMS[0];
                        Integer ss =(Integer.parseInt(Ss))*(1000);
                        String Mms = StartTimeSMS[1];
                        Integer mms =Integer.parseInt(Mms);

                        Integer millisecondsStartTime=(hs+ms+ss+mms);
                        StartTime.add(millisecondsStartTime);                       //result StartTime

                        // ----------------------------------------------------------

                        String SplitEndTime = parts[1].trim();

                        String[] EndTimeHM = SplitEndTime.split(":");
                        String He = EndTimeHM[0];
                        Integer he =(Integer.parseInt(He)*60)*(60*1000);
                        String Me = EndTimeHM[1];
                        Integer me =(Integer.parseInt(Me))*(60*1000);

                        String[] EndTimeSMS = EndTimeHM[2].split(",");
                        String Se = EndTimeSMS[0];
                        Integer se =(Integer.parseInt(Se))*(1000);
                        String Mme = EndTimeSMS[1];
                        Integer mme =Integer.parseInt(Mme);

                        Integer millisecondsEndTime=(he+me+se+mme);
                        EndTime.add(millisecondsEndTime);                            //result EndTime

                        //      System.out.println("TimeEnd:Split"+SplitStartTime);
                        //   System.out.println("TimeStart:"+StartTime);

                        //     System.out.println("TimeEnd:Split"+SplitEndTime);
                        //      System.out.println("TimeEnd:"+EndTime);

                        //  System.out.println("TimeViduo:"+timeViduo.group(0));

                    }


                    else if (translate.matches()){ //     String regex3 ="(.*)";

                        String[] Words = translate.group(0).split(" ");


                        for( String word : Words )
                        {
                            word = word.replaceAll("[^a-zA-Z']","").toUpperCase();                       // ?error stop|...|. error ears data
                            Vocabulary.removeAll(Arrays.asList("", null));
                            Vocabulary.add(word);
                        }

                        if (Translate.isEmpty()) {
                            Translate.add(translate.group(0));
                            //  System.out.println("Translate null:"+translate.group(0));
                        }
                        else if(Time.size()==((Translate.size())+1)){

                            Translate.add(translate.group(0));
                            //   System.out.println("Translate:"+translate.group(0));

                        }
                        else if(Time.size()!=((Translate.size())+1)){

                            int Line=Translate.size()-1;
                            String Tline = Translate.get(Line);
                            Translate.remove(Line);
                            Translate.add(Tline+" "+translate.group(0));
                            // System.out.println("Translate reblase :"+Translate.get(Line)); //result  Translate

                        }

                    }

                    else{ Toast.makeText(this, "No Match!", Toast.LENGTH_LONG).show(); }

                }

            }
            Text = (EditText) findViewById(R.id.editText1); // تعريف النص وربطه بالاي دي الخاص به .. النص الذي تريد وضعه داخله الملف النصي
            Text.setText(sb); // استخدم سلسلة السترنج التي تم بنائها وتخزين الملف النصي داخلها في محل النص المحدد

            fis.close(); // اغلاق الادخال
        } catch (IOException e) { // هنا توضع الاوامر اذا ظهر اي خطأ وغالباً ما تستخدم رسالة توست لتخبرك ما هو الخطأ
        }*/
//--------------------------------------------------------------------------------------------------------------------------------------------------
    }

    @Override
    protected void onResume() {
        if (PathMP4 != null) {
            but_ChooseVideo.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        }
        if (PathSRT != null) {
            but_FileSRT.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
        }
        super.onResume();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GetName ) {

            editText_Title.setText(data.getStringExtra("Title"));

            PathMP4= data.getStringExtra("PathMP4");
            Title= editText_Title.getText().toString().trim();
        }
        if (requestCode == REQUEST_CODE_GetStr) {
            but_FileSRT = (Button)findViewById(R.id.but_FileSRT);
            PathSRT= data.getStringExtra("PathSRT");
        }
    }

    public void OK_FileSave(View view) throws IOException {
        if (PathMP4 == null) {
            Toast.makeText(getBaseContext(), "Please select the video",Toast.LENGTH_LONG).show();}
        else if(PathSRT == null){ Toast.makeText(getBaseContext(), "Please select the file srt ",Toast.LENGTH_LONG).show();}
        else if(editText_Title.getText().toString().isEmpty()){editText_Title.setError("Enter The Title");}
        else {
            DBAccent dbAccent = new DBAccent(this);
            dbAccent.Insert_TFSPath(Title, PathMP4, PathSRT);


          //  finish();
        }

/*        try {

           // String Path = "/storage/sdcard1/abc.en.srt";
            FileIN = new File(PathSRT);
            FReader = new FileReader(FileIN);
            Breader = new BufferedReader(FReader);
            SBuilder  = new StringBuilder();

            while ((data = Breader.readLine()) != null) {
                SBuilder.append(data + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this,  SBuilder, Toast.LENGTH_LONG).show();
        Breader.close();*/

    }


    public void ok_FileSRT(View view) {
        Intent intent =new Intent(this ,Movies_List.class);
        Bundle extras = new Bundle();
        extras.putString("Key", "srt");
        intent.putExtras(extras);
        startActivityForResult(intent, REQUEST_CODE_GetStr);
    }

    public void ok_ChooseVideo(View view) {}

    public void ok_Cancel(View view) {
        finish();
    }


}


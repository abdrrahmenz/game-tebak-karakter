package id.kunya.sinsekai;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class DetailChars extends AppCompatActivity {
    private int columnIndex;
    private String fileManagerString,imagePath;
    private ImageView isCorrect,gambare;
    private TextView textcorect,answer,asking,oncoin;
    private DatabaseHelper myDb;
    private String resultes0,resultes4,resultes5,resultes6,resultes7,resultes,resultesLst;
    private SharedPreferences sh_Pref;
    private SharedPreferences.Editor toEdit;
    private MediaPlayer mp;
    private ImageView help1,help2,help3;
    private int strHealerint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chars);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        myDb = new DatabaseHelper(this);
        answer= (TextView) findViewById(R.id.onansw);
        oncoin= (TextView) findViewById(R.id.oncoi);
        asking= (TextView) findViewById(R.id.asking);
        gambare= (ImageView) findViewById(R.id.gambare);
        isCorrect= (ImageView) findViewById(R.id.isCorrect);
        textcorect= (TextView) findViewById(R.id.isCorrect2);
        help1= (ImageView) findViewById(R.id.help1);
        help2= (ImageView) findViewById(R.id.help2);
        help3= (ImageView) findViewById(R.id.help3);
        View background= findViewById(R.id.bgColor);


        SharedPreferences setBgcolor = getSharedPreferences("Set Level Detail", 0);
        String bgColor = setBgcolor.getString("Bgcolor", null);
        if (bgColor != null) {
            background.setBackgroundColor(Color.parseColor(bgColor));
        }

        SharedPreferences mysettings = getSharedPreferences("General Settings", 0);
        String checkPoint = mysettings.getString("Point", null);
        String checkHealer = mysettings.getString("Healer", null);
        if (checkHealer != null) {
            if (checkHealer.equals("3")) {
                help1.setVisibility(View.VISIBLE);
                help2.setVisibility(View.VISIBLE);
                help3.setVisibility(View.VISIBLE);
            }
        }else{
            spUpdateHealer(String.valueOf(3));
        }
        if (checkPoint != null) {
            oncoin.setText(checkPoint);
        }
        if (checkHealer != null) {
            if(checkHealer.equals("0")){
                help1.setVisibility(View.GONE);
                help2.setVisibility(View.GONE);
                help3.setVisibility(View.GONE);
            }else if(checkHealer.equals("1")){
                help2.setVisibility(View.GONE);
                help3.setVisibility(View.GONE);
            }else if(checkHealer.equals("2")){
                help3.setVisibility(View.GONE);
            }
        }

        final Button findop= (Button) findViewById(R.id.findop);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            resultes0 = extras.getString("id");
            resultes4 = extras.getString("ask_type");
            resultes5 = extras.getString("ask");
            resultes6 = extras.getString("answer");
            resultes7 = extras.getString("point");
            resultes = extras.getString("answered");
            resultesLst = extras.getString("locked");
            if(resultes4.equals("text")){
                asking.setText(resultes5 + "\n");
                asking.setTextColor(Color.parseColor("#0696a7"));
            }else{
                asking.setVisibility(View.GONE);
                Resources ress = getResources();
                String mDrawableName = resultes5;
                int resID = ress.getIdentifier(mDrawableName , "drawable", getPackageName());
                Drawable drawable = ress.getDrawable(resID );
                gambare.setImageDrawable(drawable);
            }


            findop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fromText = String.valueOf(answer.getText().toString());
                    String fromIntent = resultes6.toString();

                    if(!fromText.equals("")){
                        if (fromText.equalsIgnoreCase(fromIntent)) {
                        mp = MediaPlayer.create(getApplicationContext(), R.raw.benar);
                        mp.start();
                        isCorrect.setImageResource(R.drawable.correctly);
                        isCorrect.setVisibility(View.VISIBLE);
                        textcorect.setText("Benar!");
                        findop.setEnabled(false);
                        textcorect.setVisibility(View.VISIBLE);
                        setAnimationQuiz(1100);
                        setInterval(1100);
                        int nextID = Integer.parseInt(resultes0) + 1;
                        int thisID = Integer.parseInt(resultes0);
                        Cursor newQuiz = myDb.updateNewQuiz(nextID, "1");
                        Cursor thisQuiz = myDb.updateThisQuiz(thisID);
                        if (newQuiz.getCount() == 0 && thisQuiz.getCount() == 0) {

                            SharedPreferences mysettings = getSharedPreferences("General Settings", 0);
                            String checkPoint = mysettings.getString("Point", null);
                            String checkHealer = mysettings.getString("Healer", null);

                            if (checkPoint != null && checkHealer != null) {
                                if (resultes.equals("0")) {
                                    int sumPoint = Integer.parseInt(resultes7) + Integer.parseInt(checkPoint);
                                    spSettings(String.valueOf(sumPoint), checkHealer);
                                    oncoin.setText(String.valueOf(sumPoint));
                                    mp = MediaPlayer.create(getApplicationContext(), R.raw.koin);
                                    mp.start();
                                    spLastLevel(resultes0);
                                }
                            } else {
                                spSettings("10", "3");
                                oncoin.setText("10");
                            }
                        }
                    }else if(fromText.matches(".*"+fromIntent+".*")){
                            answer.setError("Kurang tepat nakama!");
                        }else if(!fromText.equals(fromIntent)) {
                        answer.setError("Salah!");

                        SharedPreferences mysettings = getSharedPreferences("General Settings", 0);
                        String checkHealer = mysettings.getString("Healer", null);

                        if (checkHealer != null) {

                            if (Integer.parseInt(checkHealer) <= 0) {
                                strHealerint = 3;
                            } else {
                                strHealerint = Integer.parseInt(checkHealer) - 1;
                            }
                            spUpdateHealer(String.valueOf(strHealerint));
                            if (checkHealer.equals("1")) {
                                help1.setVisibility(View.GONE);
                                help2.setVisibility(View.GONE);
                                help3.setVisibility(View.GONE);
                                spUpdateHealer(String.valueOf(3));
                                finish();
                                Intent lvl1 = new Intent(DetailChars.this, Characters.class);
                                startActivity(lvl1);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            }
                            if (checkHealer.equals("2")) {
                                help2.setVisibility(View.GONE);
                                help3.setVisibility(View.GONE);
                            }
                            if (checkHealer.equals("3")) {
                                help3.setVisibility(View.GONE);
                            }
                        }
                        mp = MediaPlayer.create(getApplicationContext(), R.raw.salah);
                        mp.start();
                    }
                }
                }
            });

            String isnull= resultes0==null? "0" :resultes0;
            int toInt= Integer.parseInt(String.valueOf(isnull));
            Cursor res=myDb.showCharByID(toInt);
            if (res.getCount()==0) {
                answer.setHint("kuis tidak ditemukan");
            }else{
                answer.setHint("Sini Jawab");
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        spRefresh();
    }

    private void spRefresh() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String myPref = prefs.getString("Healer", "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent refrash=new Intent(DetailChars.this,Characters.class);
        startActivity(refrash);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    private void setInterval(int i) {
        final int interval = i;
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            public void run() {
                finish();
                Intent refresh=new Intent(DetailChars.this,Characters.class);
                startActivity(refresh);
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);
    }

    public void setAnimationQuiz(int duration){
        isCorrect.animate()
                .translationY(0)
                .alpha(0.0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        isCorrect.setVisibility(View.VISIBLE);
                    }
                });
        textcorect.animate()
                .translationY(0)
                .alpha(0.0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        textcorect.setVisibility(View.VISIBLE);
                    }
                });
    }

    public void spSettings(String point, String healer) {
        sh_Pref = getSharedPreferences("General Settings", MODE_PRIVATE);
        toEdit = sh_Pref.edit();
        toEdit.putString("Point", point);
        toEdit.putString("Healer", healer);
        toEdit.commit();
    }

    public void spUpdateHealer(String healer) {
        sh_Pref = getSharedPreferences("General Settings", MODE_PRIVATE);
        toEdit = sh_Pref.edit();
        toEdit.putString("Healer", healer);
        toEdit.commit();
    }

    public void spLastLevel(String lastLevel) {
        sh_Pref = getSharedPreferences("By Level Now", MODE_PRIVATE);
        toEdit = sh_Pref.edit();
        toEdit.putString("TheLastLevel", lastLevel);
        toEdit.commit();
    }

    private String getPath(Uri uri)
    {
        // TODO Auto-generated method stub
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        imagePath = cursor.getString(columnIndex);

        return cursor.getString(columnIndex);
    }
}

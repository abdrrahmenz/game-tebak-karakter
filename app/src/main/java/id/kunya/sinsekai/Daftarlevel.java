package id.kunya.sinsekai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Daftarlevel extends AppCompatActivity implements View.OnClickListener {

    private Button level1,level2,level3,level4;
    private ImageView newlvl1,newlvl2,newlvl3,newlvl4;

    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        myDb.openDataBase();
        setContentView(R.layout.activity_daftarlevel);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Collage.ttf");
        newlvl2= (ImageView) findViewById(R.id.newlvl2);
        newlvl3= (ImageView) findViewById(R.id.newlvl3);
        newlvl4= (ImageView) findViewById(R.id.newlvl4);
        level1= (Button) findViewById(R.id.lvl1);
        level1.setTypeface(custom_font);
        level2= (Button) findViewById(R.id.lvl2);
        level2.setTypeface(custom_font);
        level3= (Button) findViewById(R.id.lvl3);
        level3.setTypeface(custom_font);
        level4= (Button) findViewById(R.id.lvl4);
        level4.setTypeface(custom_font);
        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);
        level4.setOnClickListener(this);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        SharedPreferences mysettings = getSharedPreferences("By Level Now", 0);
        String lastlevel = mysettings.getString("TheLastLevel", null);

        if (lastlevel != null) {
            int checkLevelparse = Integer.parseInt(lastlevel);
            if (checkLevelparse >= 21) {
                level2.setText("Level 2");
                level2.setEnabled(true);
                newlvl2.setVisibility(View.VISIBLE);
            } else {
                level2.setText("TERKUNCI");
                level2.setEnabled(false);
            }
            if (checkLevelparse >= 42) {
                level3.setText("Level 3");
                level3.setEnabled(true);
                newlvl3.setVisibility(View.VISIBLE);
            } else {
                level3.setText("TERKUNCI");
                level3.setEnabled(false);
            }
            if (checkLevelparse >= 63) {
                level4.setText("Level 4");
                level4.setEnabled(true);
                newlvl4.setVisibility(View.VISIBLE);
            } else {
                level4.setText("TERKUNCI");
                level4.setEnabled(false);
            }
            if (checkLevelparse >= 84 || checkLevelparse <= 85) {
            }
        }
//        new CountDownTimer(500000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
////                level4.setText("seconds remaining: " + millisUntilFinished / 1000);
//                //here you can have your logic to set text to edittext
//                level4.setText(""+String.format("%d min, %d sec",
//                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
//                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
//                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
//            }
//
//            public void onFinish() {
//                level4.setText("done!");
//            }
//
//        }.start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lvl1:
                goTo(Characters.class,"1","#00acc1");
                break;
            case R.id.lvl2:
                goTo(Characters.class,"2","#d7b704");
                break;
            case R.id.lvl3:
                goTo(Characters.class,"3","#02b723");
                break;
            case R.id.lvl4:
                goTo(Characters.class,"4","#d62801");
                break;
        }
    }

    public void goTo(Class intent, String level, String bg_color){
        Intent in=new Intent(Daftarlevel.this,intent);
        spLevelNow(level,bg_color);
        startActivity(in);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    public void spLevelNow(String level_now, String bg_color) {
        SharedPreferences sh_Pref = getSharedPreferences("Set Level Detail", MODE_PRIVATE);
        SharedPreferences.Editor toEdit = sh_Pref.edit();
        toEdit.putString("Levelnow", level_now);
        toEdit.putString("Bgcolor", bg_color);
        toEdit.commit();
    }
}

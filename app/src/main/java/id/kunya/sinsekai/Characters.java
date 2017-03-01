package id.kunya.sinsekai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import id.kunya.sinsekai.adapter.CustomCursorAdapter;

public class Characters extends AppCompatActivity {
    String arrData[][];
    DatabaseHelper myDb;
    CustomCursorAdapter adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grider);
        TextView judul= (TextView) findViewById(R.id.charstitle);
        TextView levelID= (TextView) findViewById(R.id.levelID);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Collage.ttf");
        judul.setTypeface(custom_font);
        levelID.setTypeface(custom_font);

        myDb = new DatabaseHelper(this);
        myDb.openDataBase();
        final GridView gridView = (GridView)findViewById(R.id.gridView);

        SharedPreferences mysettings = getSharedPreferences("By Level Now", 0);
        String lastlevel = mysettings.getString("TheLastLevel", null);

        if (lastlevel != null) {
            int checkLevelparse = Integer.parseInt(lastlevel);
            if(checkLevelparse%5==0){
                Intent in=new Intent(Characters.this,Nekonime.class);
                startActivity(in);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }

        SharedPreferences enterLevel = getSharedPreferences("Set Level Detail", 0);
        String levelLive = enterLevel.getString("Levelnow", null);
        String bgcolor = enterLevel.getString("Bgcolor", null);
        String level = null;
        if (levelLive != null) {
            level = levelLive;
            levelID.setText("Level " + level);
            levelID.setBackgroundColor(Color.parseColor(bgcolor));
        }
        final Cursor myData = myDb.SelectAllData(Integer.parseInt(level));
        startManagingCursor(myData);
        arrData = myDb.getFromArrayData(Integer.parseInt(level));
        final String[] cols = new String[]{"_id"};
//        int[] names = new int[]{android.R.id.text1};
        int[] names = new int[]{
                R.id.textView1
        };
//        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.showinfo , myData ,cols,names);
        adapter2 = new CustomCursorAdapter(this, R.layout.showinfo , myData ,cols,names);
        gridView.setAdapter(adapter2);
//        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Cursor res = myDb.deleteCharByID(arrData[position][0]);
//                if (res.getCount() == 0) {
//                    //show message
//                    Toast.makeText(getApplicationContext(), "deleted", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int isfinish = Integer.parseInt(arrData[position][6].toString());
                if (isfinish == 1) {
//                    Toast.makeText(getApplicationContext(), arrData[position][1].toString() + " " + position + "" + myData.getColumnIndex(String.valueOf(position)), Toast.LENGTH_SHORT).show();
                    Intent ont = new Intent(Characters.this, DetailChars.class);
                    ont.putExtra("id", arrData[position][0].toString());
                    ont.putExtra("ask_type", arrData[position][1].toString());
                    ont.putExtra("ask", arrData[position][2].toString());
                    ont.putExtra("answer", arrData[position][3].toString());
                    ont.putExtra("point", arrData[position][4].toString());
                    ont.putExtra("answered", arrData[position][5].toString());
                    ont.putExtra("locked", arrData[position][6].toString());
                    startActivity(ont);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Selesaikan tantangan sebelumnya", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in=new Intent(Characters.this,Daftarlevel.class);
        startActivity(in);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}

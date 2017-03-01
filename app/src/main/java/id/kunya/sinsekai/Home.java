package id.kunya.sinsekai;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Home extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sh_Pref, isLastNull;
    SharedPreferences.Editor toEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        isLastNull = getSharedPreferences("By Level Now", 0);
        String commit1stLevel = isLastNull.getString("TheLastLevel", null);
        if (commit1stLevel == null) {
            spLastLevel("0");
        }
        Button goNew= (Button) findViewById(R.id.goNew);
        Button goCara= (Button) findViewById(R.id.goCara);
        Button goAbout= (Button) findViewById(R.id.goAbout);
        Button goLogout= (Button) findViewById(R.id.goLogout);
        Button goDownload= (Button) findViewById(R.id.goDownload);
        ImageView mainLogo= (ImageView) findViewById(R.id.mainLogo);
        goNew.setOnClickListener(this);
        goCara.setOnClickListener(this);
        goAbout.setOnClickListener(this);
        goLogout.setOnClickListener(this);
        goDownload.setOnClickListener(this);
        mainLogo.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goNew:
                goTo(Daftarlevel.class);
                break;
            case R.id.goCara:
                goTo(CaraMain.class);
                break;
            case R.id.goAbout:
                goTo(About.class);
                break;
            case R.id.goLogout:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Ingin keluar dari aplikasi?");
                alertDialogBuilder
//                        .setMessage("Click yes to exit!")
                        .setCancelable(false)
                        .setPositiveButton("Ya",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        moveTaskToBack(true);
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                        System.exit(1);
                                    }
                                })

                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.goDownload:
                goToUrl("http://nekonime.com/");
                break;
            case R.id.mainLogo:
//                goTo(Asker.class);
                break;
        }
    }

    public void goTo(Class intent){
        Intent in=new Intent(Home.this,intent);
        startActivity(in);
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void spLastLevel(String lastLevel) {
        sh_Pref = getSharedPreferences("By Level Now", MODE_PRIVATE);
        toEdit = sh_Pref.edit();
        toEdit.putString("TheLastLevel", lastLevel);
        toEdit.commit();
    }
}

package id.kunya.sinsekai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Toko extends AppCompatActivity {
    InterstitialAd mInterstitialAd;
    Button mNewGameButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);

//        AdView mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
//        mNewGameButton = (Button) findViewById(R.id.newgame_button);
//
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-1042691733050574/7602760850");
//
//        mNewGameButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
////                } else {
////                    //Begin Game, continue with app
////                }
//            }
//        });
//
//        mInterstitialAd.setAdListener(new AdListener() {
//            @Override
//            public void onAdClosed() {
//
//                //Begin Game, continue with app
//            }
//        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//        mInterstitialAd.loadAd(adRequest);
    }
}

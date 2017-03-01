package id.kunya.sinsekai;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class PuzzelArc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzel_arc);
        /*puzzle top*/
        final Button buttopTopLeft= (Button) findViewById(R.id.bt1);
        final Button buttopTopCenter= (Button) findViewById(R.id.bt2);
        final Button buttopTopRight= (Button) findViewById(R.id.bt3);

        /*puzzle center*/
        final Button buttopCenterLeft= (Button) findViewById(R.id.bt12);
        final Button buttopCenterCenter= (Button) findViewById(R.id.bt22);
        final Button buttopCenterRight= (Button) findViewById(R.id.bt32);

        /*puzzle bottom*/
        final Button buttopBottomLeft= (Button) findViewById(R.id.bt13);
        final Button buttopBottomCenter= (Button) findViewById(R.id.bt23);
        final Button buttopBottomRight= (Button) findViewById(R.id.bt33);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.milkshake);

        buttopTopLeft.setAnimation(myAnim);
        buttopTopCenter.setAnimation(myAnim);
        buttopTopRight.setAnimation(myAnim);
        buttopCenterLeft.setAnimation(myAnim);
        buttopCenterCenter.setAnimation(myAnim);
        buttopCenterRight.setAnimation(myAnim);
        buttopBottomLeft.setAnimation(myAnim);
        buttopBottomCenter.setAnimation(myAnim);
        buttopBottomRight.setAnimation(myAnim);

        buttopTopLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttopTopLeft.setBackgroundColor(Color.TRANSPARENT);



                if (buttopTopLeft.getVisibility() == View.GONE) {
                    buttopCenterCenter.animate()
                            .translationY(buttopCenterCenter.getHeight()).alpha(1.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    buttopCenterCenter.setVisibility(View.VISIBLE);
                                    buttopCenterCenter.setAlpha(0.0f);
                                }
                            });
                } else {
                    buttopCenterCenter.animate()
                            .translationY(0).alpha(0.0f)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    buttopCenterCenter.setVisibility(View.GONE);
                                }
                            });
                }


            }
        });

    }
}

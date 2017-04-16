package com.shuvam.recsnd;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Shuvam Ghosh on 4/15/2017.
 */

public class ResultActivity extends AppCompatActivity{

    private FloatingActionButton fab;
    private TextView UDRP;
    private TextView result;
    Animation bubble;
    private String UDRPScore;
    private int hasParkinsons;
    private ImageView ivNext;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        fab = (FloatingActionButton)findViewById(R.id.resultFab);
        UDRP = (TextView)findViewById(R.id.resultScore);
        result = (TextView)findViewById(R.id.resultComment);
        progressBar = (ProgressBar)findViewById(R.id.progressToMap);
        ivNext = (ImageView)findViewById(R.id.btnNext);

        UDRP.setVisibility(View.INVISIBLE);
        result.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();

        UDRPScore = intent.getStringExtra("UPDRS");
        hasParkinsons = intent.getIntExtra("isParkinsons",-1);




       /* if(hasParkinsons==1)
        {
            ivNext.setVisibility(View.VISIBLE);
        }
        else if(hasParkinsons==0)
        {
            ivNext.setVisibility(View.INVISIBLE);
        }*/

        UDRPScore = UDRPScore.substring(0,UDRPScore.length()-16);



        UDRP.setText("UPDRS: "+UDRPScore);

        if(hasParkinsons==1)
        {
            result.setText("We predict you may have Parkinsons");
        }
        else if(hasParkinsons==0)
        {
            result.setText("You have no Parkinsons");
        }


        if(UDRPScore.equalsIgnoreCase("16"))
        {
            result.setText("We suspect you may have Parkinsons");
        }


        ObjectAnimator animatorx = ObjectAnimator.ofFloat(fab,"scaleX",0,1f).setDuration(500);
        ObjectAnimator animatory = ObjectAnimator.ofFloat(fab,"scaleY",0,1f).setDuration(500);

        animatorx.setInterpolator(new AccelerateDecelerateInterpolator());
        animatory.setInterpolator(new AccelerateDecelerateInterpolator());

        animatorx.start();
        animatory.start();

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(UDRP,"alpha",0,1).setDuration(800);
        ObjectAnimator fadeIn1 = ObjectAnimator.ofFloat(result,"alpha",0,1).setDuration(800);

        UDRP.setVisibility(View.VISIBLE);
        result.setVisibility(View.VISIBLE);

        fadeIn.start();
        fadeIn1.start();


        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                Intent i = new Intent(ResultActivity.this,MapsActivity.class);
                startActivity(i);
            }
        });

    }
}

package com.project.wissemcomp.veloapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;


import com.project.wissemcomp.veloapp.data.SqliteController;
import com.project.wissemcomp.veloapp.mvp.presenter.RegisterBikePresenter;
import com.project.wissemcomp.veloapp.mvp.presenter.RegisterBikePresenterImpl;
import com.project.wissemcomp.veloapp.mvp.view.RegisterBikeView;
import com.rengwuxian.materialedittext.MaterialEditText;


import java.util.ArrayList;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterBikeActivity extends AppCompatActivity implements RegisterBikeView, View.OnClickListener {

    private int RESULT_LOAD_IMAGE = 1;
    private CircleImageView img_user;
    private CircularProgressButton circularButton1;
    private RegisterBikePresenter registerBikePresenter;

    private MaterialEditText name;
    private MaterialEditText price;
    private MaterialEditText date;
    private MaterialEditText description;
    private String image_url;

    SqliteController sqliteController;


    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";

    private View rootLayout;

    private int revealX;
    private int revealY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_bike);

        registerBikePresenter = new RegisterBikePresenterImpl(this, this);

        img_user = (CircleImageView) findViewById(R.id.profile_image);
        circularButton1 = (CircularProgressButton) findViewById(R.id.btnAddBike);
        name = (MaterialEditText) findViewById(R.id.reg_bike_name);
        price = (MaterialEditText) findViewById(R.id.reg_bike_price);
        date = (MaterialEditText) findViewById(R.id.reg_bike_date);
        description = (MaterialEditText) findViewById(R.id.reg_bike_description);

        circularButton1.setOnClickListener(this);
        img_user.setOnClickListener(this);

        initBackButton();

        final Intent intent = getIntent();

        rootLayout = findViewById(R.id.root_layout);

        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)) {
            rootLayout.setVisibility(View.INVISIBLE);

            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(revealX, revealY);
                        rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        } else {
            rootLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1 && null != data) {
            image_url = data.getData().toString();
            Utility.getImageFromGallery(getBaseContext(), data.getData(), img_user);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.profile_image:
                openGallery();
                break;

            case R.id.btnAddBike:
                ArrayList<String> fields = new ArrayList<String>();
                fields.add(name.getText().toString());
                fields.add(price.getText().toString());
                fields.add(date.getText().toString());
                fields.add(description.getText().toString());
                fields.add(image_url);
                registerBikePresenter.checkFormValues(fields);
                break;
        }
    }

    @Override
    public void showButtonLoading() {
        circularButton1.startAnimation();
    }

    @Override
    public void showButtonError() {
        circularButton1.revertAnimation(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                circularButton1.setBackgroundColor(Color.parseColor("#fc464a"));
                circularButton1.setText("Erreur");
                resetButtonAfterError();
            }


        });
    }

    @Override
    public void showButtonComplete() {
        circularButton1.revertAnimation(new OnAnimationEndListener() {
            @Override
            public void onAnimationEnd() {
                circularButton1.setBackgroundColor(Color.parseColor("#9aca27"));
                circularButton1.setText("C'est bon !");
                circularButton1.setOnClickListener(null);
            }


        });
    }

    @Override
    public void onResume() {
        super.onResume();
        this.setTitle("Nouveau vélo");
    }

    private void initBackButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetButtonAfterError() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                circularButton1.setBackgroundColor(Color.parseColor("#f18623"));
                circularButton1.setText("Ajouter");

            }
        }, 1000);
    }

    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }


    protected void revealActivity(int x, int y) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(rootLayout, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            rootLayout.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }

    /*protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(rootLayout.getWidth(), rootLayout.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    rootLayout, revealX, revealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    rootLayout.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }*/
}

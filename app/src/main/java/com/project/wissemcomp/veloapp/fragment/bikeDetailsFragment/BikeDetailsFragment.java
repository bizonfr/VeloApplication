package com.project.wissemcomp.veloapp.fragment.bikeDetailsFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.wissemcomp.veloapp.MainActivity;
import com.project.wissemcomp.veloapp.R;
import com.project.wissemcomp.veloapp.Utility;
import com.project.wissemcomp.veloapp.dialog.RepairDialog;
import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.presenter.BikeDetailsPresenter;
import com.project.wissemcomp.veloapp.mvp.presenter.BikeDetailsPresenterImpl;
import com.project.wissemcomp.veloapp.mvp.view.BikeDetailsView;

import net.igenius.customcheckbox.CustomCheckBox;

public class BikeDetailsFragment extends Fragment implements BikeDetailsView, AppBarLayout.OnOffsetChangedListener, View.OnClickListener, RepairDialog.RepairDialogListener {

    public static final String BIKE_ID = "BIKE_ID";

    private BikeDetailsPresenter presenter;
    private View view;

    private TextView bike_name;
    private TextView bike_date;
    private TextView bike_description;
    private TextView bike_price;
    private TextView bike_total_price;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private ImageView imageBike;
    private LinearLayout button_rep;


    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
    private View mFab;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;

    private Animation animCrossFadeIn, animCrossFadeOut;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    public static BikeDetailsFragment newInstance(int bike_id) {
        BikeDetailsFragment fragment = new BikeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(BIKE_ID, bike_id);
        fragment.setArguments(args);
        return fragment;
    }

    public BikeDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BikeDetailsPresenterImpl(this, getContext());
        animCrossFadeIn = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.fade_in);
        animCrossFadeOut = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.fade_out);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bike_details, container, false);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linearLayout = getActivity().findViewById(R.id.linear_container);
        mFab = view.findViewById(R.id.flexible_example_fab);

        imageBike = view.findViewById(R.id.bike_image);
        bike_name = view.findViewById(R.id.bike_name);
        bike_date = view.findViewById(R.id.bike_date);
        bike_description = view.findViewById(R.id.bike_description);
        bike_price = view.findViewById(R.id.bike_price);
        bike_total_price = view.findViewById(R.id.bike_total_price);

        progressBar = view.findViewById(R.id.progress_bar_details);
        button_rep = view.findViewById(R.id.button_rep);
        button_rep.setOnClickListener(this);
        imageBike.setOnClickListener(this);
        mFab.setOnClickListener(this);

        Toolbar toolbar = view.findViewById(R.id.flexible_example_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        AppBarLayout appbar = view.findViewById(R.id.flexible_example_appbar);
        appbar.addOnOffsetChangedListener(this);

    }


    /*
    BIKEDETAILSVIEW METHODS
     */
    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public int getBikeId() {
        return getArguments().getInt(BIKE_ID);
    }

    @Override
    public void showProgress() {
        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setBikeDetails(Bike bike) {
        if(bike.getDescription().isEmpty())
            bike_description.setText("Pas de description");
        else
            bike_description.setText(bike.getDescription());
        if(!bike.getImage().isEmpty())
            Utility.getImageFromGallery(getContext(), Uri.parse(bike.getImage()),imageBike);
        bike_name.setText(bike.getName());
        bike_date.setText(bike.getDate());
        bike_price.setText(bike.getPrice() + "€");
        bike_total_price.setText(bike.getPrice() + "€");
        ajustTitle();
        mFab.setClickable(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_rep:
                v.startAnimation(buttonClick);
                showDialog();
                break;
            case R.id.bike_image:
                Toast.makeText(getContext(), "cliquééé", Toast.LENGTH_LONG).show();
                break;
            case R.id.flexible_example_fab:
                showDialogSold();
                break;
        }
    }


    private void ajustTitle() {
        if (isViewOverlapping(bike_name, mFab)) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(linearLayout.getWidth() - mFab.getWidth(), bike_name.getHeight());
            params.setMargins(0, 20, 0, 0);
            bike_name.setLayoutParams(params);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

        int width = linearLayout.getWidth();
        int width_fab = mFab.getWidth();
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(i)) * 100
                / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;
                if (isViewOverlapping(bike_name, mFab)) {
                    bike_name.startAnimation(animCrossFadeOut);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, bike_name.getHeight());
                    params.setMargins(0, 20, 0, 0);
                    bike_name.setLayoutParams(params);
                    bike_name.startAnimation(animCrossFadeIn);

                }
                ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                if (isViewOverlapping(mFab, bike_name)) {
                    bike_name.startAnimation(animCrossFadeOut);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width - width_fab, bike_name.getHeight());
                    params.setMargins(0, 20, 0, 0);
                    bike_name.setLayoutParams(params);
                    bike_name.startAnimation(animCrossFadeIn);
                }
                ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
            }
        }
    }






    private boolean isViewOverlapping(View firstView, View secondView) {
        int[] firstPosition = new int[2];
        int[] secondPosition = new int[2];

        firstView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        firstView.getLocationOnScreen(firstPosition);
        secondView.getLocationOnScreen(secondPosition);

        int r = firstView.getMeasuredWidth() + firstPosition[0];
        int l = secondPosition[0];
        return r >= l && (r != 0 && l != 0);
    }

    private void showDialog() {

        RepairDialog repairDialogFragment = new RepairDialog();
        Bundle bundle = new Bundle();
        bundle.putString("myKey", null);
        repairDialogFragment.setArguments(bundle);
        repairDialogFragment.setTargetFragment(this, 0);
        repairDialogFragment.setCancelable(true);
        repairDialogFragment.show(getActivity().getSupportFragmentManager(), "repairDialogFragment");


    }

    private void showDialogSold() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Vélo vendu ?");
        alert.setMessage("Avez-vous vendu le vélo ? Si oui, entrez le prix de vente ci-dessous :");
        View view = getLayoutInflater().inflate(R.layout.custom_view_dialog_sold, null);
        alert.setView(view);

        final EditText price = view.findViewById(R.id.text_price);
        alert.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), price.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("Annuler", null);

        price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                price.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(price, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });
        price.requestFocus();


        final AlertDialog dialog = alert.show();

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                else
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);


    }

    public void onAddField(String repair_type, String repair_price) {
        LayoutInflater inflater = getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.field_rep, null);
        final TextView repair_text = rowView.findViewById(R.id.repair_text_name);
        repair_text.setText(repair_type);
        final TextView repair_text_price = rowView.findViewById(R.id.repair_text_price);
        repair_text_price.setText("+" + repair_price + "€");
        CustomCheckBox checkBox = rowView.findViewById(R.id.customCheckBox);
        checkBox.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                if (isChecked)
                    repair_text.setPaintFlags(repair_text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                else
                    repair_text.setPaintFlags(repair_text.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
        });

        linearLayout.addView(rowView, linearLayout.getChildCount()-3);
    }

    @Override
    public void onFinishRepairDialog(String repair_type, String repair_price) {
        onAddField(repair_type,repair_price);
    }
}

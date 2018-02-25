package com.project.wissemcomp.veloapp.fragment.bikeListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.melnykov.fab.FloatingActionButton;
import com.project.wissemcomp.veloapp.fragment.bikeDetailsFragment.BikeDetailsFragment;
import com.project.wissemcomp.veloapp.MainActivity;
import com.project.wissemcomp.veloapp.R;
import com.project.wissemcomp.veloapp.RegisterBikeActivity;
import com.project.wissemcomp.veloapp.adapter.AdapterVelo;
import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.model.Picture;
import com.project.wissemcomp.veloapp.mvp.view.BikeListMvpView;
import com.project.wissemcomp.veloapp.mvp.presenter.BikeListPresenterImpl;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jhordan on 13/10/15.
 */
public class BikeListFragment extends Fragment implements BikeListMvpView, RecyclerBikeItemClickListener, View.OnClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private BikeListPresenterImpl bikeListPresenter;
    RecyclerView.Adapter adapter;

    private LinearLayoutManager linearLayoutManager;
    private  View rootView;



    public static BikeListFragment newInstance() {
        return new BikeListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bike_list, container, false);
        ButterKnife.bind(this, rootView);

        bikeListPresenter = new BikeListPresenterImpl(getContext());
        bikeListPresenter.attachedView(this);
        setupRecyclerView();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.bike_list_fragment));
        ((MainActivity)getActivity()).updateStatusBarColor(R.color.statusbar_velo, R.color.actionbar_velo);
        bikeListPresenter.onResume();
    }

    @Override
    public void setItems(ArrayList<Bike> bikesList) {
        adapter = new AdapterVelo(bikesList, R.layout.item_bike_list);
        recyclerView.setAdapter(adapter);
        if(this instanceof BikeListFragment)
            ((AdapterVelo) adapter).setRecyclerBikeItemClickListener(this);

        setupFloatingButton();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void redirect(int bike_id) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, BikeDetailsFragment.newInstance(bike_id))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroy() {
        bikeListPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onBikeItemClickListener(int position) {
        bikeListPresenter.onItemSelected(position);
    }



    private void setupRecyclerView() {

            linearLayoutManager = linearLayoutManager = new LinearLayoutManager(
                    getActivity(),
                    LinearLayoutManager.VERTICAL,
                    false);

            recyclerView.setLayoutManager(linearLayoutManager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().getSupportFragmentManager().popBackStack();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.attachToRecyclerView(recyclerView);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {

        presentActivity(v);
    }


    public void presentActivity(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(getActivity(), RegisterBikeActivity.class);
        intent.putExtra(RegisterBikeActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(RegisterBikeActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

}

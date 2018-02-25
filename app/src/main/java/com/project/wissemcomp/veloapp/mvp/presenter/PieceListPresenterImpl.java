package com.project.wissemcomp.veloapp.mvp.presenter;

import android.content.Context;

import com.project.wissemcomp.veloapp.fragment.bikeListFragment.LoaderListener;
import com.project.wissemcomp.veloapp.mvp.interactor.PictureInteractor;
import com.project.wissemcomp.veloapp.mvp.interactor.PictureInteractorImpl;
import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.model.Picture;
import com.project.wissemcomp.veloapp.mvp.view.PieceListMvpView;

import java.util.ArrayList;


public class PieceListPresenterImpl implements Presenter<PieceListMvpView>, LoaderListener {

    private PieceListMvpView pictureMvpView;
    private PictureInteractor pictureInteractor;

    public PieceListPresenterImpl(Context context) {
        pictureInteractor = new PictureInteractorImpl(context);
    }

    @Override
    public void attachedView(PieceListMvpView view) {
        if (view == null)
            throw new IllegalArgumentException("You can't set a null view");

        pictureMvpView = view;
    }

    @Override
    public void detachView() {
        pictureMvpView = null;
    }

    @Override
    public void onResume() {
        pictureMvpView.showProgress();
        pictureInteractor.loadItems(this);
    }

    @Override
    public void onItemSelected(int position) {
        pictureMvpView.showListPiece(position);

    }

    @Override
    public void onFinished(ArrayList<Bike> pictureList) {
        pictureMvpView.setItems(pictureList);
        pictureMvpView.hideProgress();
    }
}

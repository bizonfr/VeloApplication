package com.project.wissemcomp.veloapp.mvp.view;

import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.model.Picture;

import java.util.ArrayList;


public interface BikeListMvpView {

    void setItems(ArrayList<Bike> bikesList);

    void showProgress();

    void hideProgress();

    void redirect(int id);

}


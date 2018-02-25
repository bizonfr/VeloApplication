package com.project.wissemcomp.veloapp.mvp.view;

import com.project.wissemcomp.veloapp.mvp.model.Bike;

/**
 * Created by Wissem on 14/02/2018.
 */

public interface BikeDetailsView {

    void showProgress();

    void hideProgress();

    void setBikeDetails(Bike bike);

    int getBikeId();
}

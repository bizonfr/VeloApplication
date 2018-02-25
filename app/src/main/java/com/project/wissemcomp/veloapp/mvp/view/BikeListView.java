package com.project.wissemcomp.veloapp.mvp.view;

import com.project.wissemcomp.veloapp.mvp.model.Bike;

import java.util.List;

/**
 * Created by Wissem on 14/02/2018.
 */

public interface BikeListView {

    void showProgress();

    void hideProgress();

    void setItems(List<Bike> items);

    void showBikeDetails(int id);
}

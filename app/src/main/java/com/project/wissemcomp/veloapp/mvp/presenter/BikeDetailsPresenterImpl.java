/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.project.wissemcomp.veloapp.mvp.presenter;

import android.content.Context;

import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.interactor.FindBikeInteractor;
import com.project.wissemcomp.veloapp.mvp.interactor.FindBikeInteractorImpl;
import com.project.wissemcomp.veloapp.mvp.view.BikeDetailsView;

import java.util.logging.Handler;

public class BikeDetailsPresenterImpl implements BikeDetailsPresenter, FindBikeInteractor.OnFinishedListener {

    private BikeDetailsView bikeListView;
    private FindBikeInteractor findBikesInteractor;

    public BikeDetailsPresenterImpl(BikeDetailsView view, Context context) {
        this.bikeListView = view;
        this.findBikesInteractor = new FindBikeInteractorImpl(context);
    }

    @Override
    public void onResume() {
        if (bikeListView != null) {
            bikeListView.showProgress();
        }

        findBikesInteractor.findVelo(this, bikeListView.getBikeId());
    }

    @Override
    public void onDestroy() {
        bikeListView = null;
        findBikesInteractor.cancelCallbacks();
    }

    @Override
    public void onFinished(final Bike bike) {
        if (bikeListView != null) {
            bikeListView.hideProgress();
        }

        bikeListView.setBikeDetails(bike);
    }

    public BikeDetailsView getBikeListView() {
        return bikeListView;
    }

}

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

package com.project.wissemcomp.veloapp.mvp.interactor;

import android.content.Context;
import android.os.Handler;

import com.project.wissemcomp.veloapp.data.SqliteController;
import com.project.wissemcomp.veloapp.mvp.model.Bike;

public class FindBikeInteractorImpl implements FindBikeInteractor {


    private SqliteController sqliteController;
    private Handler loadData;
    public FindBikeInteractorImpl(Context context)
    {
        sqliteController = new SqliteController(context);
    }
    @Override
    public void findVelo(final OnFinishedListener listener, final int bike_id) {
        loadData = new Handler();
        Runnable getBikes = new Runnable() {
            @Override
            public void run() {
                listener.onFinished(getBike(bike_id));
            }
        };
        loadData.postDelayed(getBikes, 2000);
    }

    @Override
    public void cancelCallbacks() {
        loadData.removeCallbacksAndMessages(null);
    }

    private Bike getBike(int bike_id) {
        return sqliteController.getBikeDetails(bike_id);
    }

}

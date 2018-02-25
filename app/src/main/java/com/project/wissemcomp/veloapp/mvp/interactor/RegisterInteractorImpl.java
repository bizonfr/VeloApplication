package com.project.wissemcomp.veloapp.mvp.interactor;

import android.content.Context;

import com.project.wissemcomp.veloapp.data.SqliteController;
import com.project.wissemcomp.veloapp.mvp.model.Bike;

/**
 * Created by Wissem on 25/02/2018.
 */

public class RegisterInteractorImpl implements RegisterInteractor {


    private SqliteController sqliteController;
    public RegisterInteractorImpl(Context context)
    {
        sqliteController = new SqliteController(context);
    }


    @Override
    public boolean save(Bike bike) {
        return sqliteController.saveUserData(bike);
    }
}

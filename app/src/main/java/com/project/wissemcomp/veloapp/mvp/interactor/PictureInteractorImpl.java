package com.project.wissemcomp.veloapp.mvp.interactor;


import android.content.Context;

import com.project.wissemcomp.veloapp.R;
import com.project.wissemcomp.veloapp.data.SqliteController;
import com.project.wissemcomp.veloapp.fragment.bikeListFragment.LoaderListener;
import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.model.Picture;

import java.util.ArrayList;


/**
 * Created by Jhordan on 13/10/15.
 */
public class PictureInteractorImpl implements PictureInteractor {

    private SqliteController sqliteController;

    public PictureInteractorImpl(Context context)
    {
        sqliteController = new SqliteController(context);
    }

    private final static String[] pictureNames = {
            "Rocket in the universe",
            "A scene in London",
            "Moon over mountains",
            "A simple moon",
            "Sun and volcano",
            "A collection of mountains",
            "River between mountains",
            "Some pine trees",
            "On Small Town",
            "Volcanos reflection"
    };

    private final static int  pictureImages[] = {
            R.drawable.cohete_flat,
            R.drawable.ic_launcher_background,
            R.drawable.cohete_flat,
            R.drawable.ic_launcher_background,
            R.drawable.cohete_flat,
            R.drawable.ic_launcher_background,
            R.drawable.cohete_flat,
            R.drawable.ic_launcher_background,
            R.drawable.cohete_flat,
            R.drawable.ic_launcher_background
    };


    @Override
    public void loadItems(final LoaderListener loaderListener) {

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {*/
                loaderListener.onFinished(createCollectionPictures());
        /*    }
        }, 2000);*/
    }


    private ArrayList<Bike> createCollectionPictures() {



        ArrayList<Bike> bikes = (ArrayList)sqliteController.getAllBikes();



        return bikes;

    }
}

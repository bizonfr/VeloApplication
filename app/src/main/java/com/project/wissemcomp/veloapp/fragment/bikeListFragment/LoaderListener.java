package com.project.wissemcomp.veloapp.fragment.bikeListFragment;

import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.model.Picture;

import java.util.ArrayList;

/**
 * Created by Jhordan on 13/10/15.
 */
public interface LoaderListener {

    void onFinished(ArrayList<Bike> bikeList);
}

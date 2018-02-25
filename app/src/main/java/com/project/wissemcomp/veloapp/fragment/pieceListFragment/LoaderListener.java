package com.project.wissemcomp.veloapp.fragment.pieceListFragment;

import com.project.wissemcomp.veloapp.mvp.model.Picture;

import java.util.ArrayList;

/**
 * Created by Jhordan on 13/10/15.
 */
public interface LoaderListener {

    void onFinished(ArrayList<Picture> pictureList);
}

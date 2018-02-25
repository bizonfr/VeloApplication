package com.project.wissemcomp.veloapp.mvp.presenter;

/**
 * Created by Jhordan on 13/10/15.
 */
public interface Presenter <V> {

    void attachedView(V view);

    void detachView();

    void onResume();

    void onItemSelected(int position);
}

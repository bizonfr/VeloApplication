package com.project.wissemcomp.veloapp.mvp.view;


public interface RegisterBikeView {

    // Fairly un necessary interface but if we wanted our
    // MainActivity to actually do anything we would use this view in combination
    // with a presenter

    public void showButtonLoading();
    public void showButtonError();
    public void showButtonComplete();
}

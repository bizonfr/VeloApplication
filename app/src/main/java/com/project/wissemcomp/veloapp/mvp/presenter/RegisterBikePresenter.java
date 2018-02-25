package com.project.wissemcomp.veloapp.mvp.presenter;


import android.content.Context;
import android.net.Uri;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public interface RegisterBikePresenter {

    void checkFormValues(List<String> list_fields);

}

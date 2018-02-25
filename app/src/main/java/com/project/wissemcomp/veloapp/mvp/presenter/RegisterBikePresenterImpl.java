package com.project.wissemcomp.veloapp.mvp.presenter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RegionIterator;
import android.net.Uri;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.project.wissemcomp.veloapp.data.SqliteController;
import com.project.wissemcomp.veloapp.mvp.interactor.RegisterInteractor;
import com.project.wissemcomp.veloapp.mvp.interactor.RegisterInteractorImpl;
import com.project.wissemcomp.veloapp.mvp.model.Bike;
import com.project.wissemcomp.veloapp.mvp.view.RegisterBikeView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegisterBikePresenterImpl implements RegisterBikePresenter {
    private RegisterBikeView registerBikeView;
    private String name, price, date, description, image_url;

    private RegisterInteractor registerIteractor;
    public RegisterBikePresenterImpl(RegisterBikeView view, Context context) {
        this.registerBikeView = view;
        registerIteractor = new RegisterInteractorImpl(context);
    }


    @Override
    public void checkFormValues(final List<String> fields) {
        if (registerBikeView != null) {
            registerBikeView.showButtonLoading();
        }
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                if(validateFields(fields))
                    validated();
                else
                    registerBikeView.showButtonError();
            }
        }, 2000);



    }

    private void validated(){

        if (savingData(name, price,date, description,image_url)){
            registerBikeView.showButtonComplete();
        }else{
            registerBikeView.showButtonError();
        }

    }

    private boolean validateFields(final List<String> fields)
    {
        name = fields.get(0);
        price = fields.get(1);
        date = fields.get(2);
        description = fields.get(3);
        image_url = fields.get(4);

        if(name.isEmpty() || price.isEmpty() || date.isEmpty())
            return false;
        else
            return true;

    }
    private boolean savingData(String name, String price, String date, String description, String image_url){
        Bike bike = new Bike();
        bike.setName(name);
        bike.setDate(date);
        bike.setPrice(price);
        bike.setDescription(description);
        bike.setImage(image_url);
        return registerIteractor.save(bike);
    }


}

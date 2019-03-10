package com.kor.foodmanager.ui.eventInfo.guestEventInfoPending;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class GuestEventInfoPendingPresenter extends MvpPresenter<IGuestEventInfoPending> {

    public void callOwner(String phone){
        Uri uri = Uri.parse(phone);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(uri);
        Intent chooser = Intent.createChooser(intent,"Select app");
        //startActivity(chooser);

    }

    public void showOnMap(String address){
        Uri uri = Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345");

/**OR*/

        uri = Uri.parse("google.navigation:q=an+address+city");

/**OR*/

        uri = Uri.parse("geo:55.754283,37.62002");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        Intent chooser = Intent.createChooser(intent,"Select app");
        //startActivity(chooser);
    }
}

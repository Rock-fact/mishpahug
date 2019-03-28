package com.kor.foodmanager.ui.editPicture;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kor.foodmanager.R;
import com.kor.foodmanager.ui.CropCircleTransformation;
import com.kor.foodmanager.ui.IToolbar;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class EditPictureFragment extends Fragment {
    private IToolbar iToolbar;
    private Unbinder unbinder;
    @BindView(R.id.avatar_img)
    ImageView avatar;


    public EditPictureFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_picture, container, false);
        iToolbar=(IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Edit pictures",false,true,false);
        unbinder = ButterKnife.bind(this, view);
        Picasso.get().load("http://i.imgur.com/DvpvklR.png")
                .transform(new CropCircleTransformation())
                .into(avatar);
        return view;
    }


    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}

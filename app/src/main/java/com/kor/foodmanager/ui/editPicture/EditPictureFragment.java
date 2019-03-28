package com.kor.foodmanager.ui.editPicture;


import android.content.Intent;
import android.net.Uri;
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

import javax.xml.transform.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


public class EditPictureFragment extends Fragment implements View.OnClickListener {
    private IToolbar iToolbar;
    private Unbinder unbinder;

    private static final int AVATAR_EDIT_REQUEST = 1;
    private static final int EVENT_BANNER_EDIT_REQUEST = 2;

    @BindView(R.id.avatar_img)
    ImageView avatar;
    @BindView(R.id.event_img)
    ImageView eventBanner;

    public EditPictureFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_picture, container, false);
        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Edit pictures", false, true, false);
        unbinder = ButterKnife.bind(this, view);
        Picasso.get().load("http://i.imgur.com/DvpvklR.png")
                .transform(new CropCircleTransformation())
                .into(avatar);
        Picasso.get().load("http://i.imgur.com/DvpvklR.png").fit()
                .into(eventBanner);
        avatar.setOnClickListener(this);
        eventBanner.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        if (v.getId() == R.id.avatar_img) {
            startActivityForResult(photoPickerIntent, AVATAR_EDIT_REQUEST);
        } else if (v.getId() == R.id.event_img) {
            startActivityForResult(photoPickerIntent, EVENT_BANNER_EDIT_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri picUri = data.getData();
            if (picUri != null) {
                switch (requestCode) {
                    case AVATAR_EDIT_REQUEST:
                        Picasso.get().load(picUri).into(avatar);
                        break;
                    case EVENT_BANNER_EDIT_REQUEST:
                        Picasso.get().load(picUri).into(eventBanner);
                        break;
                }

            }
        }
    }
}

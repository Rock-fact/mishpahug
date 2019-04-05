package com.kor.foodmanager.ui.editPicture;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.ui.CropCircleTransformation;
import com.kor.foodmanager.ui.IToolbar;
import com.kor.foodmanager.ui.MainActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


public class EditPictureFragment extends MvpAppCompatFragment implements IEditPicture, View.OnClickListener {
    @InjectPresenter
    EditPicturePresenter presenter;
    private IToolbar iToolbar;
    private Unbinder unbinder;


    protected static final int AVATAR_EDIT_REQUEST = 1;
    protected static final int EVENT_BANNER_EDIT_REQUEST = 2;

    @BindView(R.id.avatar_img)
    ImageView avatar;
    @BindView(R.id.event_img)
    ImageView eventBanner;
    @BindView(R.id.avatar_txt)
    TextView avatarTxt;
    @BindView(R.id.event_banner_txt)
    TextView eventBannerTxt;
    private RequestCreator rc;

    public EditPictureFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_picture, container, false);
        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Edit pictures", false, true, false);
        unbinder = ButterKnife.bind(this, view);
        loadImages();
        avatar.setOnClickListener(this);
        eventBanner.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    public void loadImages() {
        if (presenter.getPicUrl(AVATAR_EDIT_REQUEST) != null) {
            Picasso.get().invalidate(presenter.getPicUrl(AVATAR_EDIT_REQUEST));
            rc = Picasso.get().load(presenter.getPicUrl(AVATAR_EDIT_REQUEST))
                    .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE);
        } else {
            rc = Picasso.get().load("http://i.imgur.com/DvpvklR.png");
        }
        rc.error(R.drawable.logo).into(avatar);

        if (presenter.getPicUrl(EVENT_BANNER_EDIT_REQUEST) != null) {
            Picasso.get().invalidate(presenter.getPicUrl(EVENT_BANNER_EDIT_REQUEST));
            rc = Picasso.get().load(presenter.getPicUrl(EVENT_BANNER_EDIT_REQUEST))
                    .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE);
        } else {
            rc = Picasso.get().load("http://i.imgur.com/DvpvklR.png");
        }
        rc.error(R.drawable.logo).into(eventBanner);
    }

    @Override
    public void onClick(View v) {

        new AlertDialog.Builder(getActivity()).setMessage("What do you want to do?")
                .setNegativeButton("Delete picture", (dialog, which) -> {
                    if (v.getId() == R.id.avatar_img) {
                        presenter.deletePic(MainActivity.AVATAR_PICTURE);
                        Picasso.get().load(R.drawable.logo).into(avatar);
                    } else if (v.getId() == R.id.event_img) {
                        presenter.deletePic(MainActivity.EVENT_BANNER_PICTURE);
                        Picasso.get().load(R.drawable.logo).into(eventBanner);
                    }

                })
                .setPositiveButton("Edit picture", (dialog, which) -> {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK); //ACTION_GET_CONTENT
                    photoPickerIntent.setType("image/*");
                    if (v.getId() == R.id.avatar_img) {
                        startActivityForResult(photoPickerIntent, AVATAR_EDIT_REQUEST);
                    } else if (v.getId() == R.id.event_img) {
                        startActivityForResult(photoPickerIntent, EVENT_BANNER_EDIT_REQUEST);
                    }
                })
                .show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK & requestCode == AVATAR_EDIT_REQUEST ||
                requestCode == EVENT_BANNER_EDIT_REQUEST) {
            Uri picUri = data.getData();
            if (picUri != null) {
                //RequestCreator rc = Picasso.get().load(picUri);
                presenter.loadImage(requestCode, picUri);
                switch (requestCode) {
                    case AVATAR_EDIT_REQUEST:
                        Picasso.get().load(presenter.getPicUrl(AVATAR_EDIT_REQUEST)).into(avatar);
                        //rc.transform(new CropCircleTransformation()).into(avatar);
                        break;
                    case EVENT_BANNER_EDIT_REQUEST:
                        Picasso.get().load(presenter.getPicUrl(EVENT_BANNER_EDIT_REQUEST)).into(eventBanner);
                        //rc.fit().into(eventBanner);
                        break;
                }

            }
        }
    }

    @Override
    public void showProgressFrame() {
        eventBanner.setVisibility(View.GONE);
        avatar.setVisibility(View.GONE);
        avatarTxt.setVisibility(View.GONE);
        eventBannerTxt.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressFrame() {
        eventBanner.setVisibility(View.VISIBLE);
        avatar.setVisibility(View.VISIBLE);
        avatarTxt.setVisibility(View.VISIBLE);
        eventBannerTxt.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadAvatarPicture(String uri) {
        Picasso.get().load(uri).error(R.drawable.logo).into(avatar);
    }

    @Override
    public void loadEvenerBannerPicture(String uri) {
        Picasso.get().load(uri).error(R.drawable.logo).fit().into(avatar);
    }
}

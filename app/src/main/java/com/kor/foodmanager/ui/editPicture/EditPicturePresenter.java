package com.kor.foodmanager.ui.editPicture;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.utils.ObjectUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@InjectViewState
public class EditPicturePresenter extends MvpPresenter<IEditPicture> {

    public void loadImage(int position, Uri picUri) {
        new LoadImageTask(position, picUri).execute();
    }


    private class LoadImageTask extends AsyncTask<Void, Void, Void> {
        private int position;
        private Uri picUri;
        private Boolean res;

        public LoadImageTask(int position, Uri picUri) {
            this.position = position;
            this.picUri = picUri;
            res = true;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            Map config = new HashMap();
            config.put("cloud_name", "newmishpahug");
            config.put("api_key", "893573575281754");
            config.put("api_secret", "aYACgLcWNlBuKjxd5_McsRkf4pQ");
            Cloudinary cloudinary = new Cloudinary(config);
            Log.d("MY_TAG", "uri: "+picUri.getPath());
            File imgFile = new File(picUri.getPath());

            switch (position) {
                case EditPictureFragment.AVATAR_EDIT_REQUEST:
                    try {
                        cloudinary.uploader().upload(imgFile, ObjectUtils.asMap("public_id", "avatar"));
                    } catch (IOException e) {
                        res = false;
                        e.printStackTrace();
                    }
                    break;
                case EditPictureFragment.EVENT_BANNER_EDIT_REQUEST:
                    try {
                        cloudinary.uploader().upload(imgFile, ObjectUtils.asMap("public_id", "event_banner"));
                    } catch (IOException e) {
                        res = false;
                        e.printStackTrace();
                    }
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("MY_TAG", "res: "+res);
        }
    }

}

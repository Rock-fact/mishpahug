package com.kor.foodmanager.ui.editPicture;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.pictureEditor.IEditPictureRepository;

import java.io.IOException;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class EditPicturePresenter extends MvpPresenter<IEditPicture> {
    private String result;
    @Inject
    IEditPictureRepository editPictureRepository;
    @Inject
    Router router;

    public EditPicturePresenter() {
        App.get().mainComponent().inject(this);
    }

    public String loadImage(int position, Uri picUri) {
        new LoadImageTask(position, picUri).execute();
        return result;
    }

    public String getPicUrl(int position) {
        switch (position) {
            case EditPictureFragment.AVATAR_EDIT_REQUEST:
                return editPictureRepository.getPicUrl("_avatar");
            case EditPictureFragment.EVENT_BANNER_EDIT_REQUEST:
                return editPictureRepository.getPicUrl("_event_banner");
            default:
                return null;
        }
    }

    public String deletePic(String name) {
        new DeleteImgTask(name).execute();
        return name;
    }

    private class LoadImageTask extends AsyncTask<Void, Void, Void> {
        private int position;
        private Uri picUri;
        private String res;

        public LoadImageTask(int position, Uri picUri) {
            this.position = position;
            this.picUri = picUri;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            switch (position) {
                case EditPictureFragment.AVATAR_EDIT_REQUEST:
                    res = editPictureRepository.uploadPic(picUri, "_avatar");
                    break;
                case EditPictureFragment.EVENT_BANNER_EDIT_REQUEST:
                    res = editPictureRepository.uploadPic(picUri, "_event_banner");
                    break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            result=res;
        }
    }

    private class DeleteImgTask extends AsyncTask<Void, Void, Void>{
        private String name;
        private Boolean res;

        public DeleteImgTask(String name) {
            this.name = name;
            res = true;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                editPictureRepository.destroyPic(name);
            } catch (IOException e) {
                router.showSystemMessage(e.getMessage());
                res = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("MY_TAG", "deleting image onPostExecute: "+res);
        }
    }

}

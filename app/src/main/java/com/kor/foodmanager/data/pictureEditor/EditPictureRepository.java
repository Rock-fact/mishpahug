package com.kor.foodmanager.data.pictureEditor;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.auth.IAuthRepository;

import java.util.HashMap;
import java.util.Map;

public class EditPictureRepository implements IEditPictureRepository{
    private IAuthRepository authRepository;
    private String public_id;

    public EditPictureRepository(Context context, IAuthRepository authRepository) {
        Map config = new HashMap();
        config.put("cloud_name", "newmishpahug");
        config.put("api_key", "893573575281754");
        config.put("api_secret", "aYACgLcWNlBuKjxd5_McsRkf4pQ");
        MediaManager.init(context, config);
        this.authRepository = authRepository;
        public_id = authRepository.getToken().substring(6);
    }

    @Override
    public String uploadPic(Uri uri, String name) {
        return MediaManager.get().upload(uri).option("public_id",public_id.concat(name)).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {

            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {

            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
               // public_id = authRepository.getToken().substring(6);
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {

            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {

            }
        }).dispatch();
    }

    @Override
    public String getPicUrl(String name) {
        Log.d("MY_TAG", "getPicUrl public id: " + public_id.concat(name));
        return MediaManager.get().url().generate(public_id.concat(name));
    }

    @Override
    public String destroyPic(String name) {
        Log.d("MY_TAG", "destroyPic: "+public_id.concat(name));
        String uri = MediaManager.get().url().generate("sample");
        return MediaManager.get().upload(R.drawable.logo).option("public_id",public_id.concat(name)).dispatch();
    }

}

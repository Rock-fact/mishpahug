package com.kor.foodmanager.data.pictureEditor;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.auth.IAuthRepository;

import java.io.IOException;
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
        String res = MediaManager.get().upload(uri).option("public_id",public_id.concat(name))
                .option("overwrite", true).dispatch();
        Log.d("LOADER", "uploadPic: "+res);
        return res;
    }

    @Override
    public String getPicUrl(String name) {
        Log.d("MY_TAG", "getPicUrl public id: " + public_id.concat(name));
        return MediaManager.get().url().generate(public_id.concat(name));
    }

    @Override
    public String destroyPic(String name) throws IOException {
//        Log.d("MY_TAG", "destroyPic: "+public_id.concat(name));
//        String uri = MediaManager.get().url().generate("sample");

        MediaManager.get().getCloudinary().uploader().destroy(public_id.concat(name), ObjectUtils.emptyMap());
        //return MediaManager.get().upload(R.drawable.logo).option("public_id",public_id.concat(name)).dispatch();
        return name;
    }

}

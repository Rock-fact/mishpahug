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
import com.kor.foodmanager.ui.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    }

    @Override
    public String uploadPic(Uri uri, String name) {
        public_id = authRepository.getToken().substring(6);
        String res = MediaManager.get().upload(uri).option("public_id",public_id.concat(name))
                .option("invalidate", true)
                .option("overwrite", true).dispatch();
        Log.d("LOADER", "uploadPic: "+res);
        return res;
    }

    @Override
    public String getPicUrl(String name) {
        if(public_id==null) {
            public_id = authRepository.getToken().substring(6);
        }
        Log.d("MY_TAG", "getPicUrl public id: " + public_id.concat(name));
        return MediaManager.get().url().generate(public_id.concat(name));
    }

    @Override
    public String destroyPic(String name) throws IOException {

        Map options = new HashMap();
        options.put("invalidate", true);
        MediaManager.get().getCloudinary().uploader().destroy(public_id.concat(name), options);
        //return MediaManager.get().upload(R.drawable.logo).option("public_id",public_id.concat(name)).dispatch();
        return name;
    }

    @Override
    public List<String> getPictureLincs() {
        List<String> links = new ArrayList<>();
        links.add(0, getPicUrl(MainActivity.AVATAR_PICTURE));
        links.add(1, getPicUrl(MainActivity.EVENT_BANNER_PICTURE));
        return links;
    }

    @Override
    public List<String> getPictureLincsFromServer() {
        List<String> links = new ArrayList<>();
        links.add(0,"none");
        links.add(1,"none");
        Log.d("LINKSLIST", "default: "+links.get(0)+" "+links.get(1)+ " size: "+links.size());
        if(authRepository.getUser().getPictureLink().size()>0) {
            links.add(0, authRepository.getUser().getPictureLink().get(0));
            Log.d("LINKSLIST", "adding 0: "+links.get(0)+" "+links.get(1)+ " size: "+links.size());
        }
        if(authRepository.getUser().getPictureLink().size()>1) {
            links.add(1, authRepository.getUser().getPictureLink().get(1));
            Log.d("LINKSLIST", "adding 1: "+links.get(0)+" "+links.get(1)+ " size: "+links.size());
        }
        return links;
    }


}

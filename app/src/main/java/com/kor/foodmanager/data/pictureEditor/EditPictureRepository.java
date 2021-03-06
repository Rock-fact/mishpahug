package com.kor.foodmanager.data.pictureEditor;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.utils.ObjectUtils;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.auth.IAuthRepository;
import com.kor.foodmanager.ui.MainActivity;
import com.kor.foodmanager.ui.editPicture.EditPictureFragment;
import com.kor.foodmanager.ui.editPicture.EditPicturePresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditPictureRepository implements IEditPictureRepository{
    private IAuthRepository authRepository;
    private String public_id;
    private List<String> notLoadedUriList;
    private long version;
    private MyUplosdPicListener listener;

    public EditPictureRepository(Context context, IAuthRepository authRepository) {
        Map config = ObjectUtils.asMap("cloud_name", "newmishpahug",
        "api_key", "893573575281754", "api_secret", "aYACgLcWNlBuKjxd5_McsRkf4pQ");
        MediaManager.init(context, config);
        this.authRepository = authRepository;
        if(notLoadedUriList==null) {
            notLoadedUriList = new ArrayList<>();
            notLoadedUriList.add(0, "none");
            notLoadedUriList.add(1, "none");
        }
        version=0;
    }

    public void setListener(MyUplosdPicListener listener) {
        this.listener = listener;
    }

    @Override
    public void saveLinks(Uri picUri, int position) {
        Log.d("PICS", "position: "+position);
        if(position==1){
            notLoadedUriList.remove(0);
            notLoadedUriList.add(0, picUri.toString());
        } else {
            notLoadedUriList.remove(1);
            notLoadedUriList.add(1, picUri.toString());
        }
    }

    @Override
    public void clearNonLoadedList() {
        notLoadedUriList.clear();
        notLoadedUriList.add(0, "none");
        notLoadedUriList.add(1, "none");
    }


    @Override
    public String uploadPic(Uri uri, String name, int position) {
            public_id = authRepository.getToken().substring(6);
            saveLinks(uri, position);
            String res = MediaManager.get().upload(uri)
                    .option("invalidate", true)
                    .option("overwrite", true)
                    .option("public_id", public_id.concat(name))
                    .callback(new UploadCallback() {
                        @Override
                        public void onStart(String requestId) {

                        }

                        @Override
                        public void onProgress(String requestId, long bytes, long totalBytes) {

                        }

                        @Override
                        public void onSuccess(String requestId, Map resultData) {
                            if (listener != null) {
                                listener.onPicUploaded(position);
                            }
                            Log.d("BOOO", "onSuccess: "+resultData);

                        }

                        @Override
                        public void onError(String requestId, ErrorInfo error) {

                        }

                        @Override
                        public void onReschedule(String requestId, ErrorInfo error) {

                        }
                    })
                    .dispatch();

            return res;

    }

    @Override
    public List<String> getNotLoadedUriList() {
       return notLoadedUriList;
    }


    @Override
    public String destroyPic(String name) throws IOException {

        Map options = new HashMap();
        options.put("invalidate", true);
        MediaManager.get().getCloudinary().uploader().destroy(public_id.concat(name), options);
        return name;
    }

    @Override
    public List<String> getPictureLincs() {
        List<String> links = new ArrayList<>();
        if(public_id==null) {
            if(authRepository.getToken()!=null) {
                public_id = authRepository.getToken().substring(6);
            } else {
                public_id=null;
            }
        }
        if(public_id!=null) {
            links.add(0, cropForAvatar(public_id.concat(MainActivity.AVATAR_PICTURE)));
            links.add(1, cropForBanner(public_id.concat(MainActivity.EVENT_BANNER_PICTURE)));
        } else {
            links.add("none");
            links.add("none");
        }
        return links;
    }

    @Override
    public List<String> getPictureLincsFromServer() {
        List<String> links = new ArrayList<>();
        links.add(0,"none");
        links.add(1,"none");
        Log.d("LINKSLIST", "default: "+links.get(0)+" "+links.get(1)+ " size: "+links.size());
        if(authRepository.getUser().getPictureLink().size()>0) {
            links.add(0, cropForAvatar(authRepository.getUser().getPictureLink().get(0)));
            Log.d("LINKSLIST", "adding 0: "+links.get(0)+" "+links.get(1)+ " size: "+links.size());
        }
        if(authRepository.getUser().getPictureLink().size()>1) {
            links.add(1, cropForBanner(authRepository.getUser().getPictureLink().get(1)));
            Log.d("LINKSLIST", "adding 1: "+links.get(0)+" "+links.get(1)+ " size: "+links.size());
        }
        return links;
    }

    @Override
    public String cropForAvatar(String loadedImg) {
        return MediaManager.get().url()
                .version(version++)
                .transformation(new Transformation()
                .width(500).height(500).crop("thumb").gravity("face").radius("max").fetchFormat("png"))
                .generate(loadedImg);
    }

    @Override
    public String cropForBanner(String loadedImg) {
        return MediaManager.get().url()
                .version(version++)
                .transformation(new Transformation().width(350).height(170)
                        .crop("scale").fetchFormat("png")).generate(loadedImg);

    }

    public interface MyUplosdPicListener{
        void onPicUploaded(int position);
    }

    //TODO public id and non format

}

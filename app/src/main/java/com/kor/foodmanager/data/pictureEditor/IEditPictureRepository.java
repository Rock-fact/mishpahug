package com.kor.foodmanager.data.pictureEditor;

import android.net.Uri;

public interface IEditPictureRepository {
    void uploadPic(Uri uri, String name);
    String getPicUrl(String name);
    void destroyPic(String publicId);


}

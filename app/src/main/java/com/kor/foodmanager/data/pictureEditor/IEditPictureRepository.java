package com.kor.foodmanager.data.pictureEditor;

import android.net.Uri;

import java.io.IOException;
import java.util.List;

public interface IEditPictureRepository {
    String uploadPic(Uri uri, String name);
    String getPicUrl(String name);
    String destroyPic(String name) throws IOException;
    List<String> getPictureLincs();

}

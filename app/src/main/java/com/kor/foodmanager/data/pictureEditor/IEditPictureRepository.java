package com.kor.foodmanager.data.pictureEditor;

import android.net.Uri;

import java.io.IOException;
import java.util.List;

public interface IEditPictureRepository {
    String uploadPic(Uri uri, String name, int position);
    List<String> getNotLoadedUriList();
    String destroyPic(String name) throws IOException;
    List<String> getPictureLincs();
    List<String> getPictureLincsFromServer();
    String cropForAvatar (String loadedImg);
    String cropForBanner (String loadedImg);
    void setListener(EditPictureRepository.MyUplosdPicListener listener);
    void saveLinks(Uri picUri, int position);
    void clearNonLoadedList();
}

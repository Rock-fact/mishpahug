package com.kor.foodmanager.ui.eventList;

import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.model.EventsInProgressRequestDto;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.ui.MainActivity;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class FiltersPresenter extends MvpPresenter<IFilters> {
    @Inject Router router;
    @Inject IUserDataRepository dataRepository;
    private EventsInProgressRequestDto filters = null;

    public FiltersPresenter() {
        App.get().mainComponent().inject(this);
    }

    public void apply(){
        if (filters!=null) {
            router.navigateTo(MainActivity.EVENT_LIST_SCREEN, filters);
        } else {
            router.navigateTo(MainActivity.EVENT_LIST_SCREEN);
        }
    }

    public void reset(){
        filters=null;
    }


    private class GetStaticFieldsTask extends AsyncTask<Void, Void, String> {

        private boolean successful;
        private StaticfieldsDto staticFields;

        public GetStaticFieldsTask() {
            successful = true;
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                staticFields = dataRepository.staticFields();
                return "Done";
            } catch (Exception e) {
                e.printStackTrace();
                successful = false;
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(successful){
                getViewState().setStaticFields(staticFields);
            }else{
                router.showSystemMessage(s);
            }
        }
    }


}

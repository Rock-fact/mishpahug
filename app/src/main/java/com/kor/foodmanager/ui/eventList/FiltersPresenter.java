package com.kor.foodmanager.ui.eventList;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.model.EventsInProgressRequestDto;
import com.kor.foodmanager.data.model.FiltersDto;
import com.kor.foodmanager.data.model.LocationDto;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.ui.MainActivity;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class FiltersPresenter extends MvpPresenter<IFilters> {
    @Inject Router router;
    @Inject IUserDataRepository dataRepository;
    private EventsInProgressRequestDto filters;
    private LocationDto baseLocation;

    public FiltersPresenter() {
        App.get().mainComponent().inject(this);
       filters = new EventsInProgressRequestDto();
        baseLocation = new LocationDto();
        baseLocation.setLat(32.109333);
        baseLocation.setLng(34.855499);
        baseLocation.setRadius(2000.00);
        filters.setLocation(baseLocation);
        filters.setFilters(new FiltersDto());
    }

    public void setConfession(String confession){
        filters.getFilters().setConfession(confession);
    }

    public void setHoliday(String holiday){
        filters.getFilters().setHolidays(holiday);
    }

    public void setFood(String food){
        filters.getFilters().setFood(food);
    }

    private Boolean filterFieldsFilled(){
        if(filters.getFilters().getDateFrom()!=null & filters.getFilters().getDateTo()!=null &
                filters.getFilters().getConfession()!=null & filters.getFilters().getHolidays()!=null
                & filters.getFilters().getFood()!=null & filters.getLocation()!=null) {
            return true;
        } else {
            Log.d("MY_TAG", "filterFieldsFilled: "+filters.getFilters().getDateFrom());
            Log.d("MY_TAG", "filterFieldsFilled: "+filters.getFilters().getDateTo());
            Log.d("MY_TAG", "filterFieldsFilled: "+filters.getFilters().getHolidays());
            Log.d("MY_TAG", "filterFieldsFilled: "+filters.getFilters().getFood());
            Log.d("MY_TAG", "filterFieldsFilled: "+filters.getFilters().getConfession());
            return false;
        }
    }

    public void apply(){

        filters.getFilters().setDateFrom("2019-03-01");
        filters.getFilters().setDateTo("2019-05-10");
        //TODO delete
        if (filterFieldsFilled()) {
            router.navigateTo(MainActivity.EVENT_LIST_SCREEN, filters);
        } else {
            router.showSystemMessage("All filters have to be selected");
            //router.navigateTo(MainActivity.EVENT_LIST_SCREEN);
        }
    }

    public void reset(){
        filters.getFilters().setDateFrom(null);
    }

    public void setStaticFields(){
        new GetStaticFieldsTask().execute();
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

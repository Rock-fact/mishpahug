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
//        filters = new EventsInProgressRequestDto();
//        baseLocation = new LocationDto();
//        baseLocation.setLat(32.109333);
//        baseLocation.setLng(34.855499);
//        baseLocation.setRadius(2000.00);
//        filters.setLocation(baseLocation);
//        filters.setFilters(new FiltersDto());
    }

    private void initFilters(){
        filters = new EventsInProgressRequestDto();
        baseLocation = new LocationDto();
        baseLocation.setLat(32.109333);
        baseLocation.setLng(34.855499);
        baseLocation.setRadius(2000.00);
        filters.setLocation(baseLocation);
        filters.setFilters(new FiltersDto());
    }

    public void setConfession(String confession){
        if(filters==null){
            initFilters();
        }
        filters.getFilters().setConfession(confession);
    }

    public void setHoliday(String holiday){
        if(filters==null){
            initFilters();
        }filters.getFilters().setHolidays(holiday);
    }

    public void setFood(String food){
        if(filters==null){
            initFilters();
        } filters.getFilters().setFood(food);
    }

    public void setDateFrom(String date) {  if(filters==null){
        initFilters();
    }
        filters.getFilters().setDateFrom(date);
    }

    public void setDateTo(String date){
        if(filters==null){
            initFilters();
        } filters.getFilters().setDateTo(date);
    }

    public void setCity(Object city) { //TODO
        if(filters==null){
            initFilters();
        }
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
       if (filters==null || filters.getFilters()==null){
           new LocationGetterTask().execute(); //TODO Del
            router.backTo(MainActivity.EVENT_LIST_SCREEN);
        }  else if (!filterFieldsFilled()) {
           router.showSystemMessage("Select all filters or push the reset button!");
        }  else {
           new LocationGetterTask().execute();
           //router.newRootScreen(MainActivity.EVENT_LIST_SCREEN, filters);
        }
    }

    public void reset(){
        if(filters!=null) {
            filters.getFilters().setDateFrom(null);
            filters.getFilters().setDateTo(null);
            filters.getFilters().setHolidays(null);
            filters.getFilters().setFood(null);
            filters.getFilters().setConfession(null);
            filters.setLocation(baseLocation);
            filters.setFilters(null);
        }
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
                if(filters!=null) {
                    if (filters.getFilters() != null) {
                        getViewState().setSpinners(filters.getFilters());
                        if(filters.getFilters().getDateFrom()!=null)
                        getViewState().setDates(filters.getFilters());
                    }
                }
            }else{
                router.showSystemMessage(s);
            }
        }
    }

    private class LocationGetterTask extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            getViewState().hideProgressFrame();
        }
    }


}

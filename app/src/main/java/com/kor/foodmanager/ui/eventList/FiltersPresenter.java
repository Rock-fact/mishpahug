package com.kor.foodmanager.ui.eventList;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.location.places.Place;
import com.kor.foodmanager.App;
import com.kor.foodmanager.data.model.EventsInProgressRequestDto;
import com.kor.foodmanager.data.model.FiltersDto;
import com.kor.foodmanager.data.model.LocationDto;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.data.userData.IUserDataRepository;
import com.kor.foodmanager.ui.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.terrakok.cicerone.Router;

@InjectViewState
public class FiltersPresenter extends MvpPresenter<IFilters> {
    @Inject Router router;
    @Inject IUserDataRepository dataRepository;
    public EventsInProgressRequestDto filters;
    private LocationDto baseLocation;
    private String citySelected;


    public FiltersPresenter() {
        App.get().mainComponent().inject(this);
    }

    private void initFilters(){
        filters = new EventsInProgressRequestDto();
        baseLocation = new LocationDto();
        baseLocation.setLat(32.109333);
        baseLocation.setLng(34.855499);
        baseLocation.setRadius(500.00);
        filters.setLocation(baseLocation);
        filters.setFilters(new FiltersDto());
    }

    public void setConfession(String confession, int position){
        if(filters==null){
            initFilters();
        }
        filters.getFilters().setConfession(confession);
        filters.getSpinnerPositions().setConfession(position);
    }

    public void setHoliday(String holiday, int position){
        if(filters==null){
            initFilters();
        }filters.getFilters().setHolidays(holiday);
        filters.getSpinnerPositions().setHoliday(position);
    }

    public void setFood(String food, int position){
        if(filters==null){
            initFilters();
        } filters.getFilters().setFood(food);
        filters.getSpinnerPositions().setFood(position);
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

    public void setCity(String city, int position) {
        if(filters==null){
            initFilters();
        } citySelected = city;
        filters.getSpinnerPositions().setCity(position);

    }

    private void applyCity(){
        Geocoder geocoder = new Geocoder(App.get());
        List <Address> location = new ArrayList<>();
        try {
            location = geocoder.getFromLocationName(citySelected+", Israel", 1);
            Log.d("MY_TAG", "setCity: "+geocoder.getFromLocationName(citySelected+", Israel", 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(location.get(0)!=null){
            filters.getLocation().setLng(location.get(0).getLongitude());
            filters.getLocation().setLat(location.get(0).getLatitude());
            Log.d("MY_TAG", "coordinates: "+filters.getLocation().toString());
            //TODO radius
        }
    }

    private Boolean filterFieldsFilled(){
        if(filters.getFilters().getDateFrom()!=null & filters.getFilters().getDateTo()!=null &
                filters.getFilters().getConfession()!=null & filters.getFilters().getHolidays()!=null
                & filters.getFilters().getFood()!=null & filters.getLocation()!=null) {
            return true;
        } else {
//            Log.d("MY_TAG", "filterFieldsFilled: "+filters.getFilters().getDateFrom());
//            Log.d("MY_TAG", "filterFieldsFilled: "+filters.getFilters().getDateTo());
//            Log.d("MY_TAG", "filterFieldsFilled: "+filters.getFilters().getHolidays());
//            Log.d("MY_TAG", "filterFieldsFilled: "+filters.getFilters().getFood());
//            Log.d("MY_TAG", "filterFieldsFilled: "+filters.getFilters().getConfession());
            return false;
        }
    }

    public void apply(){
       if (filters==null || filters.getFilters()==null){
            router.replaceScreen(MainActivity.EVENT_LIST_SCREEN);
        }  else if (!filterFieldsFilled()) {
           router.showSystemMessage("Select all filters or push the reset button!");
        }  else {
           applyCity();
           router.newRootScreen(MainActivity.EVENT_LIST_SCREEN, filters);
        }
    }

    public void reset(){
        if(filters!=null) {
            filters.getFilters().setDateFrom(null);
            filters.getFilters().setDateTo(null);
            filters.getFilters().setHolidays(null);
            filters.getFilters().setFood(null);
            filters.getFilters().setConfession(null);
            filters.setLocation(null);
            filters.setFilters(null);
            filters=null;
        }
    }

    public void setStaticFields(){
        new GetStaticFieldsTask().execute();
    }

    public void setStaticFields(EventsInProgressRequestDto filters) {
        new GetStaticFieldsTask(filters).execute();
    }


    private class GetStaticFieldsTask extends AsyncTask<Void, Void, String> {

        private boolean successful;
        private StaticfieldsDto staticFields;
       // private EventsInProgressRequestDto fieldsFilters;

        public GetStaticFieldsTask() {
            successful = true;
            //fieldsFilters = null;
        }
        public GetStaticFieldsTask(EventsInProgressRequestDto f) {
            successful = true;
            //fieldsFilters = f;
            filters = f;
        }

        @Override
        protected void onPreExecute() {
            getViewState().showProgressFrame();
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
                getViewState().hideProgressFrame();
                if(filters!=null) {
                    if (filters.getFilters() != null) {
                        getViewState().setDates(filters.getFilters());
                    }
                    if(filters.getSpinnerPositions()!=null){
                        getViewState().setSpinners(filters.getSpinnerPositions());
                    }
                }
            }else{
                router.showSystemMessage(s);
            }
        }
    }


}

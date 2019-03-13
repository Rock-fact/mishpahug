package com.kor.foodmanager.ui.eventList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.StaticfieldsDto;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FiltersFragment extends MvpAppCompatFragment implements IFilters, AdapterView.OnItemSelectedListener {
@InjectPresenter FiltersPresenter presenter;
private Unbinder unbinder;
private ArrayList<String> cities;

private static final String CONFESSION = "--select confession--";
private static final String HOLIDAY = "--select holiday--";
private static final String FOOD = "--select food--";
//private static final String CITY = "--select city--";

@BindView(R.id.confession_spinner) Spinner confessionSpinner;
@BindView(R.id.holiday_spinner) Spinner holidaySpinner;
@BindView(R.id.food_pref_spinner) Spinner foodPrefSpinner;
//@BindView(R.id.city_spinner) Spinner citySpinner;



    public FiltersFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.setStaticFields();
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.apply_btn)
    void apply(){
        presenter.apply();
    }

    @OnClick(R.id.reset_btn)
    void reset(){
        presenter.reset();
        confessionSpinner.setSelection(0);
        holidaySpinner.setSelection(0);
        foodPrefSpinner.setSelection(0);
    }

    @Override
    public void setStaticFields(StaticfieldsDto staticFields) {
        ArrayAdapter<String> confessionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, staticFields.getConfession());
        confessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        confessionAdapter.insert(CONFESSION, 0);
        confessionSpinner.setAdapter(confessionAdapter);
        confessionSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> holidayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, staticFields.getHoliday());
        holidayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holidayAdapter.insert(HOLIDAY, 0);
        holidaySpinner.setAdapter(holidayAdapter);
        ArrayAdapter<String> foodPrefAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, staticFields.getFoodPreferences());
        foodPrefAdapter.insert(FOOD, 0);
        foodPrefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodPrefSpinner.setAdapter(foodPrefAdapter);
//        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, cities);
//        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        citySpinner.setAdapter(cityAdapter);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.confession_spinner){
            if(confessionSpinner.getSelectedItemPosition()!=0) {
                presenter.setConfession(confessionSpinner.getSelectedItem().toString());
                //Log.d("MY_TAG", "onItemSelected: "+confessionSpinner.getSelectedItem().toString());
            }
        }
        if(parent.getId()==R.id.holiday_spinner){
            if(holidaySpinner.getSelectedItemPosition()!=0) {
                presenter.setHoliday(holidaySpinner.getSelectedItem().toString());
                Log.d("MY_TAG", "onItemSelected: "+holidaySpinner.getSelectedItem().toString());
            }
        }
        if(parent.getId()==R.id.food_pref_spinner){
            if(foodPrefSpinner.getSelectedItemPosition()!=0) {
                presenter.setFood(foodPrefSpinner.getSelectedItem().toString());
                Log.d("MY_TAG", "onItemSelected: "+foodPrefSpinner.getSelectedItem().toString());
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

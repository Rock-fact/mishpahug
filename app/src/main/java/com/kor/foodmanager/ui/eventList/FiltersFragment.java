package com.kor.foodmanager.ui.eventList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.StaticfieldsDto;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FiltersFragment extends MvpAppCompatFragment implements IFilters {
@InjectPresenter FiltersPresenter presenter;
private Unbinder unbinder;
private StaticfieldsDto staticfields;

@BindView(R.id.confession_spinner) Spinner confessionSpinner;
@BindView(R.id.holiday_spinner) Spinner holidaySpinner;
@BindView(R.id.city_spinner) Spinner citySpinner;
@BindView(R.id.food_pref_spinner) Spinner foodPrefSpinner;

    public FiltersFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);
        unbinder = ButterKnife.bind(this, view);
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
    }

    @Override
    public void setStaticFields(StaticfieldsDto staticFields) {
        ArrayAdapter<String> confessionAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getConfession());
        confessionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        confessionSpinner.setAdapter(confessionAdapter);
        ArrayAdapter<String> holidayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getHoliday());
        holidayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holidaySpinner.setAdapter(holidayAdapter);
        ArrayAdapter<String> foodPrefAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, staticFields.getFoodPreferences());
        foodPrefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodPrefSpinner.setAdapter(foodPrefAdapter);
        //ArrayAdapter<String> cityAdapter = //TODO

    }
}

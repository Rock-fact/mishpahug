package com.kor.foodmanager.ui.eventList;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.FiltersDto;
import com.kor.foodmanager.data.model.StaticfieldsDto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FiltersFragment extends MvpAppCompatFragment implements IFilters, AdapterView.OnItemSelectedListener {
@InjectPresenter FiltersPresenter presenter;
private Unbinder unbinder;
private Calendar calendar;
private Long minDate;

private static final String CONFESSION = "--select confession--";
private static final String HOLIDAY = "--select holiday--";
private static final String FOOD = "--select food--";

@BindView(R.id.event_date) TextView eventDateTxt;
@BindView(R.id.date_from_txt) TextView dateFromTxt;
@BindView(R.id.date_to_txt) TextView dateToTxt;
@BindView(R.id.confession_spinner) Spinner confessionSpinner;
@BindView(R.id.holiday_spinner) Spinner holidaySpinner;
@BindView(R.id.food_pref_spinner) Spinner foodPrefSpinner;
@BindView(R.id.city_spinner) Spinner citySpinner;




    public FiltersFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);
        unbinder = ButterKnife.bind(this, view);
        //calendar = Calendar.getInstance(); //TODO Provider maybe
        //eventDateTxt.setVisibility(View.VISIBLE);
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
        dateFromTxt.setVisibility(View.GONE);
        dateToTxt.setVisibility(View.GONE);
        eventDateTxt.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.event_date, R.id.date_from_txt})
    public void setDateFrom() {
        calendar=null;
        calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateFromCallback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                dialog.show();
    }


    DatePickerDialog.OnDateSetListener dateFromCallback = (view, year, month, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat formDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = calendar.getTime();
        String date = formDate.format(dateTime);
        presenter.setDateFrom(date);
        dateFromTxt.setText(date);
        if(eventDateTxt.getVisibility()==View.VISIBLE) {
            eventDateTxt.setVisibility(View.GONE);
            dateFromTxt.setVisibility(View.VISIBLE);
            dateToTxt.setVisibility(View.VISIBLE);
        }

        setDateTo();

    };


   // @OnClick(R.id.date_to_txt)
    public void setDateTo() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateToCallback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        dialog.show();
    }


    DatePickerDialog.OnDateSetListener dateToCallback = (view, year, month, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat formDate = new SimpleDateFormat("yyyy-MM-dd");
        String date = formDate.format(calendar.getTime());
        presenter.setDateTo(date);
        dateToTxt.setText(" - "+date);
    };


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
        holidaySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> foodPrefAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, staticFields.getFoodPreferences());
        foodPrefAdapter.insert(FOOD, 0);
        foodPrefAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodPrefSpinner.setAdapter(foodPrefAdapter);
        foodPrefSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cities, android.R.layout.simple_spinner_item);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void setSpinners(FiltersDto filters) {
        //TODO
    }

    @Override
    public void setDates(FiltersDto filters) {
        eventDateTxt.setVisibility(View.GONE);
        dateToTxt.setVisibility(View.VISIBLE);
        dateFromTxt.setVisibility(View.VISIBLE);
        dateFromTxt.setText(filters.getDateFrom());
        if(filters.getDateTo()!=null){
            dateToTxt.setText(filters.getDateTo());
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.confession_spinner){
            if(confessionSpinner.getSelectedItemPosition()!=0) {
                presenter.setConfession(confessionSpinner.getSelectedItem().toString());
            }
        }
        if(parent.getId()==R.id.holiday_spinner){
            if(holidaySpinner.getSelectedItemPosition()!=0) {
                presenter.setHoliday(holidaySpinner.getSelectedItem().toString());
            }
        }
        if(parent.getId()==R.id.food_pref_spinner){
            if(foodPrefSpinner.getSelectedItemPosition()!=0) {
                presenter.setFood(foodPrefSpinner.getSelectedItem().toString());
            }
        }
        if(parent.getId()==R.id.city_spinner){ //TODO
            if(citySpinner.getSelectedItemPosition()!=0){
                presenter.setCity(citySpinner.getSelectedItem());
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

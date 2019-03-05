package com.kor.foodmanager.ui.addEvent;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.AddressDto;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.IToolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.kor.foodmanager.ui.MainActivity.TAG;

public class AddEventFragment extends MvpAppCompatFragment implements IAddEvent{
    @InjectPresenter AddEventPresenter presenter;
    private Unbinder unbinder;
    @BindView(R.id.inputTitle) TextView inputTitle;
    @BindView(R.id.inputAddress) TextView inputAddress;
    @BindView(R.id.textDate) TextView textDate;
    @BindView(R.id.inputDescription) TextView inputDescription;
    @BindView(R.id.createBtn) Button createBtn;
    @BindView(R.id.inputHoliday) TextView inputHoliday;
    @BindView(R.id.duration_picker) NumberPicker durationPicker;
    @BindView(R.id.pickerFrame) FrameLayout pickerFrame;
    @BindView(R.id.confirm_duration) Button confirmDuration;

    Calendar dateAndTime;
    Object data;
    private IToolbar iToolbar;
    private int duration = 0;

    public AddEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        unbinder = ButterKnife.bind(this,view);
        dateAndTime = Calendar.getInstance();

        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Add event",true);

        return view;
    }

    @OnClick(R.id.textDate)
    public void setDate() {
        new DatePickerDialog(getActivity(), dateCallback,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(){
        new TimePickerDialog(getActivity(),timeCallback,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE),
                true)
                .show();
    }

    public void setDuration(){
        pickerFrame.setVisibility(View.VISIBLE);
        durationPicker.setMaxValue(24);
        durationPicker.setMinValue(0);
        durationPicker.setValue(duration);
    }

    @OnClick(R.id.confirm_duration)
    public void confirmDuration(){
        pickerFrame.setVisibility(View.GONE);
        duration = durationPicker.getValue();
        textDate.setText(textDate.getText().toString()+", duration: "+duration+" hours");
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.createBtn)
    public void makeEvent(){
        presenter.addNewEvent(createEvent());
    }

    public EventDto createEvent(){
        EventDto event = new EventDto();
        event.setTitle(inputTitle.getText().toString());
        event.setHoliday(inputHoliday.getText().toString());                // TODO: 05.03.2019 options 
        event.setDescription(inputDescription.getText().toString());
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = dateAndTime.getTime();
        String date = form.format(dateTime);
        event.setDate(date);
        event.setDuration(duration);
        AddressDto addressDto = new AddressDto();
        addressDto.setPlace_id(inputAddress.getText().toString());
        event.setAddress(addressDto);
        return event;
    }

    //обработчик выбора даты
    DatePickerDialog.OnDateSetListener dateCallback = (view, year, month, dayOfMonth) -> {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, month);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setTime();
    };

    //обработчик выбора времени
    TimePickerDialog.OnTimeSetListener timeCallback = (view, hourOfDay, minute) -> {
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);
        textDate.setText(DateUtils.formatDateTime(getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
        setDuration();
    };

}

package com.kor.foodmanager.ui.addEvent;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.AddressDto;
import com.kor.foodmanager.data.model.EventDto;

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
    Calendar dateAndTime;
    Object data;

    public AddEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        unbinder = ButterKnife.bind(this,view);
        dateAndTime = Calendar.getInstance();
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
        event.setDescription(inputDescription.getText().toString());
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = dateAndTime.getTime();
        String date = form.format(dateTime);
        event.setDate(date);
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
            textDate.setText(DateUtils.formatDateTime(getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));};

    @Override
    public void showProgressFrame() {
        // TODO: 22.02.2019
    }

    @Override
    public void hideProgressFrame() {
        // TODO: 22.02.2019
    }

    @Override
    public void showError(String error) {
        new AlertDialog.Builder(getActivity())
                .setMessage(error)
                .setTitle("Error!")
                .setPositiveButton("Ok", null)
                .setCancelable(false)
                .create()
                .show();
    }

}

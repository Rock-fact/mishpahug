package com.kor.foodmanager.ui.addEvent;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.IToolbar;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.kor.foodmanager.ui.MainActivity.TAG;

// TODO: 06.03.2019 for future improvement we need to replace current fragment with custom. for example:
// https://stackoverflow.com/questions/36999647/how-to-customize-placeautocomplete-widget-dialog-design-to-list-places
// https://techstricks.com/custom-google-place-autocomplete-android/
public class AddEventFragment extends MvpAppCompatFragment implements IAddEvent, OnDateSelectedListener {
    private static final int REQUEST_SELECT_PLACE = 1000;
    private static final String CHOOSE_HOLIDAY = "Choose holiday";
    @InjectPresenter
    AddEventPresenter presenter;
    private Unbinder unbinder;
    @BindView(R.id.inputTitle)
    TextView inputTitle;
    @BindView(R.id.textDate)
    TextView textDate;
    @BindView(R.id.inputDescription)
    TextView inputDescription;
    @BindView(R.id.createBtn)
    Button createBtn;
    @BindView(R.id.duration_picker)
    NumberPicker durationPicker;
    @BindView(R.id.pickerFrame)
    FrameLayout pickerFrame;
    @BindView(R.id.confirm_duration)
    Button confirmDuration;
    @BindView(R.id.holiday_spinner)
    Spinner holidaySpinner;
    ArrayAdapter<String> spinnerAdapter;
    int selectedHolidayPos;
    SupportPlaceAutocompleteFragment autocompleteFragment;
    private static final String COUNTRY_CODE = "IL";

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
        unbinder = ButterKnife.bind(this, view);
        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Add event", false,true,false);
        placesAutocomplete(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.startWork();
    }

    @Override
    public void onResume() {
        super.onResume();
        holidaySpinner.setSelection(selectedHolidayPos);
    }

    @OnClick(R.id.textDate)
    public void setDate() {
        presenter.showDatePicker(this);
//        new DatePickerDialog(getActivity(), dateCallback,
//                dateAndTime.get(Calendar.YEAR),
//                dateAndTime.get(Calendar.MONTH),
//                dateAndTime.get(Calendar.DAY_OF_MONTH))
//                .show();
    }

    public void setTime() {
        new TimePickerDialog(getActivity(), timeCallback,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE),
                true)
                .show();
    }

    public void setDuration() {
        pickerFrame.setVisibility(View.VISIBLE);
        durationPicker.setMaxValue(24);
        durationPicker.setMinValue(0);
        durationPicker.setValue(duration);
    }

    @OnClick(R.id.confirm_duration)
    public void confirmDuration() {
        pickerFrame.setVisibility(View.GONE);
        duration = durationPicker.getValue();
        textDate.setText(textDate.getText().toString() + ", duration: " + duration + " hours");
    }

    public void placesAutocomplete(View view){
        FragmentManager fragmentManager = getChildFragmentManager();
        SupportPlaceAutocompleteFragment fragment = presenter.showAutocomplete();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.autocomplete_layout, fragment, "AUTOCOMPLETE_FRAGMENT").commit();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.createBtn)
    public void makeEvent() {
        presenter.addNewEvent(createEvent());
    }

    public EventDto createEvent() {
        EventDto event = new EventDto();
        event.setTitle(inputTitle.getText().toString());
        Log.d(TAG, "createEvent: "+holidaySpinner.getSelectedItem().toString());
        event.setHoliday(holidaySpinner.getSelectedItem().toString());
        event.setDescription(inputDescription.getText().toString());
        SimpleDateFormat formDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateTime = dateAndTime.getTime();
        String date = formDate.format(dateTime);
        event.setDate(date);
        SimpleDateFormat formTime = new SimpleDateFormat("HH:mm:ss");
        String time = formTime.format(dateTime);
        event.setTime(time);
        event.setDuration(duration * 60);
        return event;
    }

//    //обработчик выбора даты
//    DatePickerDialog.OnDateSetListener dateCallback = (view, year, month, dayOfMonth) -> {
//        dateAndTime.set(Calendar.YEAR, year);
//        dateAndTime.set(Calendar.MONTH, month);
//        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//        setTime();
//    };

    //обработчик выбора времени
    TimePickerDialog.OnTimeSetListener timeCallback = (view, hourOfDay, minute) -> {
        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);
        textDate.setText(DateUtils.formatDateTime(getActivity(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME));
        setDuration();
    };

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        presenter.hideDatePicker();
        dateAndTime = Calendar.getInstance();
        dateAndTime.set(Calendar.YEAR, date.getYear());
        dateAndTime.set(Calendar.MONTH, date.getMonth());
        dateAndTime.set(Calendar.DAY_OF_MONTH, date.getDay());
        setTime();
    }

    @Override
    public void setHolidaySpinner(List<String> holiday) {
        spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,holiday);
        spinnerAdapter.insert(CHOOSE_HOLIDAY, 0);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holidaySpinner.setAdapter(spinnerAdapter);
        holidaySpinner.setSelection(selectedHolidayPos);
        holidaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHolidayPos=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

package com.kor.foodmanager.ui.eventList;



import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventsInProgressRequestDto;
import com.kor.foodmanager.data.model.FiltersDto;
import com.kor.foodmanager.data.model.SpinnerPositionDto;
import com.kor.foodmanager.data.model.StaticfieldsDto;
import com.kor.foodmanager.ui.IToolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FiltersFragment extends MvpAppCompatFragment implements IFilters, AdapterView.OnItemSelectedListener {
    @InjectPresenter
    FiltersPresenter presenter;
    private Unbinder unbinder;
    private Calendar calendar;
    private EventsInProgressRequestDto filters;
    private IToolbar iToolbar;


    private static final String CONFESSION = "--select confession--";
    private static final String HOLIDAY = "--select holiday--";
    private static final String FOOD = "--select food--";
    private static final String EVENTDATE = "--select date--";

    @BindView(R.id.event_date)
    TextView eventDateTxt;
    @BindView(R.id.confession_spinner)
    Spinner confessionSpinner;
    @BindView(R.id.holiday_spinner)
    Spinner holidaySpinner;
    @BindView(R.id.food_pref_spinner)
    Spinner foodPrefSpinner;
    @BindView(R.id.city_spinner)
    Spinner citySpinner;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindViews({R.id.confession_spinner, R.id.holiday_spinner, R.id.food_pref_spinner, R.id.city_spinner})
    List<Spinner> spinners;
    @BindViews({R.id.apply_btn, R.id.reset_btn})
    List<Button> buttons;


    public FiltersFragment() {
        filters = null;
    }

    public static FiltersFragment getNewInstance(EventsInProgressRequestDto filters) {
        Log.d("MY_TAG", "Filters getNewInstance: ");
        FiltersFragment fragment = new FiltersFragment();
        fragment.filters = filters;
        Log.d("MY_TAG", "Filters getNewInstance: " + filters.toString());
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            filters=(EventsInProgressRequestDto) savedInstanceState.getSerializable("filters");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("filters",filters);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);
        unbinder = ButterKnife.bind(this, view);
        iToolbar=(IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Filters",false,true,false);
        if (filters != null) {
            presenter.setStaticFields(filters);
        } else {
            presenter.setStaticFields();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.apply_btn)
    void apply() {
        presenter.apply();
    }

    @OnClick(R.id.reset_btn)
    void reset() {
        presenter.reset();
        confessionSpinner.setSelection(0);
        holidaySpinner.setSelection(0);
        foodPrefSpinner.setSelection(0);
        citySpinner.setSelection(0);
        eventDateTxt.setText(EVENTDATE);
    }


    @OnClick(R.id.event_date)
    public void setDateFrom() {
        calendar = null;
        calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateFromCallback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        //dialog.setTitle(R.string.date_from_title);
        TextView title = new TextView(getActivity());
        title.setText(R.string.date_from_title);
        title.setTextSize(24);
        title.setBackgroundResource(R.color.colorPrimaryDark);
        dialog.setCustomTitle(title);
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
        eventDateTxt.setText(date);
        setDateTo();
    };

    private void setDateTo() {
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), dateToCallback,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        dialog.setTitle("Select date to");
//        TextView title = new TextView(getActivity());
//        title.setText("Select date to");
//        title.setTextSize(24);
//        title.setBackgroundResource(R.color.colorPrimaryDark);
//        dialog.setCustomTitle(title);
        dialog.show();
    }


    DatePickerDialog.OnDateSetListener dateToCallback = (view, year, month, dayOfMonth) -> {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat formDate = new SimpleDateFormat("yyyy-MM-dd");
        String date = formDate.format(calendar.getTime());
        presenter.setDateTo(date);
        eventDateTxt.setText(eventDateTxt.getText() + " - " + date);
    };


    @Override
    public void setStaticFields(StaticfieldsDto staticFields) {
        ArrayAdapter<String> confessionAdapter = new ArrayAdapter<>(getActivity(), R.layout.my_spinner_dropdown_item, staticFields.getConfession());
        confessionAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        confessionAdapter.insert(CONFESSION, 0);
        confessionSpinner.setAdapter(confessionAdapter);
        confessionSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> holidayAdapter = new ArrayAdapter<>(getActivity(), R.layout.my_spinner_dropdown_item, staticFields.getHoliday());
        holidayAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        holidayAdapter.insert(HOLIDAY, 0);
        holidaySpinner.setAdapter(holidayAdapter);
        holidaySpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> foodPrefAdapter = new ArrayAdapter<>(getActivity(), R.layout.my_spinner_dropdown_item, staticFields.getFoodPreferences());
        foodPrefAdapter.insert(FOOD, 0);
        foodPrefAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        foodPrefSpinner.setAdapter(foodPrefAdapter);
        foodPrefSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.cities, R.layout.my_spinner_dropdown_item);
        cityAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
        citySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void setSpinners(SpinnerPositionDto positions) {
        confessionSpinner.setSelection(positions.getConfession());
        holidaySpinner.setSelection(positions.getHoliday());
        foodPrefSpinner.setSelection(positions.getFood());
        citySpinner.setSelection(positions.getCity());
    }

    @Override
    public void setDates(FiltersDto filters) {
        Log.d("MY_TAG", "setDates: working");
        if (filters.getDateTo() != null & filters.getDateFrom() != null) {
            eventDateTxt.setText(filters.getDateFrom() + " - " + filters.getDateTo());
        }
    }

    @Override
    public void showProgressFrame() {
        progressBar.setVisibility(View.VISIBLE);
        ButterKnife.apply(spinners, HIDE);
        ButterKnife.apply(buttons, HIDE);
        eventDateTxt.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressFrame() {
        progressBar.setVisibility(View.GONE);
        ButterKnife.apply(spinners, SHOW);
        ButterKnife.apply(buttons, SHOW);
        eventDateTxt.setVisibility(View.VISIBLE);
    }

    static final ButterKnife.Action<View> HIDE = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setVisibility(View.GONE);
        }
    };

    static final ButterKnife.Action<View> SHOW = new ButterKnife.Action<View>() {
        @Override
        public void apply(View view, int index) {
            view.setVisibility(View.VISIBLE);
        }
    };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.confession_spinner) {
            if (confessionSpinner.getSelectedItemPosition() != 0) {
                presenter.setConfession(confessionSpinner.getSelectedItem().toString(),
                        position);
            }
        }
        if (parent.getId() == R.id.holiday_spinner) {
            if (holidaySpinner.getSelectedItemPosition() != 0) {
                presenter.setHoliday(holidaySpinner.getSelectedItem().toString(), holidaySpinner.getSelectedItemPosition());
            }
        }
        if (parent.getId() == R.id.food_pref_spinner) {
            if (foodPrefSpinner.getSelectedItemPosition() != 0) {
                presenter.setFood(foodPrefSpinner.getSelectedItem().toString(), foodPrefSpinner.getSelectedItemPosition());
            }
        }
        if (parent.getId() == R.id.city_spinner) {
            if (citySpinner.getSelectedItemPosition() != 0) {
                presenter.setCity(citySpinner.getSelectedItem().toString(), citySpinner.getSelectedItemPosition());
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

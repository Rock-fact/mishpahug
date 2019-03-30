package com.kor.foodmanager.ui.calendar.calendar_dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.MvpDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.kor.foodmanager.ui.MainActivity.TAG;

public class CalendarDialog extends MvpAppCompatDialogFragment implements ICalendarDialog {
    @InjectPresenter CalendarDialogPresenter presenter;
    @BindView(R.id.TodaysHolidays) TextView todaysHolidays;
    @BindView(R.id.MyEvents) TextView myEvents;
    @BindView(R.id.mySubs) TextView mySubs;
    @BindView(R.id.DialogTitle) TextView dialogTitle;
    @BindView(R.id.showEventsBtn) Button showEventsBtn;
    @BindView(R.id.addNewEventBtn) Button addNewEventBtn;
    private Unbinder unbinder;

    public static CalendarDialog newInstance(Bundle args) {
        CalendarDialog fragment = new CalendarDialog();
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_dialog, null, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        if(bundle.getString("MESSAGE")!=null){
            todaysHolidays.setVisibility(View.VISIBLE);
            todaysHolidays.setText(bundle.getString("MESSAGE"));
        }
        if(bundle.getString("MY_EVENTS")!=null){
            myEvents.setVisibility(View.VISIBLE);
            myEvents.setText(bundle.getString("MY_EVENTS"));
        }
        if(bundle.getString("MY_SUBS")!=null){
            myEvents.setVisibility(View.VISIBLE);
            myEvents.setText(bundle.getString("MY_SUBS"));
        }
        if(getArguments().getString("TITLE")!=null){
            dialogTitle.setText(bundle.getString("TITLE"));
        }
        Log.d(TAG, "DIALOG" + bundle.getString("MESSAGE"));

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.addNewEventBtn)
    public void addNewEvent(){
        Calendar date = (Calendar) getArguments().getSerializable("DATE");
        presenter.addNewEvent(date);
    }

    @OnClick(R.id.showEventsBtn)
    public void showEvents(){
        presenter.showEvents();
    }
    @OnClick(R.id.MyEvents)
    public void showMyEvents(){
        presenter.showMyEvents();
    }
    @OnClick(R.id.mySubs)
    public void showMySubs(){
        presenter.showMySubs();
    }
}

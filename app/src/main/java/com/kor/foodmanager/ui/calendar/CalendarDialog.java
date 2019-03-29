package com.kor.foodmanager.ui.calendar;

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
import com.kor.foodmanager.R;

import static com.kor.foodmanager.ui.MainActivity.TAG;

public class CalendarDialog extends DialogFragment implements View.OnClickListener {
    private TextView todaysHolidays, myEvents, mySubs;
    private Button showEventsBtn, addNewEventBtn;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        todaysHolidays = view.findViewById(R.id.TodaysHolidays);
        myEvents = view.findViewById(R.id.MyEvents);
        mySubs = view.findViewById(R.id.mySubs);
        addNewEventBtn = view.findViewById(R.id.addNewEventBtn);
        showEventsBtn = view.findViewById(R.id.showEventsBtn);
        Bundle bundle = getArguments();
        addNewEventBtn.setOnClickListener(this);
        showEventsBtn.setOnClickListener(this);
        myEvents.setOnClickListener(this);
        mySubs.setOnClickListener(this);

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
            getDialog().setTitle(getArguments().getString("TITLE"));
        }
        Log.d(TAG, "DIALOG" + bundle.getString("MESSAGE"));

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public void onClick(View v) {

    }
}

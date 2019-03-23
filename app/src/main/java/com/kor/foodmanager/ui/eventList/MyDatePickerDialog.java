package com.kor.foodmanager.ui.eventList;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class MyDatePickerDialog extends DatePickerDialog {

    private CharSequence title;

    public MyDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }

    public MyDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener, int year, int month, int dayOfMonth, CharSequence title) {
        super(context, listener, year, month, dayOfMonth);
        this.title = title;
        setTitle(title);
    }

    public MyDatePickerDialog( Context context, int themeResId, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
    }

    public void setMyTitle (String t){
        title = t;
        setTitle(title);
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
        super.onDateChanged(view, year, month, dayOfMonth);
        setTitle(title);
    }
}

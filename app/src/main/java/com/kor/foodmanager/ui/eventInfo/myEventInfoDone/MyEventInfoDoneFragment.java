package com.kor.foodmanager.ui.eventInfo.myEventInfoDone;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.ui.IToolbar;
import com.kor.foodmanager.ui.myEventList.TitleRow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MyEventInfoDoneFragment extends MvpAppCompatFragment {

    @BindView(R.id.date_txt)
    TextView date;
    @BindView(R.id.event_description)
    TextView eventDescription;

    private IToolbar iToolbar;
    private Unbinder unbinder;
    private TitleRow event;


    public MyEventInfoDoneFragment() {
    }

    public static MyEventInfoDoneFragment getNewInstance(TitleRow event) {
        MyEventInfoDoneFragment myEventInfoDoneFragment = new MyEventInfoDoneFragment();
        myEventInfoDoneFragment.event = event;
        return myEventInfoDoneFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_event_info_done, container, false);
        unbinder = ButterKnife.bind(this, view);
        date.setText(event.getDate());
        eventDescription.setText(event.getDescription());

        iToolbar = (IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable(event.getTitle(), true);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
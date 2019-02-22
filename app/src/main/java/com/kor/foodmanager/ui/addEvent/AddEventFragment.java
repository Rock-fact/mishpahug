package com.kor.foodmanager.ui.addEvent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddEventFragment extends MvpAppCompatFragment implements IAddEvent{
    @InjectPresenter AddEventPresenter presenter;
    private Unbinder unbinder;
    @BindView(R.id.inputTitle) TextView inputTitle;
    @BindView(R.id.inputAddress) TextView inputAddress;
    @BindView(R.id.inputDate) TextView inputDate;
    @BindView(R.id.inputDescription) TextView inputDescription;
    @BindView(R.id.createBtn) Button createBtn;

    public AddEventFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_event, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.createBtn)
    public void createEvent(){
        // TODO: 21.02.2019  EventDto event = new EventDto ...
    }
}

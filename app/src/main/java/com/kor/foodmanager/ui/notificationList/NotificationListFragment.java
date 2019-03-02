package com.kor.foodmanager.ui.notificationList;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.NotificationListDto;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.kor.foodmanager.ui.MainActivity.TAG;

public class NotificationListFragment extends MvpAppCompatFragment implements INotificationList {
    @InjectPresenter NotificationListPresenter presenter;
    @BindView(R.id.notificationList) RecyclerView notificationList;
    @BindView(R.id.progressFrame) FrameLayout progressFrame;
    private Unbinder unbinder;

    public NotificationListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.startWork();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);
        unbinder = ButterKnife.bind(this,view);
        progressFrame.setOnClickListener(null);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void showNotificationList(NotificationListAdapter adapter) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        notificationList.setLayoutManager(layoutManager);
        notificationList.setAdapter(adapter);
        Log.d(TAG, "showNotificationList: size: "+adapter.getItemCount());

    }

    @Override
    public void showProgressFrame() {
        progressFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressFrame() {
        progressFrame.setVisibility(View.GONE);
    }
}

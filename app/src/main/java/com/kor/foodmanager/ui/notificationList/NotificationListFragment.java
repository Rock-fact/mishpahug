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
import com.kor.foodmanager.data.model.NotificationDto;
import com.kor.foodmanager.ui.IToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import static com.kor.foodmanager.ui.MainActivity.TAG;

public class NotificationListFragment extends MvpAppCompatFragment implements INotificationList, NotificationListAdapter.MyClickListener {
    @InjectPresenter NotificationListPresenter presenter;
    @BindView(R.id.notificationList) RecyclerView notificationList;
    @BindView(R.id.progressFrame) FrameLayout progressFrame;
    private Unbinder unbinder;
    private IToolbar iToolbar;

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
        iToolbar=(IToolbar) getActivity();
        iToolbar.setTitleToolbarEnable("Notifications",true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void showNotificationList(NotificationListAdapter adapter) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter.setListener(this);
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

    @Override
    public void onClick(NotificationDto notificationDto) {
        presenter.showInfo(notificationDto);
    }
}

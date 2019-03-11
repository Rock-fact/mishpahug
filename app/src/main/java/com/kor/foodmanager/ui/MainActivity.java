package com.kor.foodmanager.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.EventDto;
import com.kor.foodmanager.data.model.NotificationDto;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.aboutmyself.AboutMyselfFragment;
import com.kor.foodmanager.ui.addEvent.AddEventFragment;
import com.kor.foodmanager.ui.contactinfo.ContactInfoFragment;
import com.kor.foodmanager.ui.eventInfo.guestEventInfo.GuestEventInfoFragment;
import com.kor.foodmanager.ui.eventInfo.guestEventInfoDone.GuestEventInfoDoneFragment;
import com.kor.foodmanager.ui.eventInfo.guestEventInfoInprogress.GuestEventInfoInprogressFragment;
import com.kor.foodmanager.ui.eventInfo.guestEventInfoPending.GuestEventInfoPendingFragment;
import com.kor.foodmanager.ui.eventInfo.myEventInfoDone.MyEventInfoDoneFragment;
import com.kor.foodmanager.ui.eventInfo.myEventInfoInProgress.MyEventInfoInProgressFragment;
import com.kor.foodmanager.ui.eventInfo.myEventInfoPending.MyEventInfoPendingFragment;
import com.kor.foodmanager.ui.eventList.EventListFragment;
import com.kor.foodmanager.ui.login.LoginFragment;
import com.kor.foodmanager.ui.myEventList.MyEventListFragment;
import com.kor.foodmanager.ui.myEventList.TitleRow;
import com.kor.foodmanager.ui.notificationInfo.NotificationInfoFragment;
import com.kor.foodmanager.ui.notificationList.NotificationListFragment;
import com.kor.foodmanager.ui.participationList.ParticipationListFragment;
import com.kor.foodmanager.ui.personalinfo.PersonalProfileFragment;
import com.kor.foodmanager.ui.registration.RegistrationFragment;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class MainActivity extends MvpAppCompatActivity implements IMain,IToolbar, NavigationView.OnNavigationItemSelectedListener{
    public static final String LOGIN_SCREEN = "LOGIN_SCREEN";
    public static final String ADD_EVENT_SCREEN = "ADD_EVENT_SCREEN";
    public static final String EVENT_LIST_SCREEN = "EVENT_LIST_SCREEN";
    public static final String EVENT_INFO_SCREEN = "EVENT_INFO_SCREEN";
    public static final String NOTIFICATIONS_SCREEN = "NOTIFICATIONS_SCREEN";
    public static final String NOTIFICATION_INFO_SCREEN = "NOTIFICATION_INFO_SCREEN";
    public static final String SHOW_PROGRESS = "SHOW_PROGRESS";
    public static final String HIDE_PROGRESS = "HIDE_PROGRESS";
    public static final String PERSONALPROFILE_FRAGMENT_NEW = "PERSONALPROFILE_FRAGMENT_NEW";
    public static final String ABOUTMYSELF_FRAGMENT_NEW = "ABOUTMYSELF_FRAGMENT_NEW";
    public static final String CONTACTINFO_FRAGMENT_NEW = "CONTACTINFO_FRAGMENT_NEW";
    public static final String REGISTRATION_FRAGMENT = "REGISTRATION_FRAGMENT_NEW";
    public static final String PARTICIPATION_LIST_SCREEN = "PARTICIPATION_LIST_SCREEN";
    public static final String GUEST_EVENT_INFO_PENDING_SCREEN = "GUEST_EVENT_INFO_PENDING_SCREEN";

    public static final String MY_EVENT_LIST_SCREEN = "MY_EVENT_LIST_SCREEN";
    public static final String MY_EVENT_INFO_INPROGRESS_SCREEN = "MY_EVENT_INFO_INPROGRESS_SCREEN";
    public static final String MY_EVENT_INFO_PENDING_SCREEN = "MY_EVENT_INFO_PENDING_SCREEN";
    public static final String MY_EVENT_INFO_DONE_SCREEN = "MY_EVENT_INFO_DONE_SCREEN";

    public static final String GUEST_EVENT_INFO_INPROGRESS_SCREEN = "GUEST_EVENT_INFO_INPROGRESS_SCREEN";
    public static final String GUEST_EVENT_INFO_DONE_SCREEN = "GUEST_EVENT_INFO_DONE_SCREEN";

    public static final String TAG = "MY_TAG";
    @InjectPresenter MainActivityPresenter presenter;
    @BindView(R.id.progressFrame) FrameLayout progressFrame;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        unbinder = ButterKnife.bind(this);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        progressFrame.setOnClickListener(null);
        presenter.startWork();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
    public void showError(String error) {
        new AlertDialog.Builder(this)
                .setMessage(error)
                .setTitle("Error!")
                .setPositiveButton("Ok", null)
                .setCancelable(false)
                .create()
                .show();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.eventList) {
            presenter.eventList();
        } else if (id == R.id.myProfile) {
            presenter.myProfile();
        } else if (id == R.id.notice) {
            presenter.notice();
        } else if (id == R.id.calendar) {
            presenter.calendar();
        } else if (id == R.id.participation) {
            presenter.participation();
        } else if (id == R.id.myEventList) {
            presenter.myEventList();
        } else if (id == R.id.logOut) {
            presenter.logOut();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(),R.id.root) {
        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch (screenKey){
                case LOGIN_SCREEN:
                    return new LoginFragment();
                case ADD_EVENT_SCREEN:
                    return new AddEventFragment();
                case EVENT_LIST_SCREEN:
                    return new EventListFragment();
                case NOTIFICATIONS_SCREEN:
                    return new NotificationListFragment();
                case NOTIFICATION_INFO_SCREEN:
                    return NotificationInfoFragment.newInstance((NotificationDto)data);
                case REGISTRATION_FRAGMENT:
                    return new RegistrationFragment();
                case ABOUTMYSELF_FRAGMENT_NEW:
                    return AboutMyselfFragment.getNewInstance((UserDto)data, true);
                case PERSONALPROFILE_FRAGMENT_NEW:
                    return PersonalProfileFragment.getNewInstance((UserDto) data, true);
                case CONTACTINFO_FRAGMENT_NEW:
                    return ContactInfoFragment.getNewInstance((UserDto) data, true);
                case EVENT_INFO_SCREEN:
                    return GuestEventInfoFragment.getNewInstance((EventDto) data);
                case PARTICIPATION_LIST_SCREEN:
                    return new ParticipationListFragment();
                case GUEST_EVENT_INFO_PENDING_SCREEN:
                    return GuestEventInfoPendingFragment.getNewInstance((EventDto) data);

                case MY_EVENT_LIST_SCREEN:
                    return new MyEventListFragment();
                case MY_EVENT_INFO_INPROGRESS_SCREEN:
                    return MyEventInfoInProgressFragment.getNewInstance((TitleRow) data);
                case MY_EVENT_INFO_PENDING_SCREEN:
                    return MyEventInfoPendingFragment.getNewInstance((TitleRow) data);
                case MY_EVENT_INFO_DONE_SCREEN:
                    return MyEventInfoDoneFragment.getNewInstance((TitleRow) data);

                case GUEST_EVENT_INFO_INPROGRESS_SCREEN:
                    return GuestEventInfoInprogressFragment.getNewInstance((EventDto) data);
                case GUEST_EVENT_INFO_DONE_SCREEN:
                    return GuestEventInfoDoneFragment.getNewInstance((EventDto) data);

                default:
                    throw new RuntimeException("Unknown screen key!");
            }
        }

        @Override
        protected void showSystemMessage(String message) {
            switch (message){
                case SHOW_PROGRESS:
                    showProgressFrame();
                    break;
                case HIDE_PROGRESS:
                    hideProgressFrame();
                    break;
                    default:
                        showError(message);
            }
        }

        @Override
        protected void exit() {
            getSupportFragmentManager().popBackStack();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.removeNavigator();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void setTitleToolbarEnable(String title, Boolean isEnable) {
        toolbar.setTitle(title);
        toggle.setDrawerIndicatorEnabled(isEnable);
    }
}
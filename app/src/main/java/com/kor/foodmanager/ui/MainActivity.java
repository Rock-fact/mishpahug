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
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kor.foodmanager.R;
import com.kor.foodmanager.data.model.UserDto;
import com.kor.foodmanager.ui.aboutmyself.AboutMyselfFragment;
import com.kor.foodmanager.ui.addEvent.AddEventFragment;
import com.kor.foodmanager.ui.contactinfo.ContactInfoFragment;
import com.kor.foodmanager.ui.eventList.EventListFragment;
import com.kor.foodmanager.ui.home.HomeFragment;
import com.kor.foodmanager.ui.login.LoginFragment;
import com.kor.foodmanager.ui.personalinfo.PersonalProfileFragment;
import com.kor.foodmanager.ui.registration.RegistrationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class MainActivity extends MvpAppCompatActivity implements IMain, NavigationView.OnNavigationItemSelectedListener{
    public static final String LOGIN_SCREEN = "LOGIN_SCREEN";
    public static final String HOME_SCREEN = "HOME_SCREEN";
    public static final String ADD_EVENT_SCREEN = "ADD_EVENT_SCREEN";
    public static final String EVENT_LIST_SCREEN = "EVENT_LIST_SCREEN";
    public static final String SHOW_PROGRESS = "SHOW_PROGRESS";
    public static final String HIDE_PROGRESS = "HIDE_PROGRESS";
    public static final String PERSONALPROFILE_FRAGMENT_NEW = "PERSONALPROFILE_FRAGMENT_NEW";
    public static final String ABOUTMYSELF_FRAGMENT_NEW = "ABOUTMYSELF_FRAGMENT_NEW";
    public static final String CONTACTINFO_FRAGMENT_NEW = "CONTACTINFO_FRAGMENT_NEW";
    public static final String REGISTRATION_FRAGMENT = "REGISTRATION_FRAGMENT_NEW";
    public static final String TAG = "MY_TAG";
    @InjectPresenter MainActivityPresenter presenter;
    @BindView(R.id.progressFrame) FrameLayout progressFrame;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        unbinder = ButterKnife.bind(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
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
                case HOME_SCREEN:
                    return new HomeFragment();
                case ADD_EVENT_SCREEN:
                    return new AddEventFragment();
                case EVENT_LIST_SCREEN:
                    return new EventListFragment();
                case REGISTRATION_FRAGMENT:
                    return new RegistrationFragment();
                case ABOUTMYSELF_FRAGMENT_NEW:
                    return AboutMyselfFragment.getNewInstance((UserDto)data, true);
                case PERSONALPROFILE_FRAGMENT_NEW:
                    return PersonalProfileFragment.getNewInstance((UserDto) data, true);
                case CONTACTINFO_FRAGMENT_NEW:
                    return ContactInfoFragment.getNewInstance((UserDto) data, true);
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
}
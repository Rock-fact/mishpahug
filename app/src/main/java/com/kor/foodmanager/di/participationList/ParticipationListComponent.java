package com.kor.foodmanager.di.participationList;


import com.kor.foodmanager.ui.participationList.ParticipationListPresenter;

import dagger.Subcomponent;

@Subcomponent (modules = {ParticipationListModule.class})
@ParticipationListScope
public interface ParticipationListComponent {
    void inject(ParticipationListPresenter presenter);
}

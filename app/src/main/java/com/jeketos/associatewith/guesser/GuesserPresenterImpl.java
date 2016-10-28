package com.jeketos.associatewith.guesser;

import com.jeketos.associatewith.di.Injector;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class GuesserPresenterImpl implements GuesserMVP.GuesserPresenter {

    private GuesserMVP.GuesserView view;
    private GuesserMVP.GuesserModel model;

    public GuesserPresenterImpl(GuesserMVP.GuesserView view) {
        this.view = view;
        model = Injector.provideGuesserModel(this);
        view.init();
    }
}

package com.jeketos.associatewith.guesser;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class GuesserModelImpl implements GuesserMVP.GuesserModel {

    private GuesserMVP.GuesserPresenter presenter;

    public GuesserModelImpl(GuesserMVP.GuesserPresenter presenter) {

        this.presenter = presenter;
    }
}

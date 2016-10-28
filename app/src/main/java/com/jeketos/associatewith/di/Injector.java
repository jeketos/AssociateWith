package com.jeketos.associatewith.di;

import com.jeketos.associatewith.drawer.DrawerMVP;
import com.jeketos.associatewith.drawer.DrawerModelImpl;
import com.jeketos.associatewith.drawer.DrawerPresenterImpl;
import com.jeketos.associatewith.guesser.GuesserMVP;
import com.jeketos.associatewith.guesser.GuesserModelImpl;
import com.jeketos.associatewith.guesser.GuesserPresenterImpl;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class Injector {

    public static DrawerMVP.DrawerPresenter provideDrawPresenter(DrawerMVP.DrawerView view){
        return new DrawerPresenterImpl(view);
    }

    public static DrawerMVP.DrawerModel provideDrawModel(DrawerMVP.DrawerPresenter presenter){
        return  new DrawerModelImpl(presenter);
    }

    public static GuesserMVP.GuesserPresenter provideGuesserPresenter(GuesserMVP.GuesserView view) {
        return new GuesserPresenterImpl(view);
    }

    public static GuesserMVP.GuesserModel provideGuesserModel(GuesserMVP.GuesserPresenter presenter) {
        return new GuesserModelImpl(presenter);
    }
}

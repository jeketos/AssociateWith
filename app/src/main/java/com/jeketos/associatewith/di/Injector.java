package com.jeketos.associatewith.di;

import com.jeketos.associatewith.drawer.DrawMVP;
import com.jeketos.associatewith.drawer.DrawModelImpl;
import com.jeketos.associatewith.drawer.DrawPresenterImpl;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class Injector {

    public static DrawMVP.DrawPresenter provideDrawPresenter(DrawMVP.DrawView view){
        return new DrawPresenterImpl(view);
    }

    public static DrawMVP.DrawModel provideDrawModel(DrawMVP.DrawPresenter presenter){
        return  new DrawModelImpl(presenter);
    }
}

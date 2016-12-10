package com.jeketos.associatewith.di

import com.jeketos.associatewith.drawer.DrawerMVP
import com.jeketos.associatewith.drawer.DrawerModelImpl
import com.jeketos.associatewith.drawer.DrawerPresenterImpl
import com.jeketos.associatewith.guesser.GuesserMVP
import com.jeketos.associatewith.guesser.GuesserModelImpl
import com.jeketos.associatewith.guesser.GuesserPresenterImpl

/**
 * Created by jeketos on 05.12.2016.
 *
 */

    fun provideDrawPresenter(view : DrawerMVP.DrawerView): DrawerMVP.DrawerPresenter{
        return DrawerPresenterImpl(view)
    }

    fun provideDrawModel(presenter: DrawerMVP.DrawerPresenter): DrawerMVP.DrawerModel {
        return DrawerModelImpl(presenter)
    }

    fun provideGuesserPresenter(view: GuesserMVP.GuesserView): GuesserMVP.GuesserPresenter {
        return GuesserPresenterImpl(view)
    }

    fun provideGuesserModel(presenter: GuesserMVP.GuesserPresenter): GuesserMVP.GuesserModel {
        return GuesserModelImpl(presenter)
    }

package com.jeketos.associatewith.drawer

import dagger.Module
import dagger.Provides

/**
 * Created by jeketos on 13.12.2016.
 *
 */
@Module
class DrawerModule {

    @Provides
    fun provideDrawerPresenter(drawerPresenterImpl: DrawerPresenterImpl) : DrawerMVP.DrawerPresenter{
        return drawerPresenterImpl
    }

    @Provides
    fun provideDrawerModel(drawerModelImpl: DrawerModelImpl) : DrawerMVP.DrawerModel{
        return drawerModelImpl
    }

}
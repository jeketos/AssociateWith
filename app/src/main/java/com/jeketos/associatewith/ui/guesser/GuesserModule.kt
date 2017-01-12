package com.jeketos.associatewith.ui.guesser

import dagger.Module
import dagger.Provides

/**
 * Created by jeketos on 13.12.2016.
 *
 */
@Module
class GuesserModule {

    @Provides
    fun provideGuesserPresenter(guesserPresenterImpl: GuesserPresenterImpl) : GuesserMVP.GuesserPresenter{
        return guesserPresenterImpl
    }

    @Provides
    fun provideGuesserModel(guesserModelImpl: GuesserModelImpl) : GuesserMVP.GuesserModel{
        return guesserModelImpl
    }

}
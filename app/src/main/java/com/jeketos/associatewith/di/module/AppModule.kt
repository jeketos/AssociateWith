package com.jeketos.associatewith.di.module

import android.app.Application
import com.jeketos.associatewith.ui.guesser.GuesserMVP
import com.jeketos.associatewith.ui.guesser.GuesserModelImpl
import com.jeketos.associatewith.ui.guesser.GuesserPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Created by jeketos on 12.12.2016.
 *
 */
@Module
class AppModule(val app: Application) {

    @Provides fun provideApplication(): Application {
        return app
    }

}
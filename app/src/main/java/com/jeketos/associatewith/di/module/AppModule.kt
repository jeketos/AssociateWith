package com.jeketos.associatewith.di.module

import android.app.Application
import com.jeketos.associatewith.storage.Storer
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

    @Provides fun provideStorer() : Storer{
        return Storer(app)
    }

}
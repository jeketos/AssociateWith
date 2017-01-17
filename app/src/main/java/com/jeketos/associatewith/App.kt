package com.jeketos.associatewith

import android.app.Application
import com.jeketos.associatewith.di.component.AppComponent
import com.jeketos.associatewith.di.component.DaggerAppComponent
import com.jeketos.associatewith.di.module.AppModule

/**
 * Created by jeketos on 12.12.2016.
 *
 */

class App : Application(){

        //platformStatic allow access it from java code
       lateinit var component: AppComponent


    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))

                .build()
        component.inject(this)
    }

}
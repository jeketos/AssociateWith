package com.jeketos.associatewith.di.component

import com.jeketos.associatewith.App
import com.jeketos.associatewith.di.module.ActivityModule
import com.jeketos.associatewith.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by jeketos on 12.12.2016.
 *
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {


    fun getActivityComponent(activityModule: ActivityModule) : ActivityComponent

    fun inject(application: App)

}
package com.jeketos.associatewith.di.component

import com.jeketos.associatewith.App
import com.jeketos.associatewith.di.module.ActivityModule
import com.jeketos.associatewith.di.module.AppModule
import com.jeketos.associatewith.di.scope.AppScope
import dagger.Component

/**
 * Created by jeketos on 12.12.2016.
 *
 */
@AppScope
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {


    fun getActivityComponent(activityModule: ActivityModule) : ActivityComponent

    fun inject(application: App)

}
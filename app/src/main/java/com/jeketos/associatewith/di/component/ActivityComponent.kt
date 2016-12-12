package com.jeketos.associatewith.di.component

import com.jeketos.associatewith.di.module.ActivityModule
import com.jeketos.associatewith.drawer.DrawerActivity
import com.jeketos.associatewith.guesser.GuesserActivity
import dagger.Subcomponent

/**
 * Created by jeketos on 12.12.2016.
 *
 */
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject (guesserActivity: GuesserActivity)
    fun inject (drawerActivity: DrawerActivity)


}
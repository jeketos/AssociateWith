package com.jeketos.associatewith.di.component

import com.jeketos.associatewith.di.module.ActivityModule
import com.jeketos.associatewith.di.scope.ActivityScope
import com.jeketos.associatewith.drawer.DrawerActivity
import com.jeketos.associatewith.drawer.DrawerModule
import com.jeketos.associatewith.guesser.GuesserActivity
import com.jeketos.associatewith.guesser.GuesserModule
import dagger.Subcomponent

/**
 * Created by jeketos on 12.12.2016.
 *
 */
@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class, DrawerModule::class,GuesserModule::class))
interface ActivityComponent {

    fun inject(drawerActivity: DrawerActivity)
    fun  inject(drawerActivity: GuesserActivity)
//    fun injectDrawer (drawerActivity: DrawerActivity)


}
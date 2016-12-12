package com.jeketos.associatewith.di.module

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference

/**
 * Created by jeketos on 12.12.2016.
 *
 */
@Module
class ActivityModule(activity: AppCompatActivity) {

    private var activityRef: WeakReference<AppCompatActivity>? = null

    init {
        setActivity(activity)
    }

    // Init in the new Activity for reusing the object graph
    fun setActivity(activity: AppCompatActivity) {
        activityRef = WeakReference(activity)
    }

    @Provides
    internal fun provideActivity(): () -> AppCompatActivity {
        // Returns current acting activity
        return { activityRef!!.get() }
    }
}
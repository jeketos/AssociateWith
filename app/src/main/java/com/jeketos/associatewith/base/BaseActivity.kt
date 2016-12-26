package com.jeketos.associatewith.base

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jeketos.associatewith.App
import com.jeketos.associatewith.di.component.ActivityComponent
import com.jeketos.associatewith.di.module.ActivityModule
import com.jeketos.associatewith.util.DialogUtils

/**
 * Created by jeketos on 12.12.2016.
 *
 */
abstract class BaseActivity<V> : AppCompatActivity(){

    lateinit var component: ActivityComponent
    lateinit var activityModule: ActivityModule

    private var currentProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // Create the object graph(component)
        createOrRestoreComponent()
        // Inject before other initialization
        //component.inject(this);
        super.onCreate(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
    }

    override fun onStart() {
        getPresenter().attachView(this as V)
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        getPresenter().detachView()
    }

    private fun createOrRestoreComponent() {
        val state = lastCustomNonConfigurationInstance as Array<*>?
        if (state == null) {
            activityModule = ActivityModule(this)
            component = (this.applicationContext as App).component.getActivityComponent(activityModule)
        } else {
            // Restore the retained component
            activityModule = state[0] as ActivityModule
            activityModule.setActivity(this) // Init with current activity
            component = state[1] as ActivityComponent
        }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return arrayOf(activityModule, component)
    }

    abstract fun getPresenter() : BasePresenter<V>


    fun showProgressDialog() {
        currentProgressDialog = DialogUtils.createProgressDialog(this)
    }

    fun hideProgressDialog() {
        if (currentProgressDialog != null && currentProgressDialog!!.isShowing) {
            currentProgressDialog!!.dismiss()
        }
        currentProgressDialog = null
    }

}
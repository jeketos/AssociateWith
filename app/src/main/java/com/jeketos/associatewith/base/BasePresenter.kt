package com.jeketos.associatewith.base

/**
 * Created by jeketos on 13.12.2016.
 *
 */
interface BasePresenter<in V> {

    fun attachView(view: V)
    fun detachView()

}
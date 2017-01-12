package com.jeketos.associatewith.base

/**
 * Created by jeketos on 13.12.2016.
 *
 */
open class BaseMvpPresenter<V> : BasePresenter<V> {

    var view : V? = null

    override fun attachView(view: V) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}
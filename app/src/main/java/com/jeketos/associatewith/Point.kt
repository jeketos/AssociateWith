package com.jeketos.associatewith

/**
 * Created by jeketos on 05.12.2016.
 *
 */
class Point(var x : Float, var y : Float, var motionEvent : Int, var color : Int, var strokeWidth : Float) {

     override fun toString(): String {
        return "x = " + x +
                "\ny = " + y +
                "\nevent = " + motionEvent +
                "\ncolor = " + color +
                "\nstrokeWidth = " + strokeWidth
    }
}
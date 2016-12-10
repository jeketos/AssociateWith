package com.jeketos.associatewith.listener

import android.view.MotionEvent
import android.view.View
import com.jeketos.associatewith.Point

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

class TouchListener(val moveListener: MoveListener) : View.OnTouchListener {

    var oldX: Float = 0f
    var oldY: Float = 0f

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val point : Point
     when (event.action){
         MotionEvent.ACTION_DOWN ->{
             oldX = event.rawX - view.x
             oldY = event.rawY - view.y
             point = getPoint(oldX, oldY, MotionEvent.ACTION_DOWN)
             moveListener.actionDown(point)
         }
         MotionEvent.ACTION_MOVE ->{
             val x = event.rawX - view.x
             val y = event.rawY - view.y
             point = getPoint(x, y, MotionEvent.ACTION_MOVE)
             moveListener.actionMove(oldX,oldY,point)
             oldX = x
             oldY = y
         }
         MotionEvent.ACTION_UP ->{
             val x = event.rawX - view.x
             val y = event.rawY - view.y
             point = getPoint(x, y, MotionEvent.ACTION_UP)
             moveListener.actionUp(oldX,oldY,point)
         }
     }
        return true
    }


    fun getPoint(x: Float, y: Float, action: Int) : Point{
        return Point(x,y,action)
    }

    interface MoveListener{
       fun actionDown(point: Point)
       fun actionMove(previousX: Float, previousY: Float, point: Point)
       fun actionUp(previousX: Float, previousY: Float, point: Point)

    }
}

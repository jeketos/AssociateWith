package com.jeketos.associatewith.customViews

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by eugene.kotsogub on 11/23/16.
 *
 */

 class DrawingView : View {

   lateinit var  bitmap : Bitmap
   lateinit var  canvas : Canvas
   lateinit var path : Path
   lateinit var  bitmapPaint : Paint
   lateinit var  paint : Paint
   var bitmapWidth : Int = 0
   var bitmapHeight : Int = 0
   var mX = 0f
   var mY = 0f
   val TOUCH_TOLERANCE = 4f

    constructor(context: Context?) : super(context){
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

    }
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int) : super(context, attrs, defStyle, defStyleRes) {

    }


    fun init() {
        path = Path()
        bitmapPaint = Paint(Paint.DITHER_FLAG)
        paint = Paint()
        paint.setAntiAlias(true)
        paint.setDither(true)
        paint.setColor(Color.BLACK)
        paint.setStyle(Paint.Style.STROKE)
        paint.setStrokeJoin(Paint.Join.ROUND)
        paint.setStrokeCap(Paint.Cap.ROUND)
        paint.setStrokeWidth(12f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapWidth = w
        bitmapHeight = h
        initDrawBitmap()
    }

    private fun initDrawBitmap() {
        val newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        canvas = Canvas(newBitmap)
        canvas.drawColor(Color.WHITE)
        bitmap = newBitmap
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap,0f,0f,bitmapPaint)
        canvas.drawPath(path,paint)
    }

    fun actionDown(x: Float, y: Float){
        path.reset()
        path.moveTo(x,y)
        canvas.drawPoint(x,y,paint)
        mX = x
        mY = y
    }

    fun actionMove(x: Float,y: Float){
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            path.quadTo(mX, mY, (x+mX)/2, (y+mY)/2)
            mX = x
            mY = y
        }
    }

    fun actionUp(){
        path.lineTo(mX, mY)
        canvas.drawPath(path,paint)
        path.reset()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action){
            MotionEvent.ACTION_DOWN -> {
                actionDown(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                actionMove(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                actionUp()
                invalidate()
            }
        }
        return true
    }

    fun clear(){
        bitmap = Bitmap.createBitmap(width, height,Bitmap.Config.ARGB_8888)
        canvas =  Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        invalidate()
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable("bitmap", bitmap)
        bundle.putParcelable("superState", super.onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if(state != null && state is Bundle){
            bitmap = state.get("bitmap") as Bitmap
            super.onRestoreInstanceState(state.getParcelable("superState"))
        } else {
            super.onRestoreInstanceState(state)
        }
    }
}

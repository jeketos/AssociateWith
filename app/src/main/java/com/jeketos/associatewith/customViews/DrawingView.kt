package com.jeketos.associatewith.customViews

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.listener.TouchWatcher

/**
 * Created by eugene.kotsogub on 11/23/16.
 *
 */

 class DrawingView : View {

   var touchWatcher: TouchWatcher? = null
   var  bitmap : Bitmap? = null
   lateinit var  canvas : Canvas
   lateinit var path : Path
   lateinit var  bitmapPaint : Paint
   lateinit var  paint : Paint
   var bitmapWidth : Int = 0
   var bitmapHeight : Int = 0
   var mX = 0f
   var mY = 0f
   val TOUCH_TOLERANCE = 4f
   var density = 0

    constructor(context: Context?) : super(context){
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    @Suppress("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int, defStyleRes: Int) : super(context, attrs, defStyle, defStyleRes) {
        init()
    }


    fun init() {
        path = Path()
        bitmapPaint = Paint(Paint.DITHER_FLAG)
        paint = Paint()
        with(paint){
            isAntiAlias = true
            isDither = true
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 12f
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapWidth = w
        bitmapHeight = h
        density = context.resources.displayMetrics.densityDpi
        initDrawBitmap()
    }

    private fun initDrawBitmap() {
        if(bitmap == null) {
            val newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            canvas = Canvas(newBitmap)
            canvas.drawColor(Color.TRANSPARENT)
            bitmap = newBitmap
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap,0f,0f,bitmapPaint)
        canvas.drawPath(path,paint)
    }

    fun actionDown(x: Float, y: Float){
        path.reset()
        path.moveTo(x, y)
        canvas.drawPoint(x, y, paint)
        touchWatcher?.actionTouch(Point(x/bitmapWidth, y/bitmapHeight, MotionEvent.ACTION_DOWN, paint.color, paint.strokeWidth))
        mX = x
        mY = y
    }

    fun actionMove(x: Float,y: Float){
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            path.quadTo(mX, mY, (x+mX)/2, (y+mY)/2)
            touchWatcher?.actionTouch(com.jeketos.associatewith.Point((x+mX)/(2*bitmapWidth), (y+mY)/(2*bitmapHeight), MotionEvent.ACTION_MOVE, paint.color, paint.strokeWidth))
            mX = x
            mY = y
        }
    }

    fun actionUp(){
        touchWatcher?.actionTouch(com.jeketos.associatewith.Point(mX/bitmapWidth, mY/bitmapHeight, MotionEvent.ACTION_UP, paint.color, paint.strokeWidth))
        path.lineTo(mX, mY)
        canvas.drawPath(path, paint)
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
        if(width > 0 && height > 0) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            canvas = Canvas(bitmap)
            canvas.drawColor(Color.WHITE)
            invalidate()
        }
    }

    fun setMoveWatcher(touchWatcher: TouchWatcher) {
        this.touchWatcher = touchWatcher
    }


    fun setDrawColor(selectedColor: Int) {
        paint.color = selectedColor
    }

    fun setStrokeWidth(strokeWidth: Float) {
        paint.strokeWidth = strokeWidth
    }
}

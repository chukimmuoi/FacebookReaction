package com.developers.chukimmuoi.reaction.`object`

import android.content.res.Resources
import android.graphics.*

/**
 * @author  : Hanet Electronics
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : FacebookReaction
 * Created by chukimmuoi on 22/10/2017.
 */
class Emotions(private val resources: Resources, val image: Int,
               val sizeMax: Int, val sizeNormal: Int, val sizeMin: Int,
               private val margin: Int) {

    private var bitmap: Bitmap = BitmapFactory.decodeResource(resources, image)
    private var paint: Paint   = Paint(Paint.ANTI_ALIAS_FLAG)

    var left: Float   = 0.0F
    var top: Float    = 0.0F
    var right: Float  = 0.0F
    var bottom: Float = 0.0F

    private lateinit var rectF: RectF

    var distance: Float = 0.0F

    init {
        paint.isAntiAlias = true
    }

    fun setCoordinates(xStart: Float, d: Float, yCenter: Float, size: Int) {
        this.distance = d

        this.left   = xStart + d + margin
        this.top    = yCenter - size * 0.5F
        this.right  = left + size
        this.bottom = top + size

        rectF = RectF(left, top, right, bottom)
    }

    fun setCoordinates(left: Float, top: Float, right: Float, bottom: Float) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom

        rectF = RectF(left, top, right, bottom)
    }

    fun setCoordinates(xCenter: Float, yCenter: Float, size: Int) {
        this.left   = xCenter - size * 0.5F
        this.top    = yCenter - size * 0.5F
        this.right  = left + size
        this.bottom = top  + size

        rectF  = RectF(left, top, right, bottom)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, null, rectF, paint)
    }

    fun checkMoveAction(xCoordinates: Float, yCoordinates: Float): Boolean {
        var output = false
        if (xCoordinates > left && xCoordinates < right && yCoordinates > bottom) {
            output = true
        }

        return output
    }

    fun getCurrentSize(): Float {
        return Math.max(bottom - top, right - left)
    }

    fun setCurrentSize(size: Float, xStart: Float, d: Float) {
        distance = d

        left = xStart + d + margin
        top  = bottom - size
        right = left + size

        rectF  = RectF(left, top, right, bottom)
    }
}
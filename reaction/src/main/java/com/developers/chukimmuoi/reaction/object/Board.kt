package com.developers.chukimmuoi.reaction.`object`

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

/**
 * @author  : Hanet Electronics
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : FacebookReaction
 * Created by chukimmuoi on 22/10/2017.
 */
class Board(val color: Int, val heightNormal: Int, val heightMin: Int) {

    private var mBoardPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var left: Float   = 0.0F
    private var top: Float    = 0.0f
    private var right: Float  = 0.0f
    private var bottom: Float = 0.0f
    private var radius: Float = 0.0F
    private lateinit var rectF: RectF

    init {
        mBoardPaint.isAntiAlias = true
        mBoardPaint.style = Paint.Style.FILL
        mBoardPaint.color = color
        mBoardPaint.setShadowLayer(5.0f, 0.0f, 2.0f, 0xFF00000)
    }

    fun setCoordinates(xCenter: Float, yCenter: Float, width: Int, height: Int) {
        this.left   = xCenter - width * 0.5F
        this.top    = yCenter - height * 0.5F
        this.right  = left + width
        this.bottom = top  + height

        rectF  = RectF(left, top, right, bottom)
        radius = height * 0.5F
    }

    fun setCoordinates(left: Float, top: Float, right: Float, bottom: Float) {
        this.left   = left
        this.top    = top
        this.right  = right
        this.bottom = bottom

        rectF = RectF(left, top, right, bottom)
        radius     = (bottom - top) * 0.5F
    }

    fun draw(canvas: Canvas) {
        canvas.drawRoundRect(rectF, radius, radius, mBoardPaint)
    }

    fun checkMoveAction(xCoordinates: Float, yCoordinates: Float): Boolean {
        var output = false
        if (xCoordinates > left && xCoordinates < right && yCoordinates > bottom) {
            output = true
        }

        return output
    }

    fun getCurrentHeight(): Float {
        return bottom - top
    }

    fun setCurrentHeight(height: Float) {
        top = bottom - height

        rectF  = RectF(left, top, right, bottom)
        radius = height * 0.5F
    }
}
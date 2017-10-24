package com.developers.chukimmuoi.reaction.`object`

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.developers.chukimmuoi.reaction.util.convertDpToPixel

/**
 * @author  : Hanet Electronics
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : FacebookReaction
 * Created by chukimmuoi on 22/10/2017.
 */
class Board(private val resources: Resources, val color: Int) {

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
        val width  = width.convertDpToPixel(resources)
        val height =  height.convertDpToPixel(resources)

        left   = xCenter - width * 0.5F
        top    = yCenter - height * 0.5F
        right  = left + width
        bottom = top  + height
        rectF  = RectF(left, top, right, bottom)

        radius = height * 0.5F
    }

    fun draw(canvas: Canvas) {

        canvas.drawRoundRect(rectF, radius, radius, mBoardPaint)
    }
}
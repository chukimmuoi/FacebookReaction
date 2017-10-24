package com.developers.chukimmuoi.reaction.`object`

import android.content.res.Resources
import android.graphics.*
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
class Emotions(private val resources: Resources, image: Int) {

    private var bitmap: Bitmap = BitmapFactory.decodeResource(resources, image)
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var left: Float   = 0.0F
    private var top: Float    = 0.0f
    private var right: Float  = 0.0f
    private var bottom: Float = 0.0f
    private lateinit var rectF: RectF

    init {
        paint.isAntiAlias = true
    }

    fun setCoordinates(xCenter: Float, yCenter: Float, width: Int, height: Int) {
        val width = width.convertDpToPixel(resources)
        val height = height.convertDpToPixel(resources)

        left   = xCenter - width * 0.5F
        top    = yCenter - height * 0.5F
        right  = left + width
        bottom = top  + height
        rectF  = RectF(left, top, right, bottom)

    }

    fun draw(canvas: Canvas) {

        canvas.drawBitmap(bitmap, null, rectF, paint)
    }
}
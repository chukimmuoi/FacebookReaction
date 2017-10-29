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

    var left: Float   = 0.0F
    var top: Float    = 0.0f
    var right: Float  = 0.0f
    var bottom: Float = 0.0f

    var size: Int  = 0

    var beginSize: Int = 0
        get() = size
    var endSize: Int   = 0

    private lateinit var rectF: RectF

    init {
        paint.isAntiAlias = true
    }

    fun setCoordinates(xCenter: Float, yCenter: Float, size: Int) {
        this.size = size.convertDpToPixel(resources)

        left   = xCenter - this.size * 0.5F
        top    = yCenter - this.size * 0.5F
        right  = left + this.size
        bottom = top  + this.size
        rectF  = RectF(left, top, right, bottom)

    }

    fun draw(canvas: Canvas) {

        canvas.drawBitmap(bitmap, null, rectF, paint)
    }
}
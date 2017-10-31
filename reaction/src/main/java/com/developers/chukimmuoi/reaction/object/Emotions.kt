package com.developers.chukimmuoi.reaction.`object`

import android.content.res.Resources
import android.graphics.*
import android.text.TextPaint
import com.developers.chukimmuoi.reaction.util.getGoldenRatioLarge
import com.developers.chukimmuoi.reaction.util.getGoldenRatioSmall

/**
 * @author  : Hanet Electronics
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : FacebookReaction
 * Created by chukimmuoi on 22/10/2017.
 */
class Emotions(private val resources: Resources, val image: Int, val title: Int,
               val sizeMax: Int, private val sizeNormal: Int, val sizeMin: Int,
               private val margin: Int) {

    private var bitmap: Bitmap = BitmapFactory.decodeResource(resources, image)
    private var emotionPaint: Paint   = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bgTitlePaint: Paint   = Paint(Paint.ANTI_ALIAS_FLAG)
    private var titlePaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    var left: Float   = 0.0F
    var top: Float    = 0.0F
    var right: Float  = 0.0F
    var bottom: Float = 0.0F

    private lateinit var emotionRectF: RectF
    private lateinit var bgTitleRectF: RectF

    var distance: Float = 0.0F

    init {
        emotionPaint.isAntiAlias = true

        bgTitlePaint.isAntiAlias = true
        bgTitlePaint.color       = Color.parseColor("#80000000")

        titlePaint.isAntiAlias = true
        titlePaint.color       = Color.WHITE
        titlePaint.typeface    = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        titlePaint.textAlign   = Paint.Align.CENTER
    }

    fun setCoordinates(xStart: Float, d: Float, yCenter: Float, size: Int) {
        this.distance = d

        this.left   = xStart + d + margin
        this.top    = yCenter - size * 0.5F
        this.right  = left + size
        this.bottom = top + size

        emotionRectF = RectF(left, top, right, bottom)
        createBackgroundTitle(size.toFloat(), left, top, right, bottom)
    }

    fun setCoordinates(left: Float, top: Float, right: Float, bottom: Float) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom

        emotionRectF = RectF(left, top, right, bottom)
        createBackgroundTitle(Math.max(right - left, bottom - top), left, top, right, bottom)
    }

    fun setCoordinates(xCenter: Float, yCenter: Float, size: Int) {
        this.left   = xCenter - size * 0.5F
        this.top    = yCenter - size * 0.5F
        this.right  = left + size
        this.bottom = top  + size

        emotionRectF = RectF(left, top, right, bottom)
        createBackgroundTitle(size.toFloat(), left, top, right, bottom)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, null, emotionRectF, emotionPaint)

        if (right - left > sizeNormal || bottom - top > sizeNormal) {
            drawTitle(canvas)
        }
    }

    private fun drawTitle(canvas: Canvas) {
        canvas.drawRoundRect(
                bgTitleRectF,
                bgTitleRectF.height() * 0.5F, bgTitleRectF.height() * 0.5F,
                bgTitlePaint
        )

        var xPos = bgTitleRectF.left  + bgTitleRectF.width() * 0.5F
        var yPos = bgTitleRectF.top   + bgTitleRectF.height() * 0.5F -
                (titlePaint.descent() + titlePaint.ascent()) * 0.5F

        canvas.drawText(resources.getString(title), xPos, yPos, titlePaint)
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

        emotionRectF = RectF(left, top, right, bottom)
        createBackgroundTitle(size, left, top, right, bottom)
    }

    private fun createBackgroundTitle(size: Float, left: Float, top: Float, right: Float, bottom: Float) {
        val width  = (size - sizeNormal)
        val height = width.getGoldenRatioSmall()
        val margin = (width - height).getGoldenRatioLarge()

        val xCenter = left + (right - left) * 0.5F
        val yCenter = top + (bottom - top) * 0.5F - size * 0.5F - margin

        bgTitleRectF = RectF(
                xCenter - width * 0.5F, yCenter - height * 0.5F,
                xCenter + width * 0.5F, yCenter + height * 0.5F
        )

        titlePaint.textSize = (width - height).getGoldenRatioSmall()
    }
}
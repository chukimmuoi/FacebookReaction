package com.developers.chukimmuoi.reaction.`object`

import android.content.res.Resources
import android.graphics.*
import android.text.TextPaint
import com.developers.chukimmuoi.reaction.util.getGoldenRatioLarge
import com.developers.chukimmuoi.reaction.util.getGoldenRatioSmall
import com.developers.chukimmuoi.reaction.view.ReactionView

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

    /**
     * Thiết lập các giái trị left, top, right, bottom để vẽ bitmap emotions.
     *
     * @param xStart Chính là toạ độ left của board (toạ độ x ngoài cùng bên trái)
     * @param d Khoảng cách từ xStart đến emotions trừ đi giá trị margin
     * @param yCenter Toạ độ tâm y của hình vẽ
     * @param size Kích thước của hình vẽ
     * */
    fun setCoordinates(xStart: Float, d: Float, yCenter: Float, size: Int) {
        this.distance = d

        this.left   = xStart + d + margin
        this.top    = yCenter - size * 0.5F
        this.right  = left + size
        this.bottom = top + size

        emotionRectF = RectF(left, top, right, bottom)
        createBackgroundTitle(size.toFloat(), left, top, right, bottom)
    }

    /**
     * Vẽ bitmap dựa trên toạ độ và thông tin cần thiết.
     *
     * @param canvas được truyền từ class extends view.
     */
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, null, emotionRectF, emotionPaint)

        // Chỉ vẽ khi zoom emotions.
        if (right - left > sizeNormal || bottom - top > sizeNormal) {
            drawTitle(canvas)
        }
    }

    /**
     * Vẽ title của emotions bao gồm hình nền (drawRoundRect) và text (drawText)
     *
     * @param canvas được truyền từ class extends view.
     * */
    private fun drawTitle(canvas: Canvas) {
        canvas.drawRoundRect(
                bgTitleRectF,
                bgTitleRectF.height() * 0.5F, bgTitleRectF.height() * 0.5F,
                bgTitlePaint
        )

        // Vẽ text ở chính giữa hình chữ nhật.
        var xPos = bgTitleRectF.left  + bgTitleRectF.width() * 0.5F
        var yPos = bgTitleRectF.top   + bgTitleRectF.height() * 0.5F -
                (titlePaint.descent() + titlePaint.ascent()) * 0.5F

        canvas.drawText(resources.getString(title), xPos, yPos, titlePaint)
    }

    /**
     * Kiểm tra vị trí có toạ độ (xCoordinates, yCoordinates) có thuộc vào vùng phía dưới của emotions
     *
     * @param xCoordinates Toạ độ x
     * @param yCoordinates Toạ độ y
     * */
    fun checkMoveAction(xCoordinates: Float, yCoordinates: Float): Boolean {
        var output = false
        if (xCoordinates > left && xCoordinates < right && yCoordinates > bottom) {
            output = true
        }

        return output
    }

    /**
     * Lấy ra giá trị size hiên tại của emotions. Để làm giá trị bắt đầu khi thực hiện animation.
     * [ReactionView.ChooseEmotionAnimation]
     * */
    fun getCurrentSize(): Float {

        return Math.max(bottom - top, right - left)
    }

    /**
     * Update giá trị size mới cho hình hình vẽ. Dựa vào gía trị size, vị trí bắt đầu và khoảng cách
     * sẽ tính toán các giá trị cần thiết để vẽ ra hình mới.
     * Giá trị thay đổi là left, top và right. Bottom là giá trị cố định.
     *
     * @param size Giá trị kích thước mới của hình vẽ
     * @param xStart Chính là toạ độ left của board (toạ độ x ngoài cùng bên trái)
     * @param d Khoảng cách từ xStart đến emotions trừ đi giá trị margin
     * */
    fun setCurrentSize(size: Float, xStart: Float, d: Float) {
        distance = d

        left  = xStart + d + margin
        top   = bottom - size
        right = left + size

        emotionRectF = RectF(left, top, right, bottom)
        createBackgroundTitle(size, left, top, right, bottom)
    }

    /**
     * Update toạ độ tâm theo chiều dọc cho hình vẽ, dựa vào toạ độ tâm sẽ update toạ độ top, bottom.
     * Mục đích để thực hiện animation [ReactionView.StartEmotionAnimation]
     *
     * @param yCenter Giá trị toạ độ tâm y của hình vẽ.
     * */
    fun setCurrentTopAndBottom(yCenter: Float) {
        top    = yCenter - sizeNormal * 0.5F
        bottom = yCenter + sizeNormal * 0.5F

        emotionRectF = RectF(left, top, right, bottom)
        createBackgroundTitle(Math.max(right - left, bottom - top), left, top, right, bottom)
    }

    /**
     * Thiết lập tính toán các giá trị để vẽ title dựa trên giá trị size, left, top, right, bottom
     * của emotion chính.
     * Dựa vào text size cùng số ký tự sẽ xác định kích thước background cần vẽ.
     * */
    private fun createBackgroundTitle(size: Float, left: Float, top: Float, right: Float, bottom: Float) {
        titlePaint.textSize = (size - sizeNormal) * 0.45F.getGoldenRatioSmall()

        val margin = (size - sizeNormal).getGoldenRatioSmall()
        val width  = (resources.getString(title).length * titlePaint.textSize + margin).getGoldenRatioSmall()
        val height = titlePaint.textSize.getGoldenRatioLarge()

        val xCenter = left + (right - left) * 0.5F
        val yCenter = top  + (bottom - top) * 0.5F - size * 0.5F - margin

        bgTitleRectF = RectF(
                xCenter - width * 0.5F, yCenter - height * 0.5F,
                xCenter + width * 0.5F, yCenter + height * 0.5F
        )
    }
}
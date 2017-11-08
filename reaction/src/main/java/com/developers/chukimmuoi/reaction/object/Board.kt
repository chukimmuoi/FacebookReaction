package com.developers.chukimmuoi.reaction.`object`

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
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
class Board(val color: Int, private val heightNormal: Int, val heightMin: Int) {

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

    /**
     * Thiết lập toạ độ (left, top, right, bottom) và các thuộc tính (radius) cần thiết của hình chữ nhật
     *
     * @param xCenter Toạ độ tâm theo chiều ngang
     * @param yCenter Toạ độ tâm theo chiều dọc
     * @param width Chiều rộng của hình vẽ
     * @param height Chiều cao của hình vẽ
     */
    fun setCoordinates(xCenter: Float, yCenter: Float, width: Int, height: Int) {
        this.left   = xCenter - width * 0.5F
        this.top    = yCenter - height * 0.5F
        this.right  = left + width
        this.bottom = top  + height

        rectF  = RectF(left, top, right, bottom)
        radius = height * 0.5F
    }

    /**
     * Vẽ hình dựa trên toạ độ và thông tin cần thiết.
     *
     * @param canvas được truyền từ class extends view.
     */
    fun draw(canvas: Canvas) {
        canvas.drawRoundRect(rectF, radius, radius, mBoardPaint)
    }

    /**
     * Lấy ra chiều cao hiện tại của hình vẽ. Để làm giá trị bắt đầu cho animation.
     * [ReactionView.ChooseEmotionAnimation]
     * */
    fun getCurrentHeight(): Float {
        return bottom - top
    }

    /**
     * Update chiều cao cho hình vẽ, dựa vào chiều cao mới sẽ update toạ độ top và radius
     * để cập nhật hình vẽ. Ở đây top là giá trị thay đổi, các giá trị còn lại left, right, bottom
     * không đổi.
     *
     * @param height Giá trị chiều cao mới của hình
     * */
    fun setCurrentHeight(height: Float) {
        top = bottom - height

        rectF  = RectF(left, top, right, bottom)
        radius = height * 0.5F
    }

    /**
     * Update toạ độ tâm theo chiều dọc cho hình vẽ, dựa vào toạ độ tâm sẽ update toạ độ top, bottom
     * và radius để cập nhật hình vẽ. Mục đích để thực hiện animation [ReactionView.StartEmotionAnimation]
     *
     * @param yCenter Giá trị toạ độ tâm y của hình vẽ.
     * */
    fun setCurrentTopAndBottom(yCenter: Float) {
        this.top    = yCenter - heightNormal * 0.5F
        this.bottom = top  + heightNormal

        rectF  = RectF(left, top, right, bottom)
        radius = heightNormal * 0.5F
    }
}
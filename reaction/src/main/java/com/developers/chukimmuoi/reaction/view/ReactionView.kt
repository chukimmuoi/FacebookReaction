package com.developers.chukimmuoi.reaction.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import com.developers.chukimmuoi.reaction.R
import com.developers.chukimmuoi.reaction.`object`.Board
import com.developers.chukimmuoi.reaction.`object`.Emotions
import com.developers.chukimmuoi.reaction.animation.EaseOutBack
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
class ReactionView : View {

    private val EMOTION = intArrayOf(
            R.drawable.like,
            R.drawable.love,
            R.drawable.haha,
            R.drawable.wow,
            R.drawable.cry,
            R.drawable.angry
    )

    private val TITLE = intArrayOf(
            R.string.title_emotion_like,
            R.string.title_emotion_love,
            R.string.title_emotion_haha,
            R.string.title_emotion_wow,
            R.string.title_emotion_cry,
            R.string.title_emotion_angry
    )

    private var BOARD_BACKGROUND_DEFAULT: Int    = Color.WHITE
    private var BOARD_HEIGHT_NORMAL_DEFAULT: Int = 50.convertDpToPixel(resources) // 40 + 2 * 5 = 50
    private var BOARD_HEIGHT_MIN_DEFAULT: Int    = 38.convertDpToPixel(resources) // 28 + 2 * 5 = 38

    private var mBoardBackground   = BOARD_BACKGROUND_DEFAULT
    private var mBoardHeightNormal = BOARD_HEIGHT_NORMAL_DEFAULT
    private var mBoardHeightMin    = BOARD_HEIGHT_MIN_DEFAULT

    private var EMOTION_SIZE_NORMAL_DEFAULT: Int = 40.convertDpToPixel(resources) // 40 * 6 = 240
    private var EMOTION_SIZE_MAX_DEFAULT: Int    = 100.convertDpToPixel(resources) // 100 * 1 + 28 * 5 = 240
    private var EMOTION_SIZE_MIN_DEFAULT: Int    = 28.convertDpToPixel(resources) // 100 * 1 + 28 * 5 = 240

    private var mEmotionSizeNormal = EMOTION_SIZE_NORMAL_DEFAULT
    private var mEmotionSizeMax    = EMOTION_SIZE_MAX_DEFAULT
    private var mEmotionSizeMin    = EMOTION_SIZE_MIN_DEFAULT

    private var EMOTION_MARGIN_DEFAULT: Int = 5.convertDpToPixel(resources)

    private var mEmotionMargin = EMOTION_MARGIN_DEFAULT

    private var mBoard: Board
    private var mEmotions: Array<Emotions>
    private var mState: StateDraw = StateDraw.NORMAL

    private var mCurrentWidth: Int
    private var mCurrentPosition = -1

    // State.
    enum class StateDraw {
        START,
        NORMAL,
        CHOOSING,
        END
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        var typeArray = context.obtainStyledAttributes(attrs, R.styleable.ReactionView)

        this.mBoardBackground = typeArray.getColor(
                R.styleable.ReactionView_reaction_board_background,
                BOARD_BACKGROUND_DEFAULT
        )
        this.mBoardHeightNormal = typeArray.getDimensionPixelSize(
                R.styleable.ReactionView_reaction_board_height_normal,
                BOARD_HEIGHT_NORMAL_DEFAULT
        )
        this.mBoardHeightMin = typeArray.getDimensionPixelSize(
                R.styleable.ReactionView_reaction_board_height_min,
                BOARD_HEIGHT_MIN_DEFAULT
        )
        this.mEmotionSizeNormal = typeArray.getDimensionPixelSize(
                R.styleable.ReactionView_reaction_emotions_size_normal,
                EMOTION_SIZE_NORMAL_DEFAULT
        )
        this.mEmotionSizeMax = typeArray.getDimensionPixelSize(
                R.styleable.ReactionView_reaction_emotions_size_choose_max,
                EMOTION_SIZE_MAX_DEFAULT
        )
        this.mEmotionSizeMin = typeArray.getDimensionPixelSize(
                R.styleable.ReactionView_reaction_emotions_size_choose_min,
                EMOTION_SIZE_MIN_DEFAULT
        )
        this.mEmotionMargin = typeArray.getDimensionPixelSize(
                R.styleable.ReactionView_reaction_emotions_margin,
                EMOTION_MARGIN_DEFAULT
        )

        typeArray.recycle()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        mBoard    = Board(mBoardBackground, mBoardHeightNormal, mBoardHeightMin)
        mEmotions = Array(EMOTION.size, {
            index -> Emotions(resources, EMOTION[index], TITLE[index],
                mEmotionSizeMax, mEmotionSizeNormal, mEmotionSizeMin, mEmotionMargin)
        })

        mCurrentWidth = mEmotionSizeNormal * mEmotions.size + mEmotionMargin * (mEmotions.size + 1)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // Width & height nhỏ nhất của number.
        val desiredWidth = mCurrentWidth
        val desireHeight = mCurrentWidth

        // Tính toán, thỏa thuận với viewGroup để xác định kích thước cho view.
        // Sử dụng method resolveSize() có sẵn trong view hoăc reconcileSize() bên
        val width  = resolveSize(desiredWidth, widthMeasureSpec)
        val height = resolveSize(desireHeight, heightMeasureSpec)

        setMeasuredDimension(width, height)
    }

    private fun reconcileSize(contentSize: Int, measureSpec: Int): Int {
        val mode     = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        return when (mode) {
            // match_parent | 300dp.
            View.MeasureSpec.EXACTLY -> specSize
            // wrap_content | match_parent.
            View.MeasureSpec.AT_MOST -> Math.min(contentSize, specSize)
            View.MeasureSpec.UNSPECIFIED -> contentSize
            else -> contentSize
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        // Chính giữa màn hình.
        val xCenter = width * 0.5F
        val yCenter = height * 0.5F

        // Xác định vị trí thanh board.
        mBoard.setCoordinates(xCenter, yCenter,
                mCurrentWidth,
                mBoardHeightNormal)

        // Xác định vị trí cho 6 biểu tượng emotions.
        var d = 0.0F
        for (i in 0 until mEmotions.size) {
            mEmotions[i].setCoordinates(xCenter - mCurrentWidth / 2, d, yCenter, mEmotionSizeNormal)
            d += mEmotionSizeNormal + mEmotionMargin
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mBoard.draw(canvas)

        for (i in 0 until mEmotions.size) {
            mEmotions[i].draw(canvas)
        }
    }

    /**
     * Xử lý sự kiện: chạm, di chuyển và nhả.
     *
     * @return true nếu các sự kiện được xử lý, false nếu không.
     * */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                true
            }
            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until mEmotions.size) {
                    if(mEmotions[i].checkMoveAction(event.x, event.y)) {
                        choosing(i)
                        break
                    }
                }
                true
            }
            MotionEvent.ACTION_UP -> {
                normal()
                true
            }
            else -> false
        }
    }

    /***
     * Trạng thái start.
     * Hiển thị các view theo thứ tự và từ dưới lên.
     */
    fun start() {
        visibility = VISIBLE

        mState = StateDraw.START

        startAnimation(StartEmotionAnimation())
    }

    /**
     * Trạng thái khi chạm và di chuyển.
     * Phóng to, thu nhỏ các view.
     * */
    private fun choosing(position: Int) {
        if (mCurrentPosition == position && mState == StateDraw.CHOOSING) return

        mState = StateDraw.CHOOSING
        mCurrentPosition = position

        startAnimation(ChooseEmotionAnimation(mCurrentPosition))
    }

    /**
     * Trạng thái khi nhả.
     * Đưa các view về trạng thái bình thường.
     * */
    private fun normal() {
        mState = StateDraw.NORMAL

        startAnimation(ChooseEmotionAnimation(-1))
    }

    /**
     * NOTE: Chỉ một Animation() được thực hiện tại một thời điểm,
     * nếu nhiều Animation() được dùng, sẽ thực hiện Animation cuối cùng.
     *
     * Là animation xử lý phóng to, thu nhỏ một view.
     * Điều quan trọng ở đây là xác định from(giá trị bắt đầu)  to(giá trị kết thúc)
     * Đồng thời xác định mối quan hệ giữa chúng theo thời gian interpolatedTime có giá trị
     * nằm trong khoảng [0, 1] xem method [getCurrentValue]
     * */
    inner class ChooseEmotionAnimation(private val position: Int) : Animation() {

        private val DURATION = 300L

        init {
            duration = DURATION
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            val fromBoard = mBoard.getCurrentHeight()
            val toBoard   = getToBoard()
            mBoard.setCurrentHeight(getCurrentValue(interpolatedTime, fromBoard, toBoard))

            var d = 0.0F
            for (i in 0 until mEmotions.size) {
                val fromEmotion = mEmotions[i].getCurrentSize()
                val toEmotion   = getToEmotion(i)

                mEmotions[i].setCurrentSize(
                        getCurrentValue(interpolatedTime, fromEmotion, toEmotion),
                        (width - mCurrentWidth) * 0.5F,
                        getCurrentValue(interpolatedTime, mEmotions[i].distance, d)
                )
                d = getDistance(i, d)
            }

            invalidate()
        }

        private fun getToBoard() = when (mState) {
            StateDraw.CHOOSING -> mBoardHeightMin.toFloat()
            StateDraw.NORMAL -> mBoardHeightNormal.toFloat()
            else -> mBoard.getCurrentHeight()
        }

        private fun getToEmotion(index: Int) = when (mState) {
            StateDraw.CHOOSING -> {
                if (index == position) {
                    mEmotionSizeMax.toFloat()
                } else {
                    mEmotionSizeMin.toFloat()
                }
            }
            StateDraw.NORMAL -> mEmotionSizeNormal.toFloat()
            else -> mEmotions[index].getCurrentSize()
        }

        private fun getDistance(index: Int, d: Float) = when (mState) {
            StateDraw.CHOOSING -> {
                if (index == position) {
                    d + mEmotionSizeMax + mEmotionMargin
                } else {
                    d + mEmotionSizeMin + mEmotionMargin
                }
            }
            StateDraw.NORMAL -> d + mEmotionSizeNormal + mEmotionMargin
            else -> 0.0F
        }


        private fun getCurrentValue(interpolated: Float, from: Float, to: Float): Float {
            return from + interpolated * (to - from)
        }
    }

    /**
     * Là animation xử lý chuyển động khi bắt đầu hiển thị, các view sẽ di chuyển từ dưới lên dạng
     * EaseOutback.
     * Quan trọng là cần xác định vị trí hiển thị cuối cùng(vị trí sau khi kết thúc animation)
     * Ở đây là ylast.
     * */
    inner class StartEmotionAnimation: Animation() {

        private val DURATION = 900L

        private val easeOutBack = EaseOutBack

        private val yLast = height * 0.5F

        private val startTimeUnit: Float

        init {
            duration = DURATION

            easeOutBack.deltaTime = 300F
            easeOutBack.end       = 500F

            startTimeUnit = (duration - easeOutBack.deltaTime) / mEmotions.size // value = 100
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (mState == StateDraw.START) {
                val currentTime = interpolatedTime * DURATION
                for (i in 0 until mEmotions.size + 1) {
                    val startTime = i * startTimeUnit
                    if (currentTime > startTime) {
                        if(i == 0) {
                            mBoard.setCurrentTopAndBottom(yLast + easeOutBack.calculateDescending(currentTime, 0F))
                        } else {
                            mEmotions[i-1].setCurrentTopAndBottom(yLast + easeOutBack.calculateDescending(currentTime, startTime))
                        }
                    }
                }

                invalidate()
            }
        }
    }
}
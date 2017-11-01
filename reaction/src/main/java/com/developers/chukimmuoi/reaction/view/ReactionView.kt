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

    private var BOARD_BACKGROUND_DEFAULT: Int = Color.WHITE
    private var BOARD_HEIGHT_NORMAL_DEFAULT: Int = 50.convertDpToPixel(resources)
    private var BOARD_HEIGHT_MIN_DEFAULT: Int = 38.convertDpToPixel(resources)

    private var mBoardBackground = BOARD_BACKGROUND_DEFAULT
    private var mBoardHeightNormal = BOARD_HEIGHT_NORMAL_DEFAULT
    private var mBoardHeightMin = BOARD_HEIGHT_MIN_DEFAULT

    private var EMOTION_SIZE_NORMAL_DEFAULT: Int = 40.convertDpToPixel(resources)
    private var EMOTION_SIZE_MAX_DEFAULT: Int = 100.convertDpToPixel(resources)
    private var EMOTION_SIZE_MIN_DEFAULT: Int = 28.convertDpToPixel(resources)

    private var mEmotionSizeNormal = EMOTION_SIZE_NORMAL_DEFAULT
    private var mEmotionSizeMax = EMOTION_SIZE_MAX_DEFAULT
    private var mEmotionSizeMin = EMOTION_SIZE_MIN_DEFAULT

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
            index -> Emotions(
                resources, EMOTION[index], TITLE[index],
                mEmotionSizeMax, mEmotionSizeNormal, mEmotionSizeMin, mEmotionMargin)
        })

        mCurrentWidth = mEmotionSizeNormal * mEmotions.size + mEmotionMargin * (mEmotions.size + 1)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val xCenter = width * 0.5F
        val yCenter = height * 0.5F

        var d = 0.0F
        for (i in 0 until mEmotions.size) {
            mEmotions[i].setCoordinates(xCenter - mCurrentWidth / 2, d, yCenter, mEmotionSizeNormal)
            d += mEmotionSizeNormal + mEmotionMargin
        }

        mBoard.setCoordinates(xCenter, yCenter,
                mCurrentWidth,
                mBoardHeightNormal)

        show()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mBoard.draw(canvas)

        for (i in 0 until mEmotions.size) {
            mEmotions[i].draw(canvas)
        }
    }

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

    private fun choosing(position: Int) {
        if (mCurrentPosition == position && mState == StateDraw.CHOOSING) return

        mState = StateDraw.CHOOSING
        mCurrentPosition = position

        startAnimation(ChooseEmotionAnimation(mCurrentPosition))
    }

    private fun normal() {
        mState = StateDraw.NORMAL

        startAnimation(ChooseEmotionAnimation(-1))
    }

    /**
     * Chỉ một Animation() được thực hiện tại một thời điểm,
     * nếu nhiều Animation() được dùng, sẽ thực hiện Animation cuối cùng.
     * */
    inner class ChooseEmotionAnimation(private val position: Int) : Animation() {

        private val DURATION = 3000L

        init {
            duration = DURATION
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            var fromBoard = mBoard.getCurrentHeight()
            var toBoard   = getToBoard()

            var d = 0.0F
            for (i in 0 until mEmotions.size) {
                var fromEmotion = mEmotions[i].getCurrentSize()
                var toEmotion = getToEmotion(i)

                mEmotions[i].setCurrentSize(
                        getCurrentValue(interpolatedTime, fromEmotion, toEmotion),
                        (width - mCurrentWidth) * 0.5F,
                        getCurrentValue(interpolatedTime, mEmotions[i].distance, d)
                )
                d = getDistance(i, d)
            }

            mBoard.setCurrentHeight(getCurrentValue(interpolatedTime, fromBoard, toBoard))

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

    fun show() {
        visibility = VISIBLE

        mState = StateDraw.START

        startAnimation(StartEmotionAnimation())
    }

    inner class StartEmotionAnimation: Animation() {

        private val DURATION = 900L

        private val easeOutBack = EaseOutBack()

        init {
            duration = DURATION
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (mState == StateDraw.START) {
                val change = easeOutBack.calculateDescending(interpolatedTime, 0F, 500F, 1F)
                val currentTime = interpolatedTime * DURATION
                if (currentTime > 0) {
                    mBoard.setCurrentTopAndBottom(height * 0.5F + change)
                }
                if (currentTime > 100) {
                    mEmotions[0].setCurrentTopAndBottom(height * 0.5F + change)
                }
                if (currentTime > 200) {
                    mEmotions[1].setCurrentTopAndBottom(height * 0.5F + change)
                }
                if (currentTime > 300) {
                    mEmotions[2].setCurrentTopAndBottom(height * 0.5F + change)
                }
                if (currentTime > 400) {
                    mEmotions[3].setCurrentTopAndBottom(height * 0.5F + change)
                }
                if (currentTime > 500) {
                    mEmotions[4].setCurrentTopAndBottom(height * 0.5F + change)
                }
                if (currentTime > 600) {
                    mEmotions[5].setCurrentTopAndBottom(height * 0.5F + change)
                }

                invalidate()
            }
        }
    }
}
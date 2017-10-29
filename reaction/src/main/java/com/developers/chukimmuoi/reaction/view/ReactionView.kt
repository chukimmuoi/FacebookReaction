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
    private var mState: StateDraw = StateDraw.NORMAL

    private var currentPosition = 0

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
        mBoard = Board(mBoardBackground, mBoardHeightNormal, mBoardHeightMin)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val xCenter = width * 0.5F
        val yCenter = height * 0.5F

        mBoard.setCoordinates(xCenter, yCenter, 275.convertDpToPixel(resources), mBoardHeightNormal)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mBoard.draw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var output = false

        when (event.action) {
            MotionEvent.ACTION_DOWN -> output = true
            MotionEvent.ACTION_MOVE -> {
                if (mBoard.checkMoveAction(event.x, event.y)) {
                    choosing()
                }
                output = true
            }
            MotionEvent.ACTION_UP -> {
                normal()
                output = true
            }
        }

        return output
    }

    private fun choosing() {
        if (mState == StateDraw.CHOOSING) return

        mState = StateDraw.CHOOSING

        startAnimation(ChooseEmotionAnimation(mBoard.getCurrentHeight(), mBoardHeightMin.toFloat()))
    }

    private fun normal() {
        if (mState == StateDraw.NORMAL) return

        mState = StateDraw.NORMAL

        startAnimation(ChooseEmotionAnimation(mBoard.getCurrentHeight(), mBoardHeightNormal.toFloat()))
    }

    inner class ChooseEmotionAnimation(val from: Float, val to: Float) : Animation() {

        private val DURATION = 200L

        init {
            if (mState == StateDraw.CHOOSING) {
                choosing()
            } else if (mState == StateDraw.NORMAL) {
                normal()
            }

            duration = DURATION
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            mBoard.setCurrentTop(from + interpolatedTime * (to - from))

            invalidate()
        }
    }
}
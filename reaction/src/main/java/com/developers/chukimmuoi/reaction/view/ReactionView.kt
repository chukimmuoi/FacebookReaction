package com.developers.chukimmuoi.reaction.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.developers.chukimmuoi.reaction.R
import com.developers.chukimmuoi.reaction.`object`.Board
import com.developers.chukimmuoi.reaction.`object`.Emotions
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
    private var BOARD_HEIGHT_NORMAL_DEFAULT: Int = 50
    private var BOARD_HEIGHT_MIN_DEFAULT: Int = 38

    private var mBoardBackground = BOARD_BACKGROUND_DEFAULT
    private var mBoardHeightNormal = BOARD_HEIGHT_NORMAL_DEFAULT
    private var mBoardHeightMin = BOARD_HEIGHT_MIN_DEFAULT

    private var EMOTION_SIZE_NORMAL_DEFAULT: Int = 40
    private var EMOTION_SIZE_MAX_DEFAULT: Int = 100
    private var EMOTION_SIZE_MIN_DEFAULT: Int = 28

    private var mEmotionSizeNormal = EMOTION_SIZE_NORMAL_DEFAULT
    private var mEmotionSizeMax = EMOTION_SIZE_MAX_DEFAULT
    private var mEmotionSizeMin = EMOTION_SIZE_MIN_DEFAULT

    private var EMOTION_MARGIN_DEFAULT: Int = 5

    private var mEmotionMargin = EMOTION_MARGIN_DEFAULT

    private var mBoard: Board
    private lateinit var mEmotios: Array<Emotions>


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
        mBoard = Board(resources, mBoardBackground)

        mEmotios = Array(EMOTION.size, { i -> Emotions(resources, EMOTION[i]) })
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val xCenter = width * 0.5F
        val yCenter = height * 0.5F

        val d = ((mEmotionSizeNormal + mEmotionMargin) / 2).convertDpToPixel(resources)
        for (i in 0 until mEmotios.size) {
            var delta = (2.5F - i) * -2 * d
            mEmotios[i].setCoordinates(xCenter + delta, yCenter,
                    mEmotionSizeNormal, mEmotionSizeNormal)
        }

        mBoard.setCoordinates(
                xCenter, yCenter,
                mEmotionSizeNormal * mEmotios.size + mEmotionMargin * (mEmotios.size + 1),
                mBoardHeightNormal
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mBoard.draw(canvas)
        for (i in 0 until mEmotios.size) {
            mEmotios[i].draw(canvas)
        }
    }
}
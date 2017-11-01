package com.developers.chukimmuoi.reaction.animation

/**
 * @author : Hanet Electronics
 * @Skype  : chukimmuoi
 * @Mobile : +84 167 367 2505
 * @Email  : muoick@hanet.com
 * @Website: http://hanet.com/
 * @Project: FacebookReaction
 * Created by CHUKIMMUOI on 11/1/2017.
 */
class EaseOutBack {

    private val s = 1.70158

    fun getCoordinateYFromTime(time: Float, lastValue: Float): Float {
        var time = time / 1 - 1
        return lastValue - lastValue * (time * time * ((s + 1) * time + s) + 1).toFloat()
    }

    //fun calculate(t: Float, b: Float, c: Float, d: Float): Float {
    //    var t = t / d - 1
    //    return (c * (t * t * ((s + 1) * t + s) + 1) + b).toFloat()
    //}
}
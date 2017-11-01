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

    fun calculateDescending(interpolatedTime: Float, begin: Float, end: Float, deltaTime: Float): Float {
        return end - calculateAscending(interpolatedTime, begin, end, deltaTime)
    }

    /**
     * @param interpolatedTime gia tri time chay tu [0, 1]
     * @param begin gia tri bat dau, thuong la 0
     * @param end gia tri ket thuc
     * @param deltaTime thoi gian chay???
     * */
    fun calculateAscending(interpolatedTime: Float, begin: Float, end: Float, deltaTime: Float): Float {
        var time = interpolatedTime * deltaTime / deltaTime - 1
        return (end * (time * time * ((s + 1) * time + s) + 1) + begin).toFloat()
    }
}
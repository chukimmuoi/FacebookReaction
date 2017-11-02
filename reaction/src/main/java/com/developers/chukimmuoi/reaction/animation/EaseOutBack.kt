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

    fun calculateDescending(currentTime: Float, deltaTime: Float, startTime: Float, end: Float, begin: Float = 0F): Float {
        return end - calculateAscending(currentTime, deltaTime, startTime, end, begin)
    }

    /**
     * @param currentTime giá trị thời gian chạy từ [0, DURATION] vì currentTime = [0, 1] * DURATION
     * @param begin giá trị khi bắt đầu, thường là 0
     * @param end giá trị kết thúc, phải khác 0
     * @param deltaTime khoảng thời gian chạy
     * @param startTime thời gian bắt đầu, nằm trong khoảng [0, DURATION] của currentTime
     * */
    fun calculateAscending(currentTime: Float, deltaTime: Float, startTime: Float, end: Float, begin: Float = 0F): Float {
        // NOTE: Luôn đảm bảo giá trị của time nằm trong khoảng [-1, 0], begin: Float
        var time = Math.min(currentTime - startTime, deltaTime) / deltaTime - 1
        return (end * (time * time * ((s + 1) * time + s) + 1) + begin).toFloat()
    }
}
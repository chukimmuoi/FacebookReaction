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
object EaseOutBack {

    private val s = 1.70158

    var deltaTime: Float = 0.0F // Khoảng thời gian thực hiện hết 1 animation.
    var end: Float       = 0.0F // Khoảng giá trị thay đổi ở đây là [0, end] hoặc [end - 0].

    fun calculateDescending(currentTime: Float, deltaTime: Float, startTime: Float, end: Float,
                            begin: Float = 0F): Float {
        return end - calculateAscending(currentTime, deltaTime, startTime, end, begin)
    }

    fun calculateDescending(currentTime: Float, startTime: Float, begin: Float = 0F): Float {
        return calculateDescending(currentTime, deltaTime, startTime, end, begin)
    }

    /**
     * @param currentTime giá trị thời gian chạy từ [0, DURATION] vì currentTime = [0, 1] * DURATION
     * @param begin giá trị khi bắt đầu, thường là 0
     * @param end giá trị kết thúc, phải khác 0
     * @param deltaTime khoảng thời gian chạy
     * @param startTime thời gian bắt đầu, nằm trong khoảng [0, DURATION] của currentTime
     * */
    fun calculateAscending(currentTime: Float, deltaTime: Float, startTime: Float, end: Float,
                           begin: Float = 0F): Float {
        // NOTE: Luôn đảm bảo giá trị của time nằm trong khoảng [-1, 0]
        var time = Math.min(currentTime - startTime, deltaTime) / deltaTime - 1
        return (end * (time * time * ((s + 1) * time + s) + 1) + begin).toFloat()
    }

    fun calculateAscending(currentTime: Float, startTime: Float, begin: Float = 0F): Float {
        return calculateAscending(currentTime, deltaTime, startTime, end, begin)
    }
}
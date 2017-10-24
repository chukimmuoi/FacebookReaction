package com.developers.chukimmuoi.reaction.util

import android.content.res.Resources

/**
 * @author  : Hanet Electronics
 * @Skype   : chukimmuoi
 * @Mobile  : +84 167 367 2505
 * @Email   : muoick@hanet.com
 * @Website : http://hanet.com/
 * @Project : FacebookReaction
 * Created by chukimmuoi on 22/10/2017.
 */
fun Float.convertPixelToDp(resources: Resources) : Float {

    return this / resources.displayMetrics.density
}

fun Float.convertDpToPixel(resources: Resources) : Float {

    return this * resources.displayMetrics.density
}

/**
 * Converts a pixel value to a density independent pixels (DPs).
 *
 * @param resources A reference to the [Resources] in the current context.
 * @param pixels    A measurement in pixels.
 * @return The value of {@code pixels} in DPs.
 */
fun Int.convertPixelToDp(resources: Resources) : Int {

    return Math.round(this / resources.displayMetrics.density)
}

/**
 * Converts a density independent pixels (DPs) value to a pixel.
 *
 * @param resources A reference to the [Resources] in the current context.
 * @param dp    A measurement in Dps.
 * @return The value of {@code Dps} in pixels.
 */
fun Int.convertDpToPixel(resources: Resources) : Int {

    return Math.round(this * resources.displayMetrics.density)
}


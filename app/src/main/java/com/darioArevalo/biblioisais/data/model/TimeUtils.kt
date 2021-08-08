package com.darioArevalo.biblioisais.data.model

import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

private const val SECOND_MILLIS = 1
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAYS_MILLIS = 24 * HOUR_MILLIS

object TimeUtils {

    fun timeAgo(time_post:Long):String{
        val now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        var time_posted = TimeUnit.MILLISECONDS.toSeconds(time_post)
        if (time_posted > now || time_posted <=0){
            return "In the future"
        }

        val diffTime = now - time_posted

        return  when{
            diffTime < MINUTE_MILLIS -> "Just Now"
            diffTime < 2 * MINUTE_MILLIS -> "Minute Ago"
            diffTime < 60 * MINUTE_MILLIS -> "${diffTime/ MINUTE_MILLIS} Minutes Ago"
            diffTime < 2  * HOUR_MILLIS -> "An Hour Ago"
            diffTime < 24 * HOUR_MILLIS -> "${diffTime/ HOUR_MILLIS} Hours Ago"
            diffTime < 48 * HOUR_MILLIS -> "Yesterday"
            else -> "${diffTime/DAYS_MILLIS} Days Ago"

        }
    }

}
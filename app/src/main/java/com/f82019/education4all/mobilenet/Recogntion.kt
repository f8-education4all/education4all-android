package com.f82019.education4all.mobilenet

import android.graphics.RectF

class Recognition(
    id : String, title : String, confidence : Float, location : RectF
) {
    /**
     * A unique identifier for what has been recognized. Specific to the class, not the instance of
     * the object.
     */
    val id : String
    /**
     * Display name for the recognition.
     */
    val title : String
    /**
     * A sortable score for how good the recognition is relative to others. Higher should be better.
     */
    val confidence : Float
    /** Optional location within the source image for the location of the recognized object. */
    var location : RectF

    init{
        this.id = id
        this.title = title
        this.confidence = confidence
        this.location = location
    }
    override fun toString():String {
        var resultString = ""

        resultString += "[" + id + "] "
        resultString += title + " "
        resultString += String.format("(%.1f%%) ", confidence * 100.0f)
        resultString += location.toString() + " "

        return resultString.trim()
    }
}
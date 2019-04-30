package com.f82019.education4all.mobilenet


import android.content.Context
import android.graphics.*
import android.graphics.Paint.Style.FILL
import android.graphics.Paint.Style.STROKE
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.f82019.education4all.R
import com.f82019.education4all.utils.dip
import java.util.ArrayList

class ObjectDrawView : View {

    private var mRatioWidth = 0
    private var mRatioHeight = 0
    private var mObjectHash = HashMap<RectF, String>()
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    var mRatioX: Float = 0.toFloat()
    var mRatioY: Float = 0.toFloat()
    private var mImgWidth: Int = 0
    private var mImgHeight: Int = 0
    var humanRect : RectF = RectF()

    private val circleRadius: Float by lazy {
        dip(5).toFloat()
    }

    private val mPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            style = FILL
            strokeWidth = dip(2).toFloat()
        }
    }
    private val bPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            style = FILL
            strokeWidth = dip(2).toFloat()
        }
    }
    private val tPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            style = STROKE
            strokeWidth = dip(2).toFloat()
        }
    }

    constructor(context: Context) : super(context)

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    fun setImgSize(
        width: Int,
        height: Int
    ) {
        mImgWidth = width
        mImgHeight = height
        requestLayout()
    }

    fun setDrawPoint(
        recognitions: ArrayList<Recognition>
    ) {
        mObjectHash.clear()

        val size = recognitions.size

        for (i in 0 until size) {
            val left: Float = recognitions[i].location.left / mRatioX
            val top: Float = recognitions[i].location.top / mRatioY
            val right: Float = recognitions[i].location.right / mRatioX
            val bottom: Float = recognitions[i].location.bottom / mRatioY

            Log.i(
                "mObjectHash",
                "mRatioX : $mRatioX, mRaitoY : $mRatioY, left : $left, top : $top, right : $right, bottom : $bottom"
            )
            mObjectHash.put(RectF(left, top, right, bottom), recognitions[i].title)
        }
    }

    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that is,
     * calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    fun setAspectRatio(
        width: Int,
        height: Int
    ) {
        if (width < 0 || height < 0) {
            throw IllegalArgumentException("Size cannot be negative.")
        }
        mRatioWidth = width
        mRatioHeight = height
        requestLayout()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (mObjectHash.isEmpty()) return

        for ((rectF, title) in mObjectHash.entries) {
            mPaint.color = resources.getColor(R.color.colorBaseTransparent)
            canvas.drawRect(rectF, mPaint)
            tPaint.textSize = (rectF.bottom - rectF.top)/7
            tPaint.color = resources.getColor(R.color.colorPrimary)
            canvas.drawText(title,
                    (rectF.right+rectF.left)*0.5f, (rectF.top-5), tPaint)
        }

    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)

        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height)
        } else {
            if (width < height * mRatioWidth / mRatioHeight) {
                mWidth = width
                mHeight = width * mRatioHeight / mRatioWidth
            } else {
                mWidth = height * mRatioWidth / mRatioHeight
                mHeight = height
            }
        }

        setMeasuredDimension(mWidth, mHeight)

        mRatioX = mImgWidth.toFloat() / mWidth
        mRatioY = mImgHeight.toFloat() / mHeight
        Log.i("mRatioMeasure", "mRatioX : $mRatioX = mImgWidth : $mImgWidth / mWidth :$mWidth")
        Log.i("mRatioMeasure", "mRatioY : $mRatioY = mImgHeight : $mImgHeight / mHeight :$mHeight")
    }
}

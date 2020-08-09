package com.gambitdev.lifeup.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.gambitdev.lifeup.R
import kotlin.math.ceil


class CounterView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    //paint objects
    private val labelPaint = Paint(ANTI_ALIAS_FLAG)
    private val counterTextPaint = Paint(ANTI_ALIAS_FLAG)
    private val progressBarArcPaint = Paint(ANTI_ALIAS_FLAG)

    //view data
    var counterValue: Int

    //view dimens
    private val suggestedMinViewWidth: Float
    private val suggestedMinViewHeight: Float
    private var progressDiameter: Float = 0F
    private val progressWidth: Float
    private var labelDiameter: Float = 0F
    private var progressArcBounds: RectF? = null

    //view logic params
    var arcSweepAngle = 0F

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CounterView)
        //paint objects init
        labelPaint.apply {
            style = Paint.Style.FILL
            color = attributes.getColor(R.styleable.CounterView_labelColor,
                ContextCompat.getColor(context, R.color.colorPrimary))
        }
        counterTextPaint.apply {
            style = Paint.Style.FILL
            color = attributes.getColor(R.styleable.CounterView_textColor, 0xFFFFFFFF.toInt())
            textSize = attributes.getDimension(R.styleable.CounterView_counterTextSize,
                context.resources.getDimension(R.dimen.counter_view_counter_def_size))
            textAlign = Paint.Align.CENTER
        }
        progressBarArcPaint.apply {
            style = Paint.Style.FILL
            color = attributes.getColor(R.styleable.CounterView_progressColor,
                ContextCompat.getColor(context, R.color.colorAccent))
        }
        //view data init
        counterValue = attributes.getInt(R.styleable.CounterView_counter, 0)
        //view dimens init
        progressWidth = attributes.getDimension(R.styleable.CounterView_progressWidth,
            context.resources.getDimension(R.dimen.counter_view_progress_def_width))
        suggestedMinViewWidth = context.resources.getDimension(R.dimen.counter_view_min_width)
        suggestedMinViewHeight = context.resources.getDimension(R.dimen.counter_view_min_height)
        attributes.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth: Int = (paddingStart + paddingEnd + suggestedMinViewWidth).toInt()
        val w: Int = resolveSizeAndState(minWidth, widthMeasureSpec, 1)

        val minHeight: Int = MeasureSpec.getSize(w) + paddingBottom + paddingTop
        val h: Int = resolveSizeAndState(minHeight, heightMeasureSpec, 0)

        //calculate view dimens on view measured
        progressDiameter = getProgressDiameter(w.toFloat(), h.toFloat())
        labelDiameter = progressDiameter.minus(progressWidth)

        setMeasuredDimension(w, h)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        progressDiameter = getProgressDiameter(w.toFloat(), h.toFloat())
        labelDiameter = progressDiameter.minus(progressWidth)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {
            //init arc bounds on first draw
            if (progressArcBounds == null)
                progressArcBounds = RectF(0F + paddingStart,
                    0F + paddingTop,
                    measuredHeight.toFloat() - paddingEnd,
                    measuredHeight.toFloat() - paddingBottom)
            //draw progress arc
            drawArc(progressArcBounds!!, 270F, arcSweepAngle, true, progressBarArcPaint)
            //draw label circle
            drawCircle(measuredWidth.toFloat()/2, measuredHeight.toFloat()/2,
                labelDiameter/2,
                labelPaint)
            //to center text, calc half of text height
            val metric = counterTextPaint.fontMetrics
            val textHeight = metric.descent + metric.ascent
            val counterTextYNormal = textHeight / 2
            //draw counter label
            drawText(counterValue.toString(),
                measuredWidth/2.toFloat(),
                measuredHeight/2.toFloat() - counterTextYNormal,
                counterTextPaint)
        }
    }

    private fun getProgressDiameter(w: Float, h: Float) : Float {
        //account for padding
        val xPadding = (paddingStart + paddingEnd).toFloat()
        val yPadding = (paddingTop + paddingBottom).toFloat()
        val widthSize = w - xPadding
        val heightSize = h - yPadding
        //return minimum size
        return widthSize.coerceAtMost(heightSize)
    }
}
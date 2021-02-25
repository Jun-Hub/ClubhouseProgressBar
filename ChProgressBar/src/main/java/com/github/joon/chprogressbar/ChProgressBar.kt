package com.github.joon.chprogressbar

import android.animation.*
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

class ChProgressBar(context: Context, attrs: AttributeSet): View(context, attrs) {

    private var dotCount = 3
    private var dotRadius = 65f
    private var dotInterval = 25f
    private val activePaint = Paint()
    private val inactivePaint = Paint()
    private var animDuration = 200L

    private val dotList = mutableListOf<DotDrawable>()
    private val animatorList = mutableListOf<Animator>()
    private val animatorSet = AnimatorSet()

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ChProgressBar, 0, 0).let {
            try {
                dotCount = it.getInt(R.styleable.ChProgressBar_dotCount, 3)
                dotRadius = it.getDimension(R.styleable.ChProgressBar_dotRadius, 65f)
                dotInterval = it.getDimension(R.styleable.ChProgressBar_dotInterval, 25f)
                activePaint.color = it.getColor(R.styleable.ChProgressBar_activeColor, Color.BLACK)
                inactivePaint.color = it.getColor(
                    R.styleable.ChProgressBar_inactiveColor,
                    Color.WHITE
                )
                animDuration = it.getInt(R.styleable.ChProgressBar_animationDuration, 200).toLong()
            } finally {
                it.recycle()
            }
        }

        for (i in 0 until dotCount) {
            dotList.add(DotDrawable(dotRadius, inactivePaint))
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        dotList.forEach { it.draw(canvas) }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val desiredWidth = 500
        val desiredHeight = 150

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width =
            when (widthMode) {
                MeasureSpec.EXACTLY -> widthSize
                MeasureSpec.AT_MOST -> desiredWidth.coerceAtMost(widthSize)
                else -> desiredWidth
            }

        val height =
            when (heightMode) {
                MeasureSpec.EXACTLY -> heightSize
                MeasureSpec.AT_MOST -> desiredHeight.coerceAtMost(heightSize)
                else -> desiredHeight
            }

        setMeasuredDimension(width, height)

        adjustDotBounds()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        adjustDotBounds()
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return dotList.contains(who) || super.verifyDrawable(who)
    }

    private fun adjustDotBounds() {

        val diameter = (dotRadius * 2).toInt()
        val totalDotWidth = (diameter*dotCount + dotInterval*(dotCount-1)).toInt()
        val diameterWithInterval = (diameter + dotInterval).toInt()
        val paddingStart = (measuredWidth - totalDotWidth)/2f

        var left = paddingStart.toInt()
        var right = left + diameter

        dotList.forEach {
            // adjust as if 'setGravity' center
            it.setBounds(left, 0, right, measuredHeight)
            left += diameterWithInterval
            right += diameterWithInterval
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        createAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeAnimation()
    }

    private fun createAnimation() {
        val paintEvaluator = TypeEvaluator<Paint> { fraction, _, _ ->

            when(fraction) {
                1f -> inactivePaint
                0f -> inactivePaint
                else -> activePaint
            }
        }

        dotList.forEach { dot ->
            val animator = ObjectAnimator.ofObject(dot, "paint", paintEvaluator, inactivePaint)
            animator.apply {
                duration = animDuration
                addUpdateListener { invalidate() }
            }
            animatorList.add(animator)
        }

        animatorSet.apply {
            playSequentially(animatorList)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    start()
                }
            })
            start()
        }
    }

    private fun removeAnimation() {
        animatorList.apply {
            forEach { it.removeAllListeners() }
            clear()
        }
        animatorSet.apply {
            removeAllListeners()
            cancel()
        }
    }

    private fun refreshDots() {
        dotList.clear()
        for (i in 0 until dotCount) {
            dotList.add(DotDrawable(dotRadius, inactivePaint))
        }
    }

    fun setDotCount(dotCount: Int) {
        this.dotCount = dotCount
        refreshDots()
    }

    fun setDotRadius(radius: Float) {
        this.dotRadius = radius
    }

    fun setDotInterval(interval: Float) {
        this.dotInterval = interval
        refreshDots()
    }

    fun setActiveColor(@ColorInt color: Int) {
        this.activePaint.color = color
        refreshDots()
    }

    fun setInactiveColor(@ColorInt color: Int) {
        this.inactivePaint.color = color
        refreshDots()
    }

    fun setAnimationDuration(duration: Long) {
        this.animDuration = duration
        refreshDots()
    }

    fun fadeOut(duration: Long = 1000) =
        animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        visibility = GONE
                    }
                })


}
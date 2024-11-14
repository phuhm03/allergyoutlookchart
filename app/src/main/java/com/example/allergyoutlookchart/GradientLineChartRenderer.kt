package com.example.allergyoutlookchart

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.ChartAnimator
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.renderer.LineChartRenderer
import com.github.mikephil.charting.utils.ViewPortHandler

class GradientLineChartRenderer(
    private val chart: LineChart,
    animator: ChartAnimator,
    viewPortHandler: ViewPortHandler
) : LineChartRenderer(chart, animator, viewPortHandler) {

    override fun drawLinear(c: Canvas?, dataSet: ILineDataSet?) {
        if (dataSet == null || dataSet.entryCount == 0) return

        val entryCount = dataSet.entryCount

        val colors = listOf(
            ContextCompat.getColor(chart.context, R.color.rage_low),
            ContextCompat.getColor(chart.context, R.color.rage_moderate),
            ContextCompat.getColor(chart.context, R.color.rage_high),
            ContextCompat.getColor(chart.context, R.color.rage_very_high),
            ContextCompat.getColor(chart.context, R.color.rage_extreme)
        )

        val gradientColors = mutableListOf<Int>()

        for (i in 0 until entryCount) {
            val entry = dataSet.getEntryForIndex(i)

            when {
                entry.y <= 2f -> gradientColors.add(colors[0])
                entry.y <= 4f -> gradientColors.add(colors[1])
                entry.y <= 6f -> gradientColors.add(colors[2])
                entry.y <= 8f -> gradientColors.add(colors[3])
                else -> gradientColors.add(colors[4])
            }
        }

        val positions = FloatArray(gradientColors.size) { index ->
            index.toFloat() / (gradientColors.size - 1f)
        }

        val gradient = LinearGradient(
            mViewPortHandler.contentLeft(), 0f, mViewPortHandler.contentRight(), 0f,
            gradientColors.toIntArray(), positions, Shader.TileMode.CLAMP
        )

        mRenderPaint.shader = gradient

        super.drawLinear(c, dataSet)
    }

}
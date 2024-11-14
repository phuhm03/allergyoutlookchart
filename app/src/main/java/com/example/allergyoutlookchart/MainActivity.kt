package com.example.allergyoutlookchart

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.allergyoutlookchart.databinding.ActivityMainBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initChart()
    }

    private fun initChart() {
        binding.run {
            lineChart.apply {
                description.isEnabled = false
                setTouchEnabled(false)
                setPinchZoom(false)
                animateX(1000)
                setExtraOffsets(10f, 10f, 0f, 0f)
                minOffset = 0f

                renderer = GradientLineChartRenderer(lineChart, lineChart.animator, lineChart.viewPortHandler)
            }

            lineChart.xAxis.apply {
                isEnabled = true
                setDrawGridLines(true)
                setDrawAxisLine(false)
                gridColor = ContextCompat.getColor(this@MainActivity, R.color.grey500)
                gridLineWidth = 1f
                enableGridDashedLine(6f, 8f, 0f)

                setLabelCount(7, false)
                axisMinimum = 0.5f
                axisMaximum = 7.5f

                textColor = ContextCompat.getColor(this@MainActivity, R.color.white)
                textSize = 10f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        val calendar = Calendar.getInstance()
                        calendar.set(Calendar.DAY_OF_WEEK, value.toInt() + 1)
                        val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
                        return dayOfWeek ?: ""
                    }
                }
            }

            lineChart.axisRight.isEnabled = false
            lineChart.axisLeft.apply {
                isEnabled = true
                setDrawGridLines(true)
                setDrawAxisLine(false)
                gridColor = ContextCompat.getColor(this@MainActivity, R.color.grey500)
                gridLineWidth = 1f
                enableGridDashedLine(6f, 8f, 0f)

                setLabelCount(5, false)
                axisMinimum = 0f
                axisMaximum = 10f
                xOffset = 10f

                textColor = ContextCompat.getColor(this@MainActivity, R.color.white)
                textSize = 10f
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                       return when(value) {
                            2f -> getString(R.string.txt_low)
                            4f -> getString(R.string.txt_moderate)
                            6f -> getString(R.string.txt_high)
                            8f -> getString(R.string.txt_very_high)
                            10f -> getString(R.string.txt_extreme)
                            else -> getString(R.string.txt_empty)
                        }
                    }
                }
            }

            val entries = listOf(
                Entry(1f, 6f),
                Entry(2f, 6f),
                Entry(3f, 2f),
                Entry(4f, 2f),
                Entry(5f,2f),
                Entry(6f, 2f),
                Entry(7f, 8f),
            )

            val dataSet = LineDataSet(entries, "DataExample").apply {
                setDrawValues(false)
                setDrawCircles(true)
                setDrawCircleHole(false)
                setDrawFilled(false)
                lineWidth = 2f
                circleRadius = 4f
            }

            val lineData = LineData(dataSet)
            lineChart.data = lineData

            lineChart.invalidate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
// ChartActivity.java
package com.example.weatheralertapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*

class ChartActivity : AppCompatActivity() {
    private var lineChart: LineChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        lineChart = findViewById(R.id.lineChart)

        val entries = mutableListOf(
            Entry(0F, 50F),
            Entry(1F, 75F),
            Entry(2F, 60F)
        )

        val dataSet = LineDataSet(entries, "AQI")
        val lineData = LineData(dataSet)

        lineChart?.let { chart ->
            chart.data = lineData
            val description = Description()
            description.text = "Evolución del AQI"
            chart.description = description
            chart.invalidate()
        }
    }
}
// ChartActivity.java
package com.example.weatheralertapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*

class ChartActivity : AppCompatActivity() {
    var lineChart: LineChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        lineChart = findViewById(R.id.lineChart)

        val entries: MutableList<Entry> = ArrayList<Entry>()
        entries.add(Entry(0, 50))
        entries.add(Entry(1, 75))
        entries.add(Entry(2, 60))

        val dataSet: LineDataSet = LineDataSet(entries, "AQI")
        val lineData: LineData = LineData(dataSet)
        lineChart.setData(lineData)
        val description: Description = Description()
        description.setText("Evolución del AQI")
        lineChart.setDescription(description)
        lineChart.invalidate()
    }
}
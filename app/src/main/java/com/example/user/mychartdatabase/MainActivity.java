package com.example.user.mychartdatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LineChart lineChart;
    Button btnChart;
    List<Entry> entries1 = new ArrayList<Entry>();
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno INTEGER PRIMARY KEY AUTOINCREMENT,data1 REAL,data2 REAL, data3 REAL);");
        lineChart = (LineChart) findViewById(R.id.lineChart);
        int[] numArr = {1, 2, 3, 4, 5, 6};
        final HashMap<Integer, String> numMap = new HashMap<>();
        numMap.put(1, "jan");
        numMap.put(2, "feb");
        numMap.put(3, "mar");
        numMap.put(4, "Apr");
        numMap.put(5, "may");
        numMap.put(6, "Jun");
        entries1.add(new Entry(1, 1000));
        entries1.add(new Entry(2, 2000));
        entries1.add(new Entry(3, 3000));
        entries1.add(new Entry(4, 4000));
        entries1.add(new Entry(5, 5000));
        entries1.add(new Entry(6, 6000));
        LineDataSet dataSet = new LineDataSet(entries1, "Numbers");
        LineData data = new LineData(dataSet);
        lineChart = (LineChart) findViewById(R.id.lineChart);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return numMap.get((int) value);
            }
        });
        lineChart.setData(data);
        lineChart.invalidate();
    }

    public void buttonClick(View view) {
        loadData(1);
    }

    public void buttonClick2(View view) {
        loadData(2);
    }

    public void loadData(int dataSetId) {
        //Intent intent = new Intent(MainActivity.this, MyData.class);
        //startActivity(intent);

        entries1.clear();

        //load data set 1 from db

        Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + dataSetId + "'", null);
        if (c.moveToFirst()) {
            entries1.add(new Entry(1, c.getFloat(1)));
            entries1.add(new Entry(2, c.getFloat(2)));
            entries1.add(new Entry(3, c.getFloat(3)));
            entries1.add(new Entry(4, c.getFloat(1)));
            entries1.add(new Entry(5, c.getFloat(2)));
            entries1.add(new Entry(6, c.getFloat(3)));
        }

        int[] numArr = {1, 2, 3, 4, 5, 6};

        final HashMap<Integer, String> numMap = new HashMap<>();
        numMap.put(1, "jan");
        numMap.put(2, "feb");
        numMap.put(3, "mar");
        numMap.put(4, "Apr");
        numMap.put(5, "may");
        numMap.put(6, "Jun");

        LineDataSet dataSet = new LineDataSet(entries1, "Numbers");
        LineData data = new LineData(dataSet);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return numMap.get((int) value);
            }
        });
        lineChart.setData(data);
        lineChart.invalidate();


    }

}

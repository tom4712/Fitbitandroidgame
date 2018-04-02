package kr.hs.sdh.fitbit.fitbitandroidgame;

/**
 * Created by Resten on 2018-02-25.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import static kr.hs.sdh.fitbit.fitbitandroidgame.MainActivity.arrayvalue_caloris;


public class Fragment3  extends Fragment {

    BarChart chartView;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_c,container,false);
        Button move_activty;
        move_activty = v.findViewById(R.id.detail_move);
        move_activty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(getActivity(),cal_activity.class);
                startActivity(intent);

            }
        });
        chartView = v.findViewById(R.id.chart);
        Legend legend = chartView.getLegend();
        List<BarEntry> entries = new ArrayList<>();
        YAxis left = chartView.getAxisLeft();

        XAxis xAxis = chartView.getXAxis();
        entries.add(new BarEntry(0f, (float)arrayvalue_caloris[0]));
        entries.add(new BarEntry(1f, (float)arrayvalue_caloris[1]));
        entries.add(new BarEntry(2f, (float)arrayvalue_caloris[2]));
        entries.add(new BarEntry(3f, (float)arrayvalue_caloris[3]));
        entries.add(new BarEntry(4f, (float)arrayvalue_caloris[4]));
        entries.add(new BarEntry(5f, (float)arrayvalue_caloris[5]));
        entries.add(new BarEntry(6f, (float)arrayvalue_caloris[6]));
        chartView.animateY(1000);
        chartView.setTouchEnabled(false);
        chartView.getXAxis().setEnabled(false);
        chartView.setDoubleTapToZoomEnabled(false);
        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        legend.setEnabled(false);
        legend.setWordWrapEnabled(false);
        chartView.getDescription().setEnabled(false);
        chartView.setBackgroundColor(Color.parseColor("#41bdf2"));
        data.setBarWidth(0.7f); // set custom bar width
        xAxis.setDrawGridLines(false);
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true); // draw a zero line
        left.setAxisMaxValue(7000f);
        set.setValueTextColor(Color.WHITE);
        set.setColor(Color.parseColor("#d5f6f8"));
        chartView.getAxisRight().setEnabled(false); // no right axis
        chartView.setData(data);
        chartView.setFitBars(true); // make the x-axis fit exactly all bars
        chartView.invalidate(); // refresh
        return v;
    }





}





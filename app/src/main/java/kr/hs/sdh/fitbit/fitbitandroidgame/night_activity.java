package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Resten on 2018-03-29.
 */

public class night_activity extends AppCompatActivity {
    String[] arraydate_time = new String[7];
    int[] arrayvalue_time = new int[7];
    int average;
    BarChart chartView;
    ListView listView;
    TextView week;
    MyListAdapter myListAdapter;
    ArrayList<list_item> list_itemArrayList_step;
    TextView set_step;
    TextView set_desc;
    TextView arr_step;
    TextView arr_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_night);
        week = (TextView)findViewById(R.id.week_value);
        listView = (ListView)findViewById(R.id.data_list);
        chartView = (BarChart)findViewById(R.id.chart);
        set_step = (TextView)findViewById(R.id.title);
        set_desc = (TextView)findViewById(R.id.description);
        arr_step = (TextView)findViewById(R.id.sub_title);
        arr_desc = (TextView)findViewById(R.id.sub_description);
        list_itemArrayList_step = new ArrayList<list_item>();
        String res = File_reader();
        String[] split = res.split("@");
        String data = split[2]; // 데이터 마다 변경 바람
        Log.d("data2  ",data);
        ListjsonParser(data);
        GraphShow();
        for(int i =0; i<arraydate_time.length;i++){
            list_itemArrayList_step.add(new list_item(String.valueOf(arrayvalue_time[i]/60+"시간 "+arrayvalue_time[i]%60+"분"), String.valueOf(arraydate_time[i])));
        }

        set_step.setText("오늘은 "+arrayvalue_time[0]/60+"시간 "+arrayvalue_time[0]%60+"분 주무셨습니다.");
        set_desc.setText("잠은 많이 잘 수록 좋습니다!");

        week.setText("이번 주 수면시간");
        arr_step.setText("일주일 평균 수면시간은 "+(average/7)/60+"시간"+(average/7)%60+"분 입니다");
        arr_desc.setText("천천히 늘려가는 것도 방법중 하나죠 :)");
        myListAdapter = new MyListAdapter(night_activity.this,list_itemArrayList_step);
        listView.setAdapter(myListAdapter);
    }
    String File_reader(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(getFilesDir()+"text.txt"));
            String readStr = "";
            String str = null;
            while(((str = br.readLine()) != null)){
                readStr += str +"\n";
            }
            br.close();
            return readStr.substring(0,readStr.length()-1);


        }catch (FileNotFoundException e){
            e.printStackTrace();

        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ListjsonParser(String jsonString) {

        int code = 0;
        String date = null;

        try {
            int idx = 0;
            JSONArray jarray = new JSONObject(jsonString).getJSONArray("sleep-time");
            if(jarray.length()>7){
                    idx = 7;
            }else{
                 idx = 7;
            }
            for (int i = idx-1; i >-1; i--) {
                JSONObject jObject = jarray.getJSONObject(i);
                date = jObject.optString("dateOfSleep");
                code = jObject.optInt("value");
                arrayvalue_time[i] = code;
                average += code;
                arraydate_time[i] = date;



            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    public void GraphShow(){
        Legend legend = chartView.getLegend();
        List<BarEntry> entries = new ArrayList<>();
        YAxis left = chartView.getAxisLeft();
        XAxis xAxis = chartView.getXAxis();
        entries.add(new BarEntry(0f, (float)arrayvalue_time[6]));
        entries.add(new BarEntry(1f, (float)arrayvalue_time[5]));
        entries.add(new BarEntry(2f, (float)arrayvalue_time[4]));
        entries.add(new BarEntry(3f, (float)arrayvalue_time[3]));
        entries.add(new BarEntry(4f, (float)arrayvalue_time[2]));
        entries.add(new BarEntry(5f, (float)arrayvalue_time[1]));
        entries.add(new BarEntry(6f, (float)arrayvalue_time[0]));
        chartView.animateY(1000);
        chartView.setTouchEnabled(false);
        chartView.setDoubleTapToZoomEnabled(false);
        chartView.getXAxis().setEnabled(false);
        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        legend.setEnabled(false);
        legend.setWordWrapEnabled(false);
        chartView.getDescription().setEnabled(false);
        chartView.setBackgroundColor(Color.parseColor("#5854af"));
        data.setBarWidth(0.7f); // set custom bar width
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(12f);
        xAxis.setDrawGridLines(false);
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(false); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true); // draw a zero line
        set.setColor(Color.parseColor("#aaa7f2"));
        chartView.getAxisRight().setEnabled(false); // no right axis
        chartView.setData(data);
        chartView.setFitBars(true); // make the x-axis fit exactly all bars
        chartView.invalidate(); // refresh

    }

}

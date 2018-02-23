package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Resten on 2018-02-17.
 */

public class result_activity extends AppCompatActivity {
    TextView result_num;
    Button back_button;
    MyListAdapter myListAdapter;
    ListView listView;
    ArrayList<list_item> list_itemArrayList_step;
    ArrayList<list_item> list_itemArrayList_cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        result_num = (TextView) findViewById(R.id.currect_num);
        back_button = (Button) findViewById(R.id.back_id);
        listView = (ListView) findViewById(R.id.data_list);
        list_itemArrayList_step = new ArrayList<list_item>();
        question res = new question();
        Intent intent = getIntent();
        int num = intent.getIntExtra("currect_num", 0);
        result_num.setText("5/"+num);
        String value = intent.getStringExtra("no_currect_num");
        if (value != "") {
            String data[] = value.split(",");
            for (int i = 0; i < data.length; i++) {
                list_itemArrayList_step.add(new list_item(res.mQuestion[Integer.valueOf(data[i])], res.mAnswer[Integer.valueOf(data[i])]));
            }

            myListAdapter = new MyListAdapter(result_activity.this, list_itemArrayList_step);
            listView.setAdapter(myListAdapter);
            listViewHeightSet(myListAdapter,listView);

          //  setListViewHeightBasedOnChildren(listView);

        }
    }


    private void listViewHeightSet(Adapter listAdapter, ListView listView){
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }






}

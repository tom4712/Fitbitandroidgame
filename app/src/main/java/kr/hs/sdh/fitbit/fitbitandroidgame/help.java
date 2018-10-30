package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

public class help extends AppCompatActivity {
    private ExpandableListView main_list;

    private ArrayList<String> arrayGroup = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> arrayChild = new HashMap<String, ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        main_list = findViewById(R.id.Expanlist);
        setArrayData();
        main_list.setAdapter( new helpADAPT(this, arrayGroup,arrayChild));
    }

    private void setArrayData() {
        arrayGroup.add("AR게임");
        arrayGroup.add("지도게임");
        arrayGroup.add("O X 게임");
        ArrayList<String> arrayar = new ArrayList<String>();
        arrayar.add("  1.AR 게임 단계");
        arrayar.add("        총 10단계로 구성이 되어있으며");
        arrayar.add("        출현 아이템으로는");
        arrayar.add("        -당근 -시금치 -가지");
        arrayar.add("        -새우 -참치");
        arrayar.add("        등 이 있습니다.");
        arrayar.add("  2.플레이 방법");
        arrayar.add("        휴대폰을 가로로 잡은뒤");
        arrayar.add("        화살표 방향을 따라 아이템을");
        arrayar.add("        잡으면 클리어 됩니다.");
        arrayar.add("  3.코인 보상");
        arrayar.add("        기본5개에 레벨당 1개를 추가로 더 줍니다.");
        arrayar.add("        최대 15개까지 증가합니다.");
        ArrayList<String> arraymap = new ArrayList<String>();
        arraymap.add("  1.지도게임 구성");
        arraymap.add("       플레이어가 게임에 접속했을때");
        arraymap.add("       플레이어 주변으로 목표 위치가 선정됩니다.");
        arraymap.add("       선정된 구역으로 주변으로 가게되면 목표가 달성되고");
        arraymap.add("       4코인의 보상이 지급됩니다!");
        arraymap.add("  2.사용시 주의사항");
        arraymap.add("       네트워크나 GPS접속 문제시 보상이 지급되지 않거나");
        arraymap.add("       목표 위치가 생성되지 않을 수 있습니다.");
        arraymap.add("       ※ GPS와 네트워크가 원활한 상태에서 이용해 주십시오.");
        ArrayList<String> arrayox = new ArrayList<String>();
        arrayox.add("  1.문제구성");
        arrayox.add("       이미있는 문제가 요일별로 5문제씩");
        arrayox.add("       랜덤으로 사용자에게 주어지며");
        arrayox.add("       나오는 순서 또한 랜덤으로 나오게 됩니다.");
        arrayox.add("       정답은 O X 중에 고를 수 있습니다.");
        arrayox.add("  2.코인 보상");
        arrayox.add("       맞춘 문제에 따라서 코인이 다르게 지급됩니다.");
        arrayox.add("       문제당 2개의 코인을 지급합니다.");
        ArrayList<String> arrexer = new ArrayList<String>();
        arraymap.add("  1.30초운동 구성");
        arrexer.add("       월요일에서 일요일까지");
        arrexer.add("       7가지의 운동동영상이 있습니다.");
        arrexer.add("       다 보면 요일에 맞는 코인을 받습니다.");
        arrayChild.put(arrayGroup.get(0), arrayar);
        arrayChild.put(arrayGroup.get(1), arraymap);
        arrayChild.put(arrayGroup.get(2), arrayox);
    }

    public void helpend(View view) {
        finish();
    }
}

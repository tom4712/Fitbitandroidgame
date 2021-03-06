package kr.hs.sdh.fitbit.fitbitandroidgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Resten on 2018-02-23.
 */

public class qusetionActivity extends AppCompatActivity {
    private TextView question_text;
    private ImageButton res_o;
    private ImageButton res_x;
    private Intent intent;
    private String no_currect;
    private Button button;
    private TextView text_title;
    private TextView text_result;
    private RelativeLayout answer_lay;
    private ImageView answer_;
    private TextView quiz_num;
    private int in = 0;
    private int idx = 1;
    private int currect=0;
    private int nocurrect=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        answer_ = findViewById(R.id.answer_);
        button = findViewById(R.id.close);
        text_title = findViewById(R.id.answer_act);
        text_result = findViewById(R.id.answer_text);
        question_text = findViewById(R.id.question);
        quiz_num = findViewById(R.id.question_num);
        res_o = findViewById(R.id.button_O) ;
        res_x = findViewById(R.id.button_X) ;
        answer_lay = findViewById(R.id.result);
        no_currect = "";
        final Intent intent = new Intent(qusetionActivity.this,result_activity.class);


        final question res = new question();
        Random rnd = new Random();
        final int[] num = new int[5];
        for(int i = 0; i<5; i++){
            num[i] = rnd.nextInt(10);
            for(int j=i-1;j>=0;j--){

                if(num[i]==num[j]){
                    i--;
                    break;

                }

            }

        }
        question_text.setText(res.mQuestion[num[0]]);
        quiz_num.setText(idx+"번");

        res_o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer = 1;


                if(res.result_act[num[in]]!=0) {

                    if (answer == res.result_act[num[in]]) {
                        res_o.setVisibility(View.GONE);
                        res_x.setVisibility(View.GONE);
                        Animation animation = new AlphaAnimation(0, 1);
                        animation.setDuration(250);
                        text_title.setText("정답입니다!");
                        answer_.setImageResource(R.drawable.circle);
                        text_result.setText(res.mAnswer[num[in]]);
                        answer_lay.setVisibility(View.VISIBLE);
                        answer_lay.setAnimation(animation);
                        idx++;

                        currect++;
                        in++;

                    } else if (answer != res.result_act[num[in]]) {
                        answer_.setImageResource(R.drawable.ic_clear_white_48dp);
                        res_o.setVisibility(View.GONE);
                        res_x.setVisibility(View.GONE);
                        Animation animation = new AlphaAnimation(0, 1);
                        animation.setDuration(250);
                        text_title.setText("오답입니다!");
                        text_result.setText(res.mAnswer[num[in]]);
                        answer_lay.setVisibility(View.VISIBLE);
                        answer_lay.setAnimation(animation);
                        no_currect += num[in]+",";
                        idx++;

                        nocurrect++;
                        in++;

                    }
                }
            }
        });
        res_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer = 2;
                quiz_num.setText(idx+"번");

                if(res.result_act[num[in]]!=0) {


                    if (answer == res.result_act[num[in]]) {
                        answer_.setImageResource(R.drawable.circle);
                        res_o.setVisibility(View.GONE);
                        res_x.setVisibility(View.GONE);
                        Animation animation = new AlphaAnimation(0, 1);
                        animation.setDuration(250);
                        text_title.setText("정답입니다!");
                        text_result.setText(res.mAnswer[num[in]]);
                        answer_lay.setVisibility(View.VISIBLE);
                        answer_lay.setAnimation(animation);
                        currect++;
                        idx++;
                        in++;



                    }
                    else if(answer != res.result_act[num[in]]) {
                        res_o.setVisibility(View.GONE);
                        res_x.setVisibility(View.GONE);
                        answer_.setImageResource(R.drawable.ic_clear_white_48dp);
                        Animation animation = new AlphaAnimation(0, 1);
                        animation.setDuration(250);
                        text_title.setText("오답입니다!");
                        text_result.setText(res.mAnswer[num[in]]);
                        answer_lay.setVisibility(View.VISIBLE);
                        answer_lay.setAnimation(animation);
                        nocurrect++;
                        no_currect += num[in]+",";
                        idx++;
                        in++;

                    }

                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            LinearLayout lane = (LinearLayout)findViewById(R.id.quiz);
            @Override
            public void onClick(View view) {
                res_o.setVisibility(View.VISIBLE);
                res_x.setVisibility(View.VISIBLE);
                answer_lay.setVisibility(View.INVISIBLE);
                if(in<5){       if(idx<6)
                {quiz_num.setText(idx+"번");} question_text.setText(res.mQuestion[num[in]]); answer_lay.clearAnimation();}
                if(in>4){
                    lane.setVisibility(View.INVISIBLE);
                    intent.putExtra("currect_num",currect);
                    intent.putExtra("no_currect_num",no_currect);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }
}

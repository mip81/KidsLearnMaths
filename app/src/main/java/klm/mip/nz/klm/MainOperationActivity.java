package klm.mip.nz.klm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

/**
 * Created by mikhailpastushkov on 9/20/16.
 */
public class MainOperationActivity extends AppCompatActivity {

    TextView tvScore, tvLevel, tvQuestion, tvChances;
    TextView tvTime, tvName, tvAnswer, tvX, tvY, tvSign;
    Button btn1, btn2, btn3, btn4, btnBack;
    String receivedAnswer = "";
    String correctAnswer = "";

    private String activityName = "";
    private int score = 0;
    private int chances = 3;
    private int level = 1;
    private int questionNo = 1;
    private String sign = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_operation);

        // get operation from the main activity
        sign = this.getIntent().getExtras().getString("sign");

       tvScore = (TextView) findViewById(R.id.tvScore);
       tvLevel = (TextView)findViewById(R.id.tvNumLevel);
       tvQuestion = (TextView)findViewById(R.id.tvNumQuestion);
       tvChances = (TextView)findViewById(R.id.tvChances);
       tvTime = (TextView)findViewById(R.id.tvTime);

       tvX = (TextView)findViewById(R.id.tvX);
       tvY = (TextView)findViewById(R.id.tvY);
       tvSign = (TextView)findViewById(R.id.tvSign);
        tvSign.setText(sign);


        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btnBack = (Button) findViewById(R.id.btnBack);

        //Assign the logo
        switch (sign){
            case "-": ((ImageView)findViewById(R.id.ivLogo)).setImageResource(R.drawable.logo_sub); break;
            case "+": ((ImageView)findViewById(R.id.ivLogo)).setImageResource(R.drawable.logo_add); break;
            case "/": ((ImageView)findViewById(R.id.ivLogo)).setImageResource(R.drawable.logo_div); break;
            case "*": ((ImageView)findViewById(R.id.ivLogo)).setImageResource(R.drawable.logo_m); break;
        }

        loadData();
        getSupportActionBar().hide();

    }



    private void loadData(){

            tvScore.setText(""+score);
            tvChances.setText(""+chances);
            tvLevel.setText(""+level);
            tvScore.setText(""+score);
            tvQuestion.setText(""+questionNo);


        Random random = new Random();

            int no1 = random.nextInt(10) + level;
            int no2 = random.nextInt(10) + level;


        if(sign.equals("/")){
           while ((no1 % no2) != 0){
                no1 = random.nextInt(10) + level;
                no2 = random.nextInt(10) + level;
           }
        }

        if(questionNo % 5 == 0) level++;

        tvX.setText(""+no1);
        tvY.setText(""+no2);


        int  itnBtnForCorrectAnswer = random.nextInt(4);

                String wrongAnswer1 = null;
                String wrongAnswer2 = null;
                String wrongAnswer3 = null;


        if(sign.equals("+"))
            correctAnswer = String.valueOf(no1 + no2);

        if(sign.equals("-"))
            correctAnswer = String.valueOf(no1 - no2);

        if(sign.equals("/"))
            correctAnswer = String.valueOf(no1 / no2);

        if(sign.equals("*"))
            correctAnswer = String.valueOf(no1 * no2);


            wrongAnswer1 =  String.valueOf( MyRandomNumbers.getWrongAnswer( Integer.parseInt(correctAnswer) ) );
            wrongAnswer2 =  String.valueOf( MyRandomNumbers.getWrongAnswer( Integer.parseInt(correctAnswer)
                                                                        ,  Integer.parseInt(wrongAnswer1) ));
            wrongAnswer3 =  String.valueOf( MyRandomNumbers.getWrongAnswer( Integer.parseInt(correctAnswer)
                                                                        ,  Integer.parseInt(wrongAnswer1)
                                                                        ,  Integer.parseInt(wrongAnswer2)));

            if(itnBtnForCorrectAnswer == 0){
                btn1.setText(correctAnswer);
                btn2.setText(wrongAnswer1);
                btn3.setText(wrongAnswer2);
                btn4.setText(wrongAnswer3);

            }else if(itnBtnForCorrectAnswer == 1){
                btn1.setText(wrongAnswer1);
                btn2.setText(correctAnswer);
                btn3.setText(wrongAnswer2);
                btn4.setText(wrongAnswer3);
            }else if(itnBtnForCorrectAnswer == 2){
                btn1.setText(wrongAnswer1);
                btn2.setText(wrongAnswer2);
                btn3.setText(correctAnswer);
                btn4.setText(wrongAnswer3);
            }else if(itnBtnForCorrectAnswer == 3){
                btn1.setText(wrongAnswer1);
                btn2.setText(wrongAnswer2);
                btn3.setText(wrongAnswer3);
                btn4.setText(correctAnswer);
            }











    }
    public void btnClick(View view){
        receivedAnswer = ((Button)view).getText().toString();
        if (correctAnswer.equals(receivedAnswer)){
            score++;
            Toast.makeText(this,"Right answer!!!", Toast.LENGTH_SHORT).show();

        }else{
            chances--;
        }

        if(chances == -1){
            Toast.makeText(this, "End here!!! Your level = "+level, Toast.LENGTH_LONG).show();
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
        }

        questionNo++;
        loadData();

    }

    public void goBack(View view){
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }

    // Count the time
    private void doCount(){


    }


}

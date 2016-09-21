package klm.mip.nz.klm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by mikhailpastushkov on 9/20/16.
 */
public class MainOperationActivity extends AppCompatActivity {

    private Handler handler;
    private int timer = 20;

    private TextView tvScore, tvLevel, tvQuestion, tvChances;
    private TextView tvTime, tvName, tvAnswer, tvX, tvY, tvSign;
    private Chronometer chrono;
    private Button btn1, btn2, btn3, btn4, btnBack;
    private String receivedAnswer = "";
    private String correctAnswer = "";
    private int btnIdRightAnsw;


    private String activityName = "";
    private int score = 0;
    private int chances = 3;
    private int level = 1;
    private int questionNo = 1;
    private String sign = "";
    private CountDown countThread = new CountDown();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_operation);

        // get operation from the main activity
        sign = this.getIntent().getExtras().getString("sign");
        chrono = (Chronometer) findViewById(R.id.chronometer);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvLevel = (TextView) findViewById(R.id.tvNumLevel);
        tvQuestion = (TextView) findViewById(R.id.tvNumQuestion);
        tvChances = (TextView) findViewById(R.id.tvChances);
        tvTime = (TextView) findViewById(R.id.tvTime);

        tvX = (TextView) findViewById(R.id.tvX);
        tvY = (TextView) findViewById(R.id.tvY);
        tvSign = (TextView) findViewById(R.id.tvSign);
        tvSign.setText(sign);


        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btnBack = (Button) findViewById(R.id.btnBack);

        handler = new Handler();

        //Assign the logo
        switch (sign) {
            case "-":
                ((ImageView) findViewById(R.id.ivLogo)).setImageResource(R.drawable.logo_sub);
                break;
            case "+":
                ((ImageView) findViewById(R.id.ivLogo)).setImageResource(R.drawable.logo_add);
                break;
            case "/":
                ((ImageView) findViewById(R.id.ivLogo)).setImageResource(R.drawable.logo_div);
                break;
            case "*":
                ((ImageView) findViewById(R.id.ivLogo)).setImageResource(R.drawable.logo_m);
                break;
        }

        loadData();
        getSupportActionBar().hide();

        countThread.start();

    }




    // LOAD DATA FOR NEW GAME
    private void loadData() {

        tvScore.setText("" + score);
        tvChances.setText("" + chances);
        tvLevel.setText("" + level);
        tvScore.setText("" + score);
        tvQuestion.setText("" + questionNo);


        Random random = new Random();

        int no1 = random.nextInt(10) + level;
        int no2 = random.nextInt(10) + level;


        if (sign.equals("/")) {
            while ( (no1 % no2) != 0 && (no1 > no2) ) {
                no1 = random.nextInt(10) + level;
                no2 = random.nextInt(10) + level;
            }
        }



        if (questionNo % 5 == 0) level++;

        tvX.setText("" + no1);
        tvY.setText("" + no2);


        int itnBtnForCorrectAnswer = random.nextInt(4);

        String wrongAnswer1 = null;
        String wrongAnswer2 = null;
        String wrongAnswer3 = null;


        if (sign.equals("+"))
            correctAnswer = String.valueOf(no1 + no2);

        if (sign.equals("-"))
            correctAnswer = String.valueOf(no1 - no2);

        if (sign.equals("/") && no2 == 0) {
            no2 = MyRandomNumbers.getWrongAnswer(0);
        } else {
            correctAnswer = String.valueOf(no1 / no2);
        }

        if (sign.equals("*"))
            correctAnswer = String.valueOf(no1 * no2);


        wrongAnswer1 = String.valueOf(MyRandomNumbers.getWrongAnswer(Integer.parseInt(correctAnswer)));
        wrongAnswer2 = String.valueOf(MyRandomNumbers.getWrongAnswer(Integer.parseInt(correctAnswer)
                , Integer.parseInt(wrongAnswer1)));
        wrongAnswer3 = String.valueOf(MyRandomNumbers.getWrongAnswer(Integer.parseInt(correctAnswer)
                , Integer.parseInt(wrongAnswer1)
                , Integer.parseInt(wrongAnswer2)));

        if (itnBtnForCorrectAnswer == 0) {
            btn1.setText(correctAnswer); btnIdRightAnsw = btn1.getId();
            btn2.setText(wrongAnswer1);
            btn3.setText(wrongAnswer2);
            btn4.setText(wrongAnswer3);

        } else if (itnBtnForCorrectAnswer == 1) {
            btn1.setText(wrongAnswer1);
            btn2.setText(correctAnswer); btnIdRightAnsw = btn2.getId();

            btn3.setText(wrongAnswer2);
            btn4.setText(wrongAnswer3);
        } else if (itnBtnForCorrectAnswer == 2) {
            btn1.setText(wrongAnswer1);
            btn2.setText(wrongAnswer2);
            btn3.setText(correctAnswer); btnIdRightAnsw = btn3.getId();
            btn4.setText(wrongAnswer3);
        } else if (itnBtnForCorrectAnswer == 3) {
            btn1.setText(wrongAnswer1);
            btn2.setText(wrongAnswer2);
            btn3.setText(wrongAnswer3);
            btn4.setText(correctAnswer); btnIdRightAnsw = btn4.getId();
        }

        //btn1.setText("22222");
        ((Button)findViewById(R.id.btn1)).setText("3333");

    }


    //bitton answer click

    public void btnClick(View view) {

        timer= (level>15) ?  5 :  20 - level;


        receivedAnswer = ((Button) view).getText().toString();
        if (correctAnswer.equals(receivedAnswer)) {
            score++;
            Toast.makeText(this, "RIGHT ANSWER!!!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "WRONG ANSWER!!!", Toast.LENGTH_SHORT).show();
//           for(int i = 0 ; i < 300 ; i++){
//                Random r = new Random();
//                btnRightAnsw.setTextColor(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
//           }
            ((Button)findViewById(btnIdRightAnsw)).setText("OOOO");
            Log.d("DEBUG : ", "ID BTN WITH RiGHT ANSWER "+btnIdRightAnsw);

            try {
                Thread.sleep(3000);
            }catch (Exception e){}


            chances--;
        }

        if (chances == -1) {
            Toast.makeText(this, "GAME OVER! YOUR LEVEL = " + level, Toast.LENGTH_LONG).show();
            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
        }

        questionNo++;
        loadData();





    }

    public void goBack(View view) {
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }



    // Class for counting the time
    class CountDown extends Thread{

        public void run(){

            while(true){
               // Log.d("Timer time : ", String.valueOf(timer));


                try {
                    Thread.sleep(1000);
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(),"Error : "+e.getMessage(), Toast.LENGTH_LONG).show();
                }







                timer--;

                //final int sec = timer;
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(timer == 0 && chances == 0  ){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                        tvTime.setText( String.valueOf(timer) );

                        if( timer == 0 && chances >= 1){
                            chances--;
                            tvChances.setText( String.valueOf(chances) );
                            timer = (level>15) ?  5 :  20 - level;

                        }

                    }
                });

                if( timer == 0 && chances == 0 ) {
;
                     Log.d("DEBUG : ","Exit from countdown");

                    break;
                }






            }

        }
    }


}



package klm.mip.nz.klm;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by mikhailpastushkov on 9/20/16.
 */
public class MainOperationActivity extends AppCompatActivity {

    private DecimalFormat df = new DecimalFormat("0.#");
    private Handler handler;
    private int timer = 20;
    private int lastY = 0;

    private TextView tvScore, tvLevel, tvQuestion, tvChances;
    private TextView tvTime, tvName, tvAnswer, tvX, tvY, tvSign, tvAccuracy;
    private Button btn1, btn2, btn3, btn4, btnBack;
    private String receivedAnswer = "";
    private String correctAnswer = "";
    private Button corrBtn;


    private int score = 0;
    private int chances = 3;
    private int level = 1;
    private int questionNo = 1;
    private String sign = "";
    private CountDown countThread = null;
    private boolean isQuit = false;
    private int max = 10; // Max for random



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_operation);

        //define custom font
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Smoothie Shoppe.ttf");

        // get operation from the main activity
        sign = this.getIntent().getExtras().getString("sign");


        // DEFINE VARS OF VIEWS
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvLevel = (TextView) findViewById(R.id.tvNumLevel);
        tvQuestion = (TextView) findViewById(R.id.tvNumQuestion);
        tvChances = (TextView) findViewById(R.id.tvChances);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvAccuracy = (TextView) findViewById(R.id.tvAccuracy);

        tvX = (TextView) findViewById(R.id.tvX);
        tvY = (TextView) findViewById(R.id.tvY);
        tvSign = (TextView) findViewById(R.id.tvSign);
        tvSign.setText(sign);


        btn1 = (Button) findViewById(R.id.btn1);
            btn1.setTypeface(tf);
        btn2 = (Button) findViewById(R.id.btn2);
            btn2.setTypeface(tf);
        btn3 = (Button) findViewById(R.id.btn3);
            btn3.setTypeface(tf);
        btn4 = (Button) findViewById(R.id.btn4);
            btn4.setTypeface(tf);
        btnBack = (Button) findViewById(R.id.btnBack);
            btnBack.setTypeface(tf);
        // END DEFINING VARS


        handler = new Handler();

        //ASSIGN THE LOGO
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

        //HIDE ACTION BAR
        getSupportActionBar().hide();

        //count thread
        countThread = new CountDown();

        // LOAD DATA FOR THE GAME
        loadData();

    }




    // LOAD DATA FOR NEW GAME
    private void loadData() {

        tvScore.setText("" + score);
        tvChances.setText("" + chances);
        tvLevel.setText("" + level);
        tvScore.setText("" + score);
        tvQuestion.setText("" + questionNo);
        tvAccuracy.setText( (questionNo == 1) ? "0%" : df.format( ( (double)score / (double)(questionNo-1) ) * 100d)+"%"  );


        timer = (level>15) ?  5 :  20 - level;

        Random random = new Random();

        int no1 = random.nextInt(10) + level;
        int no2 = random.nextInt(10) + level;

        // Condition for DIVISION
        if (sign.equals("/")) {

            while ( !(((no1 % no2) == 0 ) && (no1 > no2) && (no1 != no2) && (no2 != 1) && (no2 != lastY) )) {
                max = 10 + level;

                no1 = random.nextInt(10) + level;
                no2 = random.nextInt(10)+ level;
            }
                lastY = no2;
                   Log.d("DEBUG ","no1 : "+ String.valueOf(no1)+" no2 : "+String.valueOf(no2));
                   Log.d("DEBUG no1 % no2 : ", String.valueOf(no1 % no2));
        }

        // CONDITION FOR MINUS
        if (sign.equals("-")) {
            while ( !(no1 > no2)  ) {
                no1 = random.nextInt(10 + level);
                no2 = random.nextInt(10 + level);
            }

            max = no1 + no2 +level;
        }

        // CONDITION FOR PLUS
        if (sign.equals("+")) {
            max = no1 + no1 + level;
        }

        // CONDITION FOR MULTIPLICATION
        if (sign.equals("*")) {
            max = (no1 * no1) + level;
        }

        // AFTER 5 Questions lebel up
        if (questionNo % 5 == 0) level++;

        // Set
        tvX.setText("" + no1);
        tvY.setText("" + no2);


        int itnBtnForCorrectAnswer = random.nextInt(3);

        String wrongAnswer1 = null;
        String wrongAnswer2 = null;
        String wrongAnswer3 = null;


        if (sign.equals("+"))
            correctAnswer = String.valueOf(no1 + no2);

        if (sign.equals("-"))
            correctAnswer = String.valueOf(no1 - no2);

        if (sign.equals("/")){
            correctAnswer = String.valueOf(no1 / no2);
        }

        if (sign.equals("*"))
            correctAnswer = String.valueOf(no1 * no2);


        wrongAnswer1 = String.valueOf(MyRandomNumbers.getWrongAnswer(max, Integer.parseInt(correctAnswer)));
        wrongAnswer2 = String.valueOf(MyRandomNumbers.getWrongAnswer(max, Integer.parseInt(correctAnswer)
                , Integer.parseInt(wrongAnswer1)));
        wrongAnswer3 = String.valueOf(MyRandomNumbers.getWrongAnswer(max, Integer.parseInt(correctAnswer)
                , Integer.parseInt(wrongAnswer1)
                , Integer.parseInt(wrongAnswer2)));

        if (itnBtnForCorrectAnswer == 0) {
            btn1.setText(correctAnswer); corrBtn = btn1;
            btn2.setText(wrongAnswer1);
            btn3.setText(wrongAnswer2);
            btn4.setText(wrongAnswer3);

        } else if (itnBtnForCorrectAnswer == 1) {
            btn1.setText(wrongAnswer1);
            btn2.setText(correctAnswer); corrBtn = btn2;

            btn3.setText(wrongAnswer2);
            btn4.setText(wrongAnswer3);
        } else if (itnBtnForCorrectAnswer == 2) {
            btn1.setText(wrongAnswer1);
            btn2.setText(wrongAnswer2);
            btn3.setText(correctAnswer); corrBtn = btn3;
            btn4.setText(wrongAnswer3);
        } else if (itnBtnForCorrectAnswer == 3) {
            btn1.setText(wrongAnswer1);
            btn2.setText(wrongAnswer2);
            btn3.setText(wrongAnswer3);
            btn4.setText(correctAnswer); corrBtn = btn3;
        }


        // Start counting

        new CountDown().start();
    }





    /** ACTION - USER RESPOND
     *  READ THE ANSWER FROM THE BTN NAME
     *  COMPARE WITH CORRECT AND SHOW RIGHT ANSWR
     * @param view
     */
    public void btnClick(View view) {


        final Drawable SAVEDBG = corrBtn.getBackground(); //save the BG of correct button
        final Button savedCorrBtn = corrBtn; // save the correct button to change the correct answer
            // stop the timer
        isQuit = true;

            // make btns onclickable
        disableBtn();

         // get answer from user
        receivedAnswer = ((Button) view).getText().toString();

        // compare if answer is correct
        if (correctAnswer.equals(receivedAnswer)) {
            score++;
            Toast toast = Toast.makeText(this, "WELL DONE!", Toast.LENGTH_SHORT);
            ((TextView)((LinearLayout)toast.getView()).getChildAt(0))
                    .setGravity(Gravity.CENTER_HORIZONTAL);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();

        // if answer is wrong show him or her the correct one
        } else {
            // SHOW MSG AND CORRECT ANSWER
            Toast toast = Toast.makeText(this, "\tWRONG ANSWER! \n\n CORRECT ANSWER IS "+correctAnswer, Toast.LENGTH_SHORT);
            LinearLayout ll = (LinearLayout) toast.getView();
            //ll.setLayoutParams(new Toolbar.LayoutParams(this));
            TextView tvToast = ((TextView)((LinearLayout)toast.getView()).getChildAt(0));

                tvToast.setGravity(Gravity.CENTER_HORIZONTAL);

            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();

            // DISPLAY CORRECT ANSWER BY CHANGING BG
            savedCorrBtn.setBackgroundResource(R.drawable.btn_corr_answer);

//todo : to make text colors changing haotic
//            Random r = new Random();
//            int x = 0;
//            while(x < 20){
//                corrBtn.setTextColor(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
//                SystemClock.sleep(100);
//                x++;
//            }

            // Make buttons unclickable

            // Reduce chances
            chances--;
        }

        if (chances == -1) { // EXIT
            isQuit = true; // stop thread

            Toast toast = Toast.makeText(getApplicationContext(), "GAME OVER! \n YOUR LEVEL IS " + level+" \n DON'T GIVE UP!", Toast.LENGTH_LONG);
            ((TextView)((LinearLayout)toast.getView()).getChildAt(0))
                    .setGravity(Gravity.CENTER_HORIZONTAL);
            toast.setGravity(Gravity.CENTER, 0, 0);

            toast.show();

            Intent mainActivity = new Intent(this, MainActivity.class);
            startActivity(mainActivity);
        }

        // delay changing question
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                questionNo++;
                enableBtn();
                savedCorrBtn.setBackground(SAVEDBG);
                loadData();

            }
        }, 3000);


    }

    /**
     * RETURN TO MAIN ACTIVITY
     * @param view
     */
    public void goBack(View view) {
        isQuit = true;
        Intent back = new Intent(this, MainActivity.class);
        startActivity(back);
    }

    /**
     * Method makes BTN unclickable
     */
    private void disableBtn(){
        btn1.setClickable(false);
        btn2.setClickable(false);
        btn3.setClickable(false);
        btn4.setClickable(false);

    }


    /**
     * Method makes BTN clickable
     */
    private void enableBtn(){
        btn1.setClickable(true);
        btn2.setClickable(true);
        btn3.setClickable(true);
        btn4.setClickable(true);
    }


    // Class for counting the time
    class CountDown extends Thread{

        CountDown(){
            isQuit = false;
        }

        public void run(){

            while(!isQuit){
               // Log.d("Timer time : ", String.valueOf(timer));

                // Better way to sleep
                SystemClock.sleep(1000);

                timer--;
                if(timer == 0) isQuit = true;
                //final int sec = timer;
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if(timer == 0 && chances == 0  ){
                            Toast toast = Toast.makeText(getApplicationContext(), "GAME OVER! \n YOUR LEVEL IS " + level+" \n DON'T GIVE UP!", Toast.LENGTH_LONG);
                            ((TextView)((LinearLayout)toast.getView()).getChildAt(0))
                                    .setGravity(Gravity.CENTER_HORIZONTAL);
                            toast.setGravity(Gravity.CENTER, -50, 0);

                            toast.show();


                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                        tvTime.setText( String.valueOf(timer) );

                        if( timer == 0 && chances >= 1){
                            chances--;
                            tvChances.setText( String.valueOf(chances) );
                            timer = (level>15) ?  5 :  20 - level;
                            tvQuestion.setText(String.valueOf(++questionNo));

                            loadData();
                        }

                    }
                });

//                if( timer == 0 && chances == 0 ) {
//;
//                    // Log.d("DEBUG : ","Exit from countdown");
//
//                    break;
//                }
            }

        }
    }



}



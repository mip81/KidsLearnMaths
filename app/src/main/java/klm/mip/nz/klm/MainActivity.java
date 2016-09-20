package klm.mip.nz.klm;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

    }


    public void doPlay(View view){
        Intent intent =  new Intent(this, MainOperationActivity.class);
        String sign = ((Button)view).getTag().toString();

        Log.i("INFO : Operation : ", sign);
        switch (sign){

            case "-":
                intent.putExtra("sign", sign);
                startActivity(intent);
                break;
            case "+":
                    intent.putExtra("sign", sign);
                    startActivity(intent);
                    break;
            case "*":
                    intent.putExtra("sign", sign);
                    startActivity(intent);
                    break;
            case "/":
                    intent.putExtra("sign", sign);
                    startActivity(intent);
                    break;
            case "?":
                    intent.putExtra("sign", sign);
                    startActivity(intent);
                    break;

        }

    }


    public void doExit(View view){
        this.finish();
        //System.exit(0);
    }

    public void doRandomPlay(View view) {
        String[] arrSign = {"-", "+", "/", "*"};
        Intent intent = new Intent(this, MainOperationActivity.class);
        intent.putExtra("sign", arrSign[new Random().nextInt(4)]);
        startActivity(intent);
    }

}

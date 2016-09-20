package klm.mip.nz.klm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Intent intent = new Intent(this, MainOperationActivity.class);
      //  startActivity(intent);

    }

    public void btnAddition(View view){
        Intent addition = new Intent(this, MainOperationActivity.class);
        addition.putExtra("operation", "+");

    }
}

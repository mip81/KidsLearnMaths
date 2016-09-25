package klm.mip.nz.klm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by mikhailpastushkov on 9/24/16.
 */

public class DialogHelper extends DialogFragment {
    private String msg1 = null;
    private String msg2 = null;
    private Intent intentMain = null;
    private MainOperationActivity moa = null;
    private String title;
    private View view;
    private boolean isInstalled = false;
    private Typeface tf;

    private TextView tv1, tv2; // TextView for MSG

    // SET SETTINGS FOR DIALOG WINDOW
    public DialogHelper setContent(Intent mainActivity, String title, String msg1, String msg2){

        this.intentMain = mainActivity;
        this.title = title;
        this.msg1 = msg1;
        this.msg2 = msg2;
        isInstalled = true;



        return this;

    }

   //  Return the view Activity

    public View getView(){
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(isInstalled == false) return null; //IF NO SETTINGS RETURN NULL

        // Changing content in the activity

        AlertDialog.Builder ad = new  AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_layout, null);

        //define custom font
        tf = Typeface.createFromAsset(getResources().getAssets(), "fonts/Rabbit On The Moon.ttf");


        tv1 = (TextView)view.findViewById(R.id.tvAWMsg);
        tv1.setText(msg1);
            tv1.setTypeface(tf);

        tv2 = (TextView)view.findViewById(R.id.tvAWLevelInfo);
        tv2.setText(msg1);
            tv2.setTypeface(tf);

       ((TextView)view.findViewById(R.id.tvAWLevelInfo)).setText(msg2);

           ad.setView(view)
                   .setTitle(title)
                    .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    startActivity(intentMain);
            }
        });

        return ad.create();

    }
}

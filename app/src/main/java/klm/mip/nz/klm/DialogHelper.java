package klm.mip.nz.klm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

/**
 * Created by mikhailpastushkov on 9/24/16.
 */

public class DialogHelper extends DialogFragment {
    private String message = null;
    private String sign = "";
    private Intent intentMain = null;
    private MainOperationActivity moa = null;
    private String title;


    public DialogHelper setMessage(String msg) {
        this.message = msg;
        return this;
    }
    public DialogHelper setTitle(String title){
        this.title = title;
        return this;
    }
    public DialogHelper setMainActivity(Intent intent){
        this.intentMain = intent;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder ad = new  AlertDialog.Builder(getActivity());
           ad.setMessage(message)
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

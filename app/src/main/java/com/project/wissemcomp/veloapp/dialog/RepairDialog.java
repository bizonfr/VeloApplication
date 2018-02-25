package com.project.wissemcomp.veloapp.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.wissemcomp.veloapp.R;

/**
 * Created by Wissem on 24/02/2018.
 */

public class RepairDialog extends DialogFragment {

    private Button positiveButton;
    private EditText price;
    private EditText type;

    public static RepairDialog newInstance(int title) {
        RepairDialog dialog = new RepairDialog();
        Bundle args = new Bundle();
        args.putInt("title", title);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        /*AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setEnabled(false);
        }*/

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                .setTitle("Ajouter une réparation")
                .setView(R.layout.custom_view_dialog)
                .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dialog dg = (Dialog) dialog;

                        type = (EditText) dg.findViewById(R.id.text_repair);
                        price = (EditText) dg.findViewById(R.id.text_price);
                        sendBackResult(type.getText().toString(), price.getText().toString());
                    }
                })
                .setNegativeButton("Annuler", null);

        return alert.create();

    }

    public interface RepairDialogListener {
        void onFinishRepairDialog(String repair_type, String repair_price);
    }

    public void sendBackResult(String type, String price) {
        RepairDialogListener listener = (RepairDialogListener) getTargetFragment();
        if (listener != null) {
            listener.onFinishRepairDialog(type, price);
        }
        dismiss();
    }



       /* AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Ajouter une réparation");
        View view = getLayoutInflater().inflate(R.layout.custom_view_dialog, null);
        alert.setView(view);
        final EditText type = view.findViewById(R.id.text_repair);
        final EditText price = view.findViewById(R.id.text_price);
        alert.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAddField(type.getText().toString(), price.getText().toString());
            }
        });
        alert.setNegativeButton("Annuler", null);

        type.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                type.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(type, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });
        type.requestFocus();


        final AlertDialog dialog = alert.show();

        type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0 || price.getText().toString().isEmpty())
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                else
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0 || type.getText().toString().isEmpty())
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                else
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false); */
}

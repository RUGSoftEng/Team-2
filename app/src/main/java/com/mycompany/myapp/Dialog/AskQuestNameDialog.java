package com.mycompany.myapp.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;

/**
 * Class description goes here.
 *
 * Created by Ruben on 01/05/2016.
 */
//TODO: still has hardcoded strings and stuff + maybe bit messy
public class AskQuestNameDialog extends DialogFragment {

    // Use this instance of the interface to deliver action events
    private QuestNameDialogListener mListener; //field description goes here

    private String questName = "Custom Quest"; //field description goes here

    /* Method description goes here. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Quest Name");
        builder.setMessage("Finish creating by giving your quest a name");

        // Set up the input
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                questName = input.getText().toString();
                mListener.onDialogPositiveClick(AskQuestNameDialog.this);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                mListener.onDialogNegativeClick(AskQuestNameDialog.this);
            }
        });

        return builder.create();
    }


    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface QuestNameDialogListener {
        public void onDialogPositiveClick(AskQuestNameDialog dialog);
        public void onDialogNegativeClick(AskQuestNameDialog dialog);
    }

    /* Override the Fragment.onAttach() method to instantiate the QuestNameDialogListener. */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the QuestNameDialogListener so we can send events to the host
            mListener = (QuestNameDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement QuestNameDialogListener");
        }
    }

    /* Method description goes here. */
    public String getQuestName(){
        return this.questName;
    }

}
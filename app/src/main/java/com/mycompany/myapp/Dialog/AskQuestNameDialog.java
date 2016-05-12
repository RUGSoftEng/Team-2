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
 * This class represents the pop-up dialog for entering a name for one's self-created quest.
 * It includes a caption, an instruction, an input field, and a positive and a negative button.
 *
 * Created by Ruben on 01-05-2016.
 */
//TODO: still has hardcoded strings and stuff + maybe bit messy
public class AskQuestNameDialog extends DialogFragment {

    //use this instance of the interface to deliver action events
    private QuestNameDialogListener mListener; //the listener instance for reacting to button clicks

    private String questName = "Custom Quest"; //the entered quest name, initialised to a hardcoded example quest name

    /* Initialises the activity as described above, and binds the positive button
     * to processing the text inputted and the negative button to discarding it. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Quest Name");
        builder.setMessage("Finish creating by giving your quest a name");

        //set up the input
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        //set up the buttons
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
     * implement this interface in order to receive event callbacks. Each
     * method passes the DialogFragment in case the host needs to query it. */
    public interface QuestNameDialogListener {
        public void onDialogPositiveClick(AskQuestNameDialog dialog);
        public void onDialogNegativeClick(AskQuestNameDialog dialog);
    }

    /* Overrides the Fragment.onAttach() method to instantiate the QuestNameDialogListener. */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //verify that the host activity implements the callback interface
        try {
            //instantiate the QuestNameDialogListener so we can send events to the host
            mListener = (QuestNameDialogListener) activity;
        } catch (ClassCastException e) {
            //the activity doesn't implement the interface, throw an exception
            throw new ClassCastException(activity.toString()
                    + " must implement QuestNameDialogListener");
        }
    }

    /* Getter method for the entered quest name. */
    public String getQuestName(){
        return this.questName;
    }

}

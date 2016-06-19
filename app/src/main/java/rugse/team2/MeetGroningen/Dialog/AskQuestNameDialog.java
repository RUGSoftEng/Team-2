package rugse.team2.MeetGroningen.Dialog;

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
//TODO: still has hardcoded strings and stuff, and maybe a bit messy
public class AskQuestNameDialog extends DialogFragment {
    /** The listener instance for reacting to button clicks. */
    private QuestNameDialogListener mListener; //use this instance of the interface to deliver action events

    /** The entered quest name, initialised to a hardcoded example quest name. */
    private String questName = "Custom Quest";

    /**
     * Initialises the activity as described above, building the dialog fragment and binding the
     * positive button to processing the text inputted and the negative button to discarding it.
     *
     * @param savedInstanceState The last saved instance state of the dialog fragment, or null if it is freshly created.
     * @return The built dialog, to be displayed by the fragment.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(rugse.team2.MeetGroningen.R.string.questName_dialog));
        builder.setMessage(getResources().getString(rugse.team2.MeetGroningen.R.string.messageText_dialog));

        //set up the input
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        //set up the buttons
        builder.setPositiveButton(getResources().getString(rugse.team2.MeetGroningen.R.string.done_dialog), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                questName = input.getText().toString();
                mListener.onDialogPositiveClick(AskQuestNameDialog.this);
            }
        });
        builder.setNegativeButton(getResources().getString(rugse.team2.MeetGroningen.R.string.back_dialog), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                mListener.onDialogNegativeClick(AskQuestNameDialog.this);
            }
        });

        return builder.create();
    }


    /** The activity that creates an instance of this dialog fragment must
      * implement this interface in order to receive event callbacks. Each
      * method passes the DialogFragment in case the host needs to query it. */
    public interface QuestNameDialogListener {
        public void onDialogPositiveClick(AskQuestNameDialog dialog);
        public void onDialogNegativeClick(AskQuestNameDialog dialog);
    }

    /**
     * Overrides the Fragment.onAttach() method to instantiate the QuestNameDialogListener.
     *
     * @param activity The activity this dialog fragment is attached to.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //verify that the host activity implements the callback interface
        try {
            //instantiate the QuestNameDialogListener so we can send events to the host
            mListener = (QuestNameDialogListener) activity;
        } catch (ClassCastException e) {
            //the activity doesn't implement the interface; throw an exception
            throw new ClassCastException(activity.toString()
                    + " must implement QuestNameDialogListener");
        }
    }

    /** Getter method for the entered quest name. */
    public String getQuestName() {
        return this.questName;
    }

}

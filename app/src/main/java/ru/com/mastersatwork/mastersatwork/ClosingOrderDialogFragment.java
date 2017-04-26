package ru.com.mastersatwork.mastersatwork;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

public class ClosingOrderDialogFragment extends DialogFragment {

    private EditText editComment;

    public interface EditCommentDialogListener {
        void onFinishCommentDialog(String inputText);
    }

    public ClosingOrderDialogFragment() {
    }

    public static ClosingOrderDialogFragment newInstance(String title) {
        ClosingOrderDialogFragment closingOrderDialogFragment = new ClosingOrderDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        closingOrderDialogFragment.setArguments(args);
        return closingOrderDialogFragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.closing_order_alert_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setPositiveButton("Подтвердить",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                // TODO Force user to input something.
                sendBackResult();
            }
        });
        alertDialogBuilder.setNegativeButton("Отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

        });

        // The button is initially deactivated, as the field is initially empty:
        final AlertDialog dialog = alertDialogBuilder.create();

        dialog.show();

        Logger.d("positive button: " + dialog.getButton(AlertDialog.BUTTON_POSITIVE));

        editComment = (EditText) view.findViewById(R.id.dialog_edit_comment);
        editComment.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence c, int i, int i2, int i3) {}
            @Override public void onTextChanged(CharSequence c, int i, int i2, int i3) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 0){
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        return dialog;

    }

    private void sendBackResult() {
        EditCommentDialogListener listener = (EditCommentDialogListener) getTargetFragment();
        listener.onFinishCommentDialog(editComment.getText().toString());
        if (editComment.getText().toString().isEmpty() || editComment.getText().toString() == null) {
            Toast.makeText(getContext(), "You should write a comment", Toast.LENGTH_SHORT).show();
        } else {
            dismiss();
        }
    }
}

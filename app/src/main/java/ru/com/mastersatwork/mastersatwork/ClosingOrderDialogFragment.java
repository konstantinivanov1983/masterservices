package ru.com.mastersatwork.mastersatwork;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ClosingOrderDialogFragment extends DialogFragment {

    private EditText editComment;
    private EditText editAmount;
    private int orderPrice;
    private boolean hasSamePrice = true;

    public interface EditCommentDialogListener {
        void onFinishCommentDialog(int price, String inputText);
    }

    public ClosingOrderDialogFragment() {
    }

    public static ClosingOrderDialogFragment newInstance(String title, int price) {
        ClosingOrderDialogFragment closingOrderDialogFragment = new ClosingOrderDialogFragment();
        Bundle args = new Bundle();
        args.putString("order", title);
        args.putInt("price", price);
        closingOrderDialogFragment.setArguments(args);
        return closingOrderDialogFragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String order = getArguments().getString("order");
        orderPrice = getArguments().getInt("price");
        LayoutInflater factory = LayoutInflater.from(getContext());
        final View view = factory.inflate(R.layout.closing_order_alert_dialog, null);
        editAmount = (EditText) view.findViewById(R.id.dialog_edit_text_amount);
        editComment = (EditText) view.findViewById(R.id.dialog_edit_comment);
        setupFloatingLabelError(view);

        final AlertDialog dialog = new AlertDialog.Builder(getActivity())
            .setTitle("Заказ № " + order)
            .setView(view)
            .setPositiveButton("Подтвердить", null)
            .setNegativeButton("Отменить", null).create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isNumber;

                        String amount = editAmount.getText().toString();
                        try{
                            int num = Integer.parseInt(amount);
                            isNumber = true;
                        } catch(NumberFormatException e) {
                            isNumber = false;
                        }

                        if (amount.length() == 0 || !isNumber) {
                            Toast.makeText(getContext(), "Введите полученную сумму корректно", Toast.LENGTH_SHORT).show();
                        } else if (Integer.parseInt(amount) == orderPrice) {
                            sendBackResult(orderPrice, null);
                            dismiss();
                        } else if (editComment.getText().length() == 0) {
                            Toast.makeText(getContext(), "Полученная сумма отличается от изначальной суммы заказа. Введите комментарий", Toast.LENGTH_LONG).show();
                        } else {
                            sendBackResult(Integer.parseInt(amount), editComment.getText().toString());
                            dismiss();
                        }
                    }
                });
            }
        });

        return dialog;

    }

    private void sendBackResult(int finalPrice, String finalComment) {
        EditCommentDialogListener listener = (EditCommentDialogListener) getTargetFragment();
        listener.onFinishCommentDialog(finalPrice, finalComment);
    }

    private void setupFloatingLabelError(View view) {
        final TextInputLayout floatingUsernameLabel = (TextInputLayout) view.findViewById(R.id.price_text_input_layout);
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    int num = Integer.parseInt(s.toString());
                    floatingUsernameLabel.setErrorEnabled(false);
                } catch(NumberFormatException e) {
                    floatingUsernameLabel.setError("Только числа!");
                    floatingUsernameLabel.setErrorEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}

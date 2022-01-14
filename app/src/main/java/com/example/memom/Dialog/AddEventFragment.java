package com.example.memom.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.memom.Activity.MainActivity;
import com.example.memom.Fragment.MemoFragment;
import com.example.memom.Fragment.NoteFragment;
import com.example.memom.Model.Memo;
import com.example.memom.Model.Note;
import com.example.memom.Model.User;
import com.example.memom.R;
import com.example.memom.Service.UserService;

import java.util.Dictionary;
import java.util.Hashtable;

public class AddEventFragment extends DialogFragment {

    private final static String TAG = "My";
    private EditText edittext_eventText;
    private Spinner spinner_eventType;
    private FragmentTransaction transaction;

    private void loadFragment(Fragment fragment) {
        transaction.replace(R.id.layout_frame, fragment).commit();
    }

    public AddEventFragment(FragmentTransaction transaction){
        this.transaction = transaction;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View myview = inflater.inflate(R.layout.dialog_addevent, null);

        edittext_eventText = myview.findViewById(R.id.edittext_eventText);
        spinner_eventType = myview.findViewById(R.id.spinner_eventType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.newType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_eventType.setAdapter(adapter);

        builder.setView(myview)
                // Add action buttons
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing.
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing.
//                        Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("清除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing.
                    }
                });
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            Button neutralButton = (Button) d.getButton(Dialog.BUTTON_NEUTRAL);
            neutralButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edittext_eventText.setText("");
                }
            });

            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = MainActivity.user;
                    String value = edittext_eventText.getText().toString();
                    if (user != null && value != "") {
                        if (spinner_eventType.getSelectedItem().toString().equals("提醒")) {
                            Log.d(TAG, "Trying to add memo");
                            Memo memo = new Memo(user.getMemoindex() + 1, value);
                            user.setMemoindex(user.getMemoindex() + 1);
                            Dictionary dict = new Hashtable();
                            dict.put("user", user);
                            dict.put("memo", memo);
                            Dictionary result = UserService.addMemo(dict);
                            loadFragment(new MemoFragment());
                        } else {
                            Log.d(TAG, "Trying to add note");
                            Note note = new Note(user.getNoteindex() + 1, value);
                            user.setNoteindex(user.getNoteindex() + 1);
                            Dictionary dict = new Hashtable();
                            dict.put("user", user);
                            dict.put("note", note);
                            Dictionary result = UserService.addNote(dict);
                            loadFragment(new NoteFragment());
                        }

                    }
                    Log.d(TAG, String.format("Add event: %s %s", spinner_eventType.getSelectedItem().toString(), edittext_eventText.getText()));

                    dismiss();
                }
            });
        }

    }




}

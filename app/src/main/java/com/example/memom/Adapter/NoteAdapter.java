package com.example.memom.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memom.Activity.MainActivity;
import com.example.memom.Fragment.MemoFragment;
import com.example.memom.Fragment.NoteFragment;
import com.example.memom.Model.Note;
import com.example.memom.R;
import com.example.memom.Service.UserService;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private final static String TAG = "My";

    private Context context;
    private List<Note> NoteList;
    private FragmentTransaction transaction;

    public NoteAdapter(Context context, List<Note> NoteList, FragmentTransaction transaction) {
        this.context = context;
        this.NoteList = NoteList;
        this.transaction = transaction;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        final Note note = NoteList.get(position);
        holder.textContext.setText(note.getContext());
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, String.format("Delete Note %s : %s", note.getId(), note.getContext()));
                Dictionary dict = new Hashtable();
                dict.put("noteindex", note.getId());
                dict.put("uid", MainActivity.user.getUid());

                Dictionary result = UserService.deleteNote(dict);
                loadFragment();
            }
        });


    }

    private void loadFragment() {
        transaction.replace(R.id.layout_frame, new NoteFragment()).commit();
    }


    @Override
    public int getItemCount() {
        return NoteList.size();
    }

    //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
    class ViewHolder extends RecyclerView.ViewHolder {
        Button buttonDelete;
        TextView textContext;

        ViewHolder(View itemView) {
            super(itemView);
            buttonDelete = (Button) itemView.findViewById(R.id.butotnDelete);
            textContext = (TextView) itemView.findViewById(R.id.textContext);

        }
    }

}

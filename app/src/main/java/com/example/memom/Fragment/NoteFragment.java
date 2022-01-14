package com.example.memom.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memom.Adapter.NoteAdapter;
import com.example.memom.Model.Note;
import com.example.memom.R;
import com.example.memom.Service.GoogleSignInService;
import com.example.memom.Service.UserService;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {
    private static final String TAG = "My";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private List<Note> notelist;

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        notelist = new ArrayList<>();
        String uid = GoogleSignInService.getUid();

        if (uid != "") {
            Dictionary dict_uid = new Hashtable();
            dict_uid.put("uid", uid);


            Dictionary result = UserService.getNote(dict_uid);
            Task<QuerySnapshot> task = (Task) result.get("task");
            for (QueryDocumentSnapshot document : task.getResult()) {
                Note note = document.toObject(Note.class);
                Log.d(TAG, "Read Note: " + note.getContext());
                notelist.add(note);
            }
        }
//        notelist.add(new Note(1, "期末報告再撐一下就過了!就可以去泡溫泉了!"));
//        notelist.add(new Note(2, "大家加油!疫情又在增溫，要保護好自己。"));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleView_note);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(new NoteAdapter(getContext(), notelist, getFragmentManager().beginTransaction()));

        // Inflate the layout for this fragment
        return view;
    }
}
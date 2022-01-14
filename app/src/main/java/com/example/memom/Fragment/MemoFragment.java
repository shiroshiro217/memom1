package com.example.memom.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memom.Adapter.MemoAdapter;
import com.example.memom.Model.Memo;
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
 * Use the {@link MemoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemoFragment extends Fragment {
    private static final String TAG = "My";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<Memo> memoList;

    public MemoFragment() {
        // Required empty public constructor
    }

    public static MemoFragment newInstance(String param1, String param2) {
        MemoFragment fragment = new MemoFragment();
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

        memoList = new ArrayList<>();
        String uid = GoogleSignInService.getUid();

        if (uid != "") {
            Dictionary dict_uid = new Hashtable();
            dict_uid.put("uid", uid);


            Dictionary result = UserService.getMemo(dict_uid);
            Task<QuerySnapshot> task = (Task) result.get("task");
            for (QueryDocumentSnapshot document : task.getResult()) {
                Memo memo = document.toObject(Memo.class);
                Log.d(TAG, "Read Memo: " + memo.getEvent());
                memoList.add(memo);
            }
        }
//        memoList.add(new Memo(1, "期末報告", false, new Date()));
//        memoList.add(new Memo(2, "打電話", false, new Date()));
//        memoList.add(new Memo(3, "吃晚餐", false, new Date()));
//        memoList.add(new Memo(4, "洗澡", false, new Date()));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_memo, container, false);


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleView_memo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new MemoAdapter(getContext(), memoList, getFragmentManager().beginTransaction()));

        return view;
    }
}
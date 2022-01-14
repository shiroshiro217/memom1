package com.example.memom.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memom.Activity.MainActivity;
import com.example.memom.Fragment.MemoFragment;
import com.example.memom.Model.Memo;
import com.example.memom.R;
import com.example.memom.Service.UserService;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {
    private final static String TAG = "My";
    private Context context;
    private List<Memo> MemoList;
    private FragmentTransaction transaction;

    public MemoAdapter(Context context, List<Memo> MemoList, FragmentTransaction transaction) {
        this.transaction = transaction;
        this.context = context;
        this.MemoList = MemoList;
    }

    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_memo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemoAdapter.ViewHolder holder, int position) {
        final Memo memo = MemoList.get(position);
        holder.textEvent.setText(memo.getEvent());
        holder.radioStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, String.format("Delete Memo %s : %s", memo.getId(), memo.getEvent()));
                Dictionary dict = new Hashtable();
                dict.put("memoindex", memo.getId());
                dict.put("uid", MainActivity.user.getUid());

                Dictionary result = UserService.deleteMemo(dict);
                loadFragment();


            }
        });

    }

    private void loadFragment() {
        transaction.replace(R.id.layout_frame, new MemoFragment()).commit();
    }

    @Override
    public int getItemCount() {
        return MemoList.size();
    }

    //Adapter 需要一個 ViewHolder，只要實作它的 constructor 就好，保存起來的view會放在itemView裡面
    class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton radioStatus;
        //        ImageButton imagebuttonNotifyTime;
        TextView textNotifyTime;
        EditText textEvent;

        ViewHolder(View itemView) {
            super(itemView);
            radioStatus = (RadioButton) itemView.findViewById(R.id.radioStatus);
//            imagebuttonNotifyTime = (ImageButton) itemView.findViewById(R.id.imagebuttonNotifyTime);
            textNotifyTime = (TextView) itemView.findViewById(R.id.textNotifyTime);
            textEvent = (EditText) itemView.findViewById(R.id.textEvent);

        }
    }

}

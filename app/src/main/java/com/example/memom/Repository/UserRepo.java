package com.example.memom.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.memom.Model.Memo;
import com.example.memom.Model.Note;
import com.example.memom.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Dictionary;
import java.util.Hashtable;

public class UserRepo {

    private static final String TAG = "My";
    private static FirebaseFirestore db;

    static {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }
    }

    // 新增使用者到資料庫
    public static Dictionary addUser(Dictionary dict) {
        Dictionary result = new Hashtable();
        User user = (User) dict.get("data");

        // 讀取資料庫
        db.collection("user")
                .document(user.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        result.put("result", "success");
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        result.put("result", "error");
                        Log.w(TAG, "Error writing document", e);
                    }
                });

        // 將資料回傳
        return result;
    }

    // 讀取使用者物件
    public static Dictionary getUser(Dictionary dict) {
        String uid = dict.get("uid").toString();

        // 讀取資料庫
        Task<DocumentSnapshot> usertask = db.collection("user").document(uid).get();
        // 等待資料讀取完畢
        while (true) {
            if (usertask.isComplete()) {
                break;
            }
        }

        Dictionary result = new Hashtable();
        if (usertask.getResult().exists()) {
            // 將資料庫的資料轉換成 User 物件
            User user = usertask.getResult().toObject(User.class);
            // 將資料回傳
            result.put("result", "success");
            result.put("user", user);
        } else {
            result.put("result", "error");
        }

        return result;
    }

    public static Dictionary getMemo(Dictionary dict) {
        String uid = dict.get("uid").toString();
        Task<QuerySnapshot> task = db.collection("memo")
                .document(uid)
                .collection("memo")
                .get();

        // 等待所有資料庫的回傳資料讀取完畢
        while (true) {
            if (task.isComplete()) {
                break;
            }
        }
        // 回傳數值
        Dictionary result = new Hashtable();

        // 將資料回傳
        result.put("task", task);
        return result;
    }

    public static Dictionary getNote(Dictionary dict) {
        String uid = dict.get("uid").toString();
        Task<QuerySnapshot> task = db.collection("note")
                .document(uid)
                .collection("note")
                .get();

        // 等待所有資料庫的回傳資料讀取完畢
        while (true) {
            if (task.isComplete()) {
                break;
            }
        }
        // 回傳數值
        Dictionary result = new Hashtable();

        // 將資料回傳
        result.put("task", task);
        return result;
    }

    public static Dictionary addMemo(Dictionary dict) {
        Dictionary result = new Hashtable();
        User user = (User) dict.get("user");
        Memo memo = (Memo) dict.get("memo");

        // 讀取資料庫
        db.collection("user")
                .document(user.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        result.put("result", "success");
                        Log.d(TAG, "AddMemo: User successfully written!");
                    }
                });

        // 讀取資料庫
        db.collection("memo")
                .document(user.getUid())
                .collection("memo")
                .document(String.valueOf(memo.getId()))
                .set(memo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        result.put("result", "success");
                        Log.d(TAG, "AddMemo: Memo successfully written!");
                    }
                });
        ;

        // 將資料回傳
        return result;
    }

    public static Dictionary deleteMemo(Dictionary dict) {
        Dictionary result = new Hashtable();

        String memoindex = (String) String.valueOf(dict.get("memoindex"));
        String uid = (String) dict.get("uid");
        db.collection("memo")
                .document(uid)
                .collection("memo")
                .document(memoindex)
                .delete();

        return result;
    }

    public static Dictionary addNote(Dictionary dict) {
        Dictionary result = new Hashtable();
        User user = (User) dict.get("user");
        Note note = (Note) dict.get("note");

        // 讀取資料庫
        db.collection("user")
                .document(user.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        result.put("result", "success");
                        Log.d(TAG, "AddMemo: User successfully written!");
                    }
                });

        // 讀取資料庫
        db.collection("note")
                .document(user.getUid())
                .collection("note")
                .document(String.valueOf(note.getId()))
                .set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        result.put("result", "success");
                        Log.d(TAG, "AddMemo: Note successfully written!");
                    }
                });
        ;

        // 將資料回傳
        return result;

    }


    public static Dictionary deleteNote(Dictionary dict) {
        Dictionary result = new Hashtable();

        String noteindex = (String) String.valueOf(dict.get("noteindex"));
        String uid = (String) dict.get("uid");
        db.collection("note")
                .document(uid)
                .collection("note")
                .document(noteindex)
                .delete();

        return result;

    }
}
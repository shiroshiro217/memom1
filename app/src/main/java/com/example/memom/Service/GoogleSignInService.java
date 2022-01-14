package com.example.memom.Service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleSignInService {

    private static FirebaseUser mUser;
    private static FirebaseAuth mAuth;
    private static String uid;
    private static final String TAG = "My";
    static {
        initFirebaseAuthSystem();
    }

    // 與 Firebase 帳號管理系統連線
    private static void initFirebaseAuthSystem() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
    }

    // 從 Firebase 帳號管理系統取得使用者資訊
    public static FirebaseUser getFirebaseUser() {
        updateFirebaseUserStatus();
        return mUser;
    }

    public static String getUid() {
        getFirebaseUser();
        if(mUser == null){
            return "";
        }

        return mUser.getUid();
    }


    // 更新 FirebaseUser 物件
    private static void updateFirebaseUserStatus() {
        mUser = mAuth.getCurrentUser();
    }


    //input:
    // String: idToken
    // 將使用者資料上傳到 Firebase 帳號管理系統
    public static void signInWithCredential(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            // 更新使用者狀態
                            updateFirebaseUserStatus();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    // 登出使用者
    public static void signOut() {
        mAuth.signOut();
    }
}

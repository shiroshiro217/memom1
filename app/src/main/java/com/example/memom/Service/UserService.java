package com.example.memom.Service;

import com.example.memom.Model.User;
import com.example.memom.Repository.UserRepo;

import java.util.Dictionary;
import java.util.Hashtable;

public class UserService {

    static {
        // do nothing.
    }

    public static Dictionary deleteMemo(Dictionary dict){
        Dictionary result = UserRepo.deleteMemo(dict);

        return result;
    }
    public static Dictionary deleteNote(Dictionary dict) {
        Dictionary result = UserRepo.deleteNote(dict);

        return result;

    }

    public static Dictionary getMemo(Dictionary dict){
        Dictionary result = UserRepo.getMemo(dict);

        return result;
    }
    public static Dictionary getNote(Dictionary dict){
        Dictionary result = UserRepo.getNote(dict);
        return result;
    }

    public static Dictionary addNote(Dictionary dict){
        Dictionary result = UserRepo.addNote(dict);
        return result;
    }

    public static Dictionary addMemo(Dictionary dict){
        Dictionary result = UserRepo.addMemo(dict);
        return result;
    }


    // 新增使用者
    public static Dictionary addUser(Dictionary dict) {
        // 建立 User 物件
        User user = new User(dict);
        Dictionary data = new Hashtable();
        data.put("data", user);

        // 把資料傳送到 UserRepo
        Dictionary result = UserRepo.addUser(data);

        // 回傳結果
        return result;
    }

    // input:
    //      uid: "asdfhih1fueiorjgkj30g"
    // output:
    //      "result": "success"
    //      "userExist" : true
    public static Dictionary checkUserExist(Dictionary dict) {
        Dictionary data = UserRepo.getUser(dict);

        Dictionary result = new Hashtable();

        if (data.get("result") == "success") {
            result.put("userExist", true);
        } else {
            result.put("userExist", false);
        }

        // 回傳結果
        return result;
    }

    // input:
    //      uid: "asdfhih1fueiorjgkj30g"
    // output:
    //      "result": "success"
    //      "user" : User Object
    // 取得使用者
    public static Dictionary getUser(Dictionary dict) {
        // 從 UserRepo裡面讀取使用者資料
        Dictionary data = UserRepo.getUser(dict);

        // 回傳結果
        return data;
    }


}

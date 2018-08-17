package com.fy.weibo.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.fy.weibo.R;
import com.fy.weibo.activity.LoginActivity;
import com.fy.weibo.sdk.Oauth;
import com.fy.weibo.util.DataBaseUtil;

/**
 * Created by Fan on 2018/8/16.
 * Fighting!!!
 */
public class RegisterFragment extends Fragment {

    EditText accountEdit;
    EditText passEdit;
    EditText confirmEdit;
    private String account;
    private String password;
    LoginActivity loginActivity;
    private DataBaseUtil dataBaseUtil;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_frag_layout, container, false);
        if (getActivity() != null){
            loginActivity = (LoginActivity) getActivity();
            dataBaseUtil =  new DataBaseUtil(loginActivity, "UserData.db", null, 1);
        }

        loginActivity.textView.setText("注  册");
        accountEdit = view.findViewById(R.id.register_account);
        passEdit = view.findViewById(R.id.register_pass);
        confirmEdit = view.findViewById(R.id.confirm_pass);
        final Button registerButton = view.findViewById(R.id.register_button);
        registerButton.setOnClickListener(v -> {
            if (checkAccount() && checkPassword()) {
                Oauth oauth = new Oauth(loginActivity);
                oauth.oauthWeiBo(account, password);
            }
        });


        return view;
    }


    private boolean checkAccount() {

        account = accountEdit.getText().toString();
        SQLiteDatabase sqLiteDatabase = dataBaseUtil.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("User", null, null, null, null, null, null);
        if (account.length() < 10){
            Log.e("TAG", "账号长度不够");
            return false;
        }
        if (cursor.moveToFirst()) {
            do {
                String dbAccount = cursor.getString(cursor.getColumnIndex("account"));
                if (account.equals(dbAccount)){
                    Log.e("TAG", "相同账户");
                    return false;
                }
                Log.e("TAG", dbAccount);
                String a = cursor.getString(cursor.getColumnIndex("password"));
                String d = cursor.getString(cursor.getColumnIndex("token"));
                Log.e("TAG", a);
                Log.e("TAG", d);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return true;
    }

    private boolean checkPassword() {

        password = passEdit.getText().toString();
        String confirm = confirmEdit.getText().toString();
        return password.length() >= 4 && confirm.equals(password);
    }

}

/*
注册界面 注册成功时会授权 如果感觉没必要存在就删掉
 */

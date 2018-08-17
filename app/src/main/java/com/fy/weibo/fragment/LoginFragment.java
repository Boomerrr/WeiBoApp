package com.fy.weibo.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fy.weibo.R;
import com.fy.weibo.activity.LoginActivity;
import com.fy.weibo.activity.Main2Activity;
import com.fy.weibo.sdk.Constants;
import com.fy.weibo.util.DataBaseUtil;

/**
 * Created by Fan on 2018/8/16.
 * Fighting!!!
 */
public class LoginFragment extends Fragment {

    private EditText accountEdit;
    private EditText passEdit;
    private TextView registerText;
    private LoginActivity loginActivity;
    private Button signButton;
    private DataBaseUtil dataBaseUtil;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_frag_layout, container, false);
        accountEdit = view.findViewById(R.id.email);
        passEdit = view.findViewById(R.id.password);
        registerText = view.findViewById(R.id.login_text);
        signButton = view.findViewById(R.id.sign_in_button);
        loginActivity = (LoginActivity) getActivity();
        if (loginActivity != null) {
            TextView textView = loginActivity.textView;
            textView.setText("登  录");
            dataBaseUtil = new DataBaseUtil(loginActivity, "UserData.db", null, 1);
            sqLiteDatabase = dataBaseUtil.getWritableDatabase();
        }
        registerText.setOnClickListener(v -> {
            FragmentTransaction transaction = loginActivity.fragmentManager.beginTransaction();
            transaction.replace(R.id.login_frame, new RegisterFragment());
            transaction.setCustomAnimations(R.anim.in_from_left, R.anim.out_to_right);
            transaction.commit();
        });
        signButton.setOnClickListener(v -> {
            if (!checkAccount()){
                Log.e("TAG", "没有该账户");
            } else if (!checkPassword()) {
                Log.e("TAG", "密码错误");
            } else {
                Constants.ACCESS_TOKEN = getToken();
                Intent intent = new Intent(loginActivity, Main2Activity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    private boolean checkAccount() {
        String account = accountEdit.getText().toString();
        Cursor cursor = sqLiteDatabase.query("User", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String a = cursor.getString(cursor.getColumnIndex("password"));
                String b = cursor.getString(cursor.getColumnIndex("token"));
                String dbAccount = cursor.getString(cursor.getColumnIndex("account"));
                Log.e("TAG", a + "   " + b + "  "+ dbAccount);
                if (account.equals(dbAccount))
                    return true;
            } while (cursor.moveToNext());
        }

        cursor.close();
        return false;
    }

    private boolean checkPassword() {

        String password = passEdit.getText().toString();
        String account = accountEdit.getText().toString();
        cursor = sqLiteDatabase.rawQuery("SELECT password FROM User WHERE account=" + account, null);
        cursor.moveToFirst();
        Log.e("TAG", cursor.getString(0));
        if (password.equals(cursor.getString(0)))
            return true;
        cursor.close();
        return false;
    }

    private String getToken() {

        String account = accountEdit.getText().toString();
        String password = passEdit.getText().toString();
        cursor = sqLiteDatabase.rawQuery("SELECT token FROM User WHERE account= \"" + account + "\" AND password= \"" + password + "\"", null);
        cursor.moveToFirst();
        String token = cursor.getString(0);
        Log.e("TAG", token);
        cursor.close();
        return token;
    }

}

/*

登录界面
 */

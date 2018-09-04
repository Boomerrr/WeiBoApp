package com.fy.weibo.fragment;

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
import android.widget.Toast;

import com.fy.weibo.App;
import com.fy.weibo.R;
import com.fy.weibo.activity.LoginActivity;
import com.fy.weibo.sdk.Oauth;
import com.fy.weibo.util.DataBase;
import com.fy.weibo.util.DataBaseHelper;

/**
 * Created by Fan on 2018/8/16.
 * Fighting!!!
 */
public final class RegisterFragment extends Fragment {

    EditText accountEdit;
    EditText passEdit;
    EditText confirmEdit;
    private String account;
    private String password;
    LoginActivity loginActivity;
    private DataBase dataBase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_frag_layout, container, false);
        if (getActivity() != null){
            loginActivity = (LoginActivity) getActivity();
            dataBase =  new DataBase(App.getAppInstance().getApplicationContext(), "UserData.db", null, 1);
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
        if (account.length() < 10){
            Toast.makeText(loginActivity, "账号长度不够或前后密码不一致", Toast.LENGTH_SHORT).show();
            Log.e("TAG", "账号长度不够");
            return false;
        }

        boolean isAccount = DataBaseHelper.getDataBaseHelper().checkRegisterAccount(account);
        if (!isAccount){
            Toast.makeText(loginActivity, "该账号已被注册", Toast.LENGTH_SHORT).show();
        }
       return isAccount;
    }

    private boolean checkPassword() {

        password = passEdit.getText().toString();
        String confirm = confirmEdit.getText().toString();
        boolean isPassword = password.length() >= 4 && confirm.equals(password);
        if (!isPassword)
            Toast.makeText(loginActivity, "密码长度应大于四位,且前后两次应一致", Toast.LENGTH_SHORT).show();
        return isPassword;
    }

}

/*
注册界面 注册成功时会授权 如果感觉没必要存在就删掉
 */

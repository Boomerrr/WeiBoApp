package com.fy.weibo.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fy.weibo.R;
import com.fy.weibo.activity.LoginActivity;
import com.fy.weibo.base.BaseFragment;
import com.fy.weibo.sdk.Constants;
import com.fy.weibo.sdk.Oauth;
import com.fy.weibo.util.DataBase;
import com.fy.weibo.util.DataBaseHelper;
import com.fy.weibo.util.NetStateUtil;
import com.fy.weibo.util.UserState;

/**
 * Created by Fan on 2018/8/16.
 * Fighting!!!
 */
public final class LoginFragment extends BaseFragment {

    private EditText accountEdit;
    private EditText passEdit;
    private TextView registerText;
    private LoginActivity loginActivity;
    private Button signButton;
    private DataBase dataBase;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private TextView oauthTextView;
    private Oauth oauth;


    @Override
    public int getContentViewId() {
        return R.layout.login_frag_layout;
    }

    @Override
    public void initAllMembersView(Bundle saveInstanceState) {
        UserState.setUserStateNull(mContext);
        oauth = new Oauth(getActivity());
        accountEdit = mRootView.findViewById(R.id.email);
        passEdit = mRootView.findViewById(R.id.password);
        registerText = mRootView.findViewById(R.id.login_text);
        signButton = mRootView.findViewById(R.id.sign_in_button);
        loginActivity = (LoginActivity) getActivity();
        oauthTextView = mRootView.findViewById(R.id.oauth);

        oauthTextView.setOnClickListener(v -> {
            oauth.oauthWeiBo();
        });
        Log.e("TAG", "hello");
        if (loginActivity != null) {
            TextView textView = loginActivity.textView;
            textView.setText("登  录");
            dataBase = new DataBase(loginActivity, "UserData.db", null, 1);
            sqLiteDatabase = dataBase.getWritableDatabase();
        }
        registerText.setOnClickListener(v -> {
            FragmentTransaction transaction = loginActivity.fragmentManager.beginTransaction();
            transaction.replace(R.id.login_frame, new RegisterFragment());
            transaction.addToBackStack(null);
            transaction.setCustomAnimations(R.anim.in_from_left, R.anim.out_to_right);
            transaction.commit();
        });
        signButton.setOnClickListener(v -> {
            if (NetStateUtil.checkNet(mActivity)) {
                if (!checkAccount()) {
                    Log.e("TAG", "没有该账户");
                    Toast.makeText(loginActivity, "账号错误", Toast.LENGTH_SHORT).show();
                } else if (!checkPassword()) {
                    Log.e("TAG", "密码错误");
                    Toast.makeText(loginActivity, "密码错误", Toast.LENGTH_SHORT).show();
                } else {
                    Constants.USER_ACCOUNT = accountEdit.getText().toString();
                    Constants.USER_PASSWORD = passEdit.getText().toString();
                    Constants.ACCESS_TOKEN = getToken();
                    Log.e("TAG", "LoginFragment-----" + Constants.ACCESS_TOKEN);
                    oauth.isOauth();
                }
            } else Toast.makeText(mActivity, "请检查网络", Toast.LENGTH_SHORT).show();
        });


    }


    private boolean checkAccount() {
        String account = accountEdit.getText().toString();
        return DataBaseHelper.getDataBaseHelper().checkAccount(account);
    }

    private boolean checkPassword() {
        String password = passEdit.getText().toString();
        String account = accountEdit.getText().toString();
        return DataBaseHelper.getDataBaseHelper().checkPassword(account, password);
    }

    private String getToken() {
        String account = accountEdit.getText().toString();
        String password = passEdit.getText().toString();
        return DataBaseHelper.getDataBaseHelper().getUserToken(account, password);
    }

}

/*

登录界面
 */

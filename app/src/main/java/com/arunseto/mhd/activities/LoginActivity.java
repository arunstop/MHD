package com.arunseto.mhd.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.arunseto.mhd.R;
import com.arunseto.mhd.api.GoogleAuthClient;
import com.arunseto.mhd.api.MainClient;
import com.arunseto.mhd.fragments.RegisterFragment;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.models.UserResponse;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.LoadingDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    private GlobalTools gt;
    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GoogleAuthClient googleAuthClient;
    private LoadingDialog loadingDialog;
    private LinearLayout llBtnLoginGoogle;
    private Button btnLogin, btnNavRegister;
    private EditText etEmail, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        gt = new GlobalTools(this);
        context = gt.getContext();
        session = gt.getSession();
        flContentBnv = gt.getContentBnv();
        flContent = gt.getContent();
        googleAuthClient = gt.getGoogleAuthClient();
        loadingDialog = gt.getLoadingDialog();


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnNavRegister = findViewById(R.id.btnNavRegister);
        llBtnLoginGoogle = findViewById(R.id.llBtnLoginGoogle);

        //btnLogin to log getIntent to google account
        llBtnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actLoginGoogle();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLogin();
            }
        });

        btnNavRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gt.navigateFragment(gt.getContent(), new RegisterFragment());
            }
        });

        //btnLogout to log out google account
    }

    private void initLogin() {

        String email = etEmail.getText().toString().trim().toLowerCase();
        String password = etPassword.getText().toString().trim().toLowerCase();
        if (email.isEmpty()) {
            etEmail.setError("Email tidak boleh kosong");
            return;
        }
        if (!email.contains("@")) {
            etEmail.setError("Email tidak sesuai format");
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password tidak boleh kosong");
            return;
        }
        if (password.length() < 8) {
            etPassword.setError("Password masih kurang dari 8 karakter");
            return;
        }

        loadingDialog.show();
        execLogin(email, password);
// dummy
//        session.saveUser(new User("",
//                "email",
//                "",
//                "",
//                "first",
//                "last",
//                "",
//                "",
//                "",
//                "",
//                ""));
    }


    private void execLogin(String email, String password) {
        String currentTime = gt.getCurrentTime();
        Call<UserResponse> call = MainClient.getInstance().getApi().loginUser(email,
                password, currentTime, 1);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse result = response.body();
                    if (result.isOk()) {
                        List<User> lu = result.getData();
                        session.saveUser(lu.get(0));
                        startActivity(new Intent(context, MainActivity.class));
                        finish();
                        loadingDialog.dismiss();
//                                     Toast.makeText(LoginActivity.this, "Login " + result.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "" + result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    loadingDialog.dismiss();

                } else {
                    Toast.makeText(context, response.message() + " Error", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + " Error", Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();

            }
        });
    }

    // google login
    private void actLoginGoogle() {
        loadingDialog.show();
        startActivityForResult(googleAuthClient.getIntent(), 101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);

                        String dateNow = gt.formatDate("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", gt.getCurrentTime());
                        Call<UserResponse> call = gt.callApi().loginGoogle(
                                account.getEmail(),
                                "google",
                                account.getGivenName(),
                                account.getFamilyName(),
                                "0",
                                1,
                                dateNow,
                                "",
                                account.getPhotoUrl().toString(),
                                0,
                                gt.getCurrentTime(),
                                2,
                                gt.getCurrentTime()
                        );
                        call.enqueue(new Callback<UserResponse>() {
                            @Override
                            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                                if (response.isSuccessful()) {
                                    UserResponse result = response.body();
                                    if (result.isOk()) {
                                        session.saveUser(result.getData().get(0));
                                        startActivity(new Intent(context, MainActivity.class));
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, response.message() + "", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserResponse> call, Throwable t) {
                                Toast.makeText(LoginActivity.this,
                                        "Login Failed :\n" + t.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w("Google LoginActivity", "signInResult:failed code=" + e.getStatusCode());
                        Toast.makeText(this, "Sign In failed, error code = " + e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            loadingDialog.dismiss();
        } else {
            Toast.makeText(context, "Sign In failed", Toast.LENGTH_SHORT).show();
            loadingDialog.dismiss();
        }
    }

    public void navBack(View view) {
        super.onBackPressed();
    }


}

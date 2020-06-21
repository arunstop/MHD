package com.arunseto.mhd.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.activities.MainActivity;
import com.arunseto.mhd.api.MainClient;
import com.arunseto.mhd.models.DefaultResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.models.UserResponse;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.LoadingDialog;
import com.arunseto.mhd.ui.ConfirmationDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This is the main prototype of fragmenting

public class RegisterFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;

    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private LoadingDialog loadingDialog;
    private EditText etFirstName, etLastName, etEmail, etPassword, etPhone, etPasswordConfirm, etBirthDate;
    private Button btnMale, btnFemale, btnRegister;
    private String firstName, lastName, email, password, phone, passwordConfirm, birthDate;
    private int sexO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_register, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        flContentBnv = gt.getContentBnv();
        flContent = gt.getContent();
        loadingDialog = gt.getLoadingDialog();
        confirmationDialog = gt.getConfirmationDialog();

        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etPasswordConfirm = view.findViewById(R.id.etPasswordConfirm);
        etPhone = view.findViewById(R.id.etPhone);
        btnMale = view.findViewById(R.id.btnMale);
        btnFemale = view.findViewById(R.id.btnFemale);
        etBirthDate = view.findViewById(R.id.etBirthDate);
        btnRegister = view.findViewById(R.id.btnRegister);

        sexO = 0;

        sexOpt();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRegister();
//                Call<DefaultResponse> call = MainClient.getInstance().getApi()
//                        .registerUser("email@email.com1",
//                                "email12345",
//                                "",
//                                "",
//                                "",
//                                sexO,
//                                "",
//                                "",
//                                "",
//                                1,
//                                "",
//                                1,
//                                gt.getCurrentTime()
//                                );
//                call.enqueue(new Callback<DefaultResponse>() {
//                    @Override
//                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
//                        Toast.makeText(context, response.message()+"", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<DefaultResponse> call, Throwable t) {
//                        Toast.makeText(context, t.getMessage()+"", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
            }
        });


        return view;
    }

    public void sexOpt() {
        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexO = 1;
                btnSexChecked(btnMale);
                btnSexUnchecked(btnFemale);
            }
        });
        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexO = 2;
                btnSexChecked(btnFemale);
                btnSexUnchecked(btnMale);
            }
        });
    }

    public void btnSexChecked(Button button) {
        button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorPrimary));
        button.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
    }

    public void btnSexUnchecked(Button button) {
        button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBackground));
        button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

    }

    public void initRegister() {
        firstName = etFirstName.getText().toString().trim().toLowerCase();
        lastName = etLastName.getText().toString().trim().toLowerCase();
        email = etEmail.getText().toString().trim().toLowerCase();
        password = etPassword.getText().toString().trim().toLowerCase();
        passwordConfirm = etPasswordConfirm.getText().toString().trim().toLowerCase();
        phone = etPhone.getText().toString().trim().toLowerCase();
        birthDate = etBirthDate.getText().toString().trim().toLowerCase();

        if (firstName.isEmpty()) {
            etFirstName.setError("Tidak boleh kosong");
            etFirstName.requestFocus();
            return;
        }
        if (lastName.isEmpty()) {
            etLastName.setError("Tidak boleh kosong");
            etLastName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            etEmail.setError("Tidak boleh kosong");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Tidak boleh kosong");
            etPassword.requestFocus();
            return;
        }
        if (password.length() < 8) {
            etPassword.setError("Minimal 8 karakter");
            etPassword.requestFocus();
            return;
        }
        if (passwordConfirm.isEmpty()) {
            etPasswordConfirm.setError("Tidak boleh kosong");
            etPasswordConfirm.requestFocus();
            return;
        }
        if (passwordConfirm.length() < 8) {
            etPasswordConfirm.setError("Minimal 8 karakter");
            etPasswordConfirm.requestFocus();
            return;
        }
        if (!password.equals(passwordConfirm)) {
            etPasswordConfirm.setError("Password harus sama");
            etPasswordConfirm.requestFocus();
        }
        if (birthDate.isEmpty()) {
            etBirthDate.setError("Tidak boleh kosong");
            etBirthDate.requestFocus();
            return;
        }

        execRegister();
    }

    public void execRegister() {
        loadingDialog.show();
        Call<UserResponse> call = MainClient.getInstance().getApi()
                .registerUser(email,
                        passwordConfirm,
                        firstName,
                        lastName,
                        phone,
                        sexO,
                        birthDate,
                        "",
                        "",
                        1,
                        gt.getCurrentTime(),
                        1,
                        gt.getCurrentTime()
                );
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse result = response.body();
                    if (result.isOk()) {
                        session.saveUser(result.getData().get(0));
                        Toast.makeText(context, "Registration Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(context, MainActivity.class));
                        getActivity().finish();
                        loadingDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }
}

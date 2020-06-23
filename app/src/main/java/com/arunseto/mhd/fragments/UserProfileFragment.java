package com.arunseto.mhd.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.arunseto.mhd.R;
import com.arunseto.mhd.activities.LoginActivity;
import com.arunseto.mhd.api.GoogleAuthClient;
import com.arunseto.mhd.api.MainClient;
import com.arunseto.mhd.models.DefaultResponse;
import com.arunseto.mhd.models.User;
import com.arunseto.mhd.models.UserResponse;
import com.arunseto.mhd.tools.GlobalTools;
import com.arunseto.mhd.tools.Session;
import com.arunseto.mhd.ui.ConfirmationDialog;
import com.arunseto.mhd.ui.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//This is the main prototype of fragmenting

public class UserProfileFragment extends Fragment {

    private View view;
    private LayoutInflater inflater;

    private Context context;
    private Session session;
    private User user;
    private int flContentBnv, flContent;
    private GlobalTools gt;
    private ConfirmationDialog confirmationDialog;
    private GoogleAuthClient googleAuthClient;
    private LoadingDialog loadingDialog;
    private TextView tvEmail;
    private EditText etFirstName, etLastName, etPassword, etPhone, etPasswordConfirm, etBirthDate, etCity;
    private Button btnMale, btnFemale, btnUpdateProfile, btnDeleteAccount;
    private String firstName, lastName, email, password, phone, passwordConfirm, birthDate="", city;
    private int sexO;
    private Calendar calendar;
    private SimpleDateFormat formatter;
    private SimpleDateFormat formatterForDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(getActivity());
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        flContentBnv = gt.getContentBnv();
        flContent = gt.getContent();
        googleAuthClient = gt.getGoogleAuthClient();
        loadingDialog = gt.getLoadingDialog();
        confirmationDialog = gt.getConfirmationDialog();

        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        tvEmail = view.findViewById(R.id.tvEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etPasswordConfirm = view.findViewById(R.id.etPasswordConfirm);
        etPhone = view.findViewById(R.id.etPhone);
        etCity = view.findViewById(R.id.etCity);
        btnMale = view.findViewById(R.id.btnMale);
        btnFemale = view.findViewById(R.id.btnFemale);
        etBirthDate = view.findViewById(R.id.etBirthDate);
        btnUpdateProfile = view.findViewById(R.id.btnUpdateProfile);
        btnDeleteAccount = view.findViewById(R.id.btnDeleteAccount);

        calendar = Calendar.getInstance();
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        formatterForDB = new SimpleDateFormat("yyyy-MM-dd");

        setDatePicker();
        sexOpt();
        setDataUser();

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initUpdateProfile();
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDeleteAccount();
            }
        });

        return view;
    }

    //Option for changing sex, by clicking the male or female button
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

    //If the button clicked the background and text color wil be changed
    public void btnSexChecked(Button button) {
        button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorPrimary));
        button.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
    }

    //If the other button clicked by the function above,
    //the background and text color wil be changed as well
    public void btnSexUnchecked(Button button) {
        button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBackground));
        button.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

    }

    private void setDatePicker() {

        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(
                        year,
                        monthOfYear,
                        dayOfMonth);

                birthDate = formatterForDB.format(calendar.getTime());
                etBirthDate.setText(formatter.format(calendar.getTime()));

            }
        };
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }

    //Setting out confirmation dialog to be used in update profile and delete account
    public ConfirmationDialog setConfirmationDialog(String title, String yLabel, String nLabel) {
        confirmationDialog = new ConfirmationDialog(context);
        confirmationDialog.setTitle(title);
        confirmationDialog.setYLabel(yLabel);
        confirmationDialog.setNLabel(nLabel);
        confirmationDialog.show();

        return confirmationDialog;
    }

    //Setting data user from session
    public void setDataUser() {

        etFirstName.setText(user.getFirst_name());
        etLastName.setText(user.getLast_name());
        tvEmail.setText(user.getEmail());
        etPhone.setText(user.getNo_telp());
        sexO = user.getSex();
        etCity.setText(user.getCity());

        if (sexO == 1) {
            btnSexChecked(btnMale);
            btnSexUnchecked(btnFemale);
        } else {
            btnSexChecked(btnFemale);
            btnSexUnchecked(btnMale);
        }

        birthDate = user.getBirth_date();
        String[] bDate = user.getBirth_date().split("-");
        etBirthDate.setText(bDate[2] + "-" + bDate[1] + "-" + bDate[0]);


    }

    public void initUpdateProfile() {
        firstName = etFirstName.getText().toString().trim().toLowerCase();
        lastName = etLastName.getText().toString().trim().toLowerCase();
        email = tvEmail.getText().toString().trim().toLowerCase();
        password = etPassword.getText().toString().trim().toLowerCase();
        passwordConfirm = etPasswordConfirm.getText().toString().trim().toLowerCase();
        phone = etPhone.getText().toString().trim().toLowerCase();
        city = etCity.getText().toString().trim().toLowerCase();

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
        if (!password.isEmpty()) {
            if (password.length() < 8) {
                etPassword.setError("Minimal 8 karakter");
                etPassword.requestFocus();
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
        }

        if (birthDate.isEmpty()) {
            etBirthDate.setError("Tidak boleh kosong");
            etBirthDate.requestFocus();
            return;
        }

        setConfirmationDialog(
                "Apakah anda yakin untuk mengubah informasi anda?",
                "UBAH",
                "BATAL"
        );
        confirmationDialog.getBtnYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                execUpdateProfile();
            }
        });
    }

    public void execUpdateProfile() {
        Call<UserResponse> call = MainClient.getInstance().getApi().updateUser(
                user.getId_user(),
                email,
                password,
                firstName,
                lastName,
                phone,
                sexO,
                birthDate,
                city
        );
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    final UserResponse result = response.body();
                    if (result.isOk()) {
                        //getting button must be declared after .show()
                        //otherwise it's going to crash the app
                        session.saveUser(result.getData().get(0));
                        getActivity().onBackPressed();
                        confirmationDialog.dismiss();
                        loadingDialog.dismiss();
                        Toast.makeText(context, result.getMessage() + "", Toast.LENGTH_SHORT).show();

                    }
//                    Toast.makeText(context, result.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initDeleteAccount() {
        setConfirmationDialog(
                "Akun tidak akan bisa dikembalikan setelah aksi ini,\nApakah anda yakin untuk menghapus akun ini?",
                "HAPUS",
                "BATAL"
        );
        confirmationDialog.getBtnYes().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                execDeleteAccount();

            }
        });
    }

    public void execDeleteAccount() {
        Call<DefaultResponse> call = MainClient.getInstance().getApi().deleteUser(user.getId_user());
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful()) {
                    final DefaultResponse result = response.body();
                    if (result.isOk()) {
                        googleAuthClient.gsi().signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                //On Succesfull signout we navigate the user back to LoginActivity
                                session.clear();
                                Log.w("Google Logout", "NICE");
                                startActivity(new Intent(context, LoginActivity.class));
                                getActivity().finish();
                                confirmationDialog.dismiss();
                                loadingDialog.dismiss();
                                Toast.makeText(context, result.getMessage() + "", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    Toast.makeText(context, response.message() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });

    }


}

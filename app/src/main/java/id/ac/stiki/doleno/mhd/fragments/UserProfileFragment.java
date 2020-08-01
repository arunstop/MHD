package id.ac.stiki.doleno.mhd.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import id.ac.stiki.doleno.mhd.R;
import id.ac.stiki.doleno.mhd.activities.LoginActivity;
import id.ac.stiki.doleno.mhd.api.GoogleAuthClient;
import id.ac.stiki.doleno.mhd.api.MainClient;
import id.ac.stiki.doleno.mhd.models.DefaultResponse;
import id.ac.stiki.doleno.mhd.models.ImgurResponse;
import id.ac.stiki.doleno.mhd.models.User;
import id.ac.stiki.doleno.mhd.models.UserResponse;
import id.ac.stiki.doleno.mhd.tools.GlobalTools;
import id.ac.stiki.doleno.mhd.tools.Session;
import id.ac.stiki.doleno.mhd.ui.ConfirmationDialog;
import id.ac.stiki.doleno.mhd.ui.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

//This is the main prototype of fragmenting

public class UserProfileFragment extends Fragment {

    //image properties
    private static final int PERMISSION_REQUEST_CODE = 1;
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
    private ImageView ivProfilePhoto;
    private EditText etFirstName, etLastName, etPassword, etPhone, etPasswordConfirm, etBirthDate, etCity;
    private Button btnMale, btnFemale, btnUpdateProfile, btnDeleteAccount, btnEditPhoto;
    private String firstName, lastName, email, password, phone, passwordConfirm, birthDate = "", city;
    private int sexO;
    private Calendar calendar;
    //image properties
    private Bitmap bSelectedImg;
    private String NEW_IMG_URL = "";
    //IMGUR CLIENT KEY
    private String CLIENT_ID = "70bd9800f118bdf";
    private String AUTHORIZATION_HEADER = "Client-ID " + CLIENT_ID;
    private MultipartBody.Part IMG_BODY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        //getting inflater from the parameter is important to preventing a crash caused by switching between fragment too fast
        this.inflater = inflater;

        gt = new GlobalTools(this);
        context = gt.getContext();
        session = gt.getSession();
        user = gt.getUser();
        flContentBnv = gt.getContentBnv();
        flContent = gt.getContent();
        googleAuthClient = gt.getGoogleAuthClient();
        loadingDialog = gt.getLoadingDialog();
        confirmationDialog = gt.getConfirmationDialog();

        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);
        btnEditPhoto = view.findViewById(R.id.btnEditPhoto);
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


        //checking os version
        checkVersion();

        if (!user.getPhoto_url().isEmpty()) {
//            Glide.with(context).load(user.getPhoto_url()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(ivProfilePhoto);
            gt.loadImgUrl(user.getPhoto_url(), ivProfilePhoto);
        }

        btnEditPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browsePictures();
            }
        });

        calendar = Calendar.getInstance();

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
        Log.d("imgur", "KEKW");

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
                //Format from calendar.getTime() example:
                //Fri Jun 26 08:06:50 GMT+07:00 2020
                birthDate = gt.formatDate("EEE MMM dd HH:mm:ss z yyyy", "yyyy-MM-dd", calendar.getTime().toString());
                String strDateForEt = gt.formatDate("yyyy-MM-dd", "dd-MM-yyyy", birthDate);
                etBirthDate.setText(strDateForEt);

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

    public void checkVersion() {
        //if OS version is below Marshmellow
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
//                Toast.makeText(context, "GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                requestPermission(); // Code for permission
            }
        } else {

            // Code for Below 23 API Oriented Device
            // Do next code
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show();
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                    browsePictures();
                } else {
                    Toast.makeText(context, "Permission not granted, turn it on manually in settings", Toast.LENGTH_SHORT).show();
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void browsePictures() {
        Intent iBrows = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(iBrows, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK && requestCode == 100) {
            try {
                Uri pathImg = data.getData();
                //get image
                bSelectedImg = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pathImg);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bSelectedImg.compress(Bitmap.CompressFormat.JPEG, 0, out);
                Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                ivProfilePhoto.setImageBitmap(decoded);
                //get filename/path
                File fileImg = new File(getRealPathFromURI(pathImg));
                //creating request body for file
                RequestBody finalImage = RequestBody.create(MediaType.parse("image/*"), fileImg);
                //sending image as body to imgur API
                IMG_BODY = MultipartBody.Part.createFormData("image", fileImg.getName(), finalImage);

            } catch (IOException e) {
                Toast.makeText(context, e.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        } else {
//            Toast.makeText(context, "KEKEKEKE", Toast.LENGTH_SHORT).show();
        }

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
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
                if (bSelectedImg == null) {
                    execUpdateProfile();
                } else {
                    Call<ImgurResponse> callUpload = gt.callImgurApi().uploadImg(AUTHORIZATION_HEADER, IMG_BODY);
                    callUpload.enqueue(new Callback<ImgurResponse>() {
                        @Override
                        public void onResponse(Call<ImgurResponse> call, Response<ImgurResponse> response) {

//                        Log.e("imgur", t.getMessage());
//                        try {
//                            String str = response.body().getString("status");
//                            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Toast.makeText(context, response.+"", Toast.LENGTH_SHORT).show();
                            NEW_IMG_URL = response.body().getData().getLink();
                            execUpdateProfile();
//                        etFirstName.setText(NEW_IMG_URL);

                        }

                        @Override
                        public void onFailure(Call<ImgurResponse> call, Throwable t) {
                            Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
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
                city,
                (NEW_IMG_URL == null ? user.getPhoto_url() : NEW_IMG_URL)

        );
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    final UserResponse result = response.body();
                    if (result.isOk()) {

                        //saving session
                        session.saveUser(result.getData().get(0));
                        //go back
                        gt.popFragment();

                        //dissmiss the dialogs
                        confirmationDialog.dismiss();
                        loadingDialog.dismiss();
                        Toast.makeText(context, result.getMessage() + "", Toast.LENGTH_SHORT).show();

                    }
//                    Toast.makeText(context, result.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                gt.showSnackbar("Terjadi kesalahan koneksi.", "RETRY", null).show();
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
                gt.showSnackbar("Terjadi kesalahan koneksi.", "RETRY", null).show();
                //Toast.makeText(context, t.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

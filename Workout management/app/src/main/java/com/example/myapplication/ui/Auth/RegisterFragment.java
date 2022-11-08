package com.example.myapplication.ui.Auth;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterFragment extends Fragment {

    EditText inputEmail;
    EditText inputName;
    EditText inputPwd;
    EditText inputRePwd;
    TextView inputDob;
    Button register_btn;
    TextView login_link;
    Calendar calendar;

    ProgressDialog progressDialog;

    FirebaseAuth auth;
    FirebaseFirestore fStore;

    String userId;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        inputEmail      = view.findViewById(R.id.ET_email);
        inputName       = view.findViewById(R.id.ET_name);
        inputPwd        = view.findViewById(R.id.ET_password);
        inputRePwd      = view.findViewById(R.id.ET_rePassword);
        inputDob        = view.findViewById(R.id.ET_dob);

        register_btn    = view.findViewById(R.id.register_btn);
        login_link      = view.findViewById(R.id.login_link);

        progressDialog  = new ProgressDialog(getActivity());

        inputDob.setOnClickListener(this::onClick);

        register_btn.setOnClickListener(this::onClick);

        login_link.setOnClickListener( view1 -> getParentFragmentManager().beginTransaction().replace(R.id.auth_content, new LoginFragment()).commit());
    }

    private void registerUser(String email,String pwd, String name, String dob) {
        auth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                Map<String, Object> userData = new HashMap<>();
                userData.put("Name", name);
                userData.put("email", email);
                userData.put("dob", dob);

                DocumentReference df = fStore.collection("users").document(userId);

                df.set(userData).addOnCompleteListener(task1 -> {
                    if (task1.isComplete()) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed to store user details, please update in 'My profile'", Toast.LENGTH_SHORT).show();
                    }
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                });

            } else {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onClick(View view) {

        if(view == inputDob){
            calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            @SuppressLint("SetTextI18n")
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(),
                    (datePicker, i, i1, i2) -> inputDob.setText(i2 + "/" + (i1 + 1) + "/" + i), year, month, day);
            datePickerDialog.show();
        }
        else if (view == register_btn)
        {
            final String email = inputEmail.getText().toString();
            final String pwd = inputPwd.getText().toString();
            final String rePwd = inputRePwd.getText().toString();
            final String name = inputName.getText().toString();
            final String dob = inputDob.getText().toString();

            if (email.isEmpty()) {
                inputEmail.setError("Field is empty");
                inputEmail.requestFocus();
            } else if (pwd.isEmpty()) {
                inputPwd.setError("Field is empty");
                inputPwd.requestFocus();
            } else if (pwd.length() < 8) {
                inputPwd.setError("Passphrase should contain at least 8 characters");
                inputPwd.requestFocus();
            } else if (rePwd.isEmpty()) {
                inputRePwd.setError("Confirm your password");
                inputRePwd.requestFocus();
            } else if (!pwd.equals(rePwd)) {
                inputRePwd.setError("Password does not match");
                inputRePwd.requestFocus();
            } else {
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Registration in progress");
                progressDialog.show();
                registerUser(email, pwd, name, dob);
            }
        }
    }
}
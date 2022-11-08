package com.example.myapplication.ui.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Objects;

public class LoginFragment extends Fragment {

    TextInputLayout emailLayout;
    TextInputLayout passwordLayout;
    EditText inputEmail;
    EditText inputPwd;
    MaterialButton login_btn;
    MaterialButton google_btn;

    ProgressDialog pd;

    FirebaseAuth auth;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    MaterialButton registerBtn;

    public LoginFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailLayout =  view.findViewById(R.id.email_input_layout);
        passwordLayout = view.findViewById(R.id.password_input_layout);
        registerBtn = view.findViewById(R.id.register_link);
        google_btn = view.findViewById(R.id.google_btn_login);

        emailLayout.setHelperTextEnabled(false);
        passwordLayout.setHelperTextEnabled(false);

        registerBtn.setOnClickListener(view1 -> getParentFragmentManager().beginTransaction().replace(R.id.auth_content, new RegisterFragment()).commit());

        login_btn = view.findViewById(R.id.login_btn);
        inputEmail = view.findViewById(R.id.ET_login_email);
        inputPwd = view.findViewById(R.id.ET_login_password);
        pd = new ProgressDialog(getActivity());

        auth = FirebaseAuth.getInstance();

        // google auth
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);

        google_btn.setOnClickListener(view1 -> {
            signInUsingGoogle();
        });

        // email auth
        login_btn.setOnClickListener(view2 -> {
            final String email = inputEmail.getText().toString();
            final String pwd = inputPwd.getText().toString();

            emailLayout.setErrorEnabled(false);
            passwordLayout.setErrorEnabled(false);

            if(email.isEmpty() && pwd.isEmpty()) {
                emailLayout.setError("Required");
                passwordLayout.setError("Required");
            }
            else if(email.isEmpty()) {
                emailLayout.setError("Required");
                inputEmail.requestFocus();
            } else if(pwd.isEmpty()) {
                passwordLayout.setError("Required");
                inputPwd.requestFocus();
            } else {
                pd.setTitle("Please Wait");
                pd.setMessage("Logging in as " + email);
                pd.show();

                auth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        pd.dismiss();
                        redirectToMainActivity("e_login");
                        Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                    } else {
                        pd.dismiss();
                         try {
                             throw Objects.requireNonNull(task.getException());
                         } catch (FirebaseAuthEmailException e) {
                             emailLayout.setError("Invalid e-mail");
                             inputEmail.requestFocus();
                         } catch (FirebaseAuthInvalidUserException e) {
                             emailLayout.setError("Invalid user");
                             inputEmail.requestFocus();
                         } catch (FirebaseAuthInvalidCredentialsException e) {
                             Toast.makeText(getActivity(), "One or more credentials entered are invalid", Toast.LENGTH_LONG).show();
                         } catch (Exception e) {
                             Toast.makeText(getActivity(), "Authorization failed", Toast.LENGTH_LONG).show();
                         }
                    }
                });
            }
        });
    }

    private void signInUsingGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                redirectToMainActivity("g_login");
            } catch (ApiException e) {
               Toast.makeText(getActivity(), "Google sign-in failed", Toast.LENGTH_SHORT ).show();
            }
        }

    }

    private void redirectToMainActivity(String login_type) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("login_type", login_type);
        startActivity(intent);
    }


}
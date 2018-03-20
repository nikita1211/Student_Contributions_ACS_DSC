package com.example.himanshupalve.pictcanteen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class SignupActivity extends AppCompatActivity {

    EditText mNewEmail,mNewPassword;
    Button mregButton;
    private FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        sharedPreferences=getDefaultSharedPreferences(this);
        auth=FirebaseAuth.getInstance();
        mNewEmail=findViewById(R.id.NewEmail);
        mNewPassword=findViewById(R.id.NewPassword);
        mregButton=findViewById(R.id.btn_register);
        mregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }
    private void signup()
    {
        if(!validate())
        {
            Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();
            return;
        }
        final String email=mNewEmail.getText().toString().trim();
        final String password=mNewPassword.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(getBaseContext(),"Successful registration",Toast.LENGTH_LONG).show();
                    Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                    intent.putExtra("Email",email);
                    intent.putExtra("password",password);
                    setResult(RESULT_OK,intent);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("Email",email);
                    editor.putString("Password",password);
                    editor.apply();
                    finish();
                }
                else
                {
                    Toast.makeText(getBaseContext(),"unsuccessful registration",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean validate()
    {
        String email=mNewEmail.getText().toString().trim();
        String password=mNewPassword.getText().toString().trim();
        boolean valid=true;
        if(email.length()==0||!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mNewEmail.setError("enter a valid email address");
            valid = false;
        }
        else
        {
            mNewEmail.setError(null);
        }
        if(password.length()==0)
        {
            mNewPassword.setError("between 4 and 10 alphanumeric characters");
            valid =false;
        }
        else
        {
            mNewPassword.setError(null);
        }
        return valid;
    }
}

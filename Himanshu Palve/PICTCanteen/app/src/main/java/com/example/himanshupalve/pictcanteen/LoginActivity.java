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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class LoginActivity extends AppCompatActivity {

    TextView signupLink;
    EditText mEmaileText;
    EditText mPasswordeText;
    Button mLoginButton;
    FirebaseAuth auth;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences=getDefaultSharedPreferences(this);
        auth=FirebaseAuth.getInstance();
        mLoginButton=findViewById(R.id.btn_login);
        signupLink=findViewById(R.id.link_signup);
        mEmaileText=findViewById(R.id.email);
        mPasswordeText=findViewById(R.id.password);
        String s=" ";
        mEmaileText.setText(sharedPreferences.getString("Email"," "));
        mPasswordeText.setText(sharedPreferences.getString("Password"," "));
//        if(mEmaileText.getText().toString()!=s&&mPasswordeText.getText().toString()!=s)
//            finish();
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
                startActivityForResult(intent,22);
//                finish();
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login()
    {
        if(!validate())
        {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
            return;
        }
        final String email=mEmaileText.getText().toString().trim();
        final String password=mPasswordeText.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        mLoginButton.setEnabled(false);
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mLoginButton.setEnabled(true);
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("Email",email);
                    editor.putString("Password",password);
                    editor.apply();
                    finish();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 22) {
            if (resultCode == RESULT_OK) {
                Bundle extras=data.getExtras();
//                mEmaileText.setText(extras.getString("Email"));
//                mPasswordeText.setText(extras.getString("password"));
                mEmaileText.setText(sharedPreferences.getString("Email",""));
                mPasswordeText.setText(sharedPreferences.getString("Password",""));
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
//                this.finish();
            }
        }
    }
    private boolean validate()
    {
        String email=mEmaileText.getText().toString().trim();
        String password=mPasswordeText.getText().toString().trim();
        boolean valid=true;
        if(email.length()==0||!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmaileText.setError("enter a valid email address");
            valid = false;
        }
        else
        {
            mEmaileText.setError(null);
        }
        if(password.length()==0)
        {
            mPasswordeText.setError("between 4 and 10 alphanumeric characters");
            valid =false;
        }
        else
        {
            mPasswordeText.setError(null);
        }
        return valid;
    }
}

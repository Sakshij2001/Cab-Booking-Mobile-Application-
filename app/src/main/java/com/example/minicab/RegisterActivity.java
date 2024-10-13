package com.example.minicab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.emulators.EmulatedServiceSettings;

public class RegisterActivity extends AppCompatActivity {

    Button btn_signup;
    EditText edtEmail,edtPassword,edtfname,edtphone,edtuname;
    String memail,mpass,mfname,mphone,muname,userId;
    final String TAG=getClass().getName();

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtEmail=findViewById(R.id.email);
        edtPassword=findViewById(R.id.password);
        edtfname=findViewById(R.id.fullname);
        edtphone=findViewById(R.id.phonenumber);
        edtuname=findViewById(R.id.username);
        btn_signup=findViewById(R.id.button_signUp);

        mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference dbRef=database.getReference("users");

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                memail=edtEmail.getText().toString();
                mpass=edtPassword.getText().toString();
                mfname=edtfname.getText().toString().trim();
                mphone=edtphone.getText().toString().trim();
                muname=edtuname.getText().toString().trim();

                if(!validateFname() || !validateEmail()  || !validateUname()|| !validatePass() || !validatePhno() ){
                    return;
                }

                register(edtEmail.getText().toString(),edtPassword.getText().toString());

                UserHelperClass helperClass=new UserHelperClass(mfname,muname,memail,mphone,mpass);
                userId=mAuth.getCurrentUser().getUid();
                dbRef.child(userId).setValue(helperClass);

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            //user is already sign in ,do what you want to do
        }
    }
    //for validate the fields
    private Boolean validateEmail(){
        if(memail.isEmpty()){
            edtEmail.setError("Field cannot be empty");
            return false;
        }
        else{
            edtEmail.setError(null);
            return true;
        }
    }
    private Boolean validatePass(){
        if(mpass.isEmpty()){
            edtPassword.setError("Field cannot be empty");
            return false;
        }
        else{
            edtPassword.setError(null);
            return true;
        }
    }
    private Boolean validateFname(){
        if(mfname.isEmpty()){
            edtfname.setError("Field cannot be empty");
            return false;
        }
        else{
            edtfname.setError(null);
            return true;
        }
    }
    private Boolean validatePhno(){
        if(mphone.isEmpty()){
            edtphone.setError("Field cannot be empty");
            return false;
        }
        else{
            edtphone.setError(null);
            return true;
        }
    }
    private Boolean validateUname(){
        if(muname.isEmpty()){
            edtuname.setError("Field cannot be empty");
            return false;
        }
        else{
            edtuname.setError(null);
            return true;
        }
    }
    void register(String email,String password)
    {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this,"User Successfully created",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, Dashboard.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",  Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
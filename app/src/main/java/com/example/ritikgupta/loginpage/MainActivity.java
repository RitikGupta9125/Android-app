package com.example.ritikgupta.loginpage;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

   private EditText Name;
   private EditText Password;
   private TextView Info;
   private Button Login;
   private int counter=3;
   private TextView userregistration;
   private FirebaseAuth firebaseAuth;
   private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etUserPassword);
        Info =  (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
        userregistration=(TextView)findViewById(R.id.tvRegister);

        Info.setText("No. of attempts remaining:3");
        progressDialog=new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();

        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user!=null)
        {
         finish();
         startActivity(new Intent(MainActivity.this,SecondActivity.class));
        }

       Login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               validate(Name.getText().toString(),Password.getText().toString());

           }
       });
       userregistration.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
           }
       });

    }
    private void validate(String userName , String userPassword){

       progressDialog.setMessage("Welcome to new wORLd");
       progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
          if(task.isSuccessful()){
              progressDialog.dismiss();
              Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
              startActivity(new Intent(MainActivity.this,SecondActivity.class));

          }else{
              Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
             counter--;
             Info.setText("No. of Attempts remaining"+ counter );
              progressDialog.dismiss();
             if(counter==0)
             {
                 Login.setEnabled(false);
             }
          }
         }
     });

    }


}

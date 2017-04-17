package acm.event.code2createregister.Main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import acm.event.code2createregister.R;
import acm.event.code2createregister.RetroAPI.RetroAPI;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.login_username)
    EditText usernameEditText;
    @BindView(R.id.login_password)
    EditText passwordEditText;
    @BindView(R.id.login_username_layout)
    TextInputLayout usernameLayout;
    @BindView(R.id.login_password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.login_root_layout)
    ConstraintLayout loginContainer;

    RetroAPI retroAPI;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        retroAPI = new RetroAPI();
    }

    @OnClick(R.id.login_button)
    public void onUserLogin(View v) {
        register();
    }

    public void register(){
        final String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(username.equals("") || password.equals("")) {
            usernameLayout.setError("Enter valid username");
            passwordLayout.setError("Enter valid password");
            return;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        retroAPI.observableAPIService.register(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Could not connect to server!")
                                .setCancelable(false)
                                .setPositiveButton("RETRY", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        register();
                                    }
                                })
                                .setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {}
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        if(jsonObject.get("success").getAsBoolean()) {
                            progressDialog.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage("Registration successful!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {}
                                    });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            usernameLayout.setError(null);
                            passwordLayout.setError(null);
                        } else {
                            progressDialog.dismiss();
                            usernameLayout.setError("Enter valid username");
                            passwordLayout.setError("Enter valid password");
                            return;
                        }
                    }
                });
    }
}

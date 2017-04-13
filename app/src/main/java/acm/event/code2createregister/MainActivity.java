package acm.event.code2createregister;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_button)
    public void onUserLogin(View v) {
        register();
    }

    public void register(){
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(username.equals("") || password.equals("")) {
            usernameLayout.setError("Enter valid username");
            passwordLayout.setError("Enter valid password");
            return;
        }
    }
}

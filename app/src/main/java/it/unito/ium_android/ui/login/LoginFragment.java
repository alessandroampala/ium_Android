package it.unito.ium_android.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import it.unito.ium_android.R;
import it.unito.ium_android.requests.Requests;


public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private MaterialButton btnLogin;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        btnLogin = root.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((TextInputLayout) root.findViewById(R.id.usernameEditText)).getEditText().getText().toString();
                String password = ((TextInputLayout) root.findViewById(R.id.passwordEditText)).getEditText().getText().toString();
                if (username.equals("") || password.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Username e/o Password sbagliati", Toast.LENGTH_SHORT).show();
                } else {
                    Requests post = new Requests(getActivity(), "login");
                    try {
                        String data = "username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8") + "&action=login";
                        String url = "http://10.0.2.2:8080/ProgettoTWEB_war_exploded/Controller";
                        String method = "POST";
                        post.execute(data, url, method);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return root;
    }
}
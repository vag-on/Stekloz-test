package net.simplifiedlearning.myheroapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.view.View.GONE;

public class login extends AppCompatActivity {

    protected static final int CODE_GET_REQUEST = 1024;
    protected static final int CODE_POST_REQUEST = 1025;

    EditText edit_login, edit_password;
    Button btn_login;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_login = (EditText) findViewById(R.id.login);
        edit_password = (EditText) findViewById(R.id.password);

        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        String login = edit_login.getText().toString().trim();
        String password = edit_password.getText().toString().trim();

        if (TextUtils.isEmpty(login)){
            edit_login.setError("Пожалуйста, введите логин");
            edit_login.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            edit_password.setError("Пожалуйста, введите пароль");
            edit_password.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("login", login);
        params.put("password", password);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_LOGIN, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void suc_login() {
        Intent intent = new Intent(login.this, list.class);
        startActivity(intent);
    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    if (object.getBoolean("suc")){
                        suc_login();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }

}
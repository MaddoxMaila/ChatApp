package c.maddox.maddoxmessenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText TextEmail = findViewById(R.id.email_text);
        final EditText TextPassword = findViewById(R.id.password_text);
        final Button LoginButton = findViewById(R.id.login_button);

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = TextEmail.getText().toString().trim();
                String password = TextPassword.getText().toString().trim();
                Toolbar LoginToolBar = findViewById(R.id.login_toolbar);
                setSupportActionBar(LoginToolBar);

                   if(email.isEmpty() || password.isEmpty()){
                       Toast.makeText(getApplicationContext(),"Make Sure All Input Fields Are Filled",Toast.LENGTH_LONG).show();
                   }else if(email.isEmpty() && password.isEmpty()){
                       Toast.makeText(getApplicationContext(),"Input Both Email And Password",Toast.LENGTH_LONG).show();
                   }else{
                       login(email,password);
                   }
            }
        });
    } //End Of onCreate

    private void login(String email, String password){
        JsonObjectRequest LoginRequest = new JsonObjectRequest(Request.Method.GET, "https://kampuscrush.com/android/login.php?email=" + email + "&password=" + password, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if(response.getBoolean("login")){
                                Toast.makeText(getApplicationContext(),
                                        response.getString("user_handle")+", "+response.getString("message"),
                                        Toast.LENGTH_LONG)
                                        .show();
                                Intent chatIntent = new Intent(getApplicationContext(),ChatActivity.class);
                                chatIntent.putExtra("id",response.getString("user_id"));
                                chatIntent.putExtra("handle",response.getString("user_handle"));
                                chatIntent.putExtra("name",response.getString("user_name"));
                                startActivity(chatIntent);

                            }else{
                                Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        }catch (JSONException je){
                            je.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        mQueue.add(LoginRequest);
    }

} // End Of Class

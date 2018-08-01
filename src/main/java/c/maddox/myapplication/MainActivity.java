package c.maddox.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue mQueue;
    //TextView txt=findViewById(R.id.textView);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton = findViewById(R.id.button);
        final EditText loginEmail =  findViewById(R.id.editText);
        final EditText loginPassword = findViewById(R.id.editText2);

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email=loginEmail.getText().toString().trim();
                String password=loginPassword.getText().toString().trim();
                if(email.isEmpty() && password.isEmpty() ) {
                    Toast.makeText(getApplicationContext(),"Fill In All Fields",Toast.LENGTH_LONG).show();
                }else if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Fill In All Fields",Toast.LENGTH_LONG).show();
                }else{
                    sendData(password,email);
                }
            }
        });
    }
    private void sendData(String e,String p) {

       String url = "http://192.168.43.13/myvyral.com/android/login/log_in.php?enum="+e+"&password="+p;

        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(!response.getBoolean("error")){
                                Toast.makeText(getApplicationContext(),response.getString("id") + " ," + response.getString("username") +"\t Is Logged In...",Toast.LENGTH_LONG).show();
                                Intent intent =new Intent(getApplicationContext(),home.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"Login Credentials Incorrect",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                },
        new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
            }
        });
        mQueue.add(request);
    }
}

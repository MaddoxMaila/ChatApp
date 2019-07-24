package c.maddox.maddoxmessenger;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

public class MEssageActivity extends AppCompatActivity {

    private String UserChatId, UserChatName, UserChatHandle;
    private String LoggedUserId;
    RequestQueue mQueue;
    private String TypedMessage;
    private ActionBar MessageActionBar;
    private ArrayList<MessagesDataModel> MessagesDataModelArray;
    MessagesListAdapter MessagesAdapter;
    ListView MessagesView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Toolbar MessageToolBar = (Toolbar) findViewById(R.id.messages_toolbar);
        setSupportActionBar(MessageToolBar);

        MessageToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"You Clicked Toolbar",Toast.LENGTH_LONG).show();
            }
        });


        final Button SendMessage = findViewById(R.id.message_send);
        final EditText TextEditor = findViewById(R.id.message_box);
        Intent intent = getIntent();
        UserChatId = intent.getStringExtra("user_id");
        UserChatName = intent.getStringExtra("user_name");
        UserChatHandle = intent.getStringExtra("user_handle");
        LoggedUserId = intent.getStringExtra("user_logged_id");
        MessageActionBar = getSupportActionBar();
        setTitle("@" + UserChatHandle);
        mQueue = VolleySingleton.getInstance(this).getRequestQueue();

        TextEditor.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN){

                    SendMessage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.send_icon,0,0,0);
                }
                return false;
            }

        });

        SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TypedMessage = TextEditor.getText().toString().trim();
                  if(TypedMessage.isEmpty()){
                      Toast.makeText(getApplicationContext(),"Type A Message First",Toast.LENGTH_LONG).show();
                  }else{

                      // Call The sendMessages() To Actually Send The Typed Messages !!
                      sendMessages();
                      TextEditor.setText("");
                  }
            }
        });


        MessagesView = findViewById(R.id.message_lv);

        // Call The showMessages() To Actually Show Messages !!
        showMessages();

    } // End Of onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater MenuInflater = getMenuInflater();
        MenuInflater.inflate(R.menu.messages_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem Item){

        switch (Item.getItemId()){

            case R.id.menu_item_one :
                return true;
            case R.id.menu_item_two :
                showMessages();
                Toast.makeText(getApplicationContext(),"Refreshing...",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_item_three :
                return true;
            default :
                return super.onOptionsItemSelected(Item);
        }
    }
    // Method For Sending Messages!!
    private void sendMessages(){

        String url = "https://kampuscrush.com/android/list_chat.php";
        StringRequest MessageRequest = new StringRequest(Request.Method.POST,
                 url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {

                        try {
                            JSONObject response = new JSONObject(resp);
                            if(response.getBoolean("chats")){

                                Toast.makeText(getApplicationContext(),response.getString("message"),Toast.LENGTH_LONG).show();

                                showMessages();

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
                }){@Override
        public Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("id",LoggedUserId);
            params.put("uid",UserChatId);
            params.put("cxt","3");
            params.put("message",TypedMessage);
              return params;
           }
        };

        mQueue.add(MessageRequest);

    }

    // Method For Showing Sent Messages
    private void showMessages(){

        // Define URL
        String url = "https://kampuscrush.com/android/list_chat.php";

        // Define And Handle The Request Object
        StringRequest showMessageRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        try{
                            JSONObject response = new JSONObject(resp);

                            // Checks If There Are Messages Returned From The API
                              if(response.getBoolean("chats")){

                                  // Initialize The Messages Array List Object
                                  MessagesDataModelArray = new ArrayList<>();

                                  JSONArray MessagesJsonArray = response.getJSONArray("messages");

                                   // Loop Through The Messages While Getting The Json Objects By Array Index
                                   for (int i = 0; i < MessagesJsonArray.length(); i++ ){

                                       // Create A MessagesDataModel Object
                                       MessagesDataModel MessageData = new MessagesDataModel();

                                       // Create A Json Object For Objects In Json Array
                                       JSONObject MessageObj = MessagesJsonArray.getJSONObject(i);

                                       // Inflate The Messages Data Model
                                       MessageData.setUserOneId(Integer.parseInt(MessageObj.getString("id_one")));
                                       MessageData.setUserTwoId(Integer.parseInt(MessageObj.getString("id_two")));
                                         if(MessageObj.getString("seen").equals("0")){
                                             MessageData.setSeen("");
                                         }else{
                                             MessageData.setSeen("seen");
                                         }
                                       MessageData.setUrl(MessageObj.getString("url"));
                                       MessageData.setDate(MessageObj.getString("date"));
                                       MessageData.setTime(MessageObj.getString("time"));
                                       MessageData.setMessage(MessageObj.getString("message"));

                                       // Inflate The Message Data Model Array List

                                       MessagesDataModelArray.add(MessageData);

                                   }
                                   messagesListView();
                              }else{
                                  // No Messages Returned From API
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
                }
        ){
            @Override
            public Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("id",LoggedUserId);
                params.put("uid",UserChatId);
                params.put("cxt","2");
                return params;
            }
        };
        mQueue.add(showMessageRequest);
    }

    private void messagesListView(){

        MessagesAdapter = new MessagesListAdapter(this, MessagesDataModelArray, Integer.parseInt(this.LoggedUserId), Integer.parseInt(this.UserChatId));
        MessagesView.setAdapter(MessagesAdapter);

    }
}// End Of Class

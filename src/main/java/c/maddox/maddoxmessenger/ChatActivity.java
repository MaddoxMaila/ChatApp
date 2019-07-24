package c.maddox.maddoxmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private TextView mTextMessage;
    RequestQueue mQueue;
    ListView listView;
    ListAdapter listAdapter;
    private String user_name, user_handle,user_id;
    private ArrayList<UserDataModel> UserDataModelArrayList;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);

                    loadChats();
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar ChatsToolBar = findViewById(R.id.chats_toolbar);
        setSupportActionBar(ChatsToolBar);
        setTitle("Chats");
        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        mTextMessage = (TextView) findViewById(R.id.message);
        Intent intent = getIntent();
        user_handle = intent.getStringExtra("handle");
        user_id = intent.getStringExtra("id");
        user_name = intent.getStringExtra("name");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listView = findViewById(R.id.lv);
        loadChats();
    } // End Of onCreate()

    private void loadChats(){

        JsonObjectRequest chatsRequest = new JsonObjectRequest(Request.Method.GET, "https://kampuscrush.com/android/list_chat.php?id=" + user_id+"&cxt=1", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getBoolean("chats")){
                                // Means There Are Chats In Response
                                Toast.makeText(getApplicationContext(),"There Are Conversations!!",Toast.LENGTH_LONG).show();
                                mTextMessage.setText("");

                                UserDataModelArrayList = new ArrayList<>();

                                JSONArray JsonDataArray = response.getJSONArray("chat");

                                for(int i = 0; i < JsonDataArray.length(); i++){
                                   UserDataModel UserData = new UserDataModel();
                                   JSONObject JsonDataObj = JsonDataArray.getJSONObject(i);

                                   UserData.setUserId(Integer.parseInt(JsonDataObj.getString("user_id")));
                                   UserData.setUserName(JsonDataObj.getString("user_name"));
                                   UserData.setUserHandle(JsonDataObj.getString("user_handle"));
                                   UserData.setSeen(Integer.parseInt(JsonDataObj.getString("seen")));
                                   UserData.setImageUrl(JsonDataObj.getString("img_url"));
                                   UserData.setMessage(JsonDataObj.getString("message"));
                                   UserData.setChatId(Integer.parseInt(JsonDataObj.getString("chat_id")));
                                   UserData.setImageId(Integer.parseInt(JsonDataObj.getString("img_id")));
                                   UserData.setCount(JsonDataObj.getString("count"));


                                   UserDataModelArrayList.add(UserData);
                                }
                                listView();
                            }else{
                                //Means There Are No Chats In Response
                                mTextMessage.setText("Start A Conversation, Your Chats Will Appear Here");
                            }
                        } catch ( JSONException je){
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
         mQueue.add(chatsRequest);
    }

    private void listView(){
        listAdapter = new c.maddox.maddoxmessenger.ListAdapter(this,UserDataModelArrayList, Integer.parseInt(user_id));
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent messagesIntent = new Intent(view.getContext(),MEssageActivity.class);
                messagesIntent.putExtra("user_name",UserDataModelArrayList.get(position).getUserName());
                messagesIntent.putExtra("user_handle",UserDataModelArrayList.get(position).getUserHandle());
                messagesIntent.putExtra("user_id",Integer.toString(UserDataModelArrayList.get(position).getUserId()));
               // messagesIntent.putExtra("user_url",UserDataModelArrayList.get(position).getImageUrl());
                // messagesIntent.putExtra("user_url",))
                messagesIntent.putExtra("user_logged_id",user_id);
                startActivity(messagesIntent);
            }
        });
    }

}

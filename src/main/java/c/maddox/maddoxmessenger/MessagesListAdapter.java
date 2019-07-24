package c.maddox.maddoxmessenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessagesListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<MessagesDataModel> MessagesDataModelArrayList;
    private int LoggedId, ChatId;

    public MessagesListAdapter(Context context, ArrayList<MessagesDataModel> messagesDataModelArrayList, int loggedId, int chatId){
        this.context = context;
        this.MessagesDataModelArrayList = messagesDataModelArrayList;
        this.LoggedId = loggedId;
        this.ChatId = chatId;

    }
    @Override
    public int getCount() {
        return MessagesDataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return MessagesDataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessagesViewHolder mHolder;

        if(convertView == null){
            mHolder = new MessagesViewHolder();
            LayoutInflater inflater =  (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if(this.MessagesDataModelArrayList.get(position).getUserOneId() == this.LoggedId){

                convertView = inflater.inflate(R.layout.right_message,null,true);
                 mHolder.ImagePicture = convertView.findViewById(R.id.right_message_pic);
                mHolder.TextMessage = convertView.findViewById(R.id.right_sent_message);
                mHolder.TextDateSeen = convertView.findViewById(R.id.right_date_seen);

            }else{

                convertView = inflater.inflate(R.layout.left_message,null, true);
                mHolder.ImagePicture = convertView.findViewById(R.id.left_message_pic);
                mHolder.TextMessage = convertView.findViewById(R.id.left_sent_message);
                mHolder.TextDateSeen = convertView.findViewById(R.id.left_date_seen);

            }

            convertView.setTag(mHolder);
        }else{

            mHolder = (MessagesViewHolder) convertView.getTag();

        }

        Picasso.with(context).load(MessagesDataModelArrayList.get(position).getUrl())
                .centerCrop()
                .fit()
                .into(mHolder.ImagePicture);
        mHolder.TextMessage.setText(MessagesDataModelArrayList.get(position).getMessage());
        mHolder.TextDateSeen.setText(MessagesDataModelArrayList.get(position).getTime() + " " + MessagesDataModelArrayList.get(position).getSeen());
        return convertView;
    }
}

class MessagesViewHolder{
    protected TextView TextMessage, TextDateSeen;
     ImageView ImagePicture;
}

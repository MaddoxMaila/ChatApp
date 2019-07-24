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

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UserDataModel> UserDataArrayList;
    private Integer Id;
    public ListAdapter(Context context, ArrayList<UserDataModel> UserDataArrayList, Integer id){
        this.context = context;
        this.UserDataArrayList = UserDataArrayList;
        this.Id = id;
    }
    @Override
    public int getCount() {
        return this.UserDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.UserDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater =  (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_layout, null, true);

            holder.Message = convertView.findViewById(R.id.user_message);
            holder.UserHandle = convertView.findViewById(R.id.user_handle);
            //holder.UserName = convertView.findViewById(R.id.user_name);
            holder.Seen = convertView.findViewById(R.id.seen_or_not);
            holder.ProfileImage = convertView.findViewById(R.id.profile_pic);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(UserDataArrayList.get(position).getImageUrl())
                .centerCrop()
                .fit()
                .into(holder.ProfileImage);
        //holder.UserName.setText(UserDataArrayList.get(position).getUserName());
                // Check Who This Message Belongs Too
                // Set The Seen, Message Sent, Message Count Accordingly 
                if( UserDataArrayList.get(position).getUserId() == this.Id){

                    // Message Belongs To The User Logged In
                    if(UserDataArrayList.get(position).getSeen() == 0){

                        // Set Icon To Taking Flight
                        holder.Seen.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.seen_not_icon,0,0,0);
                    }else{

                        // Set Icon To Landing
                        holder.Seen.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.seen_yes_icon,0,0,0);

                    }
                }else{

                    // Set The Count To Notify User How Many Messages Were Sent
                    holder.Seen.setText(UserDataArrayList.get(position).getCount());
                }
        holder.UserHandle.setText(UserDataArrayList.get(position).getUserName() +" @"+ UserDataArrayList.get(position).getUserHandle());
        holder.Message.setText(UserDataArrayList.get(position).getMessage());
        return convertView;
    }
}
 class ViewHolder{
    //protected TextView UserName;
     protected TextView UserHandle, Message, Seen;
    protected ImageView ProfileImage;

 }
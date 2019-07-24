package c.maddox.maddoxmessenger;

public class UserDataModel {

    private String UserName, UserHandle, ImageUrl, Message, Count;
    private Integer UserId;
    private int ImageId, ChatId, Seen;

    public void setCount(String count){
        this.Count = count;
    }
    public String getCount(){
        return this.Count;
    }
    public int getSeen(){
        return this.Seen;
    }
    public int getChatId() {
        return this.ChatId;
    }

    public int getUserId() {
        return this.UserId;
    }

    public String getImageUrl() {
        return this.ImageUrl;
    }

    public String getMessage() {
        return this.Message;
    }

    public int getImageId() {
        return this.ImageId;
    }

    public String getUserHandle() {
        return this.UserHandle;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setSeen(int seen) {
        this.Seen = seen;
    }

    public void setChatId(int chatId) {
        this.ChatId = chatId;
    }

    public void setImageId(int imageId) {
        this.ImageId = imageId;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    public void setMessage(String message) {
        this.Message = message;
    }

    public void setUserHandle(String userHandle) {
        this.UserHandle = userHandle;
    }

    public void setUserId(int userId) {
        this.UserId = userId;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }
}

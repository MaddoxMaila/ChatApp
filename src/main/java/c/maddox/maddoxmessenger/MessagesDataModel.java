package c.maddox.maddoxmessenger;

public class MessagesDataModel {
    private int userOneId, userTwoId;
    private String message, date, time, seen, imgUrl;

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public void setUrl(String url) {
        this.imgUrl = url;
    }

    public String getUrl() {
        return imgUrl;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserOneId() {
        return userOneId;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUserOneId(int userOneId) {
        this.userOneId = userOneId;
    }

    public void setUserTwoId(int userTwoId) {
        this.userTwoId = userTwoId;
    }

    public String getSeen() {
        return seen;
    }

    public int getUserTwoId() {
        return userTwoId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

} // End of Class

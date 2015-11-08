package big.news.yam;

public class NotificationCard {
    String title;
    String description;
    String yesBtnText;
    String noBtnText;
    boolean hasCountDown;
    int countDownTime;

    boolean autoReply;
    String autoReply_content;
    String autoReply_phone;

    boolean autoCalendar;
    String autoCalendar_content;
    long autoCalendar_time;

    String picUrl;


    public NotificationCard(String title,
                            String description,
                            String yesBtnText,
                            String noBtnText,
                            boolean hasCountDown,
                            int countDownTime,
                            boolean autoReply,
                            String autoReply_content,
                            String autoReply_phone,
                            boolean autoCalendar,
                            String autoCalendar_content,
                            long autoCalendar_time,
                            String picUrl){
        this.title = title;
        this.description = description;
        this.yesBtnText = yesBtnText;
        this.noBtnText = noBtnText;
        this.hasCountDown = hasCountDown;
        this.countDownTime = countDownTime;
        this.autoReply = autoReply;
        this.autoReply_content = autoReply_content;
        this.autoReply_phone = autoReply_phone;
        this.autoCalendar = autoCalendar;
        this.autoCalendar_content = autoCalendar_content;
        this.autoCalendar_time = autoCalendar_time;
        this.picUrl = picUrl;
    }
}
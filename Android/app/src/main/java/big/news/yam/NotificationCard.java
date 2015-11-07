package big.news.yam;

public class NotificationCard {
    String title;
    String description;
    String yesBtnText;
    String noBtnText;
    boolean hasCountDown;
    int countDownTime;

    public NotificationCard(String title, String description, String yesBtnText, String noBtnText, boolean hasCountDown, int countDownTime){
        this.title = title;
        this.description = description;
        this.yesBtnText = yesBtnText;
        this.noBtnText = noBtnText;
        this.hasCountDown = hasCountDown;
        this.countDownTime = countDownTime;
    }
}
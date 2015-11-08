package big.news.yam;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {
    private List<NotificationCard>  notificationCards;
    private Context context;
    private CountDownTimer countDownTimer;
    private static String calanderURL = "";
    private static String calanderEventURL = "";
    private static String calanderRemiderURL = "";

    static{
        if(Integer.parseInt(Build.VERSION.SDK) >= 8){
            calanderURL = "content://com.android.calendar/calendars";
            calanderEventURL = "content://com.android.calendar/events";
            calanderRemiderURL = "content://com.android.calendar/reminders";

        }else{
            calanderURL = "content://calendar/calendars";
            calanderEventURL = "content://calendar/events";
            calanderRemiderURL = "content://calendar/reminders";
        }
    }

    public NotificationRecyclerViewAdapter(Context context, List<NotificationCard> notificationCards){
        this.context = context;
        this.notificationCards = notificationCards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        Fresco.initialize(viewGroup.getContext());
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_card,viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i){
        final NotificationCard p = notificationCards.get(i);
        viewHolder.title.setText(p.title);
        viewHolder.description.setText(p.description);
        viewHolder.yes_btn.setText(p.yesBtnText);
        viewHolder.no_btn.setText(p.noBtnText);

        final ViewHolder inner_viewholder = viewHolder;

        Uri imgUrl = Uri.parse(p.picUrl);
        viewHolder.pic.setImageURI(imgUrl);

        if (p.hasCountDown){
            viewHolder.title.setTextColor(context.getResources().getColor(R.color.black));

            countDownTimer = new CountDownTimer((p.countDownTime + 1)*1000,1000){
                @Override
                public void onTick(long l) {
                    inner_viewholder.yes_btn.setText(p.yesBtnText + " (" + l / 1000 + ")");
                }

                @Override
                public void onFinish() {
                    if (p.autoReply && p.autoCalendar) {
                        Toast.makeText(context, "已发送自动回复并记录日程", Toast.LENGTH_SHORT).show();
                        if (!p.autoReply_phone.equals("-1")){
                            android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
                            smsManager.sendTextMessage(p.autoReply_phone, null, p.autoReply_content, null, null);
                        }

                        addCalendar(p.autoCalendar_content, "", p.autoCalendar_time);


                    } else if (p.autoReply) {
                        Toast.makeText(context, "已发送自动回复", Toast.LENGTH_SHORT).show();
                        if (!p.autoReply_phone.equals("-1")){
                            android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
                            smsManager.sendTextMessage(p.autoReply_phone, null, p.autoReply_content, null, null);
                        }
                    } else if (p.autoCalendar) {
                        Toast.makeText(context, "已记录日程", Toast.LENGTH_SHORT).show();
                        addCalendar(p.autoCalendar_content, "", p.autoCalendar_time);
                    }
                    int index = notificationCards.indexOf(p);
                    notificationCards.remove(p);
                    notifyItemRemoved(index);
                }
            }.start();

        } else{
            viewHolder.title.setTextColor(context.getResources().getColor(R.color.red));
        }

        viewHolder.yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p.hasCountDown){
                    countDownTimer.cancel();
                }
                int index = notificationCards.indexOf(p);
                notificationCards.remove(p);
                notifyItemRemoved(index);
            }
        });

        viewHolder.no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (p.hasCountDown){
                    countDownTimer.cancel();
                }
                int index = notificationCards.indexOf(p);
                notificationCards.remove(p);
                notifyItemRemoved(index);
            }
        });

    }

    @Override
    public int getItemCount(){
        return notificationCards == null? 0 : notificationCards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView description;
        public Button yes_btn;
        public Button no_btn;
        public SimpleDraweeView pic;

        public ViewHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
            yes_btn = (Button) v.findViewById(R.id.yes_btn);
            no_btn = (Button) v.findViewById(R.id.no_btn);
            pic = (SimpleDraweeView) v.findViewById(R.id.net_image_view);

        }
    }

    public void addCalendar(String title, String description, long time){
        Intent calIntent = new Intent(Intent.ACTION_INSERT); calIntent.setType("vnd.android.cursor.item/event");
        calIntent.putExtra(CalendarContract.Events.TITLE, title);
        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, time * 1000);
        context.startActivity(calIntent);
    }

}

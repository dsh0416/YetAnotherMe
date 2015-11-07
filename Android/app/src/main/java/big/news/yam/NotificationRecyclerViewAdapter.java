package big.news.yam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {
    private List<NotificationCard>  notificationCards;
    private Context context;

    public NotificationRecyclerViewAdapter(Context context, List<NotificationCard> notificationCards){
        this.context = context;
        this.notificationCards = notificationCards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_card,viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i){
        final NotificationCard p = notificationCards.get(i);
        viewHolder.title.setText(p.title);
        viewHolder.description.setText(p.description);
        viewHolder.yes_btn.setText(p.yesBtnText);
        viewHolder.no_btn.setText(p.noBtnText);
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


        public ViewHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
            yes_btn = (Button) v.findViewById(R.id.yes_btn);
            no_btn = (Button) v.findViewById(R.id.no_btn);
        }
    }

}

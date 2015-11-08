package big.news.yam;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {

    private List<HistoryCard> historyCards;
    private Context context;

    public HistoryRecyclerViewAdapter(Context context, List<HistoryCard> historyCards){
        this.context = context;
        this.historyCards = historyCards;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        Fresco.initialize(viewGroup.getContext());
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_card,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i){
        final HistoryCard p = historyCards.get(i);
        viewHolder.title.setText(p.title);
        viewHolder.description.setText(p.description);
        viewHolder.pic.setImageURI(Uri.parse(p.picUrl));
    }

    @Override
    public int getItemCount()
    {
        // 返回数据总数
        return historyCards == null ? 0 : historyCards.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title, description;
        public SimpleDraweeView pic;

        public ViewHolder(View v){
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
            pic = (SimpleDraweeView) v.findViewById(R.id.net_image_view);
        }
    }
}

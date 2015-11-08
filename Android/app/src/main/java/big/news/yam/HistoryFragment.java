package big.news.yam;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;


public class HistoryFragment extends Fragment {

    public List<HistoryCard> historyCards = new ArrayList<>();
    public RecyclerView recyclerView;
    public SwipeRefreshLayout swipe;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_notification_center, container, false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);
        swipe = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new HistoryRecyclerViewAdapter(getActivity(), historyCards));
        recyclerView.setItemAnimator(new FlipInTopXAnimator());
        recyclerView.getItemAnimator().setAddDuration(250);
        recyclerView.getItemAnimator().setRemoveDuration(250);
        recyclerView.getItemAnimator().setMoveDuration(250);
        recyclerView.getItemAnimator().setChangeDuration(250);

        int prevCount = historyCards.size();
        historyCards.add(new HistoryCard("明天15点一起去看电影吧～？","来自 Sarah [SMS]\n自动回复：抱歉，我那段时间有安排了。","http://api.itimepost.com:7777/img/sarah.jpg"));
        historyCards.add(new HistoryCard("明晚 6 点一起去吃饭?","来自 Chancellor Yu [SMS]\n自动回复：好的，不见不散。","http://api.itimepost.com:7777/img/ylz.jpg"));
        historyCards.add(new HistoryCard("HackShanghai 成功举办！","来自 News [Public Info]\nHackShanghai成功举办！分享这份喜悦到您的好友圈！","http://api.itimepost.com:7777/img/default.jpg"));
        historyCards.add(new HistoryCard("我想我们没必要再联系了。你是一个好人。","来自 Girl Friend [Contact]\n重要且紧急的事情，请主动处理！","http://api.itimepost.com:7777/img/feng.jpg"));
        historyCards.add(new HistoryCard("上海今日PM2.5为200","来自 Weather [Public Info]\n您的家用空气净化器将于您到家10分钟前自动打开","http://api.itimepost.com:7777/img/haze.png"));
        historyCards.add(new HistoryCard("TFBOY即将在上海举办演唱会","来自 idol [Public Info]\n自动为你订购TFBOY上海演唱会第一排VIP座位，售价18888元。","http://api.itimepost.com:7777/img/tf.jpg"));
        recyclerView.getAdapter().notifyItemRangeInserted(prevCount, historyCards.size());

        return rootview;
    }


}

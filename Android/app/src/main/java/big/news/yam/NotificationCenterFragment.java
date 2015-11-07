package big.news.yam;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NotificationCenterFragment extends Fragment {

    public RecyclerView recyclerView;
    public List<NotificationCard> notificationCards = new ArrayList<>();
    public SwipeRefreshLayout swipe;


    public NotificationCenterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.fragment_notification_center, container, false);

        recyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipe = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
            }
        });

        notificationCards.add(new NotificationCard("title","description","yes","no",false,3));
        recyclerView.setAdapter(new NotificationRecyclerViewAdapter(getActivity(), notificationCards));

        return rootview;

    }


}

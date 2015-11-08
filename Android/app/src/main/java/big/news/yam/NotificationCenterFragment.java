package big.news.yam;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;

public class NotificationCenterFragment extends Fragment {

    public RecyclerView recyclerView;
    public List<NotificationCard> notificationCards = new ArrayList<>();
    public SwipeRefreshLayout swipe;
    public static AsyncHttpClient client = new AsyncHttpClient();

    private long max_id = 0;


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
        recyclerView.setAdapter(new NotificationRecyclerViewAdapter(getActivity(), notificationCards));
        // 设置卡片动画
        recyclerView.setItemAnimator(new FlipInTopXAnimator());
        recyclerView.getItemAnimator().setAddDuration(250);
        recyclerView.getItemAnimator().setRemoveDuration(250);
        recyclerView.getItemAnimator().setMoveDuration(250);
        recyclerView.getItemAnimator().setChangeDuration(250);

        post("+8615704600640", "明晚 6 点一起去吃饭", 5);

        new CountDownTimer(9223372036854775806L,1000){
            @Override
            public void onFinish() {

            }

            @Override
            public void onTick(long l) {
                try {
                    Uri uri = Uri.parse("content://sms/inbox");
                    String[] projection = new String[]{"_id", "address", "person", "body"};
                    Cursor cur = getActivity().getContentResolver().query(uri, projection, null, null, "_id desc");
                    if (cur.moveToFirst()) {
                        int index_id = cur.getColumnIndex("_id");
                        int index_Address = cur.getColumnIndex("address");
                        int index_Person = cur.getColumnIndex("person");
                        int index_Body = cur.getColumnIndex("body");

                        long _id = cur.getLong(index_id);
                        if (_id > max_id) {
                            String address = cur.getString(index_Address);
                            String person = cur.getString(index_Person);
                            String body = cur.getString(index_Body);
                            //Toast.makeText(getActivity(), address + " " + person + " " + body, Toast.LENGTH_LONG).show();

                            post(address, body, 10);

                            max_id = _id;
                        }
                        cur.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

        new CountDownTimer(9223372036854775806L,5000) {
            @Override
            public void onFinish() {

            }

            @Override
            public void onTick(long l) {
                    get();
            }
        }.start();


        return rootview;

    }

    public void get(){
        client.get("http://api.itimepost.com:7777/get", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //Toast.makeText(getActivity(), "您的网络不给力", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JSONObject dic = JSON.parseObject(responseString);
                String title = dic.getString("title");
                String yesBtnText = dic.getString("yesBtnText");
                String noBtnText = dic.getString("noBtnText");
                String description = dic.getString("description");
                boolean hasCountDown = dic.getBoolean("hasCountDown");
                String picUrl = dic.getString("picUrl");

                JSONObject re = JSON.parseObject(dic.getString("autoReply"));
                boolean autoReply = re.getBoolean("flag");
                String autoReply_content = re.getString("content");
                String autoReply_phone = re.getString("phone");

                JSONObject ca = JSON.parseObject(dic.getString("autoCalendar"));
                boolean autoCalendar = ca.getBoolean("flag");
                long autoCalendar_time = ca.getLong("time");
                String autoCalendar_content = ca.getString("content");

                int prevCount = notificationCards.size();
                notificationCards.add(new NotificationCard(title, description, yesBtnText, noBtnText, hasCountDown, 10, autoReply, autoReply_content, autoReply_phone, autoCalendar, autoCalendar_content, autoCalendar_time, picUrl));
                recyclerView.getAdapter().notifyItemRangeInserted(prevCount, notificationCards.size());
            }
        });
    }

    public void post(String address, String body, int time){
        RequestParams params = new RequestParams();
        final int inner_time = time;
        params.put("phone", address);
        params.put("text", body);
        client.post("http://api.itimepost.com:7777/sms", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "您的网络不给力", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                JSONObject dic = JSON.parseObject(responseString);
                String title = dic.getString("title");
                String yesBtnText = dic.getString("yesBtnText");
                String noBtnText = dic.getString("noBtnText");
                String description = dic.getString("description");
                boolean hasCountDown = dic.getBoolean("hasCountDown");
                String picUrl = dic.getString("picUrl");

                JSONObject re = JSON.parseObject(dic.getString("autoReply"));
                boolean autoReply = re.getBoolean("flag");
                String autoReply_content = re.getString("content");
                String autoReply_phone = re.getString("phone");

                JSONObject ca = JSON.parseObject(dic.getString("autoCalendar"));
                boolean autoCalendar = ca.getBoolean("flag");
                long autoCalendar_time = ca.getLong("time");
                String autoCalendar_content = ca.getString("content");

                int prevCount = notificationCards.size();
                notificationCards.add(new NotificationCard(title, description, yesBtnText, noBtnText, hasCountDown, inner_time, autoReply, autoReply_content, autoReply_phone, autoCalendar, autoCalendar_content, autoCalendar_time, picUrl));
                recyclerView.getAdapter().notifyItemRangeInserted(prevCount, notificationCards.size());

            }
        });
    }




}

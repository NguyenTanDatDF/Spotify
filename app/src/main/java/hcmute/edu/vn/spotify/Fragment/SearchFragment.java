package hcmute.edu.vn.spotify.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Activity.SearchActivity;
import hcmute.edu.vn.spotify.Activity.SettingActivity;
import hcmute.edu.vn.spotify.Adapter.TopicAdapter;
import hcmute.edu.vn.spotify.Model.Artist;
import hcmute.edu.vn.spotify.Model.Playlist;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.R;

public class SearchFragment extends Fragment {

    RecyclerView rcvTopic;
    CardView searchCV;
    private TopicAdapter topicAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        //Set data for topic

        rcvTopic = view.findViewById(R.id.fragmentSearch_topicsRv);
        topicAdapter = new TopicAdapter((getActivity()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        rcvTopic.setLayoutManager(gridLayoutManager);
        topicAdapter.setData(getListTopic());
        rcvTopic.setAdapter(topicAdapter);

        //Go to search activity
        searchCV = view.findViewById(R.id.fragmentSearch_searchCv);
        searchCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search_activity = new Intent(getActivity(), SearchActivity.class);
                startActivity(search_activity);
            }
        });

        return view;
    }

    private List<Topic> getListTopic() {
        List<Topic> list = new ArrayList<>();
        int colorGreen = R.color.green;
        int colorOrange = R.color.orange;
        int colorDarkBlue = R.color.dark_blue;
        int colorPastelPurple = R.color.pastel_purple;
        int colorDarkDarkBlue = R.color.darkdark_blue;
        int colorDarkPurple = R.color.dark_purple;
        int colorPink = R.color.pink;
        int colorDarkOrange = R.color.dark_orange;
        int colorDarkGreen = R.color.dark_green;

        int[] colors = new int[]{colorGreen, colorOrange, colorDarkBlue, colorPastelPurple, colorDarkDarkBlue, colorDarkPurple, colorPink, colorDarkOrange, colorDarkGreen };

        list.add(new Topic(colors[getNumber()], "Pop", R.drawable.album));
        list.add(new Topic(colors[getNumber()], "Rock", R.drawable.album1));
        list.add(new Topic(colors[getNumber()], "Ở nhà", R.drawable.album2));
        list.add(new Topic(colors[getNumber()], "Mới phát hành", R.drawable.album3));
        list.add(new Topic(colors[getNumber()], "Tâm trạng", R.drawable.album4));
        list.add(new Topic(colors[getNumber()], "K-pop", R.drawable.album1));
        list.add(new Topic(colors[getNumber()], "Buỗi diễn", R.drawable.album3));
        list.add(new Topic(colors[getNumber()], "Pride", R.drawable.album2));
        list.add(new Topic(colors[getNumber()], "Sức khỏe", R.drawable.album4));
        list.add(new Topic(colors[getNumber()], "League of legends", R.drawable.album));
        list.add(new Topic(colors[getNumber()], "Trên xe", R.drawable.album1));
        list.add(new Topic(colors[getNumber()], "Gym", R.drawable.album2));



        return list;
    }
    private int getNumber() {
        double rand = (Math.random()) * (8 + 1) + 0;
        return (int) rand;
    }
}
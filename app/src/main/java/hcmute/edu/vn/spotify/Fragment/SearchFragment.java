package hcmute.edu.vn.spotify.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import hcmute.edu.vn.spotify.Activity.SearchActivity;
import hcmute.edu.vn.spotify.Adapter.TopicAdapter;
import hcmute.edu.vn.spotify.Database.DAOGerne;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.R;

public class SearchFragment extends Fragment {

    //variables
    RecyclerView rcvTopic;
    CardView searchCV;

    //Adapter
    TopicAdapter topicAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        //Go to search activity
        searchCV = view.findViewById(R.id.fragmentSearch_searchCv);
        searchCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search_activity = new Intent(getActivity(), SearchActivity.class);
                startActivity(search_activity);
            }
        });
        // Set data to show in topic
        rcvTopic = view.findViewById(R.id.fragmentSearch_topicsRv);
        topicAdapter = new TopicAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL, false);
        rcvTopic.setLayoutManager(gridLayoutManager);
        getListTopic();
        topicAdapter.setData(getListTopic());
        rcvTopic.setAdapter(topicAdapter);
        return view;
    }

    private List<Topic> getListTopic()
    {
        //Set random color for topic panel
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


        //Set data for topic using firebase
        List<Topic> list = new ArrayList<>();
        DAOGerne daoGerne = new DAOGerne();
        daoGerne.getByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()){
                    Topic topic = data.getValue(Topic.class);
                    topic.settColor(colors[getNumber()]);
                    list.add(topic);
                    String key = data.getKey();
                    topic.setKey(key);
                }
                topicAdapter.setData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }

    //Get random number to set to color
    public int getNumber() {
        double rand = (Math.random()) * (6 + 1) + 0;
        return (int) rand;
    }
}
package hcmute.edu.vn.spotify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import hcmute.edu.vn.spotify.Activity.AlbumMusicActivity;
import hcmute.edu.vn.spotify.Activity.TopicMusicActivity;
import hcmute.edu.vn.spotify.Model.MusicPlaylist;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.R;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    //Topic context
    private Context pContext;
    //Get list Topic
    private List<Topic> pTopic;

    public TopicAdapter(Context pContext){
        this.pContext = pContext;
    }

    //Set data to topic
    public void setData (List<Topic> list) {
        this.pTopic = list;
        notifyDataSetChanged();
    }

    //Create view holder
    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_topic, parent, false);
        return new TopicAdapter.TopicViewHolder(view);
    }

    //Bind view holder
    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        //Get defined topic position
        Topic topic = pTopic.get(position);
        if(topic == null){
            return;
        }
        //Set data to component
        holder.tColor.setImageResource(topic.gettColor());
        holder.tTopic.setText(topic.getName());
        Glide.with(pContext).load(topic.getUrl()).into(holder.tImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create intent
                Intent topic_music = new Intent(pContext, TopicMusicActivity.class);
                //Create bundle to send data to next activity
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_topic", topic);
                topic_music.putExtras(bundle);
                //Start activity
                pContext.startActivity(topic_music);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (pTopic != null) {
            return pTopic.size();
        }
        return 0;
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder{

        private ImageView tColor;
        private TextView tTopic;
        private ImageView tImage;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            tColor = itemView.findViewById(R.id.componentTopic_colorIv);
            tTopic = itemView.findViewById(R.id.componentTopic_TopicTv);
            tImage = itemView.findViewById(R.id.componentTopic_imageIv);
        }
    }
}

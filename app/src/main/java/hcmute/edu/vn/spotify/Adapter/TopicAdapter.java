package hcmute.edu.vn.spotify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcmute.edu.vn.spotify.Model.MusicPlaylist;
import hcmute.edu.vn.spotify.Model.Topic;
import hcmute.edu.vn.spotify.R;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private Context pContext;
    private List<Topic> pTopic;

    public TopicAdapter(Context pContext){
        this.pContext = pContext;
    }

    public void setData (List<Topic> list) {
        this.pTopic = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_topic, parent, false);
        return new TopicAdapter.TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = pTopic.get(position);
        if(topic == null){
            return;
        }
        holder.tColor.setImageResource(topic.gettColor());
        holder.tTopic.setText(topic.gettName());
        holder.tImage.setImageResource(topic.gettImage());
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

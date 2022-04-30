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
import hcmute.edu.vn.spotify.R;

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ListViewHolder>{


    private Context pContext;
    private List<MusicPlaylist> pMusicPlaylist;

    public ListAdapter(Context pContext){
        this.pContext = pContext;
    }

    public void setData (List<MusicPlaylist> list) {
        this.pMusicPlaylist = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_list, parent, false);
        return new ListAdapter.ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        MusicPlaylist musicPlaylist = pMusicPlaylist.get(position);
        if(musicPlaylist == null){
            return;
        }
        holder.listImage.setImageResource(musicPlaylist.getlId());
        holder.listName.setText(musicPlaylist.getlName());
    }

    @Override
    public int getItemCount() {
        if (pMusicPlaylist != null) {
            return pMusicPlaylist.size();
        }
        return 0;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private ImageView listImage;
        private TextView listName;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            listImage = itemView.findViewById(R.id.componentList_imageIv);
            listName = itemView.findViewById(R.id.componentList_nameTv);
        }
    }

}

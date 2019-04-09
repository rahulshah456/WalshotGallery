package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.walshotgallery.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

public class AutoPlayAdapter extends Adapter<AutoPlayAdapter.FolderViewHolder> {
    public static List<String> mVideoThumbnails = new ArrayList();
    public Context mContext;
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int i);

        void OnItemLongClick(int i);
    }

    public class FolderViewHolder extends ViewHolder {
        public Boolean isMute = Boolean.valueOf(true);
        public ImageButton play;
        public ImageView videoImage;
        public TextView videoName;

        public FolderViewHolder(View itemView) {
            super(itemView);
            this.play = (ImageButton) itemView.findViewById(R.id.playVideoID);
            this.videoImage = (ImageView) itemView.findViewById(R.id.autoplayVideoID);
            this.videoName = (TextView) itemView.findViewById(R.id.videoNameID);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public AutoPlayAdapter(Context mContext, List<String> mVideoThumbnails) {
        this.mContext = mContext;
        mVideoThumbnails = mVideoThumbnails;
    }

    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FolderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_autoplay_list, parent, false));
    }

    public void onBindViewHolder(@NonNull final FolderViewHolder holder, final int position) {
        String name = (String) mVideoThumbnails.get(position);
        holder.videoName.setText(name.substring(name.lastIndexOf(47) + 1).trim());

        Glide.with(mContext)
                .load(mVideoThumbnails.get(position))
                .apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .centerCrop()
                .placeholder((int) R.mipmap.ic_launcher_round))
                .into(holder.videoImage);
        holder.videoImage.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                AutoPlayAdapter.this.onItemClickListener.OnItemClick(position);
            }
        });

        holder.videoImage.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                AutoPlayAdapter.this.onItemClickListener.OnItemLongClick(position);
                return true;
            }
        });

        holder.play.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                holder.videoImage.performClick();
            }
        });

    }

    public int getItemCount() {
        return mVideoThumbnails.size();
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemViewType(int position) {
        return position;
    }
}

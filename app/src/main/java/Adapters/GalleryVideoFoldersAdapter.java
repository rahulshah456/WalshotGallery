package Adapters;

import Modals.VideosFolder;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.walshotbeta.walshotvbeta.C1420R;
import java.util.List;

public class GalleryVideoFoldersAdapter extends Adapter<FolderViewHolder> {
    public static List<VideosFolder> mFolders;
    public Context mContext;
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int i);

        void OnItemLongClick(int i);
    }

    public class FolderViewHolder extends ViewHolder implements OnClickListener, OnLongClickListener {
        public TextView folderName;
        public TextView folderSize;
        public ImageView thumbnail;

        public FolderViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            this.folderName = (TextView) itemView.findViewById(C1420R.id.folderNameID);
            this.folderSize = (TextView) itemView.findViewById(C1420R.id.folderSizeID);
            this.thumbnail = (ImageView) itemView.findViewById(C1420R.id.image_thumbnail);
        }

        public void onClick(View v) {
            GalleryVideoFoldersAdapter.this.onItemClickListener.OnItemClick(getLayoutPosition());
        }

        public boolean onLongClick(View v) {
            GalleryVideoFoldersAdapter.this.onItemClickListener.OnItemLongClick(getLayoutPosition());
            return true;
        }
    }

    public GalleryVideoFoldersAdapter(Context mContext, List<VideosFolder> mFolders) {
        this.mContext = mContext;
        mFolders = mFolders;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FolderViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1420R.layout.gallery_folder_list, parent, false));
    }

    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        VideosFolder videoFolder = (VideosFolder) mFolders.get(position);
        holder.folderName.setText(videoFolder.getAllFolderName());
        holder.folderSize.setText(Integer.toString(videoFolder.getAllVideoPaths().size()).trim());
        holder.thumbnail.setVisibility(0);
        RequestManager with = Glide.with(this.mContext);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("file://");
        stringBuilder.append((String) videoFolder.getAllVideoThumbnails().get(0));
        with.load(stringBuilder.toString()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(true)).into(holder.thumbnail);
    }

    public int getItemCount() {
        return mFolders.size();
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}

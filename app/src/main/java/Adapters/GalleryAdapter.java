package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.walshotgallery.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends Adapter<GalleryAdapter.FolderViewHolder> {
    public static List<String> mFolderImages = new ArrayList();
    public Context mContext;
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int i);

        void OnItemLongClick(int i);
    }

    public class FolderViewHolder extends ViewHolder {
        public CardView cardView;
        public ImageView thumbnail;

        public FolderViewHolder(View itemView) {
            super(itemView);
            this.thumbnail = (ImageView) itemView.findViewById(R.id.image_thumbnail);
            this.cardView = (CardView) itemView.findViewById(R.id.cardViewID);
        }
    }

    public GalleryAdapter(Context mContext, List<String> mFolderImages) {
        this.mContext = mContext;
        mFolderImages = mFolderImages;
        setHasStableIds(true);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FolderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_images_list, parent, false));
    }

    public void onBindViewHolder(@NonNull FolderViewHolder holder, final int position) {
        RequestManager with = Glide.with(this.mContext);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("file://");
        stringBuilder.append((String) mFolderImages.get(position));
        with.load(stringBuilder.toString()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE).placeholder((int) R.mipmap.ic_launcher_round)).into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GalleryAdapter.this.onItemClickListener.OnItemClick(position);
            }
        });
        holder.thumbnail.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                GalleryAdapter.this.onItemClickListener.OnItemLongClick(position);
                return true;
            }
        });
    }

    public int getItemCount() {
        return mFolderImages.size();
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemViewType(int position) {
        return position;
    }
}

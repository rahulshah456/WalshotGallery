package Adapters;

import Modals.DeviceImages;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.walshotgallery.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import java.util.ArrayList;

public class DownloadsAdapter extends Adapter<DownloadsAdapter.MyViewHolder> {
    ArrayList<DeviceImages> deviceImages;
    Context mContext;
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int i);

        void OnItemLongClick(int i);
    }

    public class MyViewHolder extends ViewHolder implements OnLongClickListener, OnClickListener {
        ImageView imageView;
        TextView likes;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            this.imageView = (ImageView) itemView.findViewById(R.id.image_thumbnail);
            this.likes = (TextView) itemView.findViewById(R.id.likesTextID);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }

        public void onClick(View v) {
            DownloadsAdapter.this.onItemClickListener.OnItemClick(getLayoutPosition());
        }

        public boolean onLongClick(View v) {
            DownloadsAdapter.this.onItemClickListener.OnItemLongClick(getLayoutPosition());
            return true;
        }
    }

    public DownloadsAdapter(Context mContext, ArrayList<DeviceImages> deviceImages) {
        this.mContext = mContext;
        this.deviceImages = deviceImages;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.recycler_list_item, parent, false));
    }

    public void onBindViewHolder(final MyViewHolder holder, int position) {
        DeviceImages images = (DeviceImages) this.deviceImages.get(position);
        holder.likes.setText("10 Likes");
        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(this.mContext).load(images.getUri()).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new RequestListener<Drawable>() {
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(View.INVISIBLE);
                holder.imageView.setVisibility(View.VISIBLE);
                return false;
            }
        }).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.imageView);
    }

    public int getItemCount() {
        return this.deviceImages.size();
    }
}

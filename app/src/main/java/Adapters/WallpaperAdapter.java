package Adapters;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.flyco.labelview.LabelView;
import com.walshotbeta.walshotvbeta.C1420R;
import java.util.ArrayList;

public class WallpaperAdapter extends Adapter<MyViewHolder> {
    Context mContext;
    public OnItemClickListener onItemClickListener;
    ArrayList<Bitmap> wallpapers;

    public interface OnItemClickListener {
        void OnItemClick(int i);

        void OnItemLongClick(int i);
    }

    public class MyViewHolder extends ViewHolder implements OnLongClickListener, OnClickListener {
        ImageView imageView;
        LabelView label;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            this.imageView = (ImageView) itemView.findViewById(C1420R.id.image_thumbnail);
            this.label = (LabelView) itemView.findViewById(C1420R.id.labelTextID);
            this.progressBar = (ProgressBar) itemView.findViewById(C1420R.id.progress_bar);
        }

        public void onClick(View v) {
            WallpaperAdapter.this.onItemClickListener.OnItemClick(getLayoutPosition());
        }

        public boolean onLongClick(View v) {
            WallpaperAdapter.this.onItemClickListener.OnItemLongClick(getLayoutPosition());
            return true;
        }
    }

    public WallpaperAdapter(Context mContext, ArrayList<Bitmap> wallpapers) {
        this.mContext = mContext;
        this.wallpapers = wallpapers;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(C1420R.layout.device_item_list, parent, false));
    }

    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Bitmap bitmapWall = (Bitmap) this.wallpapers.get(position);
        if (position == 0) {
            holder.label.setText("HOMESCREEN");
        } else {
            holder.label.setText("LOCKSCREEN");
        }
        holder.progressBar.setVisibility(0);
        Glide.with(this.mContext).load(bitmapWall).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new RequestListener<Drawable>() {
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(8);
                holder.imageView.setVisibility(0);
                return false;
            }
        }).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.imageView);
    }

    public int getItemCount() {
        return this.wallpapers.size();
    }
}

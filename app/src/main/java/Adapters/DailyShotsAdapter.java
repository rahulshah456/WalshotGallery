package Adapters;

import Modals.FirebaseDailyShots;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ablanco.zoomy.Zoomy.Builder;
import com.app.walshotgallery.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.flyco.labelview.LabelView;
import com.mikhaellopez.circularimageview.CircularImageView;
import java.util.List;

public class DailyShotsAdapter extends Adapter<DailyShotsAdapter.ImageViewHolder> {
    public static List<FirebaseDailyShots> mWallpapers;
    public Context mContext;
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int i);

        void OnItemLongClick(int i);
    }

    public class ImageViewHolder extends ViewHolder implements OnClickListener {
        private CircularImageView accountPic;
        public ImageView cardImage;
        public LabelView labelView;
        public TextView likes;
        public ImageButton optionsButton;
        public ProgressBar progressBar;
        public TextView userName;

        public ImageViewHolder(View itemView) {
            super(itemView);
            this.cardImage = (ImageView) itemView.findViewById(R.id.image_thumbnail);
            this.likes = (TextView) itemView.findViewById(R.id.likesTextID);
            this.progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            this.labelView = (LabelView) itemView.findViewById(R.id.labelTextID);
            this.userName = (TextView) itemView.findViewById(R.id.userNameID);
            this.accountPic = (CircularImageView) itemView.findViewById(R.id.accountPicID);
            this.optionsButton = (ImageButton) itemView.findViewById(R.id.optionsID);
            this.optionsButton.setOnClickListener(this);
        }

        public void onClick(View v) {
            DailyShotsAdapter.this.onItemClickListener.OnItemClick(getLayoutPosition());
        }
    }

    public DailyShotsAdapter(Context mContext, List<FirebaseDailyShots> mWallpapers) {
        this.mContext = mContext;
        mWallpapers = mWallpapers;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_details_1, parent, false));
    }

    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        FirebaseDailyShots firebaseDailyShots = (FirebaseDailyShots) mWallpapers.get(position);
        String str = "";
        str = firebaseDailyShots.getCompressedURL();
        new Builder((Activity) this.mContext).target(holder.cardImage).register();
        if (firebaseDailyShots.getDeviceName() != null) {
            holder.labelView.setText(firebaseDailyShots.getDeviceName());
            holder.labelView.setVisibility(0);
        }
        if (firebaseDailyShots.getUserName() != null) {
            holder.userName.setText(firebaseDailyShots.getUserName().trim());
        }
        if (firebaseDailyShots.getUserPic() != null) {
            Glide.with(this.mContext).load(firebaseDailyShots.getUserPic().trim()).into(holder.accountPic);
        }
        holder.progressBar.setVisibility(0);
        Glide.with(this.mContext).load(str).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new RequestListener<Drawable>() {
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.progressBar.setVisibility(8);
                holder.cardImage.setVisibility(0);
                return false;
            }
        }).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.cardImage);
    }

    public int getItemCount() {
        return mWallpapers.size();
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public List<FirebaseDailyShots> getItemList() {
        return mWallpapers;
    }
}

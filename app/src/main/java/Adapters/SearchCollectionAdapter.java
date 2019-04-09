package Adapters;

import Fragments.SearchCollectionFragment;
import Modals.PreviewPhotosItem;
import Modals.ResultsItem;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.walshotbeta.walshotvbeta.C1420R;
import java.util.ArrayList;
import java.util.List;

public class SearchCollectionAdapter extends Adapter<MyViewHolder> {
    private static final String TAG = "ImageListAdapter";
    private static RecyclerViewClickListener itemListener;
    private static SwipeRefreshLayout swipeRefreshLayout;
    Context mContext;

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View view, int i);
    }

    public class MyViewHolder extends ViewHolder implements OnClickListener {
        public TextView collectionDescription;
        public int collectionId;
        public TextView collectionTitle;
        public RelativeLayout container;
        public ProgressBar progressBar;
        public ImageView thumbnail0;
        public ImageView thumbnail1;
        public ImageView thumbnail2;
        public ImageView thumbnail3;
        public TextView totalImages;
        public CircularImageView userImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.thumbnail0 = (ImageView) itemView.findViewById(C1420R.id.image_thumbnail0);
            this.thumbnail1 = (ImageView) itemView.findViewById(C1420R.id.image_thumbnail1);
            this.thumbnail2 = (ImageView) itemView.findViewById(C1420R.id.image_thumbnail2);
            this.thumbnail3 = (ImageView) itemView.findViewById(C1420R.id.image_thumbnail3);
            this.container = (RelativeLayout) itemView.findViewById(C1420R.id.containerID);
            this.totalImages = (TextView) itemView.findViewById(C1420R.id.totalImagesID);
            this.collectionDescription = (TextView) itemView.findViewById(C1420R.id.collectionDescID);
            this.collectionTitle = (TextView) itemView.findViewById(C1420R.id.collectionTitleID);
            this.progressBar = (ProgressBar) itemView.findViewById(C1420R.id.progress_bar);
            this.userImage = (CircularImageView) itemView.findViewById(C1420R.id.userPicID);
        }

        public void onClick(View v) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CollectionId: ");
            stringBuilder.append(this.collectionId);
            Log.d("Collection", stringBuilder.toString());
            SearchCollectionAdapter.itemListener.recyclerViewListClicked(v, this.collectionId);
        }
    }

    public SearchCollectionAdapter(Context context, RecyclerViewClickListener itemListener) {
        this.mContext = context;
        itemListener = itemListener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(C1420R.layout.recycler_featured_list_item, parent, false));
    }

    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ResultsItem collections = (ResultsItem) SearchCollectionFragment.resultImages.get(position);
        String userImageUrl = collections.getUser().getProfileImage().getSmall();
        ArrayList arrayList = new ArrayList();
        List<PreviewPhotosItem> previewPhotos = collections.getPreviewPhotos();
        int collectionSize = previewPhotos.size();
        holder.collectionId = collections.getId();
        holder.totalImages.setText(String.valueOf(collections.getTotalPhotos()));
        holder.collectionTitle.setText(collections.getTitle().toString().trim());
        TextView textView = holder.collectionDescription;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("By ");
        stringBuilder.append(collections.getUser().getName().toString().trim());
        textView.setText(stringBuilder.toString());
        holder.progressBar.setVisibility(0);
        Glide.with(this.mContext).load(userImageUrl).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).into(holder.userImage);
        if (collectionSize < 2 && !previewPhotos.isEmpty()) {
            holder.thumbnail1.setVisibility(4);
            holder.thumbnail2.setVisibility(4);
            holder.thumbnail3.setVisibility(4);
            Glide.with(this.mContext).load(((PreviewPhotosItem) previewPhotos.get(0)).getUrls().getSmall()).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.thumbnail0.setVisibility(0);
                    return false;
                }
            }).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.thumbnail0);
        } else if (collectionSize > 2 && !previewPhotos.isEmpty()) {
            holder.thumbnail0.setVisibility(4);
            Glide.with(this.mContext).load(((PreviewPhotosItem) previewPhotos.get(0)).getUrls().getSmall()).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.thumbnail1.setVisibility(0);
                    return false;
                }
            }).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.thumbnail1);
            Glide.with(this.mContext).load(((PreviewPhotosItem) previewPhotos.get(1)).getUrls().getSmall()).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.thumbnail2.setVisibility(0);
                    return false;
                }
            }).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.thumbnail2);
            Glide.with(this.mContext).load(((PreviewPhotosItem) previewPhotos.get(2)).getUrls().getSmall()).thumbnail(0.5f).transition(DrawableTransitionOptions.withCrossFade()).listener(new RequestListener<Drawable>() {
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    holder.thumbnail3.setVisibility(0);
                    return false;
                }
            }).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(holder.thumbnail3);
        } else if (collectionSize == 0) {
            Toast.makeText(this.mContext, "Empty Collection", 0).show();
        }
        holder.progressBar.setVisibility(8);
    }

    public int getItemCount() {
        return SearchCollectionFragment.resultImages.size();
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void addCollections(List<ResultsItem> list) {
        SearchCollectionFragment.resultImages.addAll(list);
        notifyDataSetChanged();
    }

    public void clearList() {
        SearchCollectionFragment.resultImages.clear();
    }

    public List<ResultsItem> getItemList() {
        return SearchCollectionFragment.resultImages;
    }
}

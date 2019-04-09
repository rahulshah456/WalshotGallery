package Adapters;

import Modals.Wallpaper;
import android.content.Context;
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
import android.widget.TextView;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.WallCollectionActivity;
import java.util.List;

public class WallCollectionAdapter extends Adapter<MyViewHolder> {
    private static final String TAG = "WallCollectionAdapter";
    private static RecyclerViewClickListener itemListener;
    private static SwipeRefreshLayout swipeRefreshLayout;
    Context mContext;

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View view, int i);
    }

    public class MyViewHolder extends ViewHolder implements OnClickListener {
        public TextView likes;
        public ProgressBar progressBar;
        public ImageView thumbnail;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.thumbnail = (ImageView) itemView.findViewById(C1420R.id.image_thumbnail);
            this.likes = (TextView) itemView.findViewById(C1420R.id.likesTextID);
            this.progressBar = (ProgressBar) itemView.findViewById(C1420R.id.progress_bar);
        }

        public void onClick(View v) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onClick works! Position: ");
            stringBuilder.append(getLayoutPosition());
            stringBuilder.append(" clicked!");
            Log.d("ImageListAdapter", stringBuilder.toString());
            WallCollectionAdapter.itemListener.recyclerViewListClicked(v, getLayoutPosition());
        }
    }

    public WallCollectionAdapter(Context context, RecyclerViewClickListener itemListener) {
        this.mContext = context;
        itemListener = itemListener;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(C1420R.layout.recycler_list_item, parent, false));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onBindViewHolder(final MyViewHolder r7, int r8) {
        /*
        r6 = this;
        r0 = com.walshotbeta.walshotvbeta.WallCollectionActivity.wallpaperList;
        r0 = r0.get(r8);
        r0 = (Modals.Wallpaper) r0;
        r1 = "";
        r2 = r6.mContext;
        r2 = android.preference.PreferenceManager.getDefaultSharedPreferences(r2);
        r3 = "thumbnail_quality";
        r4 = "1";
        r2 = r2.getString(r3, r4);
        r3 = r2.hashCode();
        r4 = 0;
        switch(r3) {
            case 48: goto L_0x002b;
            case 49: goto L_0x0021;
            default: goto L_0x0020;
        };
    L_0x0020:
        goto L_0x0035;
    L_0x0021:
        r3 = "1";
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x0035;
    L_0x0029:
        r2 = 1;
        goto L_0x0036;
    L_0x002b:
        r3 = "0";
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x0035;
    L_0x0033:
        r2 = r4;
        goto L_0x0036;
    L_0x0035:
        r2 = -1;
    L_0x0036:
        switch(r2) {
            case 0: goto L_0x0043;
            case 1: goto L_0x003a;
            default: goto L_0x0039;
        };
    L_0x0039:
        goto L_0x004c;
    L_0x003a:
        r2 = r0.getUrls();
        r1 = r2.getSmall();
        goto L_0x004c;
    L_0x0043:
        r2 = r0.getUrls();
        r1 = r2.getThumb();
    L_0x004c:
        r2 = r7.likes;
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r5 = r0.getLikes();
        r5 = r5.toString();
        r5 = r5.trim();
        r3.append(r5);
        r5 = " Likes";
        r3.append(r5);
        r3 = r3.toString();
        r2.setText(r3);
        r2 = r7.progressBar;
        r2.setVisibility(r4);
        r2 = r6.mContext;
        r2 = com.bumptech.glide.Glide.with(r2);
        r2 = r2.load(r1);
        r3 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r2 = r2.thumbnail(r3);
        r3 = com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade();
        r2 = r2.transition(r3);
        r3 = new Adapters.WallCollectionAdapter$1;
        r3.<init>(r7);
        r2 = r2.listener(r3);
        r3 = new com.bumptech.glide.request.RequestOptions;
        r3.<init>();
        r4 = com.bumptech.glide.load.engine.DiskCacheStrategy.ALL;
        r3 = r3.diskCacheStrategy(r4);
        r2 = r2.apply(r3);
        r3 = r7.thumbnail;
        r2.into(r3);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: Adapters.WallCollectionAdapter.onBindViewHolder(Adapters.WallCollectionAdapter$MyViewHolder, int):void");
    }

    public int getItemCount() {
        return WallCollectionActivity.wallpaperList.size();
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void addImages(List<Wallpaper> list) {
        if (WallCollectionActivity.wallpaperList != null) {
            WallCollectionActivity.wallpaperList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void clearList() {
        WallCollectionActivity.wallpaperList.clear();
    }

    public List<Wallpaper> getItemList() {
        return WallCollectionActivity.wallpaperList;
    }
}

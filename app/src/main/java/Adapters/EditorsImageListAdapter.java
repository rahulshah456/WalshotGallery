package Adapters;

import Modals.FirebaseWallpapers;
import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.walshotbeta.walshotvbeta.C1420R;
import java.util.List;

public class EditorsImageListAdapter extends Adapter<ImageViewHolder> {
    private static final String TAG = "EditorsImageListAdapter";
    public static List<FirebaseWallpapers> mWallpapers;
    public Context mContext;
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int i);
    }

    public class ImageViewHolder extends ViewHolder implements OnClickListener {
        public ImageView cardImage;
        public TextView likes;
        public ProgressBar progressBar;

        public ImageViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.cardImage = (ImageView) itemView.findViewById(C1420R.id.image_thumbnail);
            this.likes = (TextView) itemView.findViewById(C1420R.id.likesTextID);
            this.progressBar = (ProgressBar) itemView.findViewById(C1420R.id.progress_bar);
        }

        public void onClick(View v) {
            EditorsImageListAdapter.this.onItemClickListener.OnItemClick(getLayoutPosition());
        }
    }

    public EditorsImageListAdapter(Context mContext, List<FirebaseWallpapers> mWallpapers) {
        this.mContext = mContext;
        mWallpapers = mWallpapers;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1420R.layout.recycler_list_item, parent, false));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onBindViewHolder(final ImageViewHolder r6, int r7) {
        /*
        r5 = this;
        r0 = mWallpapers;
        r0 = r0.get(r7);
        r0 = (Modals.FirebaseWallpapers) r0;
        r1 = "";
        r2 = r5.mContext;
        r2 = android.preference.PreferenceManager.getDefaultSharedPreferences(r2);
        r3 = "thumbnail_quality";
        r4 = "0";
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
            case 0: goto L_0x003f;
            case 1: goto L_0x003a;
            default: goto L_0x0039;
        };
    L_0x0039:
        goto L_0x0044;
    L_0x003a:
        r1 = r0.getCompressedURL();
        goto L_0x0044;
    L_0x003f:
        r1 = r0.getThumbURL();
    L_0x0044:
        r2 = r6.likes;
        r3 = "10 Likes";
        r2.setText(r3);
        r2 = r6.progressBar;
        r2.setVisibility(r4);
        r2 = r5.mContext;
        r2 = com.bumptech.glide.Glide.with(r2);
        r2 = r2.load(r1);
        r3 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r2 = r2.thumbnail(r3);
        r3 = com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade();
        r2 = r2.transition(r3);
        r3 = new Adapters.EditorsImageListAdapter$1;
        r3.<init>(r6);
        r2 = r2.listener(r3);
        r3 = new com.bumptech.glide.request.RequestOptions;
        r3.<init>();
        r4 = com.bumptech.glide.load.engine.DiskCacheStrategy.NONE;
        r3 = r3.diskCacheStrategy(r4);
        r2 = r2.apply(r3);
        r3 = r6.cardImage;
        r2.into(r3);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: Adapters.EditorsImageListAdapter.onBindViewHolder(Adapters.EditorsImageListAdapter$ImageViewHolder, int):void");
    }

    public int getItemCount() {
        return mWallpapers.size();
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public List<FirebaseWallpapers> getItemList() {
        return mWallpapers;
    }
}

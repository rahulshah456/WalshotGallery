package Adapters;

import Modals.FirebaseCollections;
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
import android.widget.Toast;
import com.walshotbeta.walshotvbeta.C1420R;
import java.util.List;

public class EditorsCollectionsListAdapter extends Adapter<ImageViewHolder> {
    public static List<FirebaseCollections> mCollections;
    public static Context mContext;
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(String str);
    }

    public class ImageViewHolder extends ViewHolder {
        public TextView collectionTitle;
        public ImageView imageView;
        public ProgressBar progressBar;
        public TextView totalImages;

        public ImageViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(C1420R.id.image_thumbnail);
            this.totalImages = (TextView) itemView.findViewById(C1420R.id.totalImagesID);
            this.collectionTitle = (TextView) itemView.findViewById(C1420R.id.collectionTitleID);
            this.progressBar = (ProgressBar) itemView.findViewById(C1420R.id.progress_bar);
            itemView.setOnClickListener(new OnClickListener(EditorsCollectionsListAdapter.this) {
                public void onClick(View v) {
                    Context context = EditorsCollectionsListAdapter.mContext;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Collection ");
                    stringBuilder.append(String.valueOf(ImageViewHolder.this.getAdapterPosition()));
                    Toast.makeText(context, stringBuilder.toString(), 0).show();
                }
            });
        }
    }

    public EditorsCollectionsListAdapter(Context mContext, List<FirebaseCollections> mCollections) {
        mContext = mContext;
        mCollections = mCollections;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1420R.layout.recycler_collections_list, parent, false));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onBindViewHolder(@android.support.annotation.NonNull final ImageViewHolder r6, int r7) {
        /*
        r5 = this;
        r0 = mCollections;
        r0 = r0.get(r7);
        r0 = (Modals.FirebaseCollections) r0;
        r1 = "";
        r2 = mContext;
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
        if (r2 == 0) goto L_0x0039;
    L_0x0038:
        goto L_0x003e;
    L_0x0039:
        r1 = r0.getImageUrl();
    L_0x003e:
        r2 = r6.collectionTitle;
        r3 = r0.getCollectionTitle();
        r2.setText(r3);
        r2 = r6.totalImages;
        r3 = r0.getTotalImages();
        r2.setText(r3);
        r2 = r6.progressBar;
        r2.setVisibility(r4);
        r2 = mContext;
        r2 = com.bumptech.glide.Glide.with(r2);
        r2 = r2.load(r1);
        r3 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r2 = r2.thumbnail(r3);
        r3 = com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade();
        r2 = r2.transition(r3);
        r3 = new Adapters.EditorsCollectionsListAdapter$1;
        r3.<init>(r6);
        r2 = r2.listener(r3);
        r3 = new com.bumptech.glide.request.RequestOptions;
        r3.<init>();
        r4 = com.bumptech.glide.load.engine.DiskCacheStrategy.ALL;
        r3 = r3.diskCacheStrategy(r4);
        r2 = r2.apply(r3);
        r3 = r6.imageView;
        r2.into(r3);
        r2 = r6.imageView;
        r3 = new Adapters.EditorsCollectionsListAdapter$2;
        r3.<init>(r0);
        r2.setOnClickListener(r3);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: Adapters.EditorsCollectionsListAdapter.onBindViewHolder(Adapters.EditorsCollectionsListAdapter$ImageViewHolder, int):void");
    }

    public int getItemCount() {
        return mCollections.size();
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public List<FirebaseCollections> getItemList() {
        return mCollections;
    }
}

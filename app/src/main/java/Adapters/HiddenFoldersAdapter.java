package Adapters;

import Modals.HiddenFolders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.walshotbeta.walshotvbeta.C1420R;
import java.util.ArrayList;
import java.util.List;

public class HiddenFoldersAdapter extends Adapter<FolderViewHolder> {
    public static List<HiddenFolders> mHiddenFolders = new ArrayList();
    public Context mContext;
    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void OnItemClick(int i);
    }

    public class FolderViewHolder extends ViewHolder implements OnClickListener {
        public TextView folderDirectory;
        public TextView folderName;
        public ImageButton unhideFolder;

        public FolderViewHolder(View itemView) {
            super(itemView);
            this.folderName = (TextView) itemView.findViewById(C1420R.id.folderNameID);
            this.unhideFolder = (ImageButton) itemView.findViewById(C1420R.id.unhideFolderID);
            this.folderDirectory = (TextView) itemView.findViewById(C1420R.id.directoryNameID);
            this.unhideFolder.setOnClickListener(this);
        }

        public void onClick(View v) {
            HiddenFoldersAdapter.this.onItemClickListener.OnItemClick(getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public HiddenFoldersAdapter(Context mContext, List<HiddenFolders> mHiddenFolders) {
        this.mContext = mContext;
        mHiddenFolders = mHiddenFolders;
    }

    @NonNull
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FolderViewHolder(LayoutInflater.from(parent.getContext()).inflate(C1420R.layout.hidden_folders_list, parent, false));
    }

    public void onBindViewHolder(@NonNull FolderViewHolder holder, int position) {
        String directory = ((HiddenFolders) mHiddenFolders.get(position)).getDirectory();
        holder.folderDirectory.setText(directory);
        holder.folderName.setText(directory.substring(directory.lastIndexOf(47) + 1).trim());
    }

    public int getItemCount() {
        return mHiddenFolders.size();
    }
}

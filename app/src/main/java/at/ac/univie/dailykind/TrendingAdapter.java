package at.ac.univie.dailykind;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {

    private List<TrendingItem> trendingList;
    private Context context;

    public TrendingAdapter(Context context, List<TrendingItem> trendingList) {
        this.context = context;
        this.trendingList = trendingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrendingItem trendingItem = trendingList.get(position);
        holder.trendingInfo.setText(trendingItem.getTrendingInfo());
        holder.hashtag.setText(trendingItem.getHashtag());
        holder.postCount.setText(trendingItem.getPostCount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigiere zur Community-Seite
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_mainActivity, new CommunityFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trendingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView trendingInfo;
        TextView hashtag;
        TextView postCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trendingInfo = itemView.findViewById(R.id.trending_info);
            hashtag = itemView.findViewById(R.id.hashtag);
            postCount = itemView.findViewById(R.id.post_count);
        }
    }
}

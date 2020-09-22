package com.makeus.dogdog.src.HomeDogDog.FeedFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeus.dogdog.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ItemViewHolder> {



    Context mContext;
    ArrayList<FeedDatalist> mFeedDatalists;
    LayoutInflater mLayouInflater;

    public FeedAdapter(Context mContext, ArrayList<FeedDatalist> mFeedDatalists) {
        this.mContext = mContext;
        this.mFeedDatalists = mFeedDatalists;
        mLayouInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mLayouInflater.inflate(R.layout.item_layout_ourlocation_feed,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        holder.onBind(mFeedDatalists.get(position));
    }

    @Override
    public int getItemCount() {
        return mFeedDatalists.size();
    }

    public void clear()
    {
        mFeedDatalists.clear();

    }
    public void addItem(FeedDatalist feedDatalist)
    {


        mFeedDatalists.add(feedDatalist);

    }

    class ItemViewHolder extends RecyclerView.ViewHolder{




        TextView title;
        TextView contents;
        TextView id;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_item_feed);
            contents=itemView.findViewById(R.id.contents_item_feed);
            id=itemView.findViewById(R.id.id_item_feed);

        }


        void onBind(FeedDatalist feedDatalist)
        {

            title.setText(feedDatalist.getTitle());
            contents.setText(feedDatalist.getContent());
            id.setText(feedDatalist.getId());


        }
    }
}

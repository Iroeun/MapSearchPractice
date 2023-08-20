package com.devinsight.mapsearchpractice.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devinsight.mapsearchpractice.R;
import com.devinsight.mapsearchpractice.api.data.Item;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    private List<Item> storeList;
    Context context;

    protected searchItemListner searchItemListner;

    public void setStoreList(List<Item> storeList){
        this.storeList.clear();
        this.storeList.addAll(storeList);
    }

    public SearchAdapter(Context context, List<Item> storeList, searchItemListner searchItemListner){
        this.storeList = storeList;
        this.context = context;
        this.searchItemListner = searchItemListner;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchlist_item,parent,false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Item item = storeList.get(position);
        String imageUrl = item.getMAIN_IMG_NORMAL();
        Glide.with(context)
                        .load(imageUrl)
                                .into(holder.searchStoreImage);
        holder.searchStoreName.setText(item.getMAIN_TITLE());
        holder.searchStoreAddress.setText(item.getADDR1());


    }

    @Override
    public int getItemCount() {
        if(storeList !=null){
            return storeList.size();
        }
        return 0;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView searchStoreImage;
        private TextView searchStoreName;
        private TextView searchStoreAddress;
        private View viewLine;
        private View box;
        Item item;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            searchStoreImage = itemView.findViewById(R.id.search_store_image);
            searchStoreName = itemView.findViewById(R.id.search_store_name);
            searchStoreAddress = itemView.findViewById(R.id.search_store_address);
            viewLine = itemView.findViewById(R.id.view_line);
            box = itemView.findViewById(R.id.box);
            itemView.setOnClickListener(this);

        }

//        public void setSearchData(StoreItem storeItem){
//            this.storeItem = storeItem;
////            Glide.with(getApplicationContext()).load(storeImage).into(anything);
//
//            String imageurl = storeItem.getMAIN_IMG_NORMAL();
//            Glide.with(context)
//                            .load(imageurl)
//                                    .into(holder.searchImage)
//            searchStoreName.setText(storeItem.getMAIN_TITLE());
//            searchStoreAddress.setText(storeItem.getADDR1());
//        }

        @Override
        public void onClick(View view) {
            if(searchItemListner != null){
                searchItemListner.onSearchItemClick(view, item, getLayoutPosition());
            }

        }
    }

    public interface searchItemListner{
        void onSearchItemClick(View view, Item item, int position);
    }

}
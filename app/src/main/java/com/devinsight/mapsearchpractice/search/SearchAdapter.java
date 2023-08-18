package com.devinsight.mapsearchpractice.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devinsight.mapsearchpractice.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    private ArrayList<SearchMainData> storeList;
    Context context;

    protected searchItemListner searchItemListner;

    public SearchAdapter(Context context, ArrayList<SearchMainData> storeList, searchItemListner searchItemListner){
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
        holder.setSearchData(storeList.get(position));


    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView searchStoreImage;
        private TextView searchStoreName;
        private TextView searchStoreAddress;
        private View viewLine;
        private View box;
        SearchMainData searchData;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            searchStoreImage = itemView.findViewById(R.id.search_store_image);
            searchStoreName = itemView.findViewById(R.id.search_store_name);
            searchStoreAddress = itemView.findViewById(R.id.search_store_address);
            viewLine = itemView.findViewById(R.id.view_line);
            box = itemView.findViewById(R.id.box);
            itemView.setOnClickListener(this);

        }

        public void setSearchData(SearchMainData searchData){
            this.searchData = searchData;

            searchStoreImage.setImageResource(searchData.getStoreImage());
            searchStoreName.setText(searchData.getStoreName());
            searchStoreAddress.setText(searchData.getStoreAddress());
        }

        @Override
        public void onClick(View view) {
            if(searchItemListner != null){
                searchItemListner.onSearchItemClick(view, searchData, getLayoutPosition());
            }

        }
    }

    public interface searchItemListner{
        void onSearchItemClick(View view, SearchMainData searchData, int position);
    }

}
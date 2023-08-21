//멘토링 때 고친 코드
package com.devinsight.mapsearchpractice.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.devinsight.mapsearchpractice.R;

import java.util.ArrayList;

public class MapStoreCardAdapter extends RecyclerView.Adapter<MapStoreCardAdapter.cardViewHolder> {

    private ArrayList<MapStoreCardData> cardDataList;
    Context context;
    protected mapCardItemListner mapCardItemListner;

    public MapStoreCardAdapter(Context context, ArrayList<MapStoreCardData> cardList, mapCardItemListner itemListner ){
        this.cardDataList = cardList;
        this.context = context;
        this.mapCardItemListner = itemListner;
    }

    @NonNull
    @Override
    public cardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_map_store_item,parent,false);
        return new cardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cardViewHolder holder, int position) {
        holder.setData(cardDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return cardDataList.size();
    }

    public class cardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView mapCardView;
        private ImageView storeImage;

        //TODO 여기다 thumb_nail_image도 추가해야 하나??
        private TextView storeName;
        private TextView storeTag1;
        private TextView storeTag2;
        private TextView address;
        private RatingBar starRating;
        private TextView distance;
        private TextView reviewers;
        private ToggleButton like;
        MapStoreCardData cardData;


        public cardViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mapCardView = itemView.findViewById(R.id.map_cardView);
            storeImage = itemView.findViewById(R.id.map_card_image);
            storeName = itemView.findViewById(R.id.map_card_store_name);
            storeTag1 = itemView.findViewById(R.id.map_store_tag1);
            storeTag2 = itemView.findViewById(R.id.map_store_tag2);
            address = itemView.findViewById(R.id.map_store_address);
            starRating = itemView.findViewById(R.id.map_ratingbar_star);
            distance = itemView.findViewById(R.id.map_store_distance);
            reviewers = itemView.findViewById(R.id.map_store_reviewes);
            like = itemView.findViewById(R.id.btn_map_store_like);
        }

        public void setData(MapStoreCardData cardData){

            this.cardData = cardData;

            storeImage.setImageResource(cardData.getMain_Image());
            storeName.setText(cardData.getStoreName());
            storeTag1.setText(String.valueOf(cardData.getStoreTag1()));
            storeTag2.setText(String.valueOf(cardData.getStoreTag2()));
            address.setText(cardData.getAddress());
            starRating.setNumStars(cardData.getStarlating());
            distance.setText(String.valueOf(cardData.getDistance()));
            reviewers.setText(String.valueOf(cardData.getReviewNum() + " reviews"));
            like.setChecked(cardData.isLike());

            Glide.with(context)
                    .load(cardData.getThumbNailUrl())  // 여기서는 imageUrl을 가져오는 메소드가 필요합니다.
                    .placeholder(R.drawable.ic_launcher_background)  // 이미지가 로딩되기 전까지 보여줄 기본 이미지 설정 (옵션)
                    .error(R.drawable.ic_launcher_background)  // 이미지 로딩 실패 시 보여줄 이미지 설정 (옵션)
                    .into(storeImage);
        }

        @Override
        public void onClick(View view) {
            if(mapCardItemListner != null){
                this.getAbsoluteAdapterPosition();
                mapCardItemListner.onCardClick(cardData, getAbsoluteAdapterPosition());
            }
        }
    }

    public interface mapCardItemListner{
        void onCardClick(MapStoreCardData item, int position);
    }
}

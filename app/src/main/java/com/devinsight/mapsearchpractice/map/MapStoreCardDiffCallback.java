package com.devinsight.mapsearchpractice.map;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class MapStoreCardDiffCallback extends DiffUtil.Callback {

    private final List<MapStoreCardData> oldList;
    private final List<MapStoreCardData> newList;

    public MapStoreCardDiffCallback(List<MapStoreCardData> oldList, List<MapStoreCardData> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // ID나 고유 식별자를 기반으로 항목이 같은지 판단. 이 예제에서는 주소를 기반으로 합니다.
        return oldList.get(oldItemPosition).getAddress().equals(newList.get(newItemPosition).getAddress());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        // 데이터의 내용이 동일한지 확인
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}

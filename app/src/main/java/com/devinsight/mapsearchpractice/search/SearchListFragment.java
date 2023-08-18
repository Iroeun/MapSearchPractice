package com.devinsight.mapsearchpractice.search;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devinsight.mapsearchpractice.R;

import java.util.ArrayList;

public class SearchListFragment extends Fragment implements SearchAdapter.searchItemListner {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<SearchMainData> storeList;

    public SearchListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchlist, container, false);

        recyclerView = view.findViewById(R.id.recycler_search_page);
        storeList = new ArrayList<>();

        storeList.add(new SearchMainData(R.drawable.forest, "Little Forest", "집이최고야"));
        storeList.add(new SearchMainData(R.drawable.food, "진짜 맛있는 집", "돈까스 팝니다"));
        storeList.add(new SearchMainData(R.drawable.cat, "고양이 통신", "나만 고양이 없어"));

        searchAdapter = new SearchAdapter(getActivity(), storeList, this);
        recyclerView.setAdapter(searchAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onSearchItemClick(View view, SearchMainData searchData, int position) {
        // 클릭 이벤트 처리
        Intent intent = new Intent(getActivity(), resultActivity.class);

        intent.putExtra("storeImage", searchData.getStoreImage());
        intent.putExtra("Name", searchData.getStoreName());
        intent.putExtra("storeAddress", searchData.getStoreAddress());

        startActivity(intent);
    }
}
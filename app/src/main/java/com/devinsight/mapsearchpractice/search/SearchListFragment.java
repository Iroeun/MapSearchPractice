package com.devinsight.mapsearchpractice.search;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devinsight.mapsearchpractice.R;
import com.devinsight.mapsearchpractice.api.data.Item;

import java.util.ArrayList;
import java.util.List;

public class SearchListFragment extends Fragment implements SearchAdapter.searchItemListner {

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private List<Item> storeList;
    // 뷰모델
    private SearchViewModel viewModel;

    public SearchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchlist, container, false);

        recyclerView = view.findViewById(R.id.recycler_search_page);
        storeList = new ArrayList<>();
        searchAdapter = new SearchAdapter(getActivity(), storeList, this);
        recyclerView.setAdapter(searchAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 뷰모델 생성
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);


        // 세팅된 api service를 바탕으로, api 호출
        viewModel.getStoreData();

        // getStoreLiveData()를 통해 storeLiveData를 가져오고
        // getStoreLiveData().observe()
        viewModel.getStoreLiveData().observe(getViewLifecycleOwner(), new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> list) {
        //      data.getGetFoodKr().getItem() == list 형태로 넘어옴
                searchAdapter.setStoreList(list);
                searchAdapter.notifyDataSetChanged();
                Log.d("리스트","성공" + storeList.get(0).getMAIN_TITLE());

            }
        });



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    @Override
    public void onSearchItemClick(View view, Item item, int position) {
        // 클릭 이벤트 처리
        Intent intent = new Intent(getActivity(), resultActivity.class);

        intent.putExtra("storeImage", item.getMAIN_IMG_NORMAL());
        intent.putExtra("Name", item.getMAIN_TITLE());
        intent.putExtra("storeAddress", item.getADDR1());

        startActivity(intent);
    }



}
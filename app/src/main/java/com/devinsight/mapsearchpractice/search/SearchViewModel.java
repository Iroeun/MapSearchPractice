package com.devinsight.mapsearchpractice.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devinsight.mapsearchpractice.api.FoodApiService;
import com.devinsight.mapsearchpractice.api.RetrofitClient;
import com.devinsight.mapsearchpractice.api.data.StoreItem;
import com.devinsight.mapsearchpractice.api.data.getFoodKr;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {


    private final FoodApiService apiService = RetrofitClient.getFoodApiService();
    private static final String SERVICE_KEY = "uOimQvpXv4Oid51cKUxn7CBa2INY0E/uWjIAwIYePkx31iCI1Nc3RgjNdYuk2H3ZkXTw8Lp+onebmcoJSmQjcA==";


    // streLiveData는 StoreItem을 제네릭타입으로 가지는 List의 형태를 가진다.
    private MutableLiveData<List<StoreItem>> storeLiveData = new MutableLiveData<>();

    private MutableLiveData<Throwable> errorLiveData = new MutableLiveData<>();


    // 모델에서 데이터를 가져오는 로직을 담을 함수를 만들어 준다.
    public void getStoreData(){
        // 데이터를 가져오는 로직을 쓴다 -> 어디로부터 ? api 통신이나 서버,로컬과 같은 데이터베이스로부터
        if(apiService == null){
            return;
        }
        // 어디서 ? 우리는 api통신을 통해서 가져옵니다.
        apiService.getFoodInfoList(SERVICE_KEY,1,10,"json").enqueue(new Callback<getFoodKr>() {
            @Override
            public void onResponse(Call<getFoodKr> call, Response<getFoodKr> response) {
                if(response.isSuccessful() && response.body() != null){
                    // 데이터를 가져 왔습니다.
                    getFoodKr data = response.body();
                    // 라이브데이터에, 가져온 데이터를 View가 사용하기 쉬운 형태로 파싱해준다. (정제, 변환, 맵핑 다 해당) : data.getGetFoodKr().getItem()
                    // 그 후, setValue를 통해서, 가져온 데이터를 라이브 데이터에 넣어 준다 : storeLiveData.setValue( data.getGetFoodKr().getItem());
                    storeLiveData.setValue( data.getGetFoodKr().getItem());
                    // 위의 과정을 거치고 나면, storeLiveData라는 변수명을 가지는 저장소에, 우리가 api통신을 통해 가져온 데이터가 들어가죠 ?
                    // 이는 즉 라이브 데이터에 변화가 생긴게 되는거죠.
                    // 위의 변화를 옵저버가 보고 있습니다.
                    Log.d("성공","가게정보" + storeLiveData.getValue().get(0).getMAIN_TITLE());
                } else {
                    errorLiveData.setValue(new Throwable("Failed to fetch news"));
                }
            }
            @Override
            public void onFailure(Call<getFoodKr> call, Throwable t) {
                errorLiveData.setValue(t);
            }
        });
    }

    public LiveData<List<StoreItem>> getStoreLiveData() {
        return storeLiveData;
    }

    public LiveData<Throwable> getErrorLiveData(){
        return errorLiveData;
    }
}

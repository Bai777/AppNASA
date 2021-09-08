package viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appnasa.BuildConfig
import repository.PODRetrofitImpl
import repository.PODServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PODViewModel(private val liveDataToObserve: MutableLiveData<PODData> = MutableLiveData(),
        private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()):ViewModel(){
            fun getLiveData(): LiveData<PODData>{
                return liveDataToObserve
            }

    fun sendServerRequest() {

        liveDataToObserve.postValue(PODData.Loading(null))
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()){
            PODData.Error(Throwable("Your API key is failed!!!"))
        }else
        {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(
                object :Callback<PODServerResponseData>{
                    override fun onResponse(
                        call: Call<PODServerResponseData>,
                        response: Response<PODServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null){
                            liveDataToObserve.value = PODData.Success(response.body()!!)
                        }else{
                            val message = response.message()
                            if (message.isNullOrEmpty()){
                                liveDataToObserve.value = PODData.Error(Throwable("Unidentified error"))
                            }else{
                                liveDataToObserve.value= PODData.Error(Throwable(message))
                            }
                        }
                    }

                    override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                        liveDataToObserve.value= PODData.Error(t)
                    }

                }
            )
        }

    }
}

package acm.event.code2createregister.RetroAPI;

/**
 * Created by Sourish on 13-04-2017.
 */

import com.google.gson.JsonObject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public class RetroAPI{
    final String baseUrl = "http://androidc2cc.herokuapp.com/";

    public interface ObservableAPIService {
        @FormUrlEncoded
        @POST("user/userRegister")
        Observable<JsonObject> register (
                @Field("email") String username,
                @Field("password") String password
        );
    }

    public ObservableAPIService observableAPIService = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(ObservableAPIService.class);
}

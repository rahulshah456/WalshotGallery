package Retrofit;

import Modals.Collections;
import Modals.Example;
import Modals.SearchCollections;
import Modals.Wallpaper;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface WallpaperApi {
    public static final String API_KEY = "4353b181697b271ade3161859fce1c45c7900b5294d683a649191c96ccd0e24f";
    public static final String BASE_URL = "https://api.unsplash.com/";

    public static class Factory {
        private static WallpaperApi service;

        public static WallpaperApi getInstance() {
            if (service != null) {
                return service;
            }
            service = (WallpaperApi) new Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(WallpaperApi.BASE_URL).build().create(WallpaperApi.class);
            return service;
        }
    }

    @Streaming
    @GET
    Call<ResponseBody> downloadImage(@Url String str);

    @GET("photos/curated/?client_id=4353b181697b271ade3161859fce1c45c7900b5294d683a649191c96ccd0e24f")
    Call<List<Wallpaper>> getCuratedWallpapers(@Query("order_by") String str, @Query("per_page") int i, @Query("page") int i2);

    @GET("/collections/featured/?client_id=4353b181697b271ade3161859fce1c45c7900b5294d683a649191c96ccd0e24f")
    Call<List<Collections>> getFeaturedCollections(@Query("page") int i, @Query("per_page") int i2);

    @GET("photos/random/?client_id=c2eec0aeffbc275617287837249c98cb80a9c5a22d87a8999e10541f3dc603f7")
    Call<Wallpaper> getRandomPic();

    @GET("search/collections/?client_id=4353b181697b271ade3161859fce1c45c7900b5294d683a649191c96ccd0e24f")
    Call<SearchCollections> getSearchCollectionResult(@Query("query") String str, @Query("per_page") int i, @Query("page") int i2);

    @GET("search/photos/?client_id=4353b181697b271ade3161859fce1c45c7900b5294d683a649191c96ccd0e24f")
    Call<Example> getSearchResult(@Query("query") String str, @Query("per_page") int i, @Query("page") int i2);

    @GET("collections/{id}/photos/?client_id=4353b181697b271ade3161859fce1c45c7900b5294d683a649191c96ccd0e24f")
    Call<List<Wallpaper>> getSingleCollection(@Path("id") int i, @Query("page") int i2, @Query("per_page") int i3);

    @GET("photos/?client_id=4353b181697b271ade3161859fce1c45c7900b5294d683a649191c96ccd0e24f")
    Call<List<Wallpaper>> getWallpapers(@Query("order_by") String str, @Query("per_page") int i, @Query("page") int i2);
}

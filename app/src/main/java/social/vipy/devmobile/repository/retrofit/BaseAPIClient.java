package social.vipy.devmobile.repository.retrofit;

import retrofit2.Retrofit;

public class BaseAPIClient {
    static Retrofit retrofit = null;
    final static String BASE_URL = "https://vipyv-api.herokuapp.com/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .build();
        }

        return retrofit;
    }

}

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.news.musicwiki.Networking.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/* RetrofitBuilder object(we will use it directly without creating any instance
of it. It is equivalent to static class in Java)*/
object RetrofitBuilder {
    //Our BASE API URL
    private const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"
    private var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    /*We will use RetrofitBuilder.apiService later in our app
    As RetrofitBuilder is an object class we need not create any instance
    of the same to call a variable*/
    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}
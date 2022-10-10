package com.example.studioghibliapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.studioghibliapp.models.Film
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmDetailsActivity : AppCompatActivity() {
    private lateinit var filmId: String
    lateinit var film: Film

    lateinit var ivImage: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvDescription: TextView
    lateinit var tvYear: TextView
    lateinit var tvDirector: TextView
    lateinit var tvProducer: TextView
    lateinit var tvScore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_details)

        filmId = intent.getStringExtra("FilmID").toString()

        initializeVariables()
        setupAdapterRESTService()
    }

    private fun initializeVariables() {
        ivImage = findViewById(R.id.iv_image)
        tvTitle = findViewById(R.id.tv_title)
        tvDescription = findViewById(R.id.tv_description)
        tvYear = findViewById(R.id.tv_year)
        tvDirector = findViewById(R.id.tv_director)
        tvProducer = findViewById(R.id.tv_producer)
        tvScore = findViewById(R.id.tv_score)
    }

    private fun setupAdapterRESTService() {
        val api = RetrofitClient.retrofit.create(StudioGhibliAPI::class.java)
        val callGetFilm = api.getFilm(filmId)
        callGetFilm.enqueue(object: Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                val filmRest = response.body()
                if(filmRest !== null) {
                    film = filmRest;

                    Picasso.get().load(film.movie_banner).into(ivImage)
                    tvTitle.text = film.title
                    tvDescription.text = film.description
                    tvYear.text = film.release_date
                    tvDirector.text = film.director
                    tvProducer.text = film.producer
                    tvScore.text = film.rt_score
                }
            }

            override fun onFailure(call: Call<Film>, t: Throwable) {
                Log.e("REST", t.message?: "")
            }
        })
    }
}
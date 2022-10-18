package com.example.studioghibliapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.studioghibliapp.models.Film
import com.example.studioghibliapp.models.YesNo
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
    lateinit var tvUndecided: TextView
    lateinit var ivYesNoImage: ImageView
    lateinit var tvYesNoResponse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_details)

        filmId = intent.getStringExtra("FilmID").toString()

        initializeVariables()
        setupAdapterRESTService()

        handleOnClickUndecided()
    }

    private fun initializeVariables() {
        ivImage = findViewById(R.id.iv_image)
        tvTitle = findViewById(R.id.tv_title)
        tvDescription = findViewById(R.id.tv_description)
        tvYear = findViewById(R.id.tv_year)
        tvDirector = findViewById(R.id.tv_director)
        tvProducer = findViewById(R.id.tv_producer)
        tvScore = findViewById(R.id.tv_score)
        tvUndecided = findViewById(R.id.tv_undecided)
        ivYesNoImage = findViewById(R.id.iv_yes_no_image)
        tvYesNoResponse = findViewById(R.id.tv_yes_no_response)
    }

    private fun handleOnClickUndecided() {
        tvUndecided.setOnClickListener {
            Log.i("PRESIONADO", "PRESIONADO")

            val api = RetrofitClient.retrofitYesNoAPI.create(YesNoAPI::class.java)
            val callYesNo= api.getYesOrNo()
            callYesNo.enqueue(object: Callback<YesNo> {
                override fun onResponse(call: Call<YesNo>, response: Response<YesNo>) {
                    val yesNoResponse = response.body()

                    if (yesNoResponse != null) {
                        Picasso.get().load(yesNoResponse.image).into(ivYesNoImage)
                        tvYesNoResponse.text = yesNoResponse.answer.uppercase()
                    }
                }

                override fun onFailure(call: Call<YesNo>, t: Throwable) {
                    Log.e("REST", t.message?: "")
                }
            })
        }
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
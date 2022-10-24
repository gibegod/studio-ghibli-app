package com.example.studioghibliapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.studioghibliapp.models.Film
import com.example.studioghibliapp.models.Review
import com.example.studioghibliapp.models.User
import com.example.studioghibliapp.models.YesNo
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmDetailsActivity : AppCompatActivity() {
    private lateinit var filmId: String
    private lateinit var username: String
    private lateinit var userBD: User
    private var reviewDB: Review? = null
    lateinit var film: Film

    lateinit var ivImage: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvDescription: TextView
    lateinit var tvYear: TextView
    lateinit var tvDirector: TextView
    lateinit var tvProducer: TextView
    lateinit var tvScore: TextView
    lateinit var tvUndecided: TextView
    lateinit var etScore: EditText
    lateinit var etComments: EditText
    lateinit var btnSaveReview: Button
    lateinit var ivYesNoImage: ImageView
    lateinit var tvYesNoResponse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_details)

        filmId = intent.getStringExtra("FilmID").toString()
        username = intent.getStringExtra("Username").toString()

        initializeVariables()
        getUser()
        getReview()

        setupAdapterRESTService()

        handleOnClickUndecided()
        handleOnClickSaveReview()
    }

    private fun getUser() {
        try {
            userBD = UserManager.getInstance(this@FilmDetailsActivity).getUserByUsername(username)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getReview() {
        try {
            reviewDB = ReviewManager.getInstance(this@FilmDetailsActivity).getReviewByUserIdAndFilmId(userBD.id, filmId)
            if(reviewDB !== null) {
                etScore.setText(reviewDB!!.score.toString())
                etComments.setText(reviewDB!!.comment)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
        etScore = findViewById(R.id.et_score)
        etComments = findViewById(R.id.et_comments)
        btnSaveReview = findViewById(R.id.btn_save_review)
        ivYesNoImage = findViewById(R.id.iv_yes_no_image)
        tvYesNoResponse = findViewById(R.id.tv_yes_no_response)
    }

    private fun handleOnClickUndecided() {
        tvUndecided.setOnClickListener {
            val api = RetrofitClient.retrofitYesNoAPI.create(YesNoAPI::class.java)
            val callYesNo = api.getYesOrNo()
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


    private fun handleOnClickSaveReview() {
        btnSaveReview.setOnClickListener {
            try {
                var score = etScore.text.toString()
                var comment = etComments.text.toString()

                // Validations
                if(score == "" || score.toInt() < 0 || score.toInt() > 100) {
                    Toast.makeText(this, "El puntaje es inválido, debe ser un número entre 0 y 100", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener;
                }

                var review = reviewDB;

                if(review != null) {
                    review.score = score.toInt()
                    review.comment = comment
                } else {
                    review = Review(userBD, filmId, score.toInt(), comment)
                }

                ReviewManager.getInstance(this@FilmDetailsActivity).createOrUpdateReview(review)

                Toast.makeText(this, "Reseña guardada exitosamente", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
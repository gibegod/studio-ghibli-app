package com.example.studioghibliapp

import com.example.studioghibliapp.RetrofitClient.retrofit
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import android.widget.Spinner
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.studioghibliapp.models.Film
import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var layout: LinearLayout
    lateinit var toolbar: Toolbar
    lateinit var rvFilms: RecyclerView
    lateinit var adapter: FilmAdapter
    private lateinit var itemsSelector: Spinner
    var itemsSelectorOptions = arrayOf<String?>("5", "10", "50")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Peliculas"

        setupAdapter()
        setupAdapterRESTService()

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        itemsSelector = findViewById(R.id.sp_items_selector)
        itemsSelector.onItemSelectedListener = this

        //Creating the ArrayAdapter instance having the country list
        val aa: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, itemsSelectorOptions)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Setting the ArrayAdapter data on the Spinner
        itemsSelector.adapter = aa
    }

    private fun setupAdapterRESTService() {
        var responseList: MutableList<Film> = mutableListOf()

        val api = retrofit.create(StudioGhibliAPI::class.java)
        val callGetFilms = api.getFilms()
        callGetFilms.enqueue(object: Callback<List<Film>?> {
            override fun onResponse(call: Call<List<Film>?>, response: Response<List<Film>?>) {
                val filmsRest = response.body()

                filmsRest?.forEach {
                    val film = Film(
                        it.id,
                        it.title,
                        it.image,
                        it.movie_banner,
                        it.description,
                        it.director,
                        it.release_date,
                        it.producer,
                        it.rt_score
                    )
                    responseList.add(film)
                }

                rvFilms = findViewById(R.id.rv_films)
                FilmAdapter(responseList) {
                    val filmDetailsActivity = Intent(this@MainActivity, FilmDetailsActivity::class.java)
                    filmDetailsActivity.putExtra("FilmID", it.id)
                    startActivity(filmDetailsActivity)
                }.let {
                    rvFilms.adapter = it
                }
            }

            override fun onFailure(call: Call<List<Film>?>, t: Throwable) {
                Log.e("REST", t.message?: "")
            }
        })
    }

    private fun setupAdapter() {
        var emptyFilmList: MutableList<Film> = mutableListOf()

        rvFilms = findViewById(R.id.rv_films)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                FilmAdapter(emptyFilmList) {}.let {
                    rvFilms.adapter = it
                }
            }
        }

        layout = findViewById(R.id.ll_layout)
        val dividerItemDecoration = DividerItemDecoration(rvFilms.context, layout.orientation)
        rvFilms.addItemDecoration(dividerItemDecoration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_exit -> {
                val sp = applicationContext.getSharedPreferences(Consts.SP_CREDENTIALS, MODE_PRIVATE)
                val spEditor = sp.edit()
                spEditor.putString(Consts.USER, null)
                spEditor.putString(Consts.PASSWORD, null)
                spEditor.apply()
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.item_Personajes -> {
                val intent0 = Intent(this@MainActivity, PersonajesActivity::class.java)
                startActivity(intent0)
            }
            R.id.item_Vehiculos -> {
                val intent1 = Intent(this@MainActivity, VehiculosActivity::class.java)
                startActivity(intent1)
            }
            R.id.item_Especies -> {
                val intent2 = Intent(this@MainActivity, EspeciesActivity::class.java)
                startActivity(intent2)
            }
            R.id.item_Lugares -> {
                val intent3 = Intent(this@MainActivity, LugaresActivity::class.java)
                startActivity(intent3)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Performing action onItemSelected and onNothing selected
    override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, l: Long) {
        //Toast.makeText(getApplicationContext(),itemsSelectorOptions[position], Toast.LENGTH_LONG).show();
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {
        // TODO Auto-generated method stub
    }
}

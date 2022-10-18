package com.example.studioghibliapp

import com.example.studioghibliapp.RetrofitClient.retrofit
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import android.widget.Spinner
import android.os.Bundle
import android.widget.ArrayAdapter
import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.studioghibliapp.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var layout: LinearLayout
    lateinit var toolbar: Toolbar
    lateinit var rvItems: RecyclerView
    private lateinit var itemsSelector: Spinner

    var itemsSelectorOptions = arrayOf<Number?>(5, 10, 50)

    private val api: StudioGhibliAPI = retrofit.create(StudioGhibliAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Peliculas"

        rvItems = findViewById(R.id.rv_items)

        setupAdapter()
        setupAdapterFilms()

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        itemsSelector = findViewById(R.id.sp_items_selector)
        itemsSelector.onItemSelectedListener = this

        //Creating the ArrayAdapter instance having the country list
        val aa: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, itemsSelectorOptions)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //Setting the ArrayAdapter data on the Spinner
        itemsSelector.adapter = aa

        itemsSelector.setSelection(2); // 50
    }

    private fun setupAdapterFilms() {
        var responseList: MutableList<Film> = mutableListOf()

        val callGetFilms = api.getFilms()
        callGetFilms.enqueue(object: Callback<List<Film>?> {
            override fun onResponse(call: Call<List<Film>?>, response: Response<List<Film>?>) {
                val filmsRest = response.body()

                var counter = 0;
                filmsRest?.forEach {
                    if (counter < itemsSelector.selectedItem.toString().toInt()){
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
                        counter++
                    }
                }

                FilmAdapter(responseList) {
                    val filmDetailsActivity = Intent(this@MainActivity, FilmDetailsActivity::class.java)
                    filmDetailsActivity.putExtra("FilmID", it.id)
                    startActivity(filmDetailsActivity)
                }.let {
                    rvItems.adapter = it
                }
            }

            override fun onFailure(call: Call<List<Film>?>, t: Throwable) {
                Log.e("REST", t.message?: "")
            }
        })
    }

    private fun setupAdapterPeopleList() {
        var responseList: MutableList<People> = mutableListOf()

        val callGetPeopleList = api.getPeople()
        callGetPeopleList.enqueue(object: Callback<List<People>?> {
            override fun onResponse(call: Call<List<People>?>, response: Response<List<People>?>) {
                val peopleRest = response.body()

                var counter = 0;
                peopleRest?.forEach {
                    if (counter < itemsSelector.selectedItem.toString().toInt()) {
                        var people = People(it.id, it.name, it.gender, it.age)
                        responseList.add(people)
                        counter++
                    }
                }

                PeopleAdapter(responseList) {}.let {
                    rvItems.adapter = it
                }
            }

            override fun onFailure(call: Call<List<People>?>, t: Throwable) {
                Log.e("REST", t.message?: "")
            }
        })
    }

    private fun setupAdapterLocations() {
        var responseList: MutableList<Location> = mutableListOf()

        val callGetLocations = api.getLocations()
        callGetLocations.enqueue(object: Callback<List<Location>?> {
            override fun onResponse(call: Call<List<Location>?>, response: Response<List<Location>?>) {
                val locationsRest = response.body()

                var counter = 0
                locationsRest?.forEach {
                    if (counter < itemsSelector.selectedItem.toString().toInt()) {
                        var location = Location(it.id, it.name)
                        responseList.add(location)
                        counter++
                    }
                }

                LocationAdapter(responseList) {}.let {
                    rvItems.adapter = it
                }
            }

            override fun onFailure(call: Call<List<Location>?>, t: Throwable) {
                Log.e("REST", t.message?: "")
            }
        })
    }

    private fun setupAdapterVehicles() {
        var responseList: MutableList<Vehicle> = mutableListOf()

        val callGetVehicles = api.getVehicles()
        callGetVehicles.enqueue(object: Callback<List<Vehicle>?> {
            override fun onResponse(call: Call<List<Vehicle>?>, response: Response<List<Vehicle>?>) {
                val vehiclesRest = response.body()

                var counter = 0
                vehiclesRest?.forEach {
                    if (counter < itemsSelector.selectedItem.toString().toInt()) {
                        var vehicle = Vehicle(it.id, it.name, it.description, it.vehicle_class)
                        responseList.add(vehicle)
                        counter++
                    }
                }

                VehicleAdapter(responseList) {}.let {
                    rvItems.adapter = it
                }
            }

            override fun onFailure(call: Call<List<Vehicle>?>, t: Throwable) {
                Log.e("REST", t.message?: "")
            }
        })
    }

    private fun setupAdapterSpeciesList() {
        var responseList: MutableList<Species> = mutableListOf()

        val callGetSpeciesList = api.getSpecies()
        callGetSpeciesList.enqueue(object: Callback<List<Species>?> {
            override fun onResponse(call: Call<List<Species>?>, response: Response<List<Species>?>) {
                val speciesListRest = response.body()

                var counter = 0
                speciesListRest?.forEach {
                    if (counter < itemsSelector.selectedItem.toString().toInt()) {
                        var species = Species(it.id, it.name, it.classification)
                        responseList.add(species)
                        counter++
                    }
                }

                SpeciesAdapter(responseList) {}.let {
                    rvItems.adapter = it
                }
            }

            override fun onFailure(call: Call<List<Species>?>, t: Throwable) {
                Log.e("REST", t.message?: "")
            }
        })
    }

    private fun setupAdapter() {
        var emptyFilmList: MutableList<Film> = mutableListOf()

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                FilmAdapter(emptyFilmList) {}.let {
                    rvItems.adapter = it
                }
            }
        }

        layout = findViewById(R.id.ll_layout)
        val dividerItemDecoration = DividerItemDecoration(rvItems.context, layout.orientation)
        rvItems.addItemDecoration(dividerItemDecoration)
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
            R.id.item_films -> {
                setupAdapterFilms();
                supportActionBar!!.title = "Peliculas"
            }
            R.id.item_people -> {
                setupAdapterPeopleList();
                supportActionBar!!.title = "Personajes"
            }
            R.id.item_vehicles -> {
                setupAdapterVehicles()
                supportActionBar!!.title = "Vehículos"
            }
            R.id.item_species -> {
                setupAdapterSpeciesList()
                supportActionBar!!.title = "Especies"
            }
            R.id.item_locations -> {
                setupAdapterLocations();
                supportActionBar!!.title = "Lugares"
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Performing action onItemSelected and onNothing selected
    override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, l: Long) {
        if(supportActionBar!!.title === "Peliculas") {
            setupAdapterFilms()
        } else if(supportActionBar!!.title === "Personajes") {
            setupAdapterPeopleList();
        } else if(supportActionBar!!.title === "Vehículos") {
            setupAdapterVehicles()
        } else if(supportActionBar!!.title === "Especies") {
            setupAdapterSpeciesList()
        } else if(supportActionBar!!.title === "Lugares") {
            setupAdapterLocations();
        }
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) {
        // TODO Auto-generated method stub
    }
}

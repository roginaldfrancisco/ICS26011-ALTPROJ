package com.ite.ics26011_altproj

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import androidx.appcompat.widget.Toolbar
import android.widget.ImageButton
import android.widget.ImageView

class FlightTable : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var searchBox: EditText
    private lateinit var tableLayout: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flight_table) // Ensure the layout file matches

        // Apply system bar insets to the TableLayout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tableLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = FirebaseFirestore.getInstance()
        searchBox = findViewById(R.id.searchBox)
        tableLayout = findViewById(R.id.tableLayout)

        // Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup Hamburger Icon
        val hamburgerIcon: ImageButton = findViewById(R.id.hamburger_icon)
        hamburgerIcon.setOnClickListener {
            // Handle navigation drawer toggle or menu click
            Toast.makeText(this, "Hamburger icon clicked", Toast.LENGTH_SHORT).show()
        }

        // Setup Logo
        val logo: ImageView = findViewById(R.id.logo)

        // Fetch flights and populate the table
        fetchFlights()

        // Add listener to filter flights as the user types
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().lowercase()
                fetchFlights(searchText)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun fetchFlights(filter: String = "") {
        db.collection("flights")
            .get()
            .addOnSuccessListener { result ->
                tableLayout.removeAllViews() // Clear existing rows

                for (document in result) {
                    val flightNumber = document.getString("flightNumber") ?: ""
                    val destination = document.getString("destination") ?: ""
                    val time = document.getString("time") ?: ""

                    // Apply the filter (if any)
                    if (filter.isEmpty() ||
                        flightNumber.lowercase().contains(filter) ||
                        destination.lowercase().contains(filter)
                    ) {
                        addRowToTable(flightNumber, destination, time)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load flights", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addRowToTable(flightNumber: String, destination: String, time: String) {
        val row = TableRow(this)

        val flightNumberView = TextView(this).apply {
            text = flightNumber
            setPadding(16, 8, 16, 8) // Add padding for better appearance
        }
        val destinationView = TextView(this).apply {
            text = destination
            setPadding(16, 8, 16, 8)
        }
        val timeView = TextView(this).apply {
            text = time
            setPadding(16, 8, 16, 8)
        }

        row.addView(flightNumberView)
        row.addView(destinationView)
        row.addView(timeView)

        tableLayout.addView(row)
    }
}


class FlightTableActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var searchBox: EditText
    private lateinit var tableLayout: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flight_table) // Ensure the layout file matches

        // Apply system bar insets to the TableLayout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tableLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = FirebaseFirestore.getInstance()
        searchBox = findViewById(R.id.searchBox)
        tableLayout = findViewById(R.id.tableLayout)

        // Setup Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup Hamburger Icon
        val hamburgerIcon: ImageButton = findViewById(R.id.hamburger_icon)
        hamburgerIcon.setOnClickListener {
            // Handle navigation drawer toggle or menu click
            Toast.makeText(this, "Hamburger icon clicked", Toast.LENGTH_SHORT).show()
        }

        // Setup Logo
        val logo: ImageView = findViewById(R.id.logo)

        // Fetch flights and populate the table
        fetchFlights()

        // Add listener to filter flights as the user types
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString().lowercase()
                fetchFlights(searchText)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun fetchFlights(filter: String = "") {
        db.collection("flights")
            .get()
            .addOnSuccessListener { result ->
                tableLayout.removeAllViews() // Clear existing rows

                for (document in result) {
                    val flightNumber = document.getString("flightNumber") ?: ""
                    val destination = document.getString("destination") ?: ""
                    val time = document.getString("time") ?: ""

                    // Apply the filter (if any)
                    if (filter.isEmpty() ||
                        flightNumber.lowercase().contains(filter) ||
                        destination.lowercase().contains(filter)
                    ) {
                        addRowToTable(flightNumber, destination, time)
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load flights", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addRowToTable(flightNumber: String, destination: String, time: String) {
        val row = TableRow(this)

        val flightNumberView = TextView(this).apply {
            text = flightNumber
            setPadding(16, 8, 16, 8) // Add padding for better appearance
        }
        val destinationView = TextView(this).apply {
            text = destination
            setPadding(16, 8, 16, 8)
        }
        val timeView = TextView(this).apply {
            text = time
            setPadding(16, 8, 16, 8)
        }

        row.addView(flightNumberView)
        row.addView(destinationView)
        row.addView(timeView)

        tableLayout.addView(row)
    }
}

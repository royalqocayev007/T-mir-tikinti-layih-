package com.royal.masterbuilder

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etLength = findViewById<EditText>(R.id.etLength)
        val etWidth = findViewById<EditText>(R.id.etWidth)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val spMaterial = findViewById<Spinner>(R.id.spMaterial)
        val btnCalc = findViewById<Button>(R.id.btnCalc)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        val materials = arrayOf("Beton (m³)", "Kafel (m²)", "Boya (m²)", "Sement (kisə)")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, materials)
        spMaterial.adapter = adapter

        btnCalc.setOnClickListener {
            val l = etLength.text.toString().toFloatOrNull() ?: 0f
            val w = etWidth.text.toString().toFloatOrNull() ?: 0f
            val h = etHeight.text.toString().toFloatOrNull() ?: 0f

            if (l <= 0 || w <= 0) {
                tvResult.text = "Zəhmət olmasa ölçüləri düzgün daxil edin!"
                return@setOnClickListener
            }

            val result = when (spMaterial.selectedItemPosition) {
                0 -> { // Beton
                    val volume = l * w * h
                    "Lazım olan beton: $volume m³\n(1 m³ beton ≈ 7 kisə sement + 0.5 m³ qum + 0.8 m³ çınqıl)"
                }
                1 -> { // Kafel
                    val area = l * w
                    val tiles = ceil(area).toInt()
                    "Kafel sahəsi: $area m²\nTəxminən $tiles ədəd 1x1 m kafel lazımdır."
                }
                2 -> { // Boya
                    val area = (2 * (l * h)) + (2 * (w * h))
                    "Divar sahəsi: $area m²\n1 litr boya ≈ 10 m² çəkir. Təxminən ${ceil(area / 10).toInt()} litr boya al."
                }
                else -> { // Sement
                    val volume = l * w * h
                    val bags = ceil(volume * 7).toInt()
                    "Təxminən $bags kisə sement lazımdır."
                }
            }
            tvResult.text = result
        }
    }
}

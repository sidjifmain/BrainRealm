package com.example.brainrealm

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.brainrealm.databinding.ActivityQuestionBinding


class Question : AppCompatActivity() {
    lateinit var binding: ActivityQuestionBinding
    private lateinit var popupManager: PopupManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resolvedColor = ContextCompat.getColor(this, R.color.gray)


        popupManager = PopupManager(this)

        val sorular: List<QuestionModel> = listOf(
            QuestionModel(
                "Uzaq ekzoplanetlərin atmosferlərini təhlil etmək üçün adətən hansı üsuldan istifadə olunur?",
                listOf(
                    "a) İnfraqırmızı spektroskopiya",
                    "b) Ultraviyole görüntüləmə",
                    "c) Radioteleskoplar",
                    "d) Qravitasiya linzalanması"
                ),
                "a) İnfraqırmızı spektroskopiya"
            ),
            QuestionModel(
                "“Super Yer”i digər ekzoplanetlərdən nə ilə fərqləndirir?",
                listOf(
                    "a) Yerdən böyük, Neptundan kiçik",
                    "b) Goldilocks zonasının orbiti",
                    "c) Maqnit sahəsinin mövcudluğu",
                    "d) Unikal atmosfer tərkibi"
                ),
                "a) Yerdən böyük, Neptundan kiçik"
            ),
            QuestionModel(
                "Nəyə görə bəzi ekzoplanetlər öz ulduzları ilə sıx bağlı ola bilər?",
                listOf(
                    "a) Sürətli fırlanma",
                    "b) Güclü maqnit sahələri",
                    "c) Qravitasiya qarşılıqlı təsirləri",
                    "d) Dəyişən ulduz parlaqlığı"
                ),
                "c) Qravitasiya qarşılıqlı təsirləri"
            ),
            QuestionModel(
                "Ekzoplanetlər arasında “Qaynar Yupiterlər”in xarakterik xüsusiyyəti nədir?",
                listOf(
                    "a) Frigid səth temperaturları",
                    "b) Ev sahibi ulduza yaxınlıq",
                    "c) Geniş halqa sistemləri",
                    "d) Sıx metal tərkibi"
                ),
                "b) Ev sahibi ulduza yaxınlıq"
            ),
            QuestionModel(
                "Yaşayış üçün əlverişli ekzoplanetlərin axtarışında yaşayış zonası nəyə istinad edir?",
                listOf(
                    "a) Maye su üçün ana ulduzdan məsafə",
                    "b) Atmosferin tərkibi",
                    "c) Maqnit sahəsinin gücü",
                    "d) Səthin cazibə qüvvəsi"
                ),
                "a) Maye su üçün ana ulduzdan məsafə"
            ),
            QuestionModel(
                "Uzaq ekzoplanetlərin orbitində fırlanan ekzomunların aşkar edilməsində hansı çətinliklər var?",
                listOf(
                    "a) Böyük ölçü",
                    "b) Məhdud müşahidə üsulları",
                    "c) Yüksək əks etdirmə qabiliyyəti",
                    "d) Stabil orbit nümunələri"
                ),
                "b) Məhdud müşahidə üsulları"
            )
        )


        val gelenIntent = intent
        val gelenData = gelenIntent.getStringExtra("planet")



        when (gelenData) {
            "planet1" -> {
                var selectedVariant = "j"
                binding.sual.text = sorular[0].text
                binding.aTextview.text = sorular[0].options[0]
                binding.bTexview.text = sorular[0].options[1]
                binding.cTextview.text = sorular[0].options[2]
                binding.dTextview.text = sorular[0].options[3]

                binding.aTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.select_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "a"
                }
                binding.bTexview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.select_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "b"
                }
                binding.cTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.select_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "c"
                }
                binding.dTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.select_rectangle)
                    selectedVariant = "d"
                }

                binding.birx.setOnClickListener {
                    binding.cTextview.setTextColor(resolvedColor)
                    binding.cTextview.isEnabled = false
                }
                binding.ikix.setOnClickListener {
                    binding.dTextview.setTextColor(resolvedColor)
                    binding.bTexview.setTextColor(resolvedColor)
                    binding.dTextview.isEnabled = false
                    binding.bTexview.isEnabled = false
                }

                binding.cavabiTesdiqle.setOnClickListener {
                    if (selectedVariant == "a") {
                        popupManager.showPopup(it)
                    } else if (selectedVariant == "j") {
                        Toast.makeText(this, "Bir variant secin", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(this, "Yanlish Cavab", Toast.LENGTH_LONG).show()
                        finish()

                    }
                }
            }

            "planet2" -> {
                var selectedVariant = "j"
                binding.sual.text = sorular[1].text
                binding.aTextview.text = sorular[1].options[0]
                binding.bTexview.text = sorular[1].options[1]
                binding.cTextview.text = sorular[1].options[2]
                binding.dTextview.text = sorular[1].options[3]

                binding.aTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.select_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "a"
                }
                binding.bTexview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.select_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "b"
                }
                binding.cTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.select_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "c"
                }
                binding.dTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.select_rectangle)
                    selectedVariant = "d"
                }

                binding.birx.setOnClickListener {
                    binding.cTextview.setTextColor(resolvedColor)
                    binding.cTextview.isEnabled = false
                }
                binding.ikix.setOnClickListener {
                    binding.dTextview.setTextColor(resolvedColor)
                    binding.bTexview.setTextColor(resolvedColor)
                    binding.dTextview.isEnabled = false
                    binding.bTexview.isEnabled = false
                }

                binding.cavabiTesdiqle.setOnClickListener {
                    if (selectedVariant == "a") {
                        popupManager.showPopup(it)
                    } else if (selectedVariant == "j") {
                        Toast.makeText(this, "Bir variant secin", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(this, "Yanlish Cavab", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }

            "planet3" -> {
                var selectedVariant = "j"
                binding.sual.text = sorular[2].text
                binding.aTextview.text = sorular[2].options[0]
                binding.bTexview.text = sorular[2].options[1]
                binding.cTextview.text = sorular[2].options[2]
                binding.dTextview.text = sorular[2].options[3]

                binding.aTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.select_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "a"
                }
                binding.bTexview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.select_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "b"
                }
                binding.cTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.select_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "c"
                }
                binding.dTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.select_rectangle)
                    selectedVariant = "d"
                }

                binding.birx.setOnClickListener {
                    binding.aTextview.setTextColor(resolvedColor)
                    binding.aTextview.isEnabled = false
                }
                binding.ikix.setOnClickListener {
                    binding.dTextview.setTextColor(resolvedColor)
                    binding.bTexview.setTextColor(resolvedColor)
                    binding.dTextview.isEnabled = false
                    binding.bTexview.isEnabled = false
                }
                binding.cavabiTesdiqle.setOnClickListener {
                    if (selectedVariant == "c") {
                        popupManager.showPopup(it)
                    } else if (selectedVariant == "j") {
                        Toast.makeText(this, "Bir variant secin", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(this, "Yanlish Cavab", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }

            "planet4" -> {
                var selectedVariant = "j"
                binding.sual.text = sorular[3].text
                binding.aTextview.text = sorular[3].options[0]
                binding.bTexview.text = sorular[3].options[1]
                binding.cTextview.text = sorular[3].options[2]
                binding.dTextview.text = sorular[3].options[3]

                binding.aTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.select_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "a"
                }
                binding.bTexview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.select_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "b"
                }
                binding.cTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.select_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "c"
                }
                binding.dTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.select_rectangle)
                    selectedVariant = "d"
                }

                binding.birx.setOnClickListener {
                    binding.cTextview.setTextColor(resolvedColor)
                    binding.cTextview.isEnabled = false
                }
                binding.ikix.setOnClickListener {
                    binding.dTextview.setTextColor(resolvedColor)
                    binding.aTextview.setTextColor(resolvedColor)
                    binding.dTextview.isEnabled = false
                    binding.aTextview.isEnabled = false
                }
                binding.cavabiTesdiqle.setOnClickListener {
                    if (selectedVariant == "b") {
                        popupManager.showPopup(it)
                    } else if (selectedVariant == "j") {
                        Toast.makeText(this, "Bir variant secin", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(this, "Yanlish Cavab", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }

            "planet5" -> {
                var selectedVariant = "j"
                binding.sual.text = sorular[4].text
                binding.aTextview.text = sorular[4].options[0]
                binding.bTexview.text = sorular[4].options[1]
                binding.cTextview.text = sorular[4].options[2]
                binding.dTextview.text = sorular[4].options[3]

                binding.aTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.select_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "a"
                }
                binding.bTexview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.select_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "b"
                }
                binding.cTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.select_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "c"
                }
                binding.dTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.select_rectangle)
                    selectedVariant = "d"
                }

                binding.birx.setOnClickListener {
                    binding.cTextview.setTextColor(resolvedColor)
                    binding.cTextview.isEnabled = false
                }
                binding.ikix.setOnClickListener {
                    binding.dTextview.setTextColor(resolvedColor)
                    binding.bTexview.setTextColor(resolvedColor)
                    binding.dTextview.isEnabled = false
                    binding.bTexview.isEnabled = false
                }

                binding.cavabiTesdiqle.setOnClickListener {
                    if (selectedVariant == "a") {
                        popupManager.showPopup(it)
                    } else if (selectedVariant == "j") {
                        Toast.makeText(this, "Bir variant secin", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(this, "Yanlish Cavab", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }

            "planet6" -> {
                var selectedVariant = "j"
                binding.sual.text = sorular[5].text
                binding.aTextview.text = sorular[5].options[0]
                binding.bTexview.text = sorular[5].options[1]
                binding.cTextview.text = sorular[5].options[2]
                binding.dTextview.text = sorular[5].options[3]

                binding.aTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.select_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "a"
                }
                binding.bTexview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.select_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "b"
                }
                binding.cTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.select_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "c"
                }
                binding.dTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.select_rectangle)
                    selectedVariant = "d"
                }

                binding.birx.setOnClickListener {
                    binding.cTextview.setTextColor(resolvedColor)
                    binding.cTextview.isEnabled = false
                }
                binding.ikix.setOnClickListener {
                    binding.dTextview.setTextColor(resolvedColor)
                    binding.aTextview.setTextColor(resolvedColor)
                    binding.aTextview.isEnabled = false
                    binding.bTexview.isEnabled = false
                }

                binding.cavabiTesdiqle.setOnClickListener {
                    if (selectedVariant == "b") {
                        popupManager.showPopup(it)
                    } else if (selectedVariant == "j") {
                        Toast.makeText(this, "Bir variant secin", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(this, "Yanlish Cavab", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }

            "planetx" -> {
                var selectedVariant = "j"
                binding.sual.text = sorular[0].text
                binding.aTextview.text = sorular[0].options[0]
                binding.bTexview.text = sorular[0].options[1]
                binding.cTextview.text = sorular[0].options[2]
                binding.dTextview.text = sorular[0].options[3]

                binding.aTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.select_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "a"
                }
                binding.bTexview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.select_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "b"
                }
                binding.cTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.select_rectangle)
                    binding.dimage.setImageResource(R.drawable.question_rectangle)
                    selectedVariant = "c"
                }
                binding.dTextview.setOnClickListener {
                    binding.aimage.setImageResource(R.drawable.question_rectangle)
                    binding.bimage.setImageResource(R.drawable.question_rectangle)
                    binding.cimage.setImageResource(R.drawable.question_rectangle)
                    binding.dimage.setImageResource(R.drawable.select_rectangle)
                    selectedVariant = "d"
                }

                binding.birx.setOnClickListener {
                    binding.cTextview.setTextColor(resolvedColor)
                    binding.cTextview.isEnabled = false
                }
                binding.ikix.setOnClickListener {
                    binding.dTextview.setTextColor(resolvedColor)
                    binding.bTexview.setTextColor(resolvedColor)
                    binding.dTextview.isEnabled = false
                    binding.bTexview.isEnabled = false
                }

                binding.cavabiTesdiqle.setOnClickListener {
                    if (selectedVariant == "a") {
                        popupManager.showPopup2(it)
                    } else if (selectedVariant == "j") {
                        Toast.makeText(this, "Bir variant secin", Toast.LENGTH_LONG).show()

                    } else {
                        Toast.makeText(this, "Yanlish Cavab", Toast.LENGTH_LONG).show()
                        finish()

                    }
                }


            }


        }


    }

    class PopupManager(private val context: Context) {

        private lateinit var popupWindow: PopupWindow

        fun showPopup(anchorView: View) {
            // PopupView'ı yükleyin
            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView: View = inflater.inflate(R.layout.qazandiniz_window, null)

            // PopupWindow'u oluşturun
            popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            // Close button işlevselliği
            val closeButton: Button = popupView.findViewById(R.id.homeBtn)
            closeButton.setOnClickListener {

                if (context is Activity) {
                    (context as Activity).finish()
                }

            }

            // PopupWindow'u göster
            showPopupAtLocation(anchorView, Gravity.CENTER, 0, 0)
        }

        fun showPopup2(anchorView: View) {
            // PopupView'ı yükleyin
            val inflater: LayoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView: View = inflater.inflate(R.layout.qazandiniz2_window, null)

            // PopupWindow'u oluşturun
            popupWindow = PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )

            // Close button işlevselliği
            val closeButton: Button = popupView.findViewById(R.id.qazandiniz2_home_btn)
            closeButton.setOnClickListener {

                if (context is Activity) {
                    (context as Activity).finish()
                }

            }

            // PopupWindow'u göster
            showPopupAtLocation(anchorView, Gravity.CENTER, 0, 0)
        }

        private fun showPopupAtLocation(anchorView: View, gravity: Int, x: Int, y: Int) {
            popupWindow.showAtLocation(anchorView, gravity, x, y)
        }


    }
}

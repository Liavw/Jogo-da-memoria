package prova.web.myjogodamemoria

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Button>
    private lateinit var buttonTags: Array<Int?>
    private var selectedButtonIndex: Int? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startGame()
    }

    private fun startGame() {
        val images = mutableListOf(
            R.drawable.img1, R.drawable.img1,
            R.drawable.img2, R.drawable.img2,
            R.drawable.img3, R.drawable.img3,
            R.drawable.img4, R.drawable.img4,
            R.drawable.img5, R.drawable.img5,
            R.drawable.img6, R.drawable.img6
        )

        images.shuffle()

        val row1 = findViewById<LinearLayout>(R.id.row1)
        val row2 = findViewById<LinearLayout>(R.id.row2)
        val row3 = findViewById<LinearLayout>(R.id.row3)
        val row4 = findViewById<LinearLayout>(R.id.row4)

        val rows = listOf(row1, row2, row3, row4)

        // Remove todos os botões antes de reiniciar
        rows.forEach { it.removeAllViews() }

        buttons = Array(12) { i ->
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1f
            ).apply {
                marginEnd = 8
                marginStart = 8
            }
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_200))
            button.setOnClickListener {
                onCardClicked(i)
            }
            rows[i / 3].addView(button)
            button
        }

        buttonTags = Array(12) { i -> images[i] }
        selectedButtonIndex = null
    }

    private fun onCardClicked(index: Int) {
        val button = buttons[index]
        val tag = buttonTags[index]

        button.setBackgroundResource(tag ?: 0)

        if (selectedButtonIndex == null) {
            selectedButtonIndex = index
        } else {
            val selectedTag = buttonTags[selectedButtonIndex!!]
            if (tag == selectedTag && index != selectedButtonIndex) {
                buttons[index].isEnabled = false
                buttons[selectedButtonIndex!!].isEnabled = false
                selectedButtonIndex = null
                if (buttons.all { !it.isEnabled }) {
                    Toast.makeText(this, "Parabéns! Você completou o jogo!", Toast.LENGTH_LONG).show()
                    handler.postDelayed({
                        startGame() // Reinicia o jogo após completar
                    }, 2000)
                }
            } else {
                handler.postDelayed({
                    button.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_200))
                    buttons[selectedButtonIndex!!].setBackgroundColor(ContextCompat.getColor(this, R.color.teal_200))
                    selectedButtonIndex = null
                }, 1000)
            }
        }
    }
}
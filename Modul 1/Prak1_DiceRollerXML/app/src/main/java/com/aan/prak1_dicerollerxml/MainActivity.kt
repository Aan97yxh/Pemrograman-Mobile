package com.aan.prak1_dicerollerxml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.graphics.toColorInt
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonRoll: Button = findViewById(R.id.buttonRoll)
        val diceImageLeft: ImageView = findViewById(R.id.DiceLeft)
        val diceImageRight: ImageView = findViewById(R.id.DiceRight)
        val resultMessage: TextView = findViewById(R.id.resultMessage)

        diceImageLeft.setImageResource(R.drawable.dice_0)
        diceImageRight.setImageResource(R.drawable.dice_0)

        buttonRoll.setOnClickListener {
            val randomIntLeft = Random.nextInt(1, 7)
            val randomIntRight = Random.nextInt(1, 7)

            val drawableResourceLeft = when (randomIntLeft) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }

            val drawableResourceRight = when (randomIntRight) {
                1 -> R.drawable.dice_1
                2 -> R.drawable.dice_2
                3 -> R.drawable.dice_3
                4 -> R.drawable.dice_4
                5 -> R.drawable.dice_5
                else -> R.drawable.dice_6
            }

            diceImageLeft.setImageResource(drawableResourceLeft)
            diceImageRight.setImageResource(drawableResourceRight)

            if (randomIntLeft == randomIntRight) {
                resultMessage.text = getString(R.string.congrats_message)
                resultMessage.setBackgroundColor("#D1C4E9".toColorInt())
            } else {
                resultMessage.text = getString(R.string.try_again_message)
                resultMessage.setBackgroundColor("#F5F5F5".toColorInt())
            }
        }
    }
}

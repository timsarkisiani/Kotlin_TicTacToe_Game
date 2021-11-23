package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var tsesebi: Button

    enum class Turn
    {
        RACKET,
        UFO
    }

    private var firstTurn = Turn.UFO
    private var currentTurn = Turn.UFO

    private var ufoScore = 0
    private var racketScore = 0

    private var boardList = mutableListOf<Button>()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
        tsesebi = findViewById(R.id.tsesebi)


        tsesebi.setOnClickListener(){
            Toast.makeText(applicationContext,"აღნიშნული თამაშისთვის საჭიროა ორი მოთამაშე. მოიგებს ის, ვინც პირველი დააწყობს თავის სამ სიმბოლოს ერთ სვეტში, სტრიქონში ან დიაგონალურად.", Toast.LENGTH_LONG).show()
        }
    }

    private fun initBoard()
    {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View)
    {
        if(view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(RACKET))
        {
            racketScore++
            result("მოიგო 🚀")
        }
        else if(checkForVictory(UFO))
        {
            ufoScore++
            result("მოიგო 🛸")
        }

        if(fullBoard())
        {
            result("უა-ჰა-ჰა! ყაიმი!")
        }

    }

    private fun checkForVictory(s: String): Boolean
    {
        //ჰორიზონტალურად
        if(match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true
        if(match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true
        if(match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true

        //ვერტიკალურად
        if(match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true
        if(match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true
        if(match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true

        //დიაგონალურად
        if(match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if(match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true

        return false
    }

    private fun match(button: Button, symbol : String): Boolean = button.text == symbol

    private fun result(title: String)
    {
        val message = "\n🚀 $racketScore\n\n🛸 $ufoScore"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("თავიდან დაწყება")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard()
    {
        for(button in boardList)
        {
            button.text = ""
        }

        if(firstTurn == Turn.RACKET)
            firstTurn = Turn.UFO
        else if(firstTurn == Turn.UFO)
            firstTurn = Turn.RACKET

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean
    {
        for(button in boardList)
        {
            if(button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button)
    {
        if(button.text != "")
            return

        if(currentTurn == Turn.RACKET)
        {
            button.text = RACKET
            currentTurn = Turn.UFO
        }
        else if(currentTurn == Turn.UFO)
        {
            button.text = UFO
            currentTurn = Turn.RACKET
        }
        setTurnLabel()
    }

    private fun setTurnLabel()
    {
        var turnText = ""
        if(currentTurn == Turn.UFO)
            turnText = "$UFO-ის სვლა"
        else if(currentTurn == Turn.RACKET)
            turnText = "$RACKET-ის სვლა"

        binding.svla.text = turnText
    }

    companion object
    {
        const val RACKET = "🚀"
        const val UFO = "🛸"
    }


}












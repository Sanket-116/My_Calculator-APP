package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.inappmessaging.model.Button
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean  = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tvInput = findViewById(R.id.tvInput)
    }

    // digit click fun
    fun onDigit(view: View) {
        tvInput?.append((view as TextView).text.toString())
        lastNumeric = true
        lastDot = false
    }

    // clear the window
    fun onClear(view:View){
        tvInput?.text=" "
    }

    // decimal point fun
    fun onDecimalPoint(view:View){
        if(lastNumeric && !lastDot){
            tvInput?.append(("."))
            lastNumeric =false
            lastDot  = true
        }
    }


    // operator click fun
    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as TextView).text.toString())
                lastNumeric = false
                lastDot = false
            }
        }
    }


    //actual calculation
    fun onEqual(view: View){
        if (lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = "" /* to eliminate unnecessary operator and identify negative value */
            try {
                if (tvValue.startsWith(("-"))) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)

                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-") //split the string where operator found
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun isOperatorAdded(value: String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("+") || value.contains("-") || value.contains("*")
        }
    }




    //round of fun for full values
    private fun removeZeroAfterDot(result: String):String{
        var value=result
        if(result.contains(".0"))
            value = result.substring(0, result.length - 2)

        return value
    }





}
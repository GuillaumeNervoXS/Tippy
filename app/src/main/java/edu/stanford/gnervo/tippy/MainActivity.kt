package edu.stanford.gnervo.tippy

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
private const val INITIAL_SPLIT = 1

class MainActivity : AppCompatActivity() {

    var tipAmount: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        seekBarTip.progress = INITIAL_TIP_PERCENT
        tvTipPercent.text = "$INITIAL_TIP_PERCENT%"
        upDateTipDescription(INITIAL_TIP_PERCENT)
        //etNumberPeople.text = "$INITIAL_SPLIT"
        seekBarTip.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tvTipPercent.text = "$progress%"
                upDateTipDescription(progress)
                computeTip()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        etBase.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTip()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        etNumberPeople.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeSplitAndTotal()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    private fun upDateTipDescription(tipPercent: Int) {
        val tipDescription: String
        when (tipPercent) {
            in 0..9 -> tipDescription = "\uD83D\uDE1E"
            in 10..14 -> tipDescription = "\uD83D\uDE10"
            in 15..19 -> tipDescription = "\uD83D\uDC4D"
            in 20..24 -> tipDescription = "\uD83D\uDE00"
            else -> tipDescription = "\uD83E\uDD11"
        }
        tvTipDescription.text = tipDescription
        val color = ArgbEvaluator().evaluate(
            tipPercent.toFloat() / seekBarTip.max,
            ContextCompat.getColor(this, R.color.colorWorstTip),
            ContextCompat.getColor(this, R.color.colorBestTip)
        ) as Int

        tvTipDescription.setTextColor(color)
    }

    private fun computeTip() {
        // function to compute the tip based on the base and tip percentage
        if (etBase.text.isEmpty()) {
            tvTipAmount.text = ""
            return
        }
        val baseAmount = etBase.text.toString().toDouble()
        val tipPercentage = seekBarTip.progress
        this.tipAmount = baseAmount * tipPercentage / 100
        tvTipAmount.text = "%.2f".format(tipAmount)
    }

    private fun computeSplitAndTotal() {
        // function to compute the tip based on the base and tip percentage
        if (etNumberPeople.text.isEmpty()) {
            tvTotalAmount.text = ""
            return
        }
        val baseAmount = etBase.text.toString().toDouble()
        val nbrSplit = etNumberPeople.text.toString().toInt()
        val totalAmount = (baseAmount + this.tipAmount) / nbrSplit
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }



//    private fun computeTipSplitAndTotal() {
//        // function to compute the tip based on the base and tip percentage
//        if (etBase.text.isEmpty() || etNumberPeople.text.isEmpty()) {
//            tvTipAmount.text = ""
//            tvTotalAmount.text = ""
//            return
//        }
//        val baseAmount = etBase.text.toString().toDouble()
//        val tipPercentage = seekBarTip.progress
//        val tipAmount = baseAmount * tipPercentage / 100
//        val nbrSplit = etNumberPeople.text.toString().toInt()
//        val totalAmount = (baseAmount + tipAmount) / nbrSplit
//        tvTipAmount.text = "%.2f".format(tipAmount)
//        tvTotalAmount.text = "%.2f".format(totalAmount)
//    }


}
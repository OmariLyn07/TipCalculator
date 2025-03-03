package com.example.tipcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipCalculatorLayout()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorLayout() {
    var amountInput by remember { mutableStateOf("") }
    var amountTip by remember { mutableStateOf("") }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount = amount, tipPercent = amountTip.toDoubleOrNull() ?: 15.0)
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            value = amountInput,
            onValueChanged = {amountInput = it},
            labelText = R.string.bill_amount,
            modifier = Modifier
            .padding(bottom = 39.dp)
            .fillMaxWidth())

        EditNumberField(
            value = amountTip,
            onValueChanged = {amountTip = it},
            labelText = R.string.tip_amount,
            modifier = Modifier
            .padding(bottom = 25.dp)
            .fillMaxWidth())
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = amount * (tipPercent / 100)
    return NumberFormat.getCurrencyInstance().format(tip)
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun EditNumberField(value: String, onValueChanged: (String) -> Unit, labelText: Int, modifier: Modifier = Modifier){

    TextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChanged,
        modifier = modifier,
        label = { Text(stringResource(labelText)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorPreview() {
    TipCalculatorTheme {
        TipCalculatorLayout()
    }
}
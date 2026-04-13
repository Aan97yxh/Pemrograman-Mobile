package com.aan.prak2_dropdown

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculatorScreen() {
    // STATE
    var amountInput by remember { mutableStateOf("") }
    var tipPercentInput by remember { mutableStateOf("15%") }
    var roundUp by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val tipOptions = listOf("15%", "18%", "20%")

    // LOGIC
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipPercentInput.replace("%", "").toDoubleOrNull() ?: 15.0
    var tip = (tipPercent / 100) * amount
    if (roundUp) tip = ceil(tip)
    val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        // Input Bill Amount
        EditNumberField(
            label = stringResource(R.string.bill_amount),
            leadingIcon = R.drawable.ic_bill,
            value = amountInput,
            onValueChange = { amountInput = it },
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
        )

        // Exposed Dropdown Menu
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.padding(bottom = 32.dp).fillMaxWidth()
        ) {
            TextField(
                value = tipPercentInput,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.tip_percentage)) },
                leadingIcon = { Icon(painterResource(id = R.drawable.ic_percent), null) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true).fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                tipOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            tipPercentInput = option
                            expanded = false
                        }
                    )
                }
            }
        }

        // Switch Round Up
        Row(
            modifier = Modifier.fillMaxWidth().size(48.dp).padding(bottom = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.round_up_tip))
            Switch(
                modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.End),
                checked = roundUp,
                onCheckedChange = { roundUp = it }
            )
        }

        // Hasil Perhitungan
        Text(
            text = stringResource(R.string.tip_amount, formattedTip),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )

        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditNumberField(
    label: String,
    leadingIcon: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        leadingIcon = { Icon(painterResource(id = leadingIcon), null) },
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}
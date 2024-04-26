package com.example.garage.views

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.garage.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


@Composable
fun EarningsScreen(
    navController: NavController,
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()



    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    SidebarContent(navController) {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                Header {
                    scope.launch { drawerState.open() }
                }
            },
            bottomBar = {
                Footer(navController, "navStatus")
            }
        ) {
            Column(
                backgroundModifier
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    backgroundModifier,
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(26.dp))
                        Text(
                            text = "Earnings",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = textStyle1,
                            fontSize = 32.sp
                        )
                        EarningsBox()
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }


}

@Composable
fun EarningsBox() {

    var dayEarningsWindow by remember { mutableStateOf(false) }
    var dayCardEarningsWindow by remember { mutableStateOf(false) }
    var monthlyEarningWindow by remember { mutableStateOf(false) }
    var bankDetailsWindow by remember { mutableStateOf(false) }
    var addedBankDetailsWindow by remember { mutableStateOf(false) }

    Card(
        modifier = cardModifier,
        border = BorderStroke(width = 2.dp, Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFB6C7E3))// Apply shadow to the outer Box
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AccountFieldButton(
                labelName = "Today's earnings",
                value = "LKR12000.00",
                onClickButton = { dayEarningsWindow = true }
            )
            AccountFieldButton(
                labelName = "Today's earnings (card)",
                value =  "LKR6800.00",
                onClickButton = { dayCardEarningsWindow = true }
            )

            AccountFieldButton(
                labelName = "30-day earnings",
                value =  "LKR47500.00",
                onClickButton = { monthlyEarningWindow = true }
            )

            AccountFieldButton(
                labelName = "Bank details",
                value =  "821234829103",
                onClickButton = { addedBankDetailsWindow = true }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CommonButton(btnName = "Add Bank Details", modifier = Modifier.align(Alignment.CenterHorizontally).width(190.dp)) {
                bankDetailsWindow = true
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    if (dayEarningsWindow) {
        MoreInfoWindow(message = "Total earnings for today") {
            dayEarningsWindow = false
        }
    }
    if (dayCardEarningsWindow) {
        MoreInfoWindow(message = "Total earnings by card (credit/debit) today. You will be paid automatically at 11:55 PM.") {
            dayCardEarningsWindow = false
        }
    }
    if (monthlyEarningWindow) {
        MoreInfoWindow(message = "Total earnings for the past 30 days") {
            monthlyEarningWindow = false
        }
    }

    if(bankDetailsWindow) {
        BankDetailsWindow {
            bankDetailsWindow = false
        }
    }

    if(addedBankDetailsWindow) {
        MoreInfoWindow(message = "This field will remain empty if you have not added your bank details.") {
            addedBankDetailsWindow = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountFieldButton(labelName: String, value: String, onClickButton: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = labelName,
                style = textStyle1,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Card(
                onClick = { onClickButton() },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                border = BorderStroke(width = 2.dp, color = Color.White),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .padding(horizontal = 38.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC6D4DE))
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = value,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f),
                        maxLines = 1,
                        style = textStyle2,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.question_fill),
                        modifier = Modifier
                            .size(30.dp),
                        contentDescription = "Info",
                        tint = Color.Unspecified
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun BankDetailsWindow(onDismiss: () -> Unit)
{
    var accountNumber by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var bank by remember { mutableStateOf("") }
    var branch by remember { mutableStateOf("") }

    var context = LocalContext.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        tonalElevation = 16.dp,
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color(0xFFDCE4EC)
            )
            .verticalScroll(rememberScrollState()),
        containerColor = Color(0xFFDCE4EC),
        confirmButton = {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Add your bank details",
                    style = textStyle2
                )
                Spacer(modifier = Modifier.height(16.dp))

                accountNumber = AccountDetailsField(labelName = "Account number (required)", value = "")
                name = AccountDetailsField(labelName = "Name (required)", value = "")

                val bankList = listOf("BOC", "Commercial Bank", "DFCC", "HNB", "NDB", "NSB", "Sampath Bank")

                bank = CommonDropdown(bankList,"Bank",Modifier ).toString()

                branch = AccountDetailsField(labelName = "Branch (required)", value = "")

                Spacer(modifier = Modifier.height(8.dp))


                Spacer(modifier = Modifier.height(16.dp))
                CommonButton(btnName = "Add", modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    if (bank.isNotEmpty() && name != "" && branch != "" && accountNumber != "") {
                        //todo
                        onDismiss()
                    } else {
                        MainScope().launch {
                            Toast.makeText(context, "Fill all the required details", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

@Composable
fun AccountDetailsField(labelName: String, value: String?): String {
    var fieldValue by remember { mutableStateOf(TextFieldValue(value ?: "" )) }

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fieldValue,
                onValueChange = { fieldValue = it },
                modifier = Modifier
                    .padding(horizontal = 0.dp)
                    .height(50.dp)
                    .border(2.dp, Color.White, shape = RoundedCornerShape(50))
                    .shadow(6.dp, shape = RoundedCornerShape(50))
                    .background(Color.White),
                textStyle = textStyle2,
                placeholder = {
                    Text(
                        text = labelName,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                singleLine = true
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    return fieldValue.text
}
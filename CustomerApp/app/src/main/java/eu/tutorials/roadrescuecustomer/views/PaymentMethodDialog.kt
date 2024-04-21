package eu.tutorials.roadrescuecustomer.views

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import eu.tutorials.roadrescuecustomer.models.ServiceRequest
import eu.tutorials.roadrescuecustomer.util.AppPreferences
import eu.tutorials.roadrescuecustomer.viewmodels.ServiceRequestViewModel
import kotlinx.coroutines.flow.collect
import kotlin.math.roundToInt

@Composable
fun PaymentMethodDialog(
    paymentSheet: PaymentSheet,
    request: ServiceRequest,
    serviceRequestViewModel: ServiceRequestViewModel,
    onStatusChanged : () -> Unit,
    onDismiss: () -> Unit
) {


    var customerConfig by remember { mutableStateOf<PaymentSheet.CustomerConfiguration?>(null) }
    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val activity = LocalContext.current as ComponentActivity

    LaunchedEffect(key1 = Unit) {
        val amount = request.reqAmount?.toDouble()?.roundToInt() ?: 0
        if(amount >= 0) {
            serviceRequestViewModel.initStipePayment(
                amount = amount,
                description = request.description,
                name = AppPreferences(context).getStringPreference("NAME", "")
            )
        }else{
            Toast.makeText(context,"Please enter amount",Toast.LENGTH_LONG).show()
        }
    }

    LaunchedEffect(key1 = Unit) {
        serviceRequestViewModel.status.collect {status->
            if(status == 4){
                onDismiss()
                onStatusChanged()
            }else{
                Toast.makeText(context,"Please make payment",Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        serviceRequestViewModel.showError.collect {err->
            if(err.isNotEmpty()){
                Toast.makeText(context,err,Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        serviceRequestViewModel.payment.collect{paymentResponse->

            paymentIntentClientSecret = paymentResponse.paymentIntent
            customerConfig = PaymentSheet.CustomerConfiguration(
                paymentResponse.customer,
                paymentResponse.ephemeralKey
            )
            val publishableKey = paymentResponse.publishableKey
            PaymentConfiguration.init(context, publishableKey)
            /*PaymentConfiguration.init(activity, paymentResponse.publishableKey)

            paymentSheet.presentWithPaymentIntent(
                paymentResponse.paymentIntent,
                PaymentSheet.Configuration(
                    "Parera",
                    PaymentSheet.CustomerConfiguration(
                        paymentResponse.customer,
                        paymentResponse.ephemeralKey
                    ),
                    defaultBillingDetails = PaymentSheet.BillingDetails()
                )
            )*/
        }
    }


    val loading = serviceRequestViewModel.loading.value

    CircularProgressBar(isDisplayed = loading)

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
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Select Payment Method",
                    style = textStyle2
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        serviceRequestViewModel.paymentDone(context, request.id.toInt())
                    },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    border = BorderStroke(width = 2.dp, color = Color.White),
                    modifier = Modifier
                        .height(58.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(250.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC6D4DE))
                ) {
                    Text(
                        text = "Cash",
                        style = textStyle2,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        onDismiss()
                        val currentConfig = customerConfig
                        val currentClientSecret = paymentIntentClientSecret

                        if (currentConfig != null && currentClientSecret != null) {
                            presentPaymentSheet(paymentSheet, currentConfig, currentClientSecret)
                        }else{
                            Toast.makeText(context,"Payment Not Initialized Retry", Toast.LENGTH_SHORT).show()
                        }
                    },
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    border = BorderStroke(width = 2.dp, color = Color.White),
                    modifier = Modifier
                        .height(58.dp)
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                        .width(250.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC6D4DE))
                ) {
                    Text(
                        text = "Card",
                        style = textStyle2,
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

private fun presentPaymentSheet(
    paymentSheet: PaymentSheet,
    customerConfig: PaymentSheet.CustomerConfiguration,
    paymentIntentClientSecret: String
) {
    paymentSheet.presentWithPaymentIntent(
        paymentIntentClientSecret,
        PaymentSheet.Configuration(
            merchantDisplayName = "My merchant name",
            customer = customerConfig,
            // Set `allowsDelayedPaymentMethods` to true if your business handles
            // delayed notification payment methods like US bank accounts.
            allowsDelayedPaymentMethods = true
        )
    )
}


private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
    when(paymentSheetResult) {
        is PaymentSheetResult.Canceled -> {
            print("Canceled")
        }
        is PaymentSheetResult.Failed -> {
            print("Error: ${paymentSheetResult.error}")
        }
        is PaymentSheetResult.Completed -> {
            // Display for example, an order confirmation screen
            print("Completed")
        }
    }
}



fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}
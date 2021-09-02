package com.terranullius.task.framework.presentation.composables

import android.text.TextUtils
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.terranullius.task.R
import com.terranullius.task.framework.presentation.composables.components.PhoneNumberField
import com.terranullius.task.framework.presentation.composables.theme.lightBlueHeadline
import com.terranullius.task.framework.presentation.util.Screen

@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    Surface() {
        Column(modifier) {

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                factory = {
                    LottieAnimationView(it).apply {
                        setAnimation(R.raw.login)
                        repeatCount = LottieDrawable.INFINITE
                        repeatMode = LottieDrawable.RESTART
                    }
                }
            ) {
                it.playAnimation()
            }

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                var isContinuable by remember {
                    mutableStateOf(false)
                }

                Text(
                    text = "LOGIN", style = MaterialTheme.typography.h6.copy(
                        color = lightBlueHeadline
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Enter your phone number to proceed")
                Spacer(modifier = Modifier.height(24.dp))

                var phoneNumber by remember {
                    mutableStateOf(0L)
                }
                isContinuable =
                    phoneNumber.toString().length == 10 && isValidNumber(phoneNumber.toString())

                val phoneNumberError = remember {
                    mutableStateOf(false)
                }

                PhoneNumberField(
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { newValue ->
                        if (isValidNumber(newValue)) {
                            phoneNumber = newValue.toLongOrNull() ?: 0L
                            phoneNumberError.value = false
                        } else {
                            phoneNumberError.value = true
                        }
                    },
                    value = if (phoneNumber == 0L) "" else phoneNumber.toString(),
                    isError = phoneNumberError
                ) {
                    login(phoneNumber.toString(), navController)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (!isContinuable) MaterialTheme.colors.primary.copy(
                            alpha = 0.4f
                        ) else MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.onPrimary
                    ),
                    onClick = { login(phoneNumber.toString(), navController) }
                ) {
                    Text(text = if (!isContinuable) "ENTER PHONE NUMBER" else "CONTINUE")
                }
            }
        }
    }
}

private fun isValidNumber(number: String) =
    (number.length <= 10 && containsOnlyNumbers(number) || number.isEmpty())

fun containsOnlyNumbers(number: String): Boolean {
    return TextUtils.isDigitsOnly(number)
}

fun login(number: String, navController: NavHostController) {
    if (isValidNumber(number)) {
        if (number.length == 10) {
            number.toLongOrNull()?.let {
                navigateMainScreen(navController)
            }
        }
    }
}

fun navigateMainScreen(navController: NavHostController) {
    navController.navigate(Screen.Main.route)
}

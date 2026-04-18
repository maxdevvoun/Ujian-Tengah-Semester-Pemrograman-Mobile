package com.example.uts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uts.ui.theme.AppGradientBackground
import com.example.uts.ui.theme.DeepBlue
import com.example.uts.ui.theme.GlassCard

@Composable
fun ResultScreen(navController: NavController, data: RegistrationData) {
    AppGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(100.dp).padding(bottom = 16.dp)
            )

            Text(
                "Registration Success!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            GlassCard {
                ResultItem("Full Name", data.name)
                ResultItem("Email", data.email)
                ResultItem("WhatsApp", data.phone)
                ResultItem("Gender", data.gender)
                ResultItem("Topic", data.topic)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = { navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    } },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Back to Home", color = DeepBlue, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ResultItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
        Text(label, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp)
        Text(value, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        HorizontalDivider(color = Color.White.copy(alpha = 0.2f), modifier = Modifier.padding(top = 4.dp))
    }
}

package com.example.uts.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.uts.ui.theme.AppGradientBackground
import com.example.uts.ui.theme.DeepBlue
import com.example.uts.ui.theme.GlassCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DaftarScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Male") }
    var topic by remember { mutableStateOf("") }
    
    val genderOptions = listOf("Male", "Female")
    val topicOptions = listOf("AI & Machine Learning", "Mobile Development", "Cyber Security", "Cloud Computing")
    var expandedTopic by remember { mutableStateOf(false) }

    // Validation
    val isEmailValid = email.isEmpty() || android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPhoneValid = phone.isEmpty() || (phone.startsWith("08") && phone.length >= 10)
    val isFormValid = name.isNotEmpty() && email.isNotEmpty() && isEmailValid && phone.isNotEmpty() && isPhoneValid && topic.isNotEmpty()

    var showConfirmDialog by remember { mutableStateOf(false) }

    AppGradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                "Seminar Registration Form",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            GlassCard {
                // Name Field
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, null, tint = Color.White) },
                    colors = textFieldColors()
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Email, null, tint = Color.White) },
                    isError = !isEmailValid,
                    supportingText = { if (!isEmailValid) Text("Invalid email format", color = Color.Red) },
                    colors = textFieldColors()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Phone Field
                OutlinedTextField(
                    value = phone,
                    onValueChange = { if (it.all { char -> char.isDigit() }) phone = it },
                    label = { Text("WhatsApp Number", color = Color.White) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Phone, null, tint = Color.White) },
                    isError = !isPhoneValid,
                    supportingText = { if (!isPhoneValid) Text("Must start with 08 and be at least 10 digits", color = Color.Red) },
                    colors = textFieldColors()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Gender Selection
                Text("Gender", color = Color.White, fontWeight = FontWeight.SemiBold)
                Row(modifier = Modifier.selectableGroup()) {
                    genderOptions.forEach { text ->
                        Row(
                            Modifier
                                .selectable(
                                    selected = (text == gender),
                                    onClick = { gender = text },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 8.dp)
                                .padding(end = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == gender),
                                onClick = null,
                                colors = RadioButtonDefaults.colors(selectedColor = Color.White, unselectedColor = Color.White.copy(alpha = 0.6f))
                            )
                            Text(text, color = Color.White, modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Topic Selection
                ExposedDropdownMenuBox(
                    expanded = expandedTopic,
                    onExpandedChange = { expandedTopic = !expandedTopic }
                ) {
                    OutlinedTextField(
                        value = topic,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Seminar Topic", color = Color.White) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTopic) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        colors = textFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedTopic,
                        onDismissRequest = { expandedTopic = false }
                    ) {
                        topicOptions.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption) },
                                onClick = {
                                    topic = selectionOption
                                    expandedTopic = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { showConfirmDialog = true },
                    enabled = isFormValid,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        disabledContainerColor = Color.White.copy(alpha = 0.3f)
                    )
                ) {
                    Text("Submit Registration", color = DeepBlue, fontWeight = FontWeight.Bold)
                }
            }
        }

        if (showConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                title = { Text("Confirm Registration") },
                text = { Text("Are you sure the data entered is correct?") },
                confirmButton = {
                    TextButton(onClick = {
                        showConfirmDialog = false
                        navController.navigate(Screen.Result.createRoute(name, email, phone, gender, topic))
                    }) {
                        Text("Yes, Submit")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmDialog = false }) {
                        Text("Edit Data")
                    }
                }
            )
        }
    }
}

@Composable
fun textFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedBorderColor = Color.White,
    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
    cursorColor = Color.White,
    errorBorderColor = Color.Red,
    focusedSupportingTextColor = Color.White,
    unfocusedSupportingTextColor = Color.White
)

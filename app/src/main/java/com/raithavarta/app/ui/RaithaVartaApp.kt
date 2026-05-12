package com.raithavarta.app.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Cloud
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.raithavarta.app.R
import com.raithavarta.app.data.RaithaData
import com.raithavarta.app.data.SuccessStory
import com.raithavarta.app.data.Tip
import java.io.File
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val PREFS_NAME = "raitha_varta_prefs"
private const val SCREEN_SPLASH = "splash"
private const val SCREEN_LANGUAGE = "language"
private const val SCREEN_PHONE = "phone"
private const val SCREEN_OTP = "otp"
private const val SCREEN_PROFILE = "profile_setup"
private const val SCREEN_MAIN = "main"
private const val SCREEN_TIP = "tip_detail"
private const val SCREEN_NOTIFICATIONS = "notifications"

private enum class MainTab(val labelEn: String, val labelKn: String) {
    Home("Home", "ಮನೆ"),
    Tips("Tips", "ಸಲಹೆ"),
    Expert("Expert", "ತಜ್ಞ"),
    Saved("Saved", "ಉಳಿಸಿದ"),
    Profile("Profile", "ಪ್ರೊಫೈಲ್")
}

@Composable
fun RaithaVartaApp() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    var screen by rememberSaveable { mutableStateOf(SCREEN_SPLASH) }
    var language by rememberSaveable { mutableStateOf(prefs.getString("preferred_language", "") ?: "") }
    var phone by rememberSaveable { mutableStateOf(prefs.getString("phone", "") ?: "") }
    var farmerName by rememberSaveable { mutableStateOf(prefs.getString("farmer_name", "") ?: "") }
    var district by rememberSaveable { mutableStateOf(prefs.getString("district", "Mysuru") ?: "Mysuru") }
    var taluk by rememberSaveable { mutableStateOf(prefs.getString("taluk", "Mysuru") ?: "Mysuru") }
    var selectedTipId by rememberSaveable { mutableStateOf(RaithaData.tips.first().id) }
    var tab by rememberSaveable { mutableStateOf(MainTab.Home.name) }
    var selectedCrops by remember {
        mutableStateOf(prefs.getStringSet("crops", setOf("paddy", "coconut", "tomato"))?.toSet() ?: emptySet())
    }
    var savedTips by remember {
        mutableStateOf(prefs.getStringSet("saved_tips", emptySet())?.toSet() ?: emptySet())
    }

    var tts by remember { mutableStateOf<TextToSpeech?>(null) }
    var ttsReady by remember { mutableStateOf(false) }
    DisposableEffect(Unit) {
        val engine = TextToSpeech(context) { status ->
            ttsReady = status == TextToSpeech.SUCCESS
            if (status != TextToSpeech.SUCCESS) {
                Toast.makeText(context, "Audio engine is not ready on this device.", Toast.LENGTH_SHORT).show()
            }
        }
        engine.setSpeechRate(0.92f)
        tts = engine
        onDispose {
            engine.stop()
            engine.shutdown()
        }
    }

    fun persistProfile(name: String, newDistrict: String, newTaluk: String, crops: Set<String>) {
        farmerName = name
        district = newDistrict
        taluk = newTaluk
        selectedCrops = crops
        prefs.edit()
            .putString("farmer_name", name)
            .putString("district", newDistrict)
            .putString("taluk", newTaluk)
            .putStringSet("crops", crops)
            .apply()
    }

    fun toggleSaved(tipId: String) {
        savedTips = if (tipId in savedTips) savedTips - tipId else savedTips + tipId
        prefs.edit().putStringSet("saved_tips", savedTips).apply()
    }

    fun toggleLanguage() {
        language = if (language == "kn") "en" else "kn"
        prefs.edit().putString("preferred_language", language).apply()
    }

    fun speak(text: String) {
        val engine = tts
        if (engine == null || !ttsReady) {
            Toast.makeText(context, "Audio is still getting ready. Please tap Listen again.", Toast.LENGTH_SHORT).show()
            return
        }
        val locale = if (language == "kn") Locale("kn", "IN") else Locale("en", "IN")
        val languageResult = engine.setLanguage(locale)
        val spokenText = if (
            languageResult == TextToSpeech.LANG_MISSING_DATA ||
            languageResult == TextToSpeech.LANG_NOT_SUPPORTED
        ) {
            engine.setLanguage(Locale("en", "IN"))
            Toast.makeText(context, "Kannada voice is not installed. Speaking guidance in English.", Toast.LENGTH_LONG).show()
            if (language == "kn") {
                "Kannada voice is not installed on this device. Please switch to English or install Kannada text to speech voice."
            } else {
                text
            }
        } else {
            text
        }
        val result = engine.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null, "raitha_tip_${System.currentTimeMillis()}")
        if (result == TextToSpeech.ERROR) {
            Toast.makeText(context, "Unable to play audio on this device.", Toast.LENGTH_SHORT).show()
        }
    }

    fun openTip(tipId: String) {
        selectedTipId = tipId
        screen = SCREEN_TIP
    }

    LaunchedEffect(Unit) {
        delay(1700)
        screen = when {
            language.isBlank() -> SCREEN_LANGUAGE
            phone.isBlank() -> SCREEN_PHONE
            farmerName.isBlank() -> SCREEN_PROFILE
            else -> SCREEN_MAIN
        }
    }

    BackHandler(enabled = screen == SCREEN_TIP || screen == SCREEN_NOTIFICATIONS) {
        screen = SCREEN_MAIN
    }

    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when (screen) {
            SCREEN_SPLASH -> SplashScreen()
            SCREEN_LANGUAGE -> LanguageScreen(
                selected = language,
                onSelected = { language = it },
                onContinue = {
                    prefs.edit().putString("preferred_language", language).apply()
                    screen = SCREEN_PHONE
                }
            )
            SCREEN_PHONE -> PhoneNumberScreen(
                language = language,
                onOtpRequested = {
                    phone = it
                    prefs.edit().putString("phone", it).apply()
                    screen = SCREEN_OTP
                }
            )
            SCREEN_OTP -> OtpScreen(
                phone = phone,
                language = language,
                onChangeNumber = { screen = SCREEN_PHONE },
                onVerified = {
                    screen = if (farmerName.isBlank()) SCREEN_PROFILE else SCREEN_MAIN
                }
            )
            SCREEN_PROFILE -> ProfileSetupScreen(
                language = language,
                phone = phone,
                initialName = farmerName,
                initialDistrict = district,
                initialTaluk = taluk,
                initialCrops = selectedCrops,
                onProfileSaved = { name, newDistrict, newTaluk, crops ->
                    persistProfile(name, newDistrict, newTaluk, crops)
                    screen = SCREEN_MAIN
                }
            )
            SCREEN_TIP -> TipDetailScreen(
                tip = RaithaData.tips.first { it.id == selectedTipId },
                language = language,
                saved = selectedTipId in savedTips,
                onBack = { screen = SCREEN_MAIN },
                onSave = { toggleSaved(selectedTipId) },
                onShare = { shareTip(context, it, language) },
                onSpeak = { speak(it) },
                onOpenTip = ::openTip
            )
            SCREEN_NOTIFICATIONS -> NotificationsScreen(
                language = language,
                onBack = { screen = SCREEN_MAIN },
                onOpenExpert = {
                    tab = MainTab.Expert.name
                    screen = SCREEN_MAIN
                }
            )
            else -> MainArea(
                language = language,
                farmerName = farmerName.ifBlank { "Farmer" },
                phone = phone,
                district = district,
                taluk = taluk,
                selectedCrops = selectedCrops,
                savedTips = savedTips,
                currentTab = MainTab.valueOf(tab),
                onTabChange = { tab = it.name },
                onOpenTip = ::openTip,
                onToggleSaved = ::toggleSaved,
                onSpeak = { speak(it) },
                onShare = { shareTip(context, it, language) },
                onNotifications = { screen = SCREEN_NOTIFICATIONS },
                onToggleLanguage = ::toggleLanguage,
                onLogout = {
                    prefs.edit().clear().apply()
                    language = ""
                    phone = ""
                    farmerName = ""
                    district = "Mysuru"
                    taluk = "Mysuru"
                    selectedCrops = emptySet()
                    savedTips = emptySet()
                    tab = MainTab.Home.name
                    screen = SCREEN_LANGUAGE
                },
                onProfileSaved = { name, newDistrict, newTaluk, crops ->
                    persistProfile(name, newDistrict, newTaluk, crops)
                }
            )
        }
    }
}

@Composable
private fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PrimaryGreen, DarkGreen))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .size(126.dp)
                    .clip(CircleShape)
                    .border(3.dp, FieldGold, CircleShape)
            )
            Spacer(Modifier.height(18.dp))
            Text("Raitha-Varta", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
            Text("ರೈತ-ವಾರ್ತಾ", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Text("ರೈತರ ದೈನಂದಿನ ಮಾರ್ಗದರ್ಶಿ", color = Color(0xFFC8E6C9), fontSize = 17.sp)
            Spacer(Modifier.height(42.dp))
            Divider(
                modifier = Modifier
                    .width(170.dp)
                    .height(2.dp),
                color = FieldGold
            )
            Spacer(Modifier.height(14.dp))
            Text(
                "Gopalan College of Engineering and Management",
                color = Color.White.copy(alpha = 0.82f),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            Text(
                "K Manjushree | 1GD22CS018",
                color = Color.White.copy(alpha = 0.82f),
                fontSize = 12.sp
            )
        }
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
                .size(28.dp),
            color = FieldGold,
            strokeWidth = 3.dp
        )
    }
}

@Composable
private fun LanguageScreen(
    selected: String,
    onSelected: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFAFA))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
                .background(Brush.verticalGradient(listOf(PrimaryGreen, DarkGreen))),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(74.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.height(10.dp))
                Text("Choose Your Language", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text("ನಿಮ್ಮ ಭಾಷೆ ಆಯ್ಕೆ ಮಾಡಿ", color = Color.White.copy(alpha = 0.92f), fontSize = 18.sp)
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(18.dp),
            verticalArrangement = Arrangement.Center
        ) {
            LanguageCard(
                title = "ಕನ್ನಡ",
                subtitle = "Kannada",
                selected = selected == "kn",
                onClick = { onSelected("kn") }
            )
            Spacer(Modifier.height(16.dp))
            LanguageCard(
                title = "English",
                subtitle = "ಇಂಗ್ಲಿಷ್",
                selected = selected == "en",
                onClick = { onSelected("en") }
            )
        }
        Button(
            onClick = onContinue,
            enabled = selected.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Text("Continue / ಮುಂದುವರೆಯಿರಿ", fontSize = 17.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun LanguageCard(title: String, subtitle: String, selected: Boolean, onClick: () -> Unit) {
    val bg = if (selected) LightGreen else Color.White
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(132.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 8.dp else 3.dp),
        border = BorderStroke(if (selected) 2.dp else 1.dp, if (selected) PrimaryGreen else Color(0xFFE0E0E0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(if (selected) PrimaryGreen else SurfaceGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(title.take(1), color = if (selected) Color.White else PrimaryGreen, fontSize = 26.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(18.dp))
            Column(Modifier.weight(1f)) {
                Text(title, color = PrimaryGreen, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                Text(subtitle, color = TextMuted, fontSize = 16.sp)
            }
            if (selected) {
                Icon(Icons.Rounded.CheckCircle, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(30.dp))
            } else {
                Box(Modifier.size(28.dp).border(2.dp, Color(0xFFBDBDBD), CircleShape))
            }
        }
    }
}

@Composable
private fun PhoneNumberScreen(language: String, onOtpRequested: (String) -> Unit) {
    var number by rememberSaveable { mutableStateOf("") }
    val startsWithMobileDigit = number.firstOrNull()?.let { it in '6'..'9' } == true
    val valid = number.length == 10 && number.all { it.isDigit() } && startsWithMobileDigit
    var submitted by rememberSaveable { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().background(Color.White)) {
        HeaderPanel(
            title = if (language == "kn") "ರೈತ-ವಾರ್ತಾಕ್ಕೆ ಸ್ವಾಗತ" else "Welcome to Raitha-Varta",
            subtitle = "ರೈತರ ಡಿಜಿಟಲ್ ಸಹಾಯಕ",
            height = 235.dp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .offsetCard()
        ) {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text("Enter Mobile Number", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                    Text("ನಿಮ್ಮ ಮೊಬೈಲ್ ಸಂಖ್ಯೆ ನಮೂದಿಸಿ", color = TextMuted, fontSize = 14.sp)
                    Spacer(Modifier.height(22.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .width(76.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF5F5F5))
                                .border(1.dp, Color(0xFFBDBDBD), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+91", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(Modifier.width(10.dp))
                        OutlinedTextField(
                            value = number,
                            onValueChange = { number = it.filter(Char::isDigit).take(10) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            label = { Text("9XXXXXXXXX") },
                            isError = submitted && !valid,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                        )
                    }
                    if (submitted && !valid) {
                        Text("Please enter a valid 10-digit Indian mobile number", color = TomatoRed, fontSize = 12.sp)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("OTP will be simulated for this student project build.", color = TextMuted, fontSize = 12.sp)
                    Spacer(Modifier.height(22.dp))
                    Button(
                        onClick = {
                            submitted = true
                            if (valid) onOtpRequested(number)
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("Send OTP / OTP ಕಳುಹಿಸಿ", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Spacer(Modifier.weight(1f))
        CropStrip()
    }
}

private fun Modifier.offsetCard(): Modifier = this.padding(top = 0.dp)

@Composable
private fun HeaderPanel(title: String, subtitle: String, height: androidx.compose.ui.unit.Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(Brush.verticalGradient(listOf(PrimaryGreen, DarkGreen))),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier.size(88.dp).clip(CircleShape)
            )
            Spacer(Modifier.height(12.dp))
            Text(title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Text(subtitle, color = Color.White.copy(alpha = 0.9f), fontSize = 16.sp)
        }
    }
}

@Composable
private fun CropStrip() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .background(SurfaceGreen)
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        listOf(R.drawable.paddy, R.drawable.coconut, R.drawable.tomatoes).forEach { image ->
            Image(
                painter = painterResource(image),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .height(62.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun OtpScreen(phone: String, language: String, onChangeNumber: () -> Unit, onVerified: () -> Unit) {
    var otp by rememberSaveable { mutableStateOf("") }
    var seconds by rememberSaveable { mutableStateOf(30) }
    val valid = otp.length == 6

    LaunchedEffect(Unit) {
        while (seconds > 0) {
            delay(1000)
            seconds -= 1
        }
    }

    Column(Modifier.fillMaxSize().background(Color.White)) {
        HeaderPanel(
            title = if (language == "kn") "OTP ಪರಿಶೀಲನೆ" else "Verify OTP",
            subtitle = "Secure farmer login",
            height = 210.dp
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Enter 6-Digit OTP", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text("OTP sent to +91 ${phone.maskPhone()}", color = TextMuted, fontSize = 14.sp)
                Spacer(Modifier.height(22.dp))
                OutlinedTextField(
                    value = otp,
                    onValueChange = {
                        otp = it.filter(Char::isDigit).take(6)
                        if (it.filter(Char::isDigit).length >= 6) onVerified()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Center, letterSpacing = 3.sp),
                    singleLine = true,
                    label = { Text("Use any 6 digits") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(Modifier.height(18.dp))
                Text(if (seconds > 0) "Resend OTP in 00:${seconds.toString().padStart(2, '0')}" else "Resend OTP", color = PrimaryGreen)
                Spacer(Modifier.height(22.dp))
                Button(
                    onClick = onVerified,
                    enabled = valid,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Verify & Continue / ಪರಿಶೀಲಿಸಿ", fontWeight = FontWeight.Bold)
                }
                TextButton(onClick = onChangeNumber) {
                    Text("Change number", color = TextMuted)
                }
            }
        }
    }
}

private fun String.maskPhone(): String {
    if (length < 4) return this
    return "XXXXXX" + takeLast(4)
}

@Composable
private fun ProfileSetupScreen(
    language: String,
    phone: String,
    initialName: String,
    initialDistrict: String,
    initialTaluk: String,
    initialCrops: Set<String>,
    onProfileSaved: (String, String, String, Set<String>) -> Unit
) {
    var name by rememberSaveable { mutableStateOf(initialName) }
    var district by rememberSaveable { mutableStateOf(initialDistrict) }
    var taluk by rememberSaveable { mutableStateOf(initialTaluk) }
    var crops by remember { mutableStateOf(initialCrops.ifEmpty { setOf("paddy", "coconut") }) }
    val valid = name.trim().length >= 2 && district.isNotBlank() && taluk.isNotBlank() && crops.isNotEmpty()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(SurfaceGreen),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            AppBar(title = if (language == "kn") "ನಿಮ್ಮ ಪ್ರೊಫೈಲ್ ಪೂರ್ಣಗೊಳಿಸಿ" else "Complete Your Profile")
        }
        item {
            ProgressSteps()
        }
        item {
            Card(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text("Personal Details", color = PrimaryGreen, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    Spacer(Modifier.height(14.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Full Name / ಪೂರ್ಣ ಹೆಸರು") },
                        singleLine = true
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = "+91 ${phone.maskPhone()}",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Verified Mobile") },
                        enabled = false
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = district,
                        onValueChange = { district = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("District / ಜಿಲ್ಲೆ") },
                        singleLine = true
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = taluk,
                        onValueChange = { taluk = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Taluk / ತಾಲ್ಲೂಕು") },
                        singleLine = true
                    )
                }
            }
        }
        item {
            Card(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text("Crop Interests / ಬೆಳೆ ಆಸಕ್ತಿಗಳು", color = PrimaryGreen, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    Spacer(Modifier.height(12.dp))
                    ChipCloud(
                        selectedIds = crops,
                        onToggle = { id -> crops = if (id in crops) crops - id else crops + id },
                        language = language,
                        includeAll = false
                    )
                }
            }
        }
        item {
            Button(
                onClick = { onProfileSaved(name.trim(), district.trim(), taluk.trim(), crops) },
                enabled = valid,
                modifier = Modifier.padding(16.dp).fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Save Profile / ಉಳಿಸಿ", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            }
        }
    }
}

@Composable
private fun ProgressSteps() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        listOf("Personal", "Location", "Crops").forEachIndexed { index, label ->
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(PrimaryGreen),
                contentAlignment = Alignment.Center
            ) {
                Text("${index + 1}", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            Text(label, modifier = Modifier.padding(horizontal = 7.dp), color = TextMuted, fontSize = 12.sp)
            if (index != 2) Divider(Modifier.width(16.dp), color = Color(0xFFBDBDBD))
        }
    }
}

@Composable
private fun MainArea(
    language: String,
    farmerName: String,
    phone: String,
    district: String,
    taluk: String,
    selectedCrops: Set<String>,
    savedTips: Set<String>,
    currentTab: MainTab,
    onTabChange: (MainTab) -> Unit,
    onOpenTip: (String) -> Unit,
    onToggleSaved: (String) -> Unit,
    onSpeak: (String) -> Unit,
    onShare: (Tip) -> Unit,
    onNotifications: () -> Unit,
    onToggleLanguage: () -> Unit,
    onLogout: () -> Unit,
    onProfileSaved: (String, String, String, Set<String>) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                MainTab.entries.forEach { item ->
                    val icon = when (item) {
                        MainTab.Home -> Icons.Rounded.Home
                        MainTab.Tips -> Icons.Rounded.List
                        MainTab.Expert -> Icons.Rounded.CameraAlt
                        MainTab.Saved -> Icons.Rounded.Bookmark
                        MainTab.Profile -> Icons.Rounded.Person
                    }
                    NavigationBarItem(
                        selected = currentTab == item,
                        onClick = { onTabChange(item) },
                        icon = { Icon(icon, contentDescription = null) },
                        label = { Text(if (language == "kn") item.labelKn else item.labelEn, fontSize = 11.sp) }
                    )
                }
            }
        }
    ) { inner ->
        Box(Modifier.padding(inner)) {
            when (currentTab) {
                MainTab.Home -> DashboardScreen(
                    language = language,
                    farmerName = farmerName,
                    district = district,
                    savedTips = savedTips,
                    onOpenTip = onOpenTip,
                    onToggleSaved = onToggleSaved,
                    onSpeak = onSpeak,
                    onShare = onShare,
                    onNotifications = onNotifications,
                    onToggleLanguage = onToggleLanguage,
                    onOpenTips = { onTabChange(MainTab.Tips) }
                )
                MainTab.Tips -> AllTipsScreen(
                    language = language,
                    savedTips = savedTips,
                    onOpenTip = onOpenTip,
                    onToggleSaved = onToggleSaved,
                    onSpeak = onSpeak
                )
                MainTab.Expert -> ExpertAskScreen(language = language, district = district)
                MainTab.Saved -> SavedTipsScreen(
                    language = language,
                    savedTips = savedTips,
                    onOpenTip = onOpenTip,
                    onToggleSaved = onToggleSaved,
                    onBrowseTips = { onTabChange(MainTab.Tips) }
                )
                MainTab.Profile -> ProfileScreen(
                    language = language,
                    farmerName = farmerName,
                    phone = phone,
                    district = district,
                    taluk = taluk,
                    crops = selectedCrops,
                    savedCount = savedTips.size,
                    onLogout = onLogout,
                    onProfileSaved = onProfileSaved
                )
            }
        }
    }
}

@Composable
private fun DashboardScreen(
    language: String,
    farmerName: String,
    district: String,
    savedTips: Set<String>,
    onOpenTip: (String) -> Unit,
    onToggleSaved: (String) -> Unit,
    onSpeak: (String) -> Unit,
    onShare: (Tip) -> Unit,
    onNotifications: () -> Unit,
    onToggleLanguage: () -> Unit,
    onOpenTips: () -> Unit
) {
    var selectedCategory by rememberSaveable { mutableStateOf("all") }
    val sessionTips = remember { RaithaData.tips.shuffled() }
    val tips = remember(selectedCategory, sessionTips) {
        if (selectedCategory == "all") sessionTips else sessionTips.filter { it.categoryId == selectedCategory }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA)),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {
        item {
            DashboardHeader(
                farmerName = farmerName,
                district = district,
                language = language,
                onToggleLanguage = onToggleLanguage,
                onNotifications = onNotifications
            )
        }
        item {
            WeatherBanner(district)
        }
        item {
            CategoryFilter(
                selectedCategory = selectedCategory,
                onSelected = { selectedCategory = it },
                language = language
            )
        }
        item {
            SectionHeader(
                title = if (language == "kn") "ಇಂದಿನ ಸಲಹೆ — Today's Tip" else "Today's Tip",
                action = "See All",
                onAction = onOpenTips
            )
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(tips.take(4)) { tip ->
                    TipFlashCard(
                        tip = tip,
                        language = language,
                        saved = tip.id in savedTips,
                        onOpen = { onOpenTip(tip.id) },
                        onSave = { onToggleSaved(tip.id) },
                        onShare = { onShare(tip) },
                        onSpeak = { onSpeak(tip.speechText(language)) },
                        modifier = Modifier.width(312.dp)
                    )
                }
            }
        }
        item {
            SectionHeader(
                title = if (language == "kn") "ಹೆಚ್ಚಿನ ಸಲಹೆಗಳು" else "More Tips",
                action = "See All",
                onAction = onOpenTips
            )
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tips.drop(4).ifEmpty { tips.take(4) }) { tip ->
                    MiniTipCard(
                        tip = tip,
                        language = language,
                        saved = tip.id in savedTips,
                        onOpen = { onOpenTip(tip.id) },
                        onSave = { onToggleSaved(tip.id) }
                    )
                }
            }
        }
        item {
            SectionHeader(
                title = if (language == "kn") "ಮೂಲ ಸಲಹೆಗಳ ಮುಖ್ಯಾಂಶಗಳು" else "Advisory Highlights",
                action = "See All",
                onAction = onOpenTips
            )
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(RaithaData.stories) { story ->
                    StoryPreviewCard(story, language)
                }
            }
        }
    }
}

@Composable
private fun DashboardHeader(
    farmerName: String,
    district: String,
    language: String,
    onToggleLanguage: () -> Unit,
    onNotifications: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryGreen)
            .padding(horizontal = 16.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.dp, FieldGold, CircleShape)
                .clickable(onClick = onToggleLanguage),
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (language == "kn") "EN" else "ಕ",
                color = PrimaryGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text("Good Morning, $farmerName!", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text("ಇಂದಿನ ಸಲಹೆ ನೋಡಿ", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
        }
        AssistChip(
            onClick = {},
            label = { Text("28°C $district", color = PrimaryGreen, fontSize = 12.sp) },
            leadingIcon = { Icon(Icons.Rounded.WbSunny, contentDescription = null, tint = FieldGold, modifier = Modifier.size(18.dp)) }
        )
        IconButton(onClick = onNotifications) {
            Icon(Icons.Rounded.Notifications, contentDescription = "Notifications", tint = Color.White)
        }
    }
}

@Composable
private fun WeatherBanner(district: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SkyBlue),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(listOf(SkyBlue, Color(0xFF0097A7))))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Rounded.Cloud, contentDescription = null, tint = Color.White, modifier = Modifier.size(42.dp))
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text("$district, Karnataka", color = Color.White, fontWeight = FontWeight.Bold)
                Text("28°C | Humid 65% | Rain likely in evening", color = Color.White.copy(alpha = 0.92f), fontSize = 13.sp)
            }
            Text("Advisory", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun CategoryFilter(selectedCategory: String, onSelected: (String) -> Unit, language: String) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().background(Color.White),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(RaithaData.categories) { category ->
            FilterChip(
                selected = selectedCategory == category.id,
                onClick = { onSelected(category.id) },
                label = { Text(if (language == "kn") category.kn else category.en) },
                leadingIcon = if (selectedCategory == category.id) {
                    { Icon(Icons.Rounded.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                } else null
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String, action: String, onAction: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp, top = 18.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, modifier = Modifier.weight(1f), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if (action.isNotBlank()) {
            TextButton(onClick = onAction) {
                Text(action, color = PrimaryGreen)
            }
        }
    }
}

@Composable
private fun TipFlashCard(
    tip: Tip,
    language: String,
    saved: Boolean,
    onOpen: () -> Unit,
    onSave: () -> Unit,
    onShare: () -> Unit,
    onSpeak: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(420.dp)
            .clickable(onClick = onOpen),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(tip.imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(190.dp),
                    contentScale = ContentScale.Crop
                )
                AssistChip(
                    onClick = {},
                    label = { Text(RaithaData.categoryName(tip.categoryId, language), color = PrimaryGreen, fontSize = 12.sp) },
                    modifier = Modifier.padding(12.dp)
                )
            }
            Column(Modifier.padding(16.dp).weight(1f)) {
                Text(tip.title(language), fontSize = 18.sp, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(8.dp))
                Text(tip.shortAdvice(language), color = Color(0xFF424242), fontSize = 14.sp, lineHeight = 20.sp, maxLines = 3, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(10.dp))
                AssistChip(
                    onClick = {},
                    label = { Text(tip.weatherTag, fontSize = 12.sp, color = SkyBlue) },
                    leadingIcon = { Icon(Icons.Rounded.Cloud, contentDescription = null, tint = SkyBlue, modifier = Modifier.size(16.dp)) }
                )
                Spacer(Modifier.weight(1f))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                    TextIconButton(Icons.Rounded.Bookmark, if (saved) "Saved" else "Save", if (saved) FieldGold else PrimaryGreen, onSave)
                    TextIconButton(Icons.Rounded.Share, "Share", PrimaryGreen, onShare)
                    TextIconButton(Icons.Rounded.VolumeUp, "Listen", PrimaryGreen, onSpeak)
                }
            }
        }
    }
}

@Composable
private fun TextIconButton(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, color: Color, onClick: () -> Unit) {
    TextButton(onClick = onClick, contentPadding = PaddingValues(horizontal = 6.dp, vertical = 4.dp)) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(3.dp))
        Text(label, color = color, fontSize = 12.sp)
    }
}

@Composable
private fun MiniTipCard(tip: Tip, language: String, saved: Boolean, onOpen: () -> Unit, onSave: () -> Unit) {
    Card(
        modifier = Modifier
            .width(184.dp)
            .height(224.dp)
            .clickable(onClick = onOpen),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(tip.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(100.dp),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(10.dp).fillMaxWidth().weight(1f)) {
                Text(RaithaData.categoryName(tip.categoryId, language), color = PrimaryGreen, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(tip.title(language), fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Bold, maxLines = 3, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(tip.type, color = TextMuted, fontSize = 11.sp, modifier = Modifier.weight(1f))
                    IconButton(onClick = onSave, modifier = Modifier.size(34.dp)) {
                        Icon(Icons.Rounded.Bookmark, contentDescription = null, tint = if (saved) FieldGold else TextMuted)
                    }
                }
            }
        }
    }
}

@Composable
private fun StoryPreviewCard(story: SuccessStory, language: String) {
    Card(
        modifier = Modifier.width(280.dp).height(188.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(Modifier.fillMaxSize().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(story.imageRes),
                contentDescription = null,
                modifier = Modifier.size(64.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(
                Modifier.fillMaxHeight().weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(story.farmerName, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text("${story.district} | ${story.crop}", color = TextMuted, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Spacer(Modifier.height(6.dp))
                    Text(if (language == "kn") story.storyKn else story.storyEn, fontSize = 12.sp, lineHeight = 16.sp, maxLines = 3, overflow = TextOverflow.Ellipsis)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceGreen)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(story.result, color = PrimaryGreen, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                }
            }
        }
    }
}

@Composable
private fun AllTipsScreen(
    language: String,
    savedTips: Set<String>,
    onOpenTip: (String) -> Unit,
    onToggleSaved: (String) -> Unit,
    onSpeak: (String) -> Unit
) {
    var selectedCategory by rememberSaveable { mutableStateOf("all") }
    var query by rememberSaveable { mutableStateOf("") }
    val filtered = RaithaData.tips.filter { tip ->
        (selectedCategory == "all" || tip.categoryId == selectedCategory) &&
            (query.isBlank() || tip.title(language).contains(query, ignoreCase = true) || tip.shortAdvice(language).contains(query, ignoreCase = true))
    }

    LazyColumn(Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        item { AppBar(title = if (language == "kn") "ಎಲ್ಲಾ ಸಲಹೆಗಳು | All Tips" else "All Tips") }
        item {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                label = { Text("Search tips... / ಹುಡುಕಿ") },
                singleLine = true
            )
        }
        item {
            CategoryFilter(selectedCategory, { selectedCategory = it }, language)
        }
        if (filtered.isEmpty()) {
            item {
                EmptyState(
                    title = "No tips found",
                    subtitle = "Clear filters or try another crop category.",
                    action = "Clear Filters",
                    onAction = {
                        selectedCategory = "all"
                        query = ""
                    }
                )
            }
        } else {
            items(filtered) { tip ->
                TipListItem(
                    tip = tip,
                    language = language,
                    saved = tip.id in savedTips,
                    onOpen = { onOpenTip(tip.id) },
                    onSave = { onToggleSaved(tip.id) },
                    onSpeak = { onSpeak(tip.speechText(language)) }
                )
            }
        }
    }
}

@Composable
private fun TipListItem(tip: Tip, language: String, saved: Boolean, onOpen: () -> Unit, onSave: () -> Unit, onSpeak: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .height(136.dp)
            .clickable(onClick = onOpen),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(Modifier.fillMaxSize().padding(10.dp)) {
            Image(
                painter = painterResource(tip.imageRes),
                contentDescription = null,
                modifier = Modifier.size(116.dp).clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("${RaithaData.categoryName(tip.categoryId, language)} | ${tip.type}", color = PrimaryGreen, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                Text(tip.title(language), fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(tip.shortAdvice(language), color = TextMuted, fontSize = 13.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.weight(1f))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    TextIconButton(Icons.Rounded.VolumeUp, "Audio", PrimaryGreen, onSpeak)
                    TextIconButton(Icons.Rounded.Bookmark, if (saved) "Saved" else "Save", if (saved) FieldGold else PrimaryGreen, onSave)
                }
            }
        }
    }
}

@Composable
private fun ExpertAskScreen(language: String, district: String) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var selectedUriText by rememberSaveable { mutableStateOf("") }
    var pendingCameraUri by remember { mutableStateOf<Uri?>(null) }
    var selectedCrop by rememberSaveable { mutableStateOf("Paddy") }
    var description by rememberSaveable { mutableStateOf("") }
    var message by rememberSaveable { mutableStateOf("") }
    var analyzing by rememberSaveable { mutableStateOf(false) }
    var resultVisible by rememberSaveable { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            selectedUriText = pendingCameraUri?.toString().orEmpty()
            message = "Image captured successfully."
        } else {
            message = "Camera closed without saving an image."
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            val uri = createCameraImageUri(context)
            pendingCameraUri = uri
            cameraLauncher.launch(uri)
        } else {
            message = "Camera permission is required to capture crop images."
        }
    }
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            selectedUriText = uri.toString()
            message = "Image selected from gallery."
        }
    }

    fun openCamera() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            val uri = createCameraImageUri(context)
            pendingCameraUri = uri
            cameraLauncher.launch(uri)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    LaunchedEffect(analyzing) {
        if (analyzing) {
            delay(1400)
            analyzing = false
            resultVisible = true
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFAFAFA)),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item { AppBar(title = if (language == "kn") "Expert Ask / ತಜ್ಞರನ್ನು ಕೇಳಿ" else "Expert Ask") }
        item {
            Card(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = LightGreen)
            ) {
                Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(64.dp).clip(CircleShape).background(Color.White), contentAlignment = Alignment.Center) {
                        Icon(Icons.Rounded.CameraAlt, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(34.dp))
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text("AI-Powered Crop Disease Detection", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("ಬೆಳೆ ರೋಗ ಪತ್ತೆ ತಂತ್ರಜ್ಞಾನ", color = PrimaryGreen, fontSize = 14.sp)
                    }
                }
            }
        }
        item {
            InfoCard(title = "How to Use / ಹೇಗೆ ಬಳಸುವುದು") {
                InstructionRow(1, "Take a clear photo of the affected leaf or plant.")
                InstructionRow(2, "Choose crop type and add symptoms if needed.")
                InstructionRow(3, "Analyze to receive a treatment plan.")
            }
        }
        item {
            InfoCard(title = "Upload Diseased Leaf / ರೋಗಪೀಡಿತ ಎಲೆ ಅಪ್ಲೋಡ್ ಮಾಡಿ") {
                ImageUploadBox(
                    uriText = selectedUriText,
                    onClick = ::openCamera,
                    onRemove = {
                        selectedUriText = ""
                        resultVisible = false
                    }
                )
                Spacer(Modifier.height(14.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = ::openCamera,
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Icon(Icons.Rounded.CameraAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Use Camera", fontSize = 13.sp)
                    }
                    OutlinedButton(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier.weight(1f).height(48.dp),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Icon(Icons.Rounded.PhotoLibrary, contentDescription = null, modifier = Modifier.size(18.dp), tint = PrimaryGreen)
                        Spacer(Modifier.width(6.dp))
                        Text("Gallery", color = PrimaryGreen, fontSize = 13.sp)
                    }
                }
                Spacer(Modifier.height(14.dp))
                CropChoiceRow(selectedCrop = selectedCrop, onSelected = { selectedCrop = it })
            }
        }
        item {
            InfoCard(title = "Describe the problem / ಸಮಸ್ಯೆ ವಿವರಿಸಿ") {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it.take(500) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 110.dp),
                    label = { Text("Yellow spots, curling leaves, after rain...") },
                    maxLines = 4
                )
                Text("${description.length}/500", color = TextMuted, fontSize = 12.sp, modifier = Modifier.align(Alignment.End))
            }
        }
        if (message.isNotBlank()) {
            item {
                Text(
                    message,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 4.dp),
                    color = if (message.contains("required") || message.contains("closed")) TomatoRed else PrimaryGreen,
                    fontSize = 13.sp
                )
            }
        }
        item {
            Button(
                onClick = {
                    analyzing = true
                    resultVisible = false
                    message = ""
                },
                enabled = selectedUriText.isNotBlank() && !analyzing,
                modifier = Modifier.padding(16.dp).fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
            ) {
                if (analyzing) {
                    CircularProgressIndicator(modifier = Modifier.size(22.dp), color = Color.White, strokeWidth = 2.dp)
                    Spacer(Modifier.width(8.dp))
                    Text("Analyzing...")
                } else {
                    Icon(Icons.Rounded.Search, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Analyze Now / ವಿಶ್ಲೇಷಿಸಿ", fontWeight = FontWeight.Bold)
                }
            }
        }
        item {
            AnimatedVisibility(resultVisible) {
                ExpertResultCard(
                    analysis = RaithaData.expertAnalysis(selectedCrop),
                    district = district,
                    onShare = {
                        scope.launch {
                            shareText(context, "Raitha-Varta AI Analysis", "AI analysis for $selectedCrop: ${RaithaData.expertAnalysis(selectedCrop).severity}")
                        }
                    }
                )
            }
        }
        item {
            PreviousQueriesRow(language)
        }
    }
}

@Composable
private fun InfoCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, color = PrimaryGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun InstructionRow(number: Int, text: String) {
    Row(Modifier.padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(28.dp).clip(CircleShape).background(PrimaryGreen), contentAlignment = Alignment.Center) {
            Text("$number", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        Spacer(Modifier.width(10.dp))
        Text(text, fontSize = 14.sp, color = TextDark)
    }
}

@Composable
private fun ImageUploadBox(uriText: String, onClick: () -> Unit, onRemove: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(SurfaceGreen)
            .border(1.dp, PrimaryGreen, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (uriText.isNotBlank()) {
            UriImage(
                uri = Uri.parse(uriText),
                modifier = Modifier.fillMaxSize()
            )
            Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.48f))
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Tap to change image", color = Color.White, fontSize = 13.sp)
            }
            IconButton(
                onClick = onRemove,
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).clip(CircleShape).background(TomatoRed)
            ) {
                Icon(Icons.Rounded.Close, contentDescription = "Remove image", tint = Color.White)
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Rounded.CameraAlt, contentDescription = null, tint = TextMuted, modifier = Modifier.size(60.dp))
                Text("Tap to upload image", fontWeight = FontWeight.Bold, color = TextDark)
                Text("ಚಿತ್ರ ಅಪ್ಲೋಡ್ ಮಾಡಲು ಟ್ಯಾಪ್ ಮಾಡಿ", color = TextMuted, fontSize = 13.sp)
            }
        }
    }
}

@Composable
private fun UriImage(uri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var imageBitmap by remember(uri) { mutableStateOf<androidx.compose.ui.graphics.ImageBitmap?>(null) }

    LaunchedEffect(uri) {
        imageBitmap = withContext(Dispatchers.IO) {
            context.contentResolver.openInputStream(uri)?.use { stream ->
                BitmapFactory.decodeStream(stream)?.asImageBitmap()
            }
        }
    }

    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap!!,
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(modifier.background(Color(0xFFE0E0E0)), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PrimaryGreen)
        }
    }
}

@Composable
private fun CropChoiceRow(selectedCrop: String, onSelected: (String) -> Unit) {
    val crops = listOf("Paddy", "Coconut", "Areca", "Tomato", "Maize", "Chilli")
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(crops) { crop ->
            FilterChip(
                selected = selectedCrop == crop,
                onClick = { onSelected(crop) },
                label = { Text(crop) },
                leadingIcon = if (selectedCrop == crop) {
                    { Icon(Icons.Rounded.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                } else null
            )
        }
    }
}

@Composable
private fun ExpertResultCard(analysis: com.raithavarta.app.data.ExpertAnalysis, district: String, onShare: () -> Unit) {
    Card(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column {
            Text(
                "Advisory Match Result",
                modifier = Modifier.fillMaxWidth().background(PrimaryGreen).padding(14.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Column(Modifier.padding(16.dp)) {
                Text(analysis.disease, color = TomatoRed, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(analysis.diseaseKn, color = TextMuted)
                Spacer(Modifier.height(8.dp))
                AssistChip(onClick = {}, label = { Text(analysis.confidence, color = SoilBrown) })
                Spacer(Modifier.height(12.dp))
                SeverityBar(analysis.severity)
                Spacer(Modifier.height(16.dp))
                Text("Recommended Management", fontWeight = FontWeight.Bold, color = PrimaryGreen)
                analysis.treatmentSteps.forEachIndexed { index, step ->
                    InstructionRow(index + 1, step)
                }
                Spacer(Modifier.height(12.dp))
                Text("Prevention", fontWeight = FontWeight.Bold, color = PrimaryGreen)
                analysis.prevention.forEach {
                    Text("- $it", modifier = Modifier.padding(vertical = 3.dp), fontSize = 14.sp)
                }
                Spacer(Modifier.height(12.dp))
                Text("Nearby support: $district Raitha Samparka Kendra", color = TextMuted, fontSize = 13.sp)
                Spacer(Modifier.height(12.dp))
                Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1))) {
                    Text(
                        "This result is matched from official agromet advisory data. Please confirm crop symptoms with your local Agricultural Officer before treatment.",
                        modifier = Modifier.padding(12.dp),
                        fontSize = 12.sp,
                        color = SoilBrown
                    )
                }
                Spacer(Modifier.height(12.dp))
                OutlinedButton(onClick = onShare, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Rounded.Share, contentDescription = null, tint = PrimaryGreen)
                    Spacer(Modifier.width(8.dp))
                    Text("Share Advisory / ಸಲಹೆ ಹಂಚಿಕೊಳ್ಳಿ", color = PrimaryGreen)
                }
            }
        }
    }
}

@Composable
private fun SeverityBar(label: String) {
    val progress by animateFloatAsState(targetValue = 0.58f, label = "severity")
    Column {
        Text("Severity Level", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.horizontalGradient(listOf(PrimaryGreen, FieldGold, TomatoRed)))
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .border(2.dp, Color.White, RoundedCornerShape(8.dp))
            )
        }
        Text(label, color = TextMuted, fontSize = 13.sp, modifier = Modifier.padding(top = 5.dp))
    }
}

@Composable
private fun PreviousQueriesRow(language: String) {
    SectionHeader(
        title = if (language == "kn") "ನನ್ನ ಹಿಂದಿನ ಪ್ರಶ್ನೆಗಳು" else "My Previous Queries",
        action = "View All",
        onAction = {}
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(RaithaData.tips.take(3)) { tip ->
            Card(
                modifier = Modifier.width(160.dp).height(176.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(3.dp)
            ) {
                Column {
                    Image(
                        painter = painterResource(tip.imageRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(96.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(Modifier.padding(10.dp)) {
                        Text(tip.type, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text("Analyzed", color = PrimaryGreen, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun SavedTipsScreen(
    language: String,
    savedTips: Set<String>,
    onOpenTip: (String) -> Unit,
    onToggleSaved: (String) -> Unit,
    onBrowseTips: () -> Unit
) {
    val tips = RaithaData.tips.filter { it.id in savedTips }
    Column(Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        AppBar(title = if (language == "kn") "ಉಳಿಸಿದ ಸಲಹೆಗಳು | Saved Tips" else "Saved Tips")
        if (tips.isEmpty()) {
            EmptyState(
                title = "No Saved Tips Yet",
                subtitle = "Save tips from the dashboard by tapping the bookmark.",
                action = "Browse Tips",
                onAction = onBrowseTips
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().background(LightGreen).padding(14.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatText("${tips.size}", "Tips Saved")
                StatText("${tips.map { it.categoryId }.distinct().size}", "Crops")
                StatText("${tips.map { it.type }.distinct().size}", "Categories")
            }
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(tips) { tip ->
                    SavedGridCard(
                        tip = tip,
                        language = language,
                        onOpen = { onOpenTip(tip.id) },
                        onRemove = { onToggleSaved(tip.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SavedGridCard(tip: Tip, language: String, onOpen: () -> Unit, onRemove: () -> Unit) {
    Card(
        modifier = Modifier.height(245.dp).clickable(onClick = onOpen),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(tip.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(132.dp),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(10.dp)) {
                Text(RaithaData.categoryName(tip.categoryId, language), color = PrimaryGreen, fontSize = 12.sp)
                Text(tip.title(language), fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.weight(1f))
                TextButton(onClick = onRemove, contentPadding = PaddingValues(0.dp)) {
                    Icon(Icons.Rounded.Delete, contentDescription = null, tint = TomatoRed, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Remove", color = TomatoRed, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun ProfileScreen(
    language: String,
    farmerName: String,
    phone: String,
    district: String,
    taluk: String,
    crops: Set<String>,
    savedCount: Int,
    onLogout: () -> Unit,
    onProfileSaved: (String, String, String, Set<String>) -> Unit
) {
    var notifications by rememberSaveable { mutableStateOf(true) }
    var offlineMode by rememberSaveable { mutableStateOf(true) }
    var editing by rememberSaveable { mutableStateOf(false) }
    var draftName by rememberSaveable { mutableStateOf(farmerName) }
    var draftDistrict by rememberSaveable { mutableStateOf(district) }
    var draftTaluk by rememberSaveable { mutableStateOf(taluk) }
    var draftCrops by remember { mutableStateOf(crops) }

    LazyColumn(Modifier.fillMaxSize().background(Color(0xFFFAFAFA)), contentPadding = PaddingValues(bottom = 24.dp)) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(Brush.verticalGradient(listOf(PrimaryGreen, DarkGreen))),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box {
                        Image(
                            painter = painterResource(R.drawable.areca_cut),
                            contentDescription = null,
                            modifier = Modifier.size(104.dp).clip(CircleShape).border(3.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier.align(Alignment.BottomEnd).size(32.dp).clip(CircleShape).background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Rounded.CameraAlt, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(18.dp))
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(farmerName, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("+91 ${phone.maskPhone()} verified", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                    AssistChip(onClick = {}, label = { Text("$district, $taluk", color = PrimaryGreen, fontSize = 12.sp) })
                }
            }
        }
        item {
            Card(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                Row(Modifier.padding(18.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    StatText("$savedCount", "Tips Saved", Modifier.weight(1f))
                    StatText("${crops.size}", "Crops", Modifier.weight(1f))
                    StatText("3", "Queries", Modifier.weight(1f))
                }
            }
        }
        item {
            InfoCard(title = "My Information") {
                if (editing) {
                    OutlinedTextField(draftName, { draftName = it }, Modifier.fillMaxWidth(), label = { Text("Full name") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(draftDistrict, { draftDistrict = it }, Modifier.fillMaxWidth(), label = { Text("District") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(draftTaluk, { draftTaluk = it }, Modifier.fillMaxWidth(), label = { Text("Taluk") })
                    Spacer(Modifier.height(12.dp))
                    ChipCloud(draftCrops, { id -> draftCrops = if (id in draftCrops) draftCrops - id else draftCrops + id }, language, includeAll = false)
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            onProfileSaved(draftName, draftDistrict, draftTaluk, draftCrops)
                            editing = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("Save Changes")
                    }
                } else {
                    ProfileInfoRow("Full Name", farmerName)
                    ProfileInfoRow("Mobile", "+91 ${phone.maskPhone()}")
                    ProfileInfoRow("District", district)
                    ProfileInfoRow("Taluk", taluk)
                    ProfileInfoRow("Language", if (language == "kn") "ಕನ್ನಡ" else "English")
                    TextButton(onClick = { editing = true }, modifier = Modifier.align(Alignment.End)) {
                        Text("Edit", color = PrimaryGreen)
                    }
                }
            }
        }
        item {
            InfoCard(title = "Preferences / ಆದ್ಯತೆಗಳು") {
                PreferenceRow("Daily Tip Notification", notifications, { notifications = it })
                PreferenceRow("Offline Mode", offlineMode, { offlineMode = it })
            }
        }
        item {
            InfoCard(title = "Help & Support / ಸಹಾಯ") {
                listOf("How to Use App", "Contact Agricultural Helpline", "Privacy Policy", "About Raitha-Varta", "App Version: 1.0.0").forEach {
                    Row(Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(it, modifier = Modifier.weight(1f))
                        Text(">", color = TextMuted)
                    }
                }
            }
        }
        item {
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier.padding(16.dp).fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(26.dp),
                border = BorderStroke(1.dp, TomatoRed)
            ) {
                Text("Logout / ಲಾಗ್ ಔಟ್", color = TomatoRed, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 9.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = TextMuted, modifier = Modifier.weight(1f), fontSize = 13.sp)
        Text(value, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.End)
    }
    Divider(color = Color(0xFFE0E0E0))
}

@Composable
private fun PreferenceRow(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(label, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onChange)
    }
}

@Composable
private fun ChipCloud(selectedIds: Set<String>, onToggle: (String) -> Unit, language: String, includeAll: Boolean) {
    val categories = if (includeAll) RaithaData.categories else RaithaData.categories.filter { it.id != "all" }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        categories.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { category ->
                    FilterChip(
                        selected = category.id in selectedIds,
                        onClick = { onToggle(category.id) },
                        label = { Text(if (language == "kn") category.kn else category.en) },
                        modifier = Modifier.weight(1f)
                    )
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun TipDetailScreen(
    tip: Tip,
    language: String,
    saved: Boolean,
    onBack: () -> Unit,
    onSave: () -> Unit,
    onShare: (Tip) -> Unit,
    onSpeak: (String) -> Unit,
    onOpenTip: (String) -> Unit
) {
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().shadow(8.dp).background(Color.White).padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextIconButton(Icons.Rounded.Bookmark, if (saved) "Saved" else "Save", if (saved) FieldGold else PrimaryGreen, onSave)
                TextIconButton(Icons.Rounded.Share, "Share", PrimaryGreen) { onShare(tip) }
                TextIconButton(Icons.Rounded.VolumeUp, "Listen", PrimaryGreen) { onSpeak(tip.speechText(language)) }
            }
        }
    ) { inner ->
        LazyColumn(Modifier.fillMaxSize().padding(inner).background(Color.White)) {
            item {
                Box(Modifier.fillMaxWidth().height(286.dp)) {
                    Image(
                        painter = painterResource(tip.imageRes),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black.copy(alpha = 0.72f))))
                    )
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.padding(12.dp).clip(CircleShape).background(Color.Black.copy(alpha = 0.35f))
                    ) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Column(Modifier.align(Alignment.BottomStart).padding(18.dp)) {
                        AssistChip(onClick = {}, label = { Text("${RaithaData.categoryName(tip.categoryId, language)} | ${tip.type}", color = PrimaryGreen) })
                        Text(tip.title(language), color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
            item {
                DetailSection(title = "Quick Summary / ಸಾರಾಂಶ", accent = PrimaryGreen) {
                    Text(tip.shortAdvice(language), fontSize = 16.sp, lineHeight = 24.sp)
                    Spacer(Modifier.height(12.dp))
                    OutlinedButton(onClick = { onSpeak(tip.speechText(language)) }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Rounded.VolumeUp, contentDescription = null, tint = PrimaryGreen)
                        Spacer(Modifier.width(8.dp))
                        Text("Listen in Audio / ಆಡಿಯೋದಲ್ಲಿ ಕೇಳಿ", color = PrimaryGreen)
                    }
                }
            }
            item {
                DetailSection(title = "Recommendation Details", accent = FieldGold) {
                    DetailRow("Dosage", tip.dosage)
                    DetailRow("Weather", tip.weatherTag)
                    DetailRow("Source", tip.expert)
                }
            }
            item {
                DetailSection(title = "Precautions / ಮುನ್ನೆಚ್ಚರಿಕೆಗಳು", accent = TomatoRed, background = Color(0xFFFFF3E0)) {
                    tip.precautions.forEach { Text("- $it", modifier = Modifier.padding(vertical = 4.dp), lineHeight = 20.sp) }
                }
            }
            item {
                DetailSection(title = "Detailed Advisory", accent = SkyBlue) {
                    Text(tip.detail(language), fontSize = 15.sp, lineHeight = 23.sp)
                }
            }
            item {
                SectionHeader("Related Tips / ಸಂಬಂಧಿತ ಸಲಹೆಗಳು", "", {})
                LazyRow(contentPadding = PaddingValues(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(RaithaData.tips.filter { it.categoryId == tip.categoryId && it.id != tip.id }.ifEmpty { RaithaData.tips.take(3) }) { related ->
                        MiniTipCard(
                            tip = related,
                            language = language,
                            saved = false,
                            onOpen = { onOpenTip(related.id) },
                            onSave = {}
                        )
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun DetailSection(title: String, accent: Color, background: Color = Color.White, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = background),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(Modifier.height(IntrinsicSize.Min)) {
            Box(Modifier.width(5.dp).fillMaxHeight().background(accent))
            Column(Modifier.padding(16.dp).weight(1f)) {
                Text(title, color = accent, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(10.dp))
                content()
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(label, color = TextMuted, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(0.36f), fontSize = 13.sp)
        Text(value, modifier = Modifier.weight(0.64f), fontSize = 14.sp)
    }
    Divider(color = Color(0xFFE0E0E0))
}

@Composable
private fun NotificationsScreen(language: String, onBack: () -> Unit, onOpenExpert: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize().background(Color(0xFFFAFAFA))) {
        item {
            AppBar(
                title = if (language == "kn") "Notifications / ಸೂಚನೆಗಳು" else "Notifications",
                navigation = { IconButton(onClick = onBack) { Icon(Icons.Rounded.ArrowBack, contentDescription = "Back", tint = Color.White) } }
            )
        }
        items(
            listOf(
                "Paddy harvest advisory" to "Harvest mature paddy on time and sun dry before bagging.",
                "Thunderstorm safety" to "Avoid spraying during gusty wind and protect harvested produce.",
                "Tomato blight watch" to "Keep irrigation uniform and monitor fruit borer or early blight."
            )
        ) { (title, body) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 7.dp)
                    .clickable { if (title.contains("Tomato") || title.contains("Advisory")) onOpenExpert() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = if (title.contains("Paddy")) SurfaceGreen else Color.White),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(46.dp).clip(CircleShape).background(PrimaryGreen), contentAlignment = Alignment.Center) {
                        Icon(Icons.Rounded.Notifications, contentDescription = null, tint = Color.White)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(title, fontWeight = FontWeight.Bold)
                        Text(body, color = TextMuted, fontSize = 13.sp)
                    }
                    Text("Now", color = TextMuted, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun AppBar(
    title: String,
    navigation: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(PrimaryGreen)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (navigation != null) navigation() else Spacer(Modifier.width(8.dp))
        Text(
            title,
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            color = Color.White,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun StatText(value: String, label: String, modifier: Modifier = Modifier) {
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = PrimaryGreen, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Text(label, color = TextMuted, fontSize = 12.sp, textAlign = TextAlign.Center)
    }
}

@Composable
private fun EmptyState(title: String, subtitle: String, action: String, onAction: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(Modifier.size(86.dp).clip(CircleShape).background(LightGreen), contentAlignment = Alignment.Center) {
            Icon(Icons.Rounded.Bookmark, contentDescription = null, tint = PrimaryGreen, modifier = Modifier.size(42.dp))
        }
        Spacer(Modifier.height(16.dp))
        Text(title, fontSize = 21.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Text(subtitle, color = TextMuted, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Button(onClick = onAction, colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)) {
            Text(action)
        }
    }
}

private fun Tip.title(language: String): String = if (language == "kn") titleKn else titleEn

private fun Tip.shortAdvice(language: String): String = if (language == "kn") shortAdviceKn else shortAdviceEn

private fun Tip.detail(language: String): String = if (language == "kn") detailKn else detailEn

private fun Tip.speechText(language: String): String {
    return "${title(language)}. ${shortAdvice(language)}. ${precautions.joinToString(" ")}"
}

private fun shareTip(context: Context, tip: Tip, language: String) {
    shareText(
        context = context,
        title = "Share farming tip",
        text = "Check this farming tip from Raitha-Varta: ${tip.title(language)}\n\n${tip.shortAdvice(language)}"
    )
}

private fun shareText(context: Context, title: String, text: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, title)
        putExtra(Intent.EXTRA_TEXT, text)
    }
    context.startActivity(Intent.createChooser(intent, title))
}

private fun createCameraImageUri(context: Context): Uri {
    val dir = File(context.cacheDir, "expert_images").apply { mkdirs() }
    val file = File.createTempFile("leaf_${System.currentTimeMillis()}_", ".jpg", dir)
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}

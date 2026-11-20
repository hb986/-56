package com.antel.elmahalla

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

private data class Sound(val title: String, val resourceName: String)

class MainActivity : ComponentActivity() {
    private var player: MediaPlayer? = null
    private val sounds = listOf(
        Sound("زبو كبير", "zabo_kebir"),
        Sound("البندوره الحمره", "batandora_elhamra"),
        Sound("فكهانيوبحبالفاكهه", "fokhany"),
        Sound("واحد", "wahed"),
        Sound("سايق", "sayek"),
        Sound("بتنجاني قديم", "betengany_adim"),
        Sound("مالح", "maleh"),
        Sound("ظبريمنجا", "zabri_manga"),
        Sound("الالماني يا يونايتد عملت", "almani")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val availableSounds = sounds.filter { resources.getIdentifier(it.resourceName, "raw", packageName) != 0 }
        setContent {
            val mud = Color(0xFF49351F)
            val swamp = Color(0xFF102B20)
            val lime = Color(0xFF9BCB48)
            var visitorTaps by remember { mutableIntStateOf(0) }
            var lastPlayed by remember { mutableStateOf("اختار صوت من المستنقع") }
            MaterialTheme {
                Box(Modifier.fillMaxSize().background(swamp)) {
                    Box(Modifier.align(Alignment.TopStart).offset(x = (-35).dp, y = 150.dp).size(145.dp, 70.dp).clip(CircleShape).background(mud.copy(alpha = .55f)))
                    Box(Modifier.align(Alignment.BottomEnd).offset(x = 38.dp, y = (-75).dp).size(180.dp, 90.dp).clip(CircleShape).background(Color(0xFF65472A).copy(alpha = .55f)))
                    Column(Modifier.fillMaxSize().padding(horizontal = 18.dp, vertical = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = R.drawable.frog), contentDescription = "صورة التطبيق", modifier = Modifier.size(112.dp).clip(RoundedCornerShape(26.dp)), contentScale = ContentScale.Crop)
                        Spacer(Modifier.height(10.dp))
                        Text("عنتيل المحله", color = Color(0xFFE5F5C8), fontSize = 29.sp, fontWeight = FontWeight.Black)
                        Text("أهلاً بيك في المستنقع 😂", color = Color(0xFFB6D58B), fontSize = 15.sp)
                        Spacer(Modifier.height(14.dp))
                        VisitorButton(visitorTaps, lime) { visitorTaps++ }
                        Spacer(Modifier.height(12.dp))
                        Text(lastPlayed, color = Color(0xFFD5E8B5), fontSize = 13.sp)
                        Spacer(Modifier.height(10.dp))
                        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.weight(1f).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(11.dp), horizontalArrangement = Arrangement.spacedBy(11.dp), contentPadding = PaddingValues(bottom = 10.dp)) {
                            items(availableSounds) { sound ->
                                SoundButton(sound.title, lime) {
                                    lastPlayed = "شغال دلوقتي: ${sound.title}"
                                    play(sound)
                                }
                            }
                            item {
                                SoundButton("🎲 صوت عشوائي", Color(0xFFE4B34E)) {
                                    if (availableSounds.isNotEmpty()) {
                                        val selected = availableSounds[Random.nextInt(availableSounds.size)]
                                        lastPlayed = "اختيار عشوائي: ${selected.title}"
                                        play(selected)
                                    }
                                }
                            }
                        }
                        Text("وحل كرتوني • ضفادع • ضحك", color = Color(0xFF8BAA6A), fontSize = 11.sp)
                    }
                }
            }
        }
    }

    private fun play(sound: Sound) {
        val id = resources.getIdentifier(sound.resourceName, "raw", packageName)
        if (id == 0) return
        player?.let { try { it.stop() } catch (_: IllegalStateException) {}; it.release() }
        player = MediaPlayer.create(this, id)?.apply {
            setOnCompletionListener { mp -> mp.release(); if (player === mp) player = null }
            start()
        }
    }
    override fun onStop() { player?.release(); player = null; super.onStop() }
    override fun onDestroy() { player?.release(); player = null; super.onDestroy() }
}

@Composable
private fun VisitorButton(count: Int, color: Color, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) 1.12f else 1f, spring(dampingRatio = .35f, stiffness = 500f), label = "visitorBounce")
    Surface(
        modifier = Modifier.scale(scale).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { pressed = true; onClick(); pressed = false },
        color = Color(0xFF294B2C), shape = RoundedCornerShape(50), border = BorderStroke(1.dp, color.copy(alpha = .7f))
    ) {
        Text("👣 الزوار: $count", modifier = Modifier.padding(horizontal = 22.dp, vertical = 11.dp), color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SoundButton(title: String, color: Color, onClick: () -> Unit) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (pressed) .94f else 1f, spring(dampingRatio = .45f, stiffness = 600f), label = "soundButton")
    Button(onClick = { pressed = true; onClick(); pressed = false }, modifier = Modifier.fillMaxWidth().height(82.dp).scale(scale), shape = RoundedCornerShape(20.dp), colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = Color(0xFF172719)), elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp, pressedElevation = 1.dp)) {
        Text(title, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
    }
}

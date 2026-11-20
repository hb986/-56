package com.antel.elmahalla

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

private val sounds = listOf(
    "زبو كبير", "البندوره الحمره", "فكهانيوبحبالفاكهه",
    "واحد", "سايق", "بتنجاني قديم", "مالح", "ظبريمنجا",
    "الالماني يا يونايتد عملت"
)

class MainActivity : ComponentActivity() {
    private var tone: ToneGenerator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tone = ToneGenerator(AudioManager.STREAM_MUSIC, 90)
        setContent {
            val green = Color(0xFF245B24)
            val bright = Color(0xFF75C843)
            MaterialTheme {
                Column(
                    Modifier.fillMaxSize().background(green).padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🐸", fontSize = 64.sp)
                    Text("عنتيل المحله", color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Black)
                    Text("اضغط على الزر وشغّل الصوت 😂", color = Color(0xFFD9F5C5), fontSize = 15.sp)
                    Spacer(Modifier.height(18.dp))
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(sounds) { index, name ->
                            Button(
                                onClick = { play(index) },
                                modifier = Modifier.fillMaxWidth().height(88.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = bright, contentColor = Color(0xFF102B10))
                            ) {
                                Text(name, fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                            }
                        }
                        item {
                            Button(
                                onClick = { play(Random.nextInt(sounds.size)) },
                                modifier = Modifier.fillMaxWidth().height(88.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD54F), contentColor = Color(0xFF342500))
                            ) { Text("🎲 صوت عشوائي", fontSize = 16.sp, fontWeight = FontWeight.Black) }
                        }
                    }
                    Text("نسخة تجريبية — ملفات MP3 الأصلية تُضاف في مجلد app/src/main/res/raw",
                        color = Color(0xFFCBE7C0), fontSize = 11.sp, textAlign = TextAlign.Center)
                }
            }
        }
    }

    private fun play(index: Int) {
        val patterns = intArrayOf(
            ToneGenerator.TONE_PROP_BEEP, ToneGenerator.TONE_PROP_ACK,
            ToneGenerator.TONE_PROP_NACK, ToneGenerator.TONE_DTMF_1,
            ToneGenerator.TONE_DTMF_2, ToneGenerator.TONE_DTMF_3,
            ToneGenerator.TONE_DTMF_4, ToneGenerator.TONE_DTMF_5,
            ToneGenerator.TONE_DTMF_6
        )
        tone?.stopTone()
        tone?.startTone(patterns[index.coerceIn(0, patterns.lastIndex)], 450)
    }

    override fun onDestroy() {
        tone?.release()
        tone = null
        super.onDestroy()
    }
}

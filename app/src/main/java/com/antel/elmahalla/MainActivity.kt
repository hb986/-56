package com.antel.elmahalla

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
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
                        itemsIndexed(sounds) { _, sound ->
                            Button(
                                onClick = { play(sound) },
                                modifier = Modifier.fillMaxWidth().height(88.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = bright, contentColor = Color(0xFF102B10))
                            ) {
                                Text(sound.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                            }
                        }
                        item {
                            Button(
                                onClick = { play(sounds[Random.nextInt(sounds.size)]) },
                                modifier = Modifier.fillMaxWidth().height(88.dp),
                                shape = RoundedCornerShape(18.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD54F), contentColor = Color(0xFF342500))
                            ) { Text("🎲 صوت عشوائي", fontSize = 16.sp, fontWeight = FontWeight.Black) }
                        }
                    }
                }
            }
        }
    }

    private fun play(sound: Sound) {
        val id = resources.getIdentifier(sound.resourceName, "raw", packageName)
        if (id == 0) {
            Toast.makeText(this, "ملف الصوت ${sound.title} لم يُضف للمشروع بعد", Toast.LENGTH_SHORT).show()
            return
        }
        player?.let {
            try { it.stop() } catch (_: IllegalStateException) {}
            it.release()
        }
        player = MediaPlayer.create(this, id)?.apply {
            setOnCompletionListener { mp ->
                mp.release()
                if (player === mp) player = null
            }
            start()
        }
    }

    override fun onStop() {
        player?.release()
        player = null
        super.onStop()
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }
}

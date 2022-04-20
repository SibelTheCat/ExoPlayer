package com.example.exoplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.exoplayer.ui.theme.ExoPlayerTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.MediaItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExoPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    VideoPlayer()
                }
            }
        }
    }
}

@Composable
fun VideoPlayer(){
    val testvid2 = "rtsp://rtsp.stream/pattern"
    val streamVideo = "rtsp://10.0.0.134:8554/stream1"
    val testVideo = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4"
    val context = LocalContext.current
    val player = ExoPlayer.Builder(context).build()
    val playerView = StyledPlayerView(context)
    //  https://exoplayer.dev/doc/reference/com/google/android/exoplayer2/ui/StyledPlayerView.html
    playerView.setShowFastForwardButton(false)
    playerView.setShowNextButton(false)
    playerView.setShowPreviousButton(false)
    playerView.setShowRewindButton(false)
    playerView.hideController()

    //video Source ist mein media Itm (I guess)
    val videoSource = RtspMediaSource.Factory().setDebugLoggingEnabled(true)
        .createMediaSource(MediaItem.fromUri(testvid2))

    val playWhenReady by rememberSaveable {
        mutableStateOf(true)
    }

    player.setMediaSource(videoSource)


    //// Bind the player to the view.
   // other option -> playerView.setPlayer(player);
    playerView.player = player

    LaunchedEffect(player) {
        player.prepare()
        player.playWhenReady = playWhenReady

    }
    AndroidView(factory = {
        playerView
    })
}

@Composable
fun VideoPlayer2(){

    val sampleVideo = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    val context = LocalContext.current
    val player = ExoPlayer.Builder(context).build()
    val playerView = StyledPlayerView(context)
    val mediaItem = MediaItem.fromUri(sampleVideo)
    val playWhenReady by rememberSaveable {
        mutableStateOf(true)
    }
    player.setMediaItem(mediaItem)
    playerView.player = player
    LaunchedEffect(player) {
        player.prepare()
        player.playWhenReady = playWhenReady

    }
    AndroidView(factory = {
        playerView
    })
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExoPlayerTheme {
        Greeting("Android")
    }
}
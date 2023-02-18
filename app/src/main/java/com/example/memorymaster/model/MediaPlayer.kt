package com.example.memorymaster.model

import android.content.Context
import android.media.MediaPlayer
import com.example.memorymaster.R

class MediaPlayer(private var context: Context) {

    var player: MediaPlayer = MediaPlayer()
    private var pause: Boolean = false
    private var muted: Boolean = false
    private var currentPosition: Int = 0

    // Soundtrack IDs:
    // 1-CorrectPair
    // 2-Lost
    // 3-Win
    // Other-Main

    private fun changeSoundtrack(soundtrack: Int) {
        player.isLooping = false
        player = when (soundtrack) {
            1 -> {
                MediaPlayer.create(context, R.raw.correct_pair)
            }
            2 -> {
                MediaPlayer.create(context, R.raw.lost)
            }
            3 -> {
                MediaPlayer.create(context, R.raw.win)
            }
            else -> {
                MediaPlayer.create(context, R.raw.main)
            }
        }
    }

    private fun pausePlayer() {
        if (player.isPlaying) {
            player.pause()
            pause = true
            currentPosition = player.currentPosition
        }
    }

    private fun continuePlayer() {
        changeSoundtrack(0)
        if (pause) {
            player.seekTo(this.currentPosition)
            pause = false
        }
        player.start()
        player.isLooping = true
    }

    private fun setCompletionListener() {
        player.start()
        player.setOnCompletionListener {
            continuePlayer()
        }
    }

    fun correctPair() {
        // play the correct pair soundtrack
        pausePlayer()
        changeSoundtrack(1)
        setCompletionListener()
    }

    fun youLost() {
        // play the time end and you lost soundtrack
        pausePlayer()
        changeSoundtrack(2)
        player.start()
    }

    fun youWin() {
        // play the you win soundtrack that you have found all pairs before the time ends
        pausePlayer()
        changeSoundtrack(3)
        player.start()
    }

    fun playMainSoundtrack() {
        changeSoundtrack(0)
        continuePlayer()
    }

    fun mutePlayer() {
        muted = if (muted) {
            player.setVolume(1f, 1f)
            false
        } else {
            player.setVolume(0f, 0f)
            true
        }
    }

    fun stopPlayer() {
        player.stop()
    }

}


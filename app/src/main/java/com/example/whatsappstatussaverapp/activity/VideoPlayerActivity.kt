package com.example.whatsappstatussaverapp.activity

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsappstatussaverapp.databinding.ActivityVideoPlayerBinding
import com.universalvideoview.UniversalMediaController
import com.universalvideoview.UniversalVideoView

class VideoPlayerActivity : AppCompatActivity() {

    lateinit var binding: ActivityVideoPlayerBinding
    lateinit var mBottomLayout: View
    var mVideoLayout: View ?= null
    lateinit var mVideoView: UniversalVideoView
    lateinit var mMediaController: UniversalMediaController
    var isFullscree = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        initView()
        setVideoView()

    }

    private fun initView() {

        binding.apply {
            mVideoView = videoView
            mMediaController = mediaController
            mVideoView.setMediaController(mMediaController)
            val fileUri = intent.getParcelableExtra(Intent.EXTRA_STREAM) as Uri?

            if (fileUri != null) {
                mVideoView.setVideoURI(fileUri)

            } else {
                val uri = Uri.parse(intent.getStringExtra("uri"))
                mVideoView.setVideoURI(uri)
            }
        }

    }

    private fun setVideoView() {

        mVideoView.setVideoViewCallback(object : UniversalVideoView.VideoViewCallback {
            override fun onScaleChange(isFullscreen: Boolean) {
                isFullscree = isFullscreen
                if (isFullscreen) {
                    val layoutParams = mVideoLayout!!.layoutParams
                    layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    mVideoLayout!!.layoutParams = layoutParams
                    //GONE the unconcerned views to leave room for video and controller
                    mBottomLayout.visibility = View.GONE
                } else {
                    val layoutParams = mVideoLayout!!.layoutParams
                    layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
                    layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    mVideoLayout!!.layoutParams = layoutParams
                    mBottomLayout.visibility = View.VISIBLE
                }
            }

            override fun onPause(mediaPlayer: MediaPlayer) { // Video pause
                Log.d("TAG", "onPause UniversalVideoView callback")
            }

            override fun onStart(mediaPlayer: MediaPlayer) { // Video start/resume to play
                binding.progress.visibility = View.GONE
                Log.d("TAG", "onStart UniversalVideoView callback")
            }

            override fun onBufferingStart(mediaPlayer: MediaPlayer) { // steam start loading
                Log.d("TAG", "onBufferingStart UniversalVideoView callback")
            }

            override fun onBufferingEnd(mediaPlayer: MediaPlayer) { // steam end loading
                Log.d("TAG", "onBufferingEnd UniversalVideoView callback")
            }
        })
        mVideoView.setOnPreparedListener {
            mVideoView.start()
        }
        var startCount = 0
        binding.apply {
            secMinus.setOnClickListener {
                startCount++
                Handler().postDelayed(Runnable {
                    startCount = 0
                }, 500)
                if (startCount == 2) mVideoView.seekTo(mVideoView.currentPosition - 10000)
            }
            var plusCount = 0
            secPlus.setOnClickListener {
                plusCount++
                Handler().postDelayed(Runnable {
                    plusCount = 0
                }, 500)
                if (plusCount == 2) {
                    mVideoView.seekTo(mVideoView.currentPosition + 10000)
                }
            }
        }

    }

}
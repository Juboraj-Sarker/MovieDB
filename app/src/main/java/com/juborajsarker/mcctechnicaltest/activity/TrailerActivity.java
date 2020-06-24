package com.juborajsarker.mcctechnicaltest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.juborajsarker.mcctechnicaltest.R;
import com.juborajsarker.mcctechnicaltest.api.Api;

public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,
YouTubePlayer.PlaybackEventListener, YouTubePlayer.PlayerStateChangeListener, YouTubePlayer.OnFullscreenListener{

    YouTubePlayerView youTubePlayerView;
    String API_KEY = Api.API_KEY_GOOGLE;
    String VIDEO_ID = "";
    YouTubePlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        init();
    }

    private void init() {

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);
        VIDEO_ID = getIntent().getStringExtra("video_id");

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.setPlayerStateChangeListener(this);
        youTubePlayer.setPlaybackEventListener(this);
        youTubePlayer.setOnFullscreenListener(this);
        player = youTubePlayer;

        if (!b){
            youTubePlayer.cueVideo(VIDEO_ID);


        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String videoId) {
        if(!TextUtils.isEmpty(videoId) && player != null){

            player.play();
           // player.setFullscreen(true);
        }

    }

    @Override
    public void onAdStarted() {

    }

    @Override
    public void onVideoStarted() {

    }

    @Override
    public void onVideoEnded() {

    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }

    @Override
    public void onFullscreen(boolean b) {

    }




}
package com.verianggoro.tmdbmovie.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog

object Utility {
    fun showErrorDialog(context: Context?, e: Throwable, okListener: (() -> Unit)?): AlertDialog? {
        return context?.showErrorDialog(e, okListener)
    }

    fun showErrorDialog(context: Context?, e: Exception): AlertDialog? {
        return this.showErrorDialog(context, e, null)
    }

    fun hideKeyBoard(view: View?){
        if (view != null) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    val headers = hashMapOf(
        "Authorization" to "Bearer "+Config.TOKEN
    )

    fun getHtmlTrailer(videoId: String): String {
        return  """
        <html>
        
            <body style="margin:0px;padding:0px;">
               <div id="player"></div>
                <script>
                    var player;
                    function onYouTubeIframeAPIReady() {
                        player = new YT.Player('player', {
                            height: '100%',
                            width: '100%',
                            videoId: '$videoId',
                            playerVars: {
                                'playsinline': 1
                            },
                            events: {
                                'onReady': onPlayerReady
                            }
                        });
                    }
                    function onPlayerReady(event) {
                     player.playVideo();
                        // Player is ready
                    }
                    function seekTo(time) {
                        player.seekTo(time, true);
                    }
                      function playVideo() {
                        player.playVideo();
                    }
                    function pauseVideo() {
                        player.pauseVideo();
                    }
                </script>
                <script src="https://www.youtube.com/iframe_api"></script>
            </body>
        </html>
    """
    }
}
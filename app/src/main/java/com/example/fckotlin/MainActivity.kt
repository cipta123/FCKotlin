package com.example.fckotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the WebView in the layout
        webView = findViewById(R.id.webView)

        // Enable JavaScript in the WebView
        webView.settings.javaScriptEnabled = true

        // Set the WebViewClient to handle redirects within the WebView
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }
        }

        // Set the WebChromeClient to handle video playback
        webView.webChromeClient = object : WebChromeClient() {

            // Handle going fullscreen
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
                if (view == null) {
                    return
                }

                webView.visibility = View.GONE
                view.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN

                // Add the custom view to the layout
                val decorView = window.decorView as FrameLayout
                decorView.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }

            // Handle leaving fullscreen
            override fun onHideCustomView() {
                super.onHideCustomView()
                webView.visibility = View.VISIBLE
                // Remove the custom view from the layout
                val decorView = window.decorView as FrameLayout
                decorView.removeViewAt(decorView.childCount - 1)
            }
        }

        // Load the WebView with a video
        webView.loadUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
    }

    // Handle back button presses
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}

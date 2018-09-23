package com.example.ken.memoboard

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_board.*
import android.widget.RelativeLayout


class BoardActivity : AppCompatActivity() {

    private val WC = ViewGroup.LayoutParams.WRAP_CONTENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        // メモ作成ボタン
        fab.setOnClickListener { view ->
            Snackbar.make(view, "New Memo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            // メモtextView作成
            val memo = TextView(this)
            memo.text = "これはメモのテストです．アイウエオかきくけこ"
            memo.textSize = 20f
            memo.setBackgroundColor(Color.GRAY)
            memo.setPadding(20, 20, 20, 20)
            memo.width = 500
            memo.setOnClickListener { view ->
                println("Memo is tapped")
            }

            // マージン設定
            val param = RelativeLayout.LayoutParams(WC, WC)
            param.setMargins(100, 100, 100, 100)

            // View追加
            addContentView(memo, param)

        }

    }
}
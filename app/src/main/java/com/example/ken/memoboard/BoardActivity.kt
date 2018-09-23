package com.example.ken.memoboard

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_board.*
import java.util.*


class BoardActivity : AppCompatActivity() {

    private val WC = ViewGroup.LayoutParams.WRAP_CONTENT
    private val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val dp = wm.defaultDisplay

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

            // タップ時の挙動
            memo.setOnClickListener {
                it.setBackgroundColor(Color.GREEN)
                println("Memo is tapped")
            }

            // マージン設定
            val topMargin = random.nextInt(dp.height-700)
            val leftMargin = random.nextInt(dp.width-500)
            val param = RelativeLayout.LayoutParams(WC, WC)
            param.setMargins(leftMargin, topMargin, 0, 0)

            // View追加
            addContentView(memo, param)

        }

    }

}
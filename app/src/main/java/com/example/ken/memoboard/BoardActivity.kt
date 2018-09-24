package com.example.ken.memoboard

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.ken.memoboard.listener.MemoListener
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

            // タッチイベント時の挙動
            val listener = MemoListener(memo)
            memo.setOnTouchListener(listener)

            // マージン設定
            val left = random.nextInt(dp.width - 500)
            val top = random.nextInt(dp.height - 700)
            val param = RelativeLayout.LayoutParams(WC, WC)
            param.setMargins(left, top, 0, 0)

            // View追加
            addContentView(memo, param)

        }

    }

}
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
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_board.*
import java.util.*


class BoardActivity : AppCompatActivity() {

    private val wc = ViewGroup.LayoutParams.WRAP_CONTENT
    private val random = Random()
    private lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        mRealm = Realm.getDefaultInstance()

        // 新規メモ作成ボタン
        fab.setOnClickListener { view ->

            Snackbar.make(view, "New Memo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            // 新規メモ作成
            createMemo()

        }

    }

    /**
     * 新規メモ作成
     */
    private fun createMemo() {

        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val dp = wm.defaultDisplay

        // デフォルト設定
        val name = "新規メモ"
        val text = "新規メモ"
        val textSize = 30f
        val color = Color.LTGRAY
        val padding = 20
        val width = 500
        val left = random.nextInt(dp.width - 500)
        val top = random.nextInt(dp.height - 500)

        // メモtextView作成
        val memoView = TextView(this)
        memoView.text = text
        memoView.textSize = textSize
        memoView.setBackgroundColor(color)
        memoView.setPadding(padding, padding, padding, padding)
        memoView.width = width

        // タッチイベント時の挙動
        val listener = MemoListener(memoView, this)
        memoView.setOnTouchListener(listener)

        // layout param
        val param = RelativeLayout.LayoutParams(wc, wc)
        param.setMargins(left, top, 0, 0)

        // メモを画面に追加
        addContentView(memoView, param)

        // Realm保存
        mRealm.executeTransaction {

            // Auto Increment
            val max = mRealm.where<Memo>().max("id")
            var newId: Long = 0
            if (max != null) {//nullチェック
                newId = max.toLong() + 1
            }

            // メモモデル作成
            val memo: Memo = mRealm.createObject<Memo>(primaryKeyValue = newId)

            // データ挿入
            memo.name = name
            memo.text = text
            memo.color = color
            memo.date = Date()
            memo.left = left
            memo.top = top

            println(memo.toString())

        }

    }

}
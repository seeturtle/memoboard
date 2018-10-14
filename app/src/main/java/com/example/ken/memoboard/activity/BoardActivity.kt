package com.example.ken.memoboard.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.ken.memoboard.R
import com.example.ken.memoboard.listener.MemoListener
import com.example.ken.memoboard.model.Board
import com.example.ken.memoboard.model.Memo
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_board.*
import java.util.*


class BoardActivity : AppCompatActivity() {

    private val wc = ViewGroup.LayoutParams.WRAP_CONTENT
    private val random = Random()
    private lateinit var mRealm: Realm

    // デフォルト設定
    val name = "新規メモ"
    val text = "新規メモ"
    val textSize = 30f
    val color = Color.YELLOW
    val padding = 20
    val width = 500


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        mRealm = Realm.getDefaultInstance()

        //メモを表示する。
        createMemoView()

        // 新規メモ作成ボタン
        fab.setOnClickListener { view ->

            Snackbar.make(view, "New Memo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            // 新規メモ作成
            createMemo()

            // アクティビティ再開
            recreate()

        }

    }

    /**
     * データベースに紐づけられたメモを表示する
     */
    private fun createMemoView() {
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val dp = wm.defaultDisplay

        if (intent != null) {

            val boardId = intent.getLongExtra("ID", -1)
            val board = mRealm.where(Board::class.java)
                    .equalTo("id", boardId)
                    .findFirst()

            for (memo in board?.memos!!) {
                // メモtextView作成
                val memoView = TextView(this)
                memoView.id = memo.id.toInt()
                memoView.text = memo.text
                memoView.textSize = textSize
                memoView.setBackgroundColor(memo.color)
                memoView.setPadding(padding, padding, padding, padding)
                memoView.width = width
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    memoView.elevation = 40f  // 影エフェクト(API21以上)
                }

                // layout param
                val param = RelativeLayout.LayoutParams(wc, wc)
                param.setMargins(memo.left, memo.top, 0, 0)

                // タッチイベント時の挙動
                val listener = MemoListener(memoView, this, mRealm, dp)
                memoView.setOnTouchListener(listener)

                // メモを画面に追加
                addContentView(memoView, param)

            }

        }
    }


    /**
     * 新規メモ作成
     */
    private fun createMemo() {

        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val dp = wm.defaultDisplay
        val left = random.nextInt(dp.width - 500)
        val top = random.nextInt(dp.height - 500)

        // Realm保存
        mRealm.executeTransaction {

            // Auto Increment
            val max = mRealm.where<Memo>().max("id")
            var newId: Long = 0
            if (max != null) {//nullチェック
                newId = max.toLong() + 1
            }

            // メモモデル作成
            val memo: Memo = mRealm.createObject(primaryKeyValue = newId)

            // 親のBoard取得
            val boardId = intent.getLongExtra("ID", -1)
            var board: Board? = mRealm.where(Board::class.java).equalTo("id", boardId).findFirst()

            // データ挿入
            memo.boardId = boardId
            memo.name = name
            memo.text = text
            memo.color = color
            memo.date = Date()
            memo.left = left
            memo.top = top
            //親のboardにmemoを保存
            board?.memos?.add(memo)

        }

    }

}
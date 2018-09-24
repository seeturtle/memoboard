package com.example.ken.memoboard.listener

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import com.example.ken.memoboard.BoardActivity
import com.example.ken.memoboard.MemoActivity


/**
 * メモ用タッチイベントListener
 */
class MemoListener(private val dragView: TextView, private val boardActivity: BoardActivity) : OnTouchListener {

    // ドラッグ中に移動量を取得するための変数
    private var oldX: Int = 0
    private var oldY: Int = 0

    private val gestureDetector = GestureDetector(dragView.context, GestureListener())

    // タップ時に発生するイベント
    override fun onTouch(view: View, event: MotionEvent): Boolean {

        // ダブルタップ時
        gestureDetector.onTouchEvent(event)

        // タッチしている位置取得
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()

        // Actinoによって分岐
        when (event.action) {

            MotionEvent.ACTION_MOVE -> {
                // 今回イベントでのView移動先の位置
                val left = dragView.left + (x - oldX)
                val top = dragView.top + (y - oldY)
                // Viewを移動する
                dragView.layout(left, top, left + dragView.width, top + dragView.height)
            }

        }

        // 今回のタッチ位置を保持
        oldX = x
        oldY = y

        // イベント処理完了
        return true
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        // ダブルタップ時のイベント
        override fun onDoubleTap(e: MotionEvent): Boolean {

            // 画面作成
            val intent = Intent(boardActivity, MemoActivity::class.java)
            val message = dragView.text.toString()
            intent.putExtra("memo", message)
            // 画面遷移
            startActivity(dragView.context, intent, null)

            return true
        }
    }

}
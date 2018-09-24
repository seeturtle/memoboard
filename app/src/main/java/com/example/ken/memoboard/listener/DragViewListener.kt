package com.example.ken.memoboard.listener

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView

/**
 * ドラッグ時にViewを移動させるListener
 */
class DragViewListener(private val dragView: TextView) : OnTouchListener {

    // ドラッグ中に移動量を取得するための変数
    private var oldx: Int = 0
    private var oldy: Int = 0

    // タップ時に発生するイベント
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        // タッチしている位置取得
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                // 今回イベントでのView移動先の位置
                val left = dragView.getLeft() + (x - oldx)
                val top = dragView.getTop() + (y - oldy)
                // Viewを移動する
                dragView.layout(left, top, left + dragView.getWidth(), top + dragView.getHeight())
            }
        }

        // 今回のタッチ位置を保持
        oldx = x
        oldy = y
        // イベント処理完了
        return true
    }
}
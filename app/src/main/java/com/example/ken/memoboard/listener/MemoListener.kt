package com.example.ken.memoboard.listener

import android.content.Intent
import android.view.*
import android.view.View.OnTouchListener
import android.widget.TextView
import com.example.ken.memoboard.activity.BoardActivity
import com.example.ken.memoboard.activity.MemoActivity
import com.example.ken.memoboard.model.Memo
import io.realm.Realm
import io.realm.kotlin.where


/**
 * メモ用タッチイベントListener
 */
class MemoListener(private val dragView: TextView, private val boardActivity: BoardActivity, private val realm: Realm, private val dp: Display) : OnTouchListener {

    // ドラッグ中に移動量を取得するための変数
    private var oldX: Int = 0
    private var oldY: Int = 0

    private val gestureDetector = GestureDetector(dragView.context, GestureListener())

    private val bottomMargin = 210

    // タップ時に発生するイベント
    override fun onTouch(view: View, event: MotionEvent): Boolean {

        // ダブルタップ時
        gestureDetector.onTouchEvent(event)

        // タッチしている位置取得
        val x = event.rawX.toInt()
        val y = event.rawY.toInt()

        // Actionによって分岐
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                dragView.bringToFront()
                dragView.alpha = 0.7f
            }

            MotionEvent.ACTION_MOVE -> {

                // 今回イベントでのView移動先の位置
                var left = dragView.left + (x - oldX)
                var top = dragView.top + (y - oldY)

                // 枠外から出ないようにする
                if (dragView.left < 0) left = 0
                if (dragView.right > dp.width) left = dp.width - dragView.width
                if (dragView.top < 0) top = 0
                if (dragView.bottom > (dp.height - bottomMargin)) top = (dp.height - bottomMargin) - dragView.height

                // Viewを移動する
                dragView.layout(left, top, left + dragView.width, top + dragView.height)

            }

            MotionEvent.ACTION_UP -> {

                // 今回イベントでのView移動先の位置
                val left = dragView.left + (x - oldX)
                val top = dragView.top + (y - oldY)

                // layoutを固定
                val param = dragView.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(left, top, 0, 0)
                dragView.layoutParams = param

                // Realmに位置を保存
                val memo = realm.where<Memo>().equalTo("id", dragView.id).findFirst()
                realm.executeTransaction {
                    memo?.left = left
                    memo?.top = top

                }

                dragView.alpha = 1.0f

            }

        }

        when (event.edgeFlags) {
            MotionEvent.EDGE_TOP -> {
                println("aaa")
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
            val memoId = dragView.id
            intent.putExtra("memoId", memoId)
            // 画面遷移
            boardActivity.startActivity(intent)

            return true
        }

    }

}
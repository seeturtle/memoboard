package com.example.ken.memoboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View




class MyView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var pathList: ArrayList<Path>  // 直線リスト
    private var paint: Paint


    init {
        //-- 初期化
        //- Path関連
        pathList = ArrayList()   // リストの作成

        //- Paint関連
        paint = Paint()
        paint.color = Color.RED          // 色の指定
        paint.style = Paint.Style.STROKE // 描画設定を'線'に設定
        paint.isAntiAlias = true           // アンチエイリアスの適応
        paint.strokeWidth = 10f           // 線の太さ
    }

    override fun onDraw(canvas: Canvas) {
        for (path in pathList) {
            canvas.drawPath(path, paint)   // Pathの描画
        }

        invalidate()   // 再描画
    }

    private var drawingPath: Path? = null

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN                     //- 画面をタッチしたとき
            -> {
                drawingPath = Path()                    // 新たなPathのインスタンスの作成
                drawingPath!!.moveTo(event.x, event.y)  // 始点を設定
                pathList.add(drawingPath!!)             // リストにPathを追加
            }
            MotionEvent.ACTION_UP                       //- 画面から指を離したとき
            -> drawingPath!!.moveTo(event.x, event.y)   // 移動先の追加
            MotionEvent.ACTION_MOVE                     //- タッチしながら指をスライドさせたとき
            -> drawingPath!!.lineTo(event.x, event.y)   // 移動先の追加
        }

        return true   /* 返却値は必ず "true" にすること!! */
    }

}
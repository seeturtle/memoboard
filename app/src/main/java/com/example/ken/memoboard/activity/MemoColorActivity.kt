package com.example.ken.memoboard.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.ken.memoboard.R
import kotlinx.android.synthetic.main.activity_memo_color.*


class MemoColorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_color)

        // インテントからmemoId, memoColor取得
        val intent = intent
        val memoId = intent.getIntExtra("memoId", 1)
        var memoColor = intent.getIntExtra("memoColor", Color.GRAY)
        var changedMemoColor = intent.getIntExtra("memoColor", Color.GRAY)

        // color picker の設定
        colorPicker?.addSVBar(svBar)
        colorPicker?.addOpacityBar(opacityBar)
        colorPicker?.addSaturationBar(saturationbar)
        colorPicker?.addValueBar(valueBar)
        colorPicker.oldCenterColor = memoColor
        colorPicker?.setOnColorChangedListener { changedMemoColor = it }

        colorSaveButton.setOnClickListener {

            val intent = Intent(this, MemoActivity::class.java)
            intent.putExtra("memoId", memoId)
            intent.putExtra("memoColor", changedMemoColor)
            startActivity(intent)

        }

        colorCancelButton.setOnClickListener {

            // cancel処理(何もせずに戻る)
            val intent = Intent(this, MemoActivity::class.java)
            intent.putExtra("memoId", memoId)
            intent.putExtra("memoColor", memoColor)
            startActivity(intent)

        }

    }

}

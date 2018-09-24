package com.example.ken.memoboard

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_memo.*


class MemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        val intent = intent
        val message = intent.getStringExtra("memo")
        editMemo.setText(message, TextView.BufferType.NORMAL)

        saveButton.setOnClickListener {

            // TODO: save処理
            val intent = Intent(this, BoardActivity::class.java)
            startActivity(intent)

        }

        cancelButton.setOnClickListener {

            // cancel処理(何もせずに戻る)
            val intent = Intent(this, BoardActivity::class.java)
            startActivity(intent)

        }
    }
}

package com.example.ken.memoboard

import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class MemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        val intent = intent
        val message = intent.getStringExtra("memo")

        println(message)
    }
}

package com.example.ken.memoboard

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_memo.*


class MemoActivity : AppCompatActivity() {

    private lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)

        mRealm = Realm.getDefaultInstance()

        // インテントからmemoId取得
        val intent = intent
        val memoId = intent.getIntExtra("memoId", 1)

        // Realmからメモ情報取得
        val memo = mRealm.where(Memo::class.java).equalTo("id", memoId).findFirst()

        // テキストフィールドに表示
        editMemo.setText(memo?.text, TextView.BufferType.NORMAL)

        saveButton.setOnClickListener {

            // save処理
            mRealm.executeTransaction {
                memo?.text = editMemo.text.toString()
            }

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

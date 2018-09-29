package com.example.ken.memoboard.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.example.ken.memoboard.R
import com.example.ken.memoboard.model.Memo
import io.realm.Realm
import io.realm.kotlin.where
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
        val memo = mRealm.where<Memo>().equalTo("id", memoId).findFirst()

        // テキストフィールドに表示
        editMemo.setText(memo?.text, TextView.BufferType.NORMAL)

        // メモの色
        val memoColor = intent.getIntExtra("memoColor", memo?.color!!)
        editColorButton.setBackgroundColor(memoColor)

        // 保存ボタン
        saveButton.setOnClickListener {

            // save処理
            mRealm.executeTransaction {
                memo?.text = editMemo.text.toString()
                memo?.color = memoColor
            }

            val intent = Intent(this, BoardActivity::class.java)
            intent.putExtra("ID", memo?.boardId)
            startActivity(intent)

        }

        // キャンセルボタン
        cancelButton.setOnClickListener {

            // cancel処理(何もせずに戻る)
            val intent = Intent(this, BoardActivity::class.java)
            intent.putExtra("ID", memo?.boardId)
            startActivity(intent)

        }

        // 削除ボタン
        deleteButton.setOnClickListener {

            val intent = Intent(this, BoardActivity::class.java)
            intent.putExtra("ID", memo?.boardId)

            // delete処理
            mRealm.executeTransaction {
                memo?.deleteFromRealm()
            }

            startActivity(intent)

        }

        // メモの色変更
        editColorButton.setOnClickListener {

            // 画面作成
            val intent = Intent(this, MemoColorActivity::class.java)
            intent.putExtra("memoId", memoId)
            intent.putExtra("memoColor", memoColor)
            // 画面遷移
            startActivity(intent)

        }

    }

}

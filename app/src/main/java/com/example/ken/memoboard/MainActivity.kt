package com.example.ken.memoboard

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        //データベースのオープン処理
        mRealm = Realm.getDefaultInstance()



        boardActivity.setOnClickListener {
            // 画面作成
            val intent = Intent(this@MainActivity, BoardActivity::class.java)
            // 画面遷移
            startActivity(intent)
        }

        createButton.setOnClickListener {
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            mRealm.executeTransaction {

                // Auto Increment機能がないのでIDの最大値を取得してくる
                val max = mRealm.where<Board>().max("id")
                var newId: Long = 0
                if (max != null) {//nullチェック
                    newId = max.toLong() + 1
                }

                //新規Board作成　IDを入れる。
                val board : Board = mRealm.createObject<Board>(primaryKeyValue = newId)

                // データ挿入
                board.name = "テストモデル"
                board.date = Date()

                textView.setText("登録しました\n" + board.toString());


            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

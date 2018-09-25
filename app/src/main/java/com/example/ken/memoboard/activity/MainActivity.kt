package com.example.ken.memoboard.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.example.ken.memoboard.BoardAdapter
import com.example.ken.memoboard.R
import com.example.ken.memoboard.model.Board
import io.realm.Realm
import io.realm.Sort
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
//
//        val realmConfig = RealmConfiguration.Builder().build()
//        Realm.deleteRealm(realmConfig)

        //データベースのオープン処理
        mRealm = Realm.getDefaultInstance()

        //adapter とListViewを連携させる。
        val boards = mRealm.where(Board::class.java).findAll().sort("id", Sort.ASCENDING)
        listView.adapter = BoardAdapter(boards)

        //リストのタップで画面遷移
        listView.setOnItemClickListener { parent, view, position, id ->
            val adapter = listView.getAdapter() as BoardAdapter
            val board = adapter.getItem(position)
            val intent = Intent(this@MainActivity, BoardActivity::class.java)
            intent.putExtra("ID", board?.id)
            startActivity(intent)
        }

        //リストの長押しで削除
        listView.setOnItemLongClickListener { parent, view, position, id ->
            val adapter = listView.getAdapter() as BoardAdapter
            val board = adapter.getItem(position)
            Snackbar.make(view, "削除しますか？", Snackbar.LENGTH_LONG)
                    .setAction("OK") { view ->
                        mRealm.executeTransaction { board?.deleteFromRealm() }
                        Snackbar.make(view, "削除されました", Snackbar.LENGTH_SHORT)
                                .show()
                    }
                    .show()
            true
        }

        //ボード作成
        createButton.setOnClickListener {
            //テキスト入力を受け付けるビューを作成。
            val editView = EditText(this@MainActivity)
            AlertDialog.Builder(this@MainActivity)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("タイトルを入力")
                    //setViewにてビューを設定します。
                    .setView(editView)
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, whichButton ->
                        // ボードデータ新規作成
                        createBoard(editView)
                    })
                    .setNegativeButton("キャンセル", DialogInterface.OnClickListener { dialog, whichButton -> })
                    .show()
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

    override fun onDestroy() {
        super.onDestroy()
        this.mRealm.close()
    }

    fun createBoard(editView: EditText) {
        mRealm.executeTransaction {
            // Auto Increment機能がないのでIDの最大値を取得してくる
            val max = mRealm.where<Board>().max("id")
            var newId: Long = 0
            if (max != null) {//nullチェック
                newId = max.toLong() + 1
            }
            //新規Board作成　IDを入れる。
            val board: Board = mRealm.createObject<Board>(primaryKeyValue = newId)
            // データ挿入
            board.name = editView.text.toString()
            board.date = Date()
            //入力した文字をトースト出力する
            Toast.makeText(this@MainActivity,
                    board.toString(),
                    Toast.LENGTH_LONG).show()
        }
    }
}
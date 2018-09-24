package com.example.ken.memoboard

import android.graphics.Color
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class Memo : RealmObject(){
    @PrimaryKey
    var id: Long = 0 // id
    @Required
    var name: String = ""  // メモの名前
    var text: String = ""  // メモの内容
    var color: Int = Color.LTGRAY
    var date: Date = Date() // 日付
    var left: Int = 0 // メモのleftマージン
    var top: Int = 0 // メモのtopマージン
}

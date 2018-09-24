package com.example.ken.memoboard

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
    var date: Date = Date() // 日付
    var x: Int = 0 // メモのx座標
    var y: Int = 0 // メモのy座標
}

package com.example.ken.memoboard.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.util.*

open class Board : RealmObject(){
    @PrimaryKey
    var id: Long = 0 // id
    @Required
    var name: String = ""  //　ボードの名前
    var date: Date = Date() // 日付
    var memos: RealmList<Memo> = RealmList() // メモのリレーション
}

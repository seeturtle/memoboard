package com.example.ken.memoboard

import android.app.Application
import io.realm.Realm

class BoardContentApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

    }
}
/*
 * Copyright 2017 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ken.memoboard


import com.example.ken.memoboard.model.Board
import io.realm.Realm

object DataHelper {
    val FIELD_ID = "id"

    // Create 3 counters and insert them into random place of the list.
    fun randomAddItemAsync(realm: Realm) {
        realm.executeTransactionAsync { realm ->
//            for (i in 0..2) Board.create(realm, true)
        }
    }

    fun addItemAsync(realm: Realm) {
//        realm.executeTransactionAsync { realm -> Board.create(realm) }
    }

    fun deleteItemAsync(realm: Realm, id: Long) {
        realm.executeTransaction {

            val item = realm.where<Board>(Board::class.java!!).equalTo(FIELD_ID, id).findFirst()
            // Otherwise it has been deleted already.
            if (item != null) {
                item!!.deleteFromRealm()
            }
        }
    }

    fun deleteItemsAsync(realm: Realm, ids: Collection<Int>) {
        // Create an new array to avoid concurrency problem.
        val idsToDelete = arrayOfNulls<Int>(ids.size)
        ids.toTypedArray()
        realm.executeTransactionAsync { realm ->
            for (id in idsToDelete) {
//                Board.delete(realm, id)
            }
        }
    }
}

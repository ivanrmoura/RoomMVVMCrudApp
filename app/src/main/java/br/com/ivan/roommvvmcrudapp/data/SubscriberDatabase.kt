package br.com.ivan.roommvvmcrudapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.ivan.roommvvmcrudapp.data.dao.SubscriberDAO
import br.com.ivan.roommvvmcrudapp.data.model.Subscriber

@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase: RoomDatabase() {

    abstract val subscriberDAO: SubscriberDAO

    companion object{
        @Volatile
        private var INSTANCE: SubscriberDatabase? = null

        fun getDatabase(context: Context): SubscriberDatabase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubscriberDatabase::class.java,
                    "subscriber_data_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
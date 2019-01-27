package com.example.h260210.room_viewmodel_rx_main.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract IUserDao userDao();

    private static volatile UserDatabase INSTANCE; //volatile: to ensure atomic access to the variable

    public static synchronized UserDatabase getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    UserDatabase.class,
                    "user_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public void destroyDatabaseInstance() {
        INSTANCE = null;
    }
}

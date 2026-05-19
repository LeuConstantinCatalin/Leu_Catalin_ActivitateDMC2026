package leu.laboratoare.lab8;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Motor.class}, version = 1)
public abstract class MotorDatabase extends RoomDatabase {

    public abstract MotorDao motorDao();

    private static volatile MotorDatabase INSTANCE;

    public static MotorDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MotorDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    MotorDatabase.class,
                                    "motor_database"
                            )
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

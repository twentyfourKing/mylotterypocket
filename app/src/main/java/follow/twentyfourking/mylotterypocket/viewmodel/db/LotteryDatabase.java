package follow.twentyfourking.mylotterypocket.viewmodel.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LotteryEntity.class}, version = 1)
public abstract class LotteryDatabase extends RoomDatabase {
    public abstract LotteryDao getDao();

    private static LotteryDatabase INSTANCE;

    private static Object lock = new Object();

    public static LotteryDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (lock) {
                INSTANCE = Room.databaseBuilder(context, LotteryDatabase.class,
                        "lootery,db").build();
            }
        }
        return INSTANCE;
    }
}

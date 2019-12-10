package follow.twentyfourking.mylotterypocket.viewmodel.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lottery_choose")
public class LotteryEntity {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "choose_time")
    private long time;
    @ColumnInfo(name = "lottery_number")
    private String number;
    @ColumnInfo(name = "lottery_type")
    private String type;

    public LotteryEntity(int id, long time, String number, String type) {
        this.id = id;
        this.time = time;
        this.number = number;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

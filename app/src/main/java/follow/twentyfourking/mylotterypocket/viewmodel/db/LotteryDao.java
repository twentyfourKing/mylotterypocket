package follow.twentyfourking.mylotterypocket.viewmodel.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LotteryDao {

    //插入号码数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLotteryNumber(LotteryEntity entity);

    //根据彩票类型拉取彩票数据
    @Query("select * from lottery_choose order by choose_time asc")
    List<LotteryEntity> queryAll();

    //根据时间和类型查询彩票数据 asc 升序 desc 倒叙
    @Query("select * from lottery_choose where choose_time >= :startTime " +
            "and choose_time <= :endTime and lottery_type like :type  order by choose_time asc")
    List<LotteryEntity> queryByTime(long startTime, long endTime, String type);
}

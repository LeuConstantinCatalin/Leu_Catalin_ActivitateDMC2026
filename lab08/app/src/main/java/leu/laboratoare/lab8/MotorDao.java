package leu.laboratoare.lab8;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MotorDao {

    @Insert
    void insertMotor(Motor motor);

    @Query("SELECT * FROM motors")
    List<Motor> getAllMotors();

    @Query("SELECT * FROM motors WHERE tip = :tipCautat")
    List<Motor> getMotorsByTip(String tipCautat);

    @Query("SELECT * FROM motors WHERE putere BETWEEN :minPutere AND :maxPutere")
    List<Motor> getMotorsByPutereRange(int minPutere, int maxPutere);

    @Query("DELETE FROM motors WHERE tensiune > :valoare")
    void deleteMotorsWithVoltageGreaterThan(int valoare);

    @Query("DELETE FROM motors WHERE tensiune < :valoare")
    void deleteMotorsWithVoltageLessThan(int valoare);

    @Query("UPDATE motors SET putere = putere + 1 WHERE tip LIKE :prefix || '%'")
    void incrementPutereForTipStartingWith(String prefix);
}
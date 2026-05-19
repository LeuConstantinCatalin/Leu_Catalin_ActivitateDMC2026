package leu.laboratoare.lab8;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "motors")
public class Motor {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String tip;
    private int putere;
    private int tensiune;

    public Motor(String tip, int putere, int tensiune) {
        this.tip = tip;
        this.putere = putere;
        this.tensiune = tensiune;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getPutere() {
        return putere;
    }

    public void setPutere(int putere) {
        this.putere = putere;
    }

    public int getTensiune() {
        return tensiune;
    }

    public void setTensiune(int tensiune) {
        this.tensiune = tensiune;
    }

    @Override
    public String toString() {
        return "ID=" + id +
                " | tip=" + tip +
                " | putere=" + putere +
                " | tensiune=" + tensiune;
    }
}

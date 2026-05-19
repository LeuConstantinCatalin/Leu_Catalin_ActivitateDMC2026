package leu.laboratoare.leucatalinpartial;

import androidx.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;

public class HusaTelefon implements Parcelable{
    public String material;
    public float lungime;
    public boolean are_clapa;
    public float greutate;

    @Override
    public String toString() {
        return "HusaTelefon{" +
                "material='" + material + '\'' +
                ", lungime=" + lungime +
                ", are_clapa=" + are_clapa +
                ", greutate=" + greutate +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }

    protected HusaTelefon(Parcel in) {
        material = in.readString();
        lungime = in.readFloat();
        greutate = in.readFloat();
        are_clapa = in.readByte() != 0;
    }

    public HusaTelefon(){}

    public static final Creator<HusaTelefon> CREATOR = new Creator<HusaTelefon>() {
        @Override
        public HusaTelefon createFromParcel(Parcel in) {
            return new HusaTelefon(in);
        }

        @Override
        public HusaTelefon[] newArray(int size) {
            return new HusaTelefon[size];
        }
    };
}

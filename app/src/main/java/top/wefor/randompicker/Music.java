package top.wefor.randompicker;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 16/8/26.
 *
 * @author ice
 */
public class Music implements Parcelable {

    public String name = "";
    public int weight = 1;
    public int pickedTime = 0;

    public Music(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.weight);
        dest.writeInt(this.pickedTime);
    }

    protected Music(Parcel in) {
        this.name = in.readString();
        this.weight = in.readInt();
        this.pickedTime = in.readInt();
    }

    public static final Parcelable.Creator<Music> CREATOR = new Parcelable.Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel source) {
            return new Music(source);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
}

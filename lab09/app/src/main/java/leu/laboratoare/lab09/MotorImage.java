package leu.laboratoare.lab09;

import android.graphics.Bitmap;

public class MotorImage {
    private String imageUrl;
    private String title;
    private String webUrl;
    private Bitmap bitmap;

    public MotorImage(String imageUrl, String title, String webUrl) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.webUrl = webUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
package leu.laboratoare.lab09;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MotorImageAdapter extends ArrayAdapter<MotorImage> {

    private Context context;
    private List<MotorImage> motorImages;

    public MotorImageAdapter(@NonNull Context context, @NonNull List<MotorImage> motorImages) {
        super(context, 0, motorImages);
        this.context = context;
        this.motorImages = motorImages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_motor_image, parent, false);
        }

        MotorImage currentItem = motorImages.get(position);

        ImageView imageView = listItem.findViewById(R.id.imageViewMotor);
        TextView textViewTitle = listItem.findViewById(R.id.textViewTitle);

        textViewTitle.setText(currentItem.getTitle());

        if (currentItem.getBitmap() != null) {
            imageView.setImageBitmap(currentItem.getBitmap());
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_report_image);
        }

        return listItem;
    }
}
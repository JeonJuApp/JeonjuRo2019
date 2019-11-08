package org.androidtown.jeonjuro2019_v2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class GridItem extends LinearLayout {
    TextView tv1;
    ImageView img;

    public GridItem(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.grid_view_item,this);

        tv1 = (TextView)findViewById(R.id.placename);
        img = (ImageView)findViewById(R.id.img2);
    }

    public void setData(Place one, Context context) {
        tv1.setText(one.getName());
        Glide.with(context).load(one.getImagno()).asBitmap().fitCenter().into((img));
    }
}

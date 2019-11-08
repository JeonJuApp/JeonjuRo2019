package org.androidtown.jeonjuro2019;

import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class CustomAdapter extends PagerAdapter {
    LayoutInflater inflater;

    public CustomAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return 4; //이미지 개수 리턴
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = null;
        view = inflater.inflate(R.layout.viewpager_childview, null);

        ImageView img = (ImageView) view.findViewById(R.id.img_viewpager_childimage);
        img.setImageResource(R.drawable.zintro1 + position);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v==obj;
    }
}
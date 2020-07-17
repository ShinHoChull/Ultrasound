package com.m2comm.ultrasound.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.m2comm.ultrasound.DTO.MainBannerDTO;
import com.m2comm.ultrasound.R;
import com.m2comm.ultrasound.module.RoundImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MainPagerAdapter extends PagerAdapter {

    private Context context;
    ArrayList<MainBannerDTO> realBannerArray;
    private Activity activity;
    private LayoutInflater inflater;

    public MainPagerAdapter(Context context, ArrayList<MainBannerDTO> realBannerArray, Activity activity, LayoutInflater inflater) {
        this.context = context;
        this.realBannerArray = realBannerArray;
        this.activity = activity;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = inflater.inflate(R.layout.pager_test , container , false);
        final RoundImageView img = view.findViewById(R.id.img);
        final MainBannerDTO mainBannerDTO = this.realBannerArray.get(position);

        img.post(new Runnable() {
            @Override
            public void run() {
                Picasso.get().load(mainBannerDTO.getImg()).error(R.mipmap.ic_launcher).fit().into(img);
                img.setRectRadius(30f);
            }
        });

        container.addView(view);
        return view;





    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return this.realBannerArray.size();
    }
}

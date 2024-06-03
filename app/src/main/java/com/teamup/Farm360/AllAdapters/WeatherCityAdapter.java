package com.teamup.Farm360.AllAdapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.AllReqs.WeatherCityReq;
import com.teamup.Farm360.R;

import java.util.List;


public class WeatherCityAdapter extends RecyclerView.Adapter<WeatherCityAdapter.ViewHolder> {

    public List<WeatherCityReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    int cur;
    public Context context;

    TinyDB tinyDB;

    public WeatherCityAdapter(List<WeatherCityReq> blog_list) {
        this.blog_list = blog_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);

        context = parent.getContext();

        tinyDB = new TinyDB(context);

        return new ViewHolder(view);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


//        Flubber.with()
//                .animation(Flubber.AnimationPreset.ALPHA) // Slide up animation
//                .repeatCount(0)                              // Repeat once
//                .duration(500)                              // Last for 1000 milliseconds(1 second)
//                .createFor(holder.reler)                             // Apply it to the view
//                .start();


//        final String PostId = blog_list.get(position).FacebookPostId;
        holder.setIsRecyclable(false);

        holder.cityNameTxt.setText(blog_list.get(position).getName());

        holder.reler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tinyDB.putString("weatherCity", blog_list.get(position).getName());
                ((Activity)context).finish();

            }
        });

    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView1, imageView2, imageView3;
        TextView cityNameTxt, Txt2, Txt3, Txt4, Txt5;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        RelativeLayout reler;
        CardView cardView;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            reler = itemView.findViewById(R.id.reler);
            cityNameTxt = itemView.findViewById(R.id.cityNameTxt);

        }


    }


}

package com.teamup.Farm360.AllAdapters;

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

import com.bumptech.glide.Glide;
import com.teamup.app_sync.AppSyncOpenUrl;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.AllReqs.HarvestingReq;
import com.teamup.Farm360.R;

import java.util.List;


public class HarvestingAdapter extends RecyclerView.Adapter<HarvestingAdapter.ViewHolder> {

    public List<HarvestingReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    int cur;
    public Context context;

    TinyDB tinyDB;

    public HarvestingAdapter(List<HarvestingReq> blog_list) {
        this.blog_list = blog_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_harvesting_techniques, parent, false);

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
//                .createFor(holder.reler)                                // Apply it to the view
//                .start();


//        final String PostId = blog_list.get(position).FacebookPostId;
        holder.setIsRecyclable(false);
        Glide.with(context).load(blog_list.get(position).getImgUrl()).into(holder.singImg);

        holder.titleTxt.setText("" + blog_list.get(position).getTitle());
        holder.descTxt.setText("" + blog_list.get(position).getDescription());

        if (blog_list.get(position).getLink().equalsIgnoreCase("null")) {
            holder.viewTxt.setVisibility(View.GONE);
        } else {
            holder.viewTxt.setVisibility(View.VISIBLE);
        }

        holder.viewTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncOpenUrl.openUrl(context, "" + blog_list.get(position).getLink());
            }
        });

    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView singImg, imageView2, imageView3;
        TextView titleTxt, descTxt, viewTxt, Txt4, Txt5;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        RelativeLayout reler;
        CardView cardView;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            descTxt = itemView.findViewById(R.id.descTxt);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            singImg = itemView.findViewById(R.id.singImg);
            viewTxt = itemView.findViewById(R.id.viewTxt);

        }


    }


}

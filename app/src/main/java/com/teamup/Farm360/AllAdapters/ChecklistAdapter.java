package com.teamup.Farm360.AllAdapters;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.teamup.Farm360.AllActivities.MainActivity;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.AllReqs.ChecklistReq;
import com.teamup.Farm360.R;

import java.util.List;


public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.ViewHolder> {

    public List<ChecklistReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    int cur;
    public Context context;

    TinyDB tinyDB;

    public ChecklistAdapter(List<ChecklistReq> blog_list) {
        this.blog_list = blog_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_checklist, parent, false);

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

        holder.checbox.setText("" + blog_list.get(position).getTitle());
        holder.dateTxt.setText("" + blog_list.get(position).getDate());

        holder.checbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HandleChecking(isChecked, holder, position);
            }
        });

        holder.checkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checbox.isChecked()) {
                    holder.checbox.setChecked(false);
                    HandleChecking(false, holder, position);
                } else {
                    holder.checbox.setChecked(true);
                    HandleChecking(true, holder, position);
                }
            }
        });

        if (blog_list.get(position).getChecked() == 0) {
            holder.checbox.setChecked(false);
            holder.checkImg.setVisibility(View.GONE);
        } else {
            holder.checkImg.setVisibility(View.VISIBLE);
            holder.checbox.setChecked(true);
            holder.checbox.setPaintFlags(holder.checbox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }

    private void HandleChecking(boolean isChecked, ViewHolder holder, int position) {
        if (!isChecked) {
            holder.checkImg.setVisibility(View.GONE);
            MainActivity.makeItQuery("UPDATE `Checklist` SET `checked` = '0' WHERE `Checklist`.`id` = " + blog_list.get(position).getId(), context);
            blog_list.get(position).setChecked(0);
            holder.checbox.setPaintFlags(0);
        } else {
            holder.checkImg.setVisibility(View.VISIBLE);
            MainActivity.makeItQuery("UPDATE `Checklist` SET `checked` = '1' WHERE `Checklist`.`id` = " + blog_list.get(position).getId(), context);
            holder.checbox.setPaintFlags(holder.checbox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            blog_list.get(position).setChecked(1);
        }
    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView checkImg, imageView2, imageView3;
        TextView dateTxt, Txt2, Txt3, Txt4, Txt5;
        CheckBox checbox;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        RelativeLayout checkCard;
        RelativeLayout reler;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            checkCard = itemView.findViewById(R.id.checkCard);
            checkImg = itemView.findViewById(R.id.checkImg);
            reler = itemView.findViewById(R.id.reler);
            dateTxt = itemView.findViewById(R.id.dateTxt);
            checbox = itemView.findViewById(R.id.checbox);

        }


    }


}

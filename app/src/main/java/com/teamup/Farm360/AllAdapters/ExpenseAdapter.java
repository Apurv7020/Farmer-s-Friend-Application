package com.teamup.Farm360.AllAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.appolica.flubber.Flubber;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teamup.Farm360.AllActivities.Add_Expense;
import com.teamup.Farm360.AllActivities.MainActivity;
import com.teamup.Farm360.AllModules.Admin;
import com.teamup.Farm360.AllModules.TinyDB;
import com.teamup.Farm360.AllReqs.ExpenseReq;
import com.teamup.Farm360.R;
import com.teamup.app_sync.AppSyncCustomDialog;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.teamup.app_sync.AppSyncCustomDialog.view2;


public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    public List<ExpenseReq> blog_list;
    private static final int CAMERA_CODE2 = 22;
    int cur;
    public Context context;

    TinyDB tinyDB;

    public ExpenseAdapter(List<ExpenseReq> blog_list) {
        this.blog_list = blog_list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_expense, parent, false);

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

        try {
            for (int i = 0; i < Admin.cropsList.size(); i++) {
                if (blog_list.get(position).getCat().contains(Admin.cropsList.get(i).getName())) {

                    Glide.with(context).load("" + Admin.cropsList.get(i).getImgUrl()).into(holder.image);

                }
            }
        } catch (Exception v) {

        }


        holder.titleTxt.setText("" + blog_list.get(position).getTitle());
        holder.priceTxt.setText("₹" + blog_list.get(position).getExpense() + "/-");
        holder.descTxt.setText("" + blog_list.get(position).getCat());

        holder.liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppSyncCustomDialog.showDialog((Activity) context, R.layout.dialog_expense_info, R.color.TransparentPrimary, true);
                TextView catTxt, titleTxt, descTxt, amountTxt, datetTxt;
                CircleImageView imgCircle = view2.findViewById(R.id.imgCircle);
                ImageView deleteImg, closeImg;
                FloatingActionButton editBtn;
                CardView cardView = view2.findViewById(R.id.cardView);

                deleteImg = view2.findViewById(R.id.deleteImg);
                titleTxt = view2.findViewById(R.id.titleTxt);
                catTxt = view2.findViewById(R.id.catTxt);
                descTxt = view2.findViewById(R.id.descTxt);
                amountTxt = view2.findViewById(R.id.amountTxt);
                closeImg = view2.findViewById(R.id.closeImg);
                editBtn = view2.findViewById(R.id.editBtn);
                datetTxt = view2.findViewById(R.id.datetTxt);

                catTxt.setText(blog_list.get(position).getCat());
                titleTxt.setText(blog_list.get(position).getTitle());
                descTxt.setText(blog_list.get(position).getDescription());
                datetTxt.setText(blog_list.get(position).getTimer());
                amountTxt.setText("₹" + blog_list.get(position).getExpense() + "/-");

                if (TextUtils.isEmpty(blog_list.get(position).getDescription())) {
                    descTxt.setText("No Description");
                }


                Flubber.with()
                        .animation(Flubber.AnimationPreset.ALPHA) // Slide up animation
                        .repeatCount(0)                              // Repeat once
                        .duration(1000)                              // Last for 1000 milliseconds(1 second)
                        .createFor(cardView)                             // Apply it to the view
                        .start();

                try {
                    for (int i = 0; i < Admin.cropsList.size(); i++) {
                        if (blog_list.get(position).getCat().contains(Admin.cropsList.get(i).getName())) {

                            Glide.with(context).load("" + Admin.cropsList.get(i).getImgUrl()).into(imgCircle);

                        }
                    }
                } catch (Exception pv) {

                }

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Admin.updation = true;
                        Admin.khataId = blog_list.get(position).getId();
                        Admin.KhataAmount = blog_list.get(position).getExpense();
                        Admin.KhataCat = blog_list.get(position).getCat();
                        Admin.KhataDesc = blog_list.get(position).getDescription();
                        Admin.KhataTimer = blog_list.get(position).getTimer();
                        Admin.KhataTitle = blog_list.get(position).getTitle();
                        AppSyncCustomDialog.stopPleaseWaitDialog(((Activity) context));
                        context.startActivity(new Intent(context, Add_Expense.class));
                    }
                });

                closeImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppSyncCustomDialog.stopPleaseWaitDialog(((Activity) context));
                    }
                });

                deleteImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            MainActivity.makeItQuery("DELETE FROM `Expense` WHERE `Expense`.`id` = " + blog_list.get(position).getId(), context);
                            blog_list.remove(position);
                            notifyDataSetChanged();
                            AppSyncCustomDialog.stopPleaseWaitDialog(((Activity) context));
                            Admin.removed = true;


                        } catch (Exception vd) {

                        }
                    }
                });

            }
        });


    }


    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView image, imageView2, imageView3;
        TextView titleTxt, priceTxt, descTxt, Txt4, Txt5;
        private View mView;
        Button Btn1, Btn2, Btn3, Btn4;
        ProgressBar progressBar;
        LinearLayout liner;
        CardView cardView;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            liner = itemView.findViewById(R.id.liner);
            image = itemView.findViewById(R.id.imgCircle);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            descTxt = itemView.findViewById(R.id.descTxt);
        }


    }


}

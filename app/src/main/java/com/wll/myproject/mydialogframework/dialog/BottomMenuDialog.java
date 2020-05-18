package com.wll.myproject.mydialogframework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wll.myproject.mydialogframework.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * You can do well if you want to
 * Create by WLL on 2020/5/15 DATA: 11:11
 */

public class BottomMenuDialog {

    public static class  Builder{
        Context context;
        Dialog mDialog;
        View view;
        DisplayMetrics dm;
        private WindowManager windowManager;
        RecyclerView mRecycler;
        MyRecyclerBottomAdapter myRecyclerBottomAdapter;
        List<String> mList;

        public Builder(Context context,List<String> mList) {
            this.context = context;
            this.mList = mList;
            initDialog();
        }


        private void initDialog() {
            mDialog = new Dialog( context ,DialogStyleUtil.getListStyle());
            view = LayoutInflater.from( context ).inflate( R.layout.layout_bottom_menu_dialog,null);
            mDialog.setContentView( view );
            dm = new DisplayMetrics();
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
            lp.width = dm.widthPixels;
//          lp.height = (int) (dm.heightPixels * 0.65);
            lp.gravity = Gravity.BOTTOM;
//          lp.y = (int) (dm.widthPixels * 0.025);
            mDialog.getWindow().setAttributes(lp);
            initRecycler();
        }

        private void initRecycler() {
            myRecyclerBottomAdapter = new MyRecyclerBottomAdapter(context,mList);
            mRecycler = view.findViewById( R.id.m_recycler_bottom_menu );
            mRecycler.setAdapter( myRecyclerBottomAdapter );
            mRecycler.setLayoutManager( new GridLayoutManager( context, 4, GridLayoutManager.VERTICAL,false ) );
            // 卧槽，在自定义的recycler子布局中加要给view就能让子布局居中对齐。。。
//            mRecycler.addItemDecoration( new GridSpaceItemDecoration( 4, 30, 10 ) );

        }

        public  Builder show() {
            if (mDialog != null) {
                mDialog.show();
            }
            return this;
        }

        public Builder dismiss() {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            return this;
        }
        //添加监听。
        public Builder setDialogClick(MyRecyclerBottomAdapter.BottomMenuDialogClick bottomMenuDialogClick) {
            myRecyclerBottomAdapter.setClick( bottomMenuDialogClick );
            return this;
        }

    }

    public static class MyRecyclerBottomAdapter extends RecyclerView.Adapter<MyRecyclerBottomAdapter.MyViewHolder> {
        Context context;
        List<String> mList;
        View view;
        MyViewHolder myViewHolder;
        BottomMenuDialogClick bottomMenuDialogClick;
        public MyRecyclerBottomAdapter(Context context, List<String> mList) {
            this.context = context;
            this.mList = mList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = LayoutInflater.from( context ).inflate(R.layout.item_recycler_bottom_dialog,parent,false);
            myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            //给分享加标题。
            if (mList != null) {
                holder.mTitle.setText( mList.get( position ) );
                holder.mShare.setImageResource( R.drawable.ic_qq );
                holder.mLL.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bottomMenuDialogClick != null) {
                            bottomMenuDialogClick.onItemClick(v,position);
                        }
                    }
                } );

            }

        }

        public void setClick(BottomMenuDialogClick bottomMenuDialogClick) {
            this.bottomMenuDialogClick = bottomMenuDialogClick;
        }

        //监听接口
        public interface BottomMenuDialogClick{
            void onItemClick(View view,int position);
        }

        @Override
        public int getItemCount() {
            return mList==null?0:mList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView mTitle;
            LinearLayout mLL;
            ImageView mShare;

            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
                mTitle = itemView.findViewById( R.id.item_title);
                mShare = itemView.findViewById( R.id.item_icon );
                mLL = itemView.findViewById( R.id.m_ll );
            }
        }
    }
}

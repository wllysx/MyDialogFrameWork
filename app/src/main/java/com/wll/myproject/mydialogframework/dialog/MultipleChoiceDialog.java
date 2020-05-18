package com.wll.myproject.mydialogframework.dialog;
/*
    Create by WLL on 2020/5/11 DATA: 15:44
*/

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wll.myproject.mydialogframework.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部弹出多选dialog。背景模糊度，左边有对号。
 */

public class MultipleChoiceDialog {

    public static class Builder {
        Dialog mDialog;
        Context context;
        //这个是需要显示在dialog每行的文本。
        List<String> mList;
        public Builder(Context context,List<String> mList) {
            this.context = context;
            this.mList = mList;
            initDialog();
        }

        View view;
        ViewHolder viewHolder;
        private DisplayMetrics mDisplayMetrics;
        private WindowManager.LayoutParams mParams;
        private WindowManager windowManager;
        private MyRecyclerAdapter myRecyclerAdapter;

        private void initDialog() {
            mDialog = new Dialog(context,DialogStyleUtil.getListStyle());//这里定义布局的格式，动画的效果。
            view = LayoutInflater.from( context ).inflate( R.layout.item_multiple_choice_dialog, null );
            //很重要。将视图和弹窗绑定起来。
            mDialog.setContentView( view );
            viewHolder = new ViewHolder( view );
            //初始化viewHolder后就实例化recyclerView
            initRecycler();

            mDisplayMetrics = new DisplayMetrics();
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
            mParams=mDialog.getWindow().getAttributes();//mDialog的属性对象。
            mParams.width = (int) (mDisplayMetrics.widthPixels * 0.95);
            mParams.gravity = Gravity.BOTTOM;
            mParams.y = (int) (mDisplayMetrics.widthPixels * 0.025);//宽度方向的像素点。
            mDialog.getWindow().setAttributes(mParams);

        }

        private void initRecycler() {
            myRecyclerAdapter = new MyRecyclerAdapter(context,mList);
            viewHolder.mRecyclerView.setAdapter( myRecyclerAdapter );
            viewHolder.mRecyclerView.setLayoutManager( new LinearLayoutManager( context, LinearLayoutManager.VERTICAL, false ) );

        }


        class ViewHolder {
            TextView mTitle;
            RecyclerView mRecyclerView;
            TextView mCancel;
            public ViewHolder(View view) {
                mTitle = view.findViewById( R.id.m_mulltiple_title );
                mCancel = view.findViewById( R.id.dialog_cancel );
                mRecyclerView = view.findViewById( R.id.m_recyclerview );

            }
        }

        //设置对话框与屏幕的占比,最大是1。
        public Builder setWidth(float width) {
            mParams.width = (int) (mDisplayMetrics.widthPixels * width);
            mDialog.getWindow().setAttributes( mParams );
            return this;
        }

        //设置从哪个地方出来。
        public Builder setGravity(int gravity) {
            //Gravity.LEFT, Gravity.RIGHT, Gravity.BOTTOM, Gravity.TOP的等等
            mDialog.getWindow().setGravity( gravity );
            return this;
        }




        public Builder show() {
            if (mDialog != null) {
                mDialog.show();
            }
            return this;
        }

        public void dimiss() {
            if (mDialog != null) {
                mDialog.dismiss();
            }

        }

        public Builder addItemListener(MyRecyclerAdapter.DialogClick dialogClick) {
            //将接口对象传入recycler的适配器中，进行监听
            myRecyclerAdapter.setmDialogClick( dialogClick );
            //实现对确定按钮的监听。。
            viewHolder.mCancel.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.dismiss();
                }
            } );

            return this;
        }
    }


    public static class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
        Context context;
        List<String> mList;
        View view;
        MyViewHolder myViewHolder;
        DialogClick mDialogClick;
        List<Integer> mList2;

        public MyRecyclerAdapter(Context context, List<String> mList) {
            this.context = context;
            this.mList = mList;
            mList2 = new ArrayList<>();

        }

        public void setmDialogClick(DialogClick dialogClick) {
            mDialogClick = dialogClick;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = LayoutInflater.from( context ).inflate( R.layout.item_recycler_multiple_dialog, parent, false );
            myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
            holder.mTv02.setText( mList.get( position ) );
            if (mList.size() == (position + 1)) {
                holder.mLl.setVisibility( View.GONE );
            }
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.mTv01.getVisibility() != View.VISIBLE) {
                        holder.mTv01.setVisibility( View.VISIBLE );
                        mList2.add(position);

                    }else{
                        holder.mTv01.setVisibility( View.GONE );
                        //删除position这个元素，如果是数字则是删除这个位置的元素。
                        mList2.remove( new Integer( position ) );
                    }
                    mDialogClick.onItemClick(v,position,mList2);


                }
            } );
        }

        public interface DialogClick{
            //mListCount 存放的是所选中的行对应的数据集合列表中所处的位置。
            void onItemClick(View view,int position,List<Integer> mListCount);
        }

        @Override
        public int getItemCount() {
            return mList==null?0:mList.size();
        }

        class  MyViewHolder extends RecyclerView.ViewHolder{
            TextView mTv01;
            TextView mTv02;
            LinearLayout mLl;

            public MyViewHolder(@NonNull View itemView) {
                super( itemView );
                mTv01 = itemView.findViewById( R.id.m_recycler_tv01 );
                mTv02 = itemView.findViewById( R.id.m_recycler_tv02 );
                mLl = itemView.findViewById( R.id.m_ll );
            }
        }
    }


}

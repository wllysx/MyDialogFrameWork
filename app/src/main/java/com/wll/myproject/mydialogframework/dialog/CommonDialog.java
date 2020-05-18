package com.wll.myproject.mydialogframework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.wll.myproject.mydialogframework.R;

import org.w3c.dom.Text;

/**
 * Create by WLL on 2020/5/11 DATA: 11:34
 *一般对话框创建，标题,副标题，下面两个按钮。模仿系统自带dialog
 * 从中间弹出,
 */

public class CommonDialog {
    //创建一个静态内部帮助类。
    public static class Builder {
        Context context;
        Dialog mDialog;
        View view;
        ViewHolder myViewHolder;

        //直接用CommonDIalog.就能调用这个内部类的构造方法。
        public Builder(Context context) {
            this.context = context;
            //构造方法中直接实例化dialog
            initDialog();

        }

        private WindowManager.LayoutParams mParams;
        private DisplayMetrics mDisplayMetrics;
        private WindowManager windowManager;
        private void initDialog() {
            mDialog = new Dialog(context);//,DialogStyleUtil.getStyle()
            view = LayoutInflater.from( context ).inflate( R.layout.item_common_dialog, null );
            myViewHolder = new ViewHolder( view );
            //最重要的一步！！将mDialog与自定义视图view绑定在一起。。
            mDialog.setContentView( view );

            mDisplayMetrics = new DisplayMetrics();
            mParams = mDialog.getWindow().getAttributes();
            windowManager = (WindowManager) context.getSystemService( Context.WINDOW_SERVICE );
            windowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
            mParams.width = (int) (mDisplayMetrics.widthPixels * 0.8f);
            mDialog.getWindow().setAttributes( mParams );
            //点击对话框以外的部分不做处理。
            mDialog.setCanceledOnTouchOutside( false );
        }

        public Builder setTitle(CharSequence title,int color) {
            myViewHolder.mTitle.setVisibility( View.VISIBLE );
            myViewHolder.mTitle.setText(  title );
            if (color != 0) {
                myViewHolder.mTitle.setTextColor( ContextCompat.getColor( context,color ) );
            }
            //这里返回此对象是为了还能接着"."
            return this;
        }

        //是0则不用设置颜色。
        public Builder setMessage(CharSequence message,int color) {
            myViewHolder.mMessage.setVisibility( View.VISIBLE );
            if (color != 0) {
                myViewHolder.mMessage.setTextColor( ContextCompat.getColor( context,color ) );
            }
            myViewHolder.mMessage.setText( message );
            return this;
        }


        public Builder setPositiveLeft(CharSequence leftText,int color,final View.OnClickListener listener) {
            myViewHolder.mLeft.setVisibility( View.VISIBLE );
            myViewHolder.mLeft.setText( leftText );
            myViewHolder.mLeft.setTextColor( ContextCompat.getColor( context,color ) );
//            myViewHolder.mLeft.setOnClickListener( listener );//这样做需要在引用的地方进行做dialog的dimiss
            myViewHolder.mLeft.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        //直接调用传过来的listener的
                        listener.onClick( view );
                        mDialog.dismiss();
                    }
                }
            } );

            return this;
        }

        public Builder setPositiveRight(CharSequence rightText,int color,final View.OnClickListener listener) {
            myViewHolder.mRight.setVisibility( View.VISIBLE );
            myViewHolder.mRight.setText( rightText );
            myViewHolder.mRight.setTextColor( ContextCompat.getColor( context,color ) );
//            myViewHolder.mLeft.setOnClickListener( listener );//这样做需要在引用的地方进行做dialog的dimiss
            myViewHolder.mRight.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        //直接调用传过来的listener的
                        listener.onClick( view );
                        mDialog.dismiss();
                    }
                }
            } );

            return this;
        }

        //设置对话框在屏幕的占比,最大是1f，一般设置为0.8f
        public Builder setWidth(float width) {
            mParams.width = (int) (mDisplayMetrics.widthPixels * width);
            mDialog.getWindow().setAttributes(mParams);
            return this;
        }

        //设置高度。感觉没什么用。
        public Builder setHeight(float height) {
            mParams.height = (int) (mDisplayMetrics.heightPixels * height);
            mDialog.getWindow().setAttributes(mParams);
            return this;
        }

        public Builder setGravity(int gravity) {
            //Gravity.LEFT, Gravity.RIGHT, Gravity.BOTTOM, Gravity.TOP
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







        class ViewHolder {
            TextView mTitle;
            TextView mMessage;
            TextView mLeft;
            TextView mRight;
            public ViewHolder(View view) {
                mTitle = view.findViewById( R.id.m_title );
                mMessage = view.findViewById( R.id.m_message );
                mLeft = view.findViewById( R.id.m_left );
                mRight = view.findViewById( R.id.m_right );
            }
        }
    }



}

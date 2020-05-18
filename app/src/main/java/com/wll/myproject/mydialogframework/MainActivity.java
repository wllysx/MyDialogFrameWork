package com.wll.myproject.mydialogframework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wll.myproject.mydialogframework.dialog.BottomMenuDialog;
import com.wll.myproject.mydialogframework.dialog.CommonDialog;
import com.wll.myproject.mydialogframework.dialog.MultipleChoiceDialog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    int message=0;
    List<String> mlist=new ArrayList<>(  );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        editText = findViewById( R.id.m_ed );
        listenEditText();
    }

    private void listenEditText() {
        editText.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    message = Integer.valueOf( s.toString() );
                } catch (Exception e) {
                    Toast.makeText( MainActivity.this, "输入内容不合规范", Toast.LENGTH_SHORT ).show();
                }

            }
        } );
    }


    //点击按钮触发点击事件。
    public void onClick(View view) {
        switch (message) {
            case 0:
                //一般对话框。
                new CommonDialog.Builder( this )
                        .setGravity( Gravity.CENTER )
                        .setTitle( "特别提醒", R.color.colorPrimary )
                        .setMessage( "测试测试测试测试", R.color.colorPrimaryDark )
                        .setPositiveLeft( "退出", R.color.colorAccent, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //点击退出按钮后
                            }
                        } )
                        .setPositiveRight( "重新登录", R.color.colorAccent, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //点击退出按钮后
                            }
                        } )
                        .show();
                break;
            case 1:
                mlist.clear();
                for (int i = 0; i <4 ; i++) {
                    mlist.add( "" + i );
                }
                new MultipleChoiceDialog.Builder( this, mlist )
                        .addItemListener( new MultipleChoiceDialog.MyRecyclerAdapter.DialogClick() {
                            @Override
                            public void onItemClick(View view, int position, List<Integer> mListCount) {
                                Toast.makeText( MainActivity.this, "点击了"+position, Toast.LENGTH_SHORT ).show();
                                Log.i( "wll11", "总计选中:" + mListCount.size() );
                            }
                        } )
                        .show();
                break;
            case 2:
                List<String> mm = new ArrayList<>();
                for (int i = 0; i <2 ; i++) {
                    mm.add( "" + i );
                }
                new BottomMenuDialog.Builder( this, mm )
                        .setDialogClick( new BottomMenuDialog.MyRecyclerBottomAdapter.BottomMenuDialogClick() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }
                        } )
                        .show();
                break;
        }
    }
}

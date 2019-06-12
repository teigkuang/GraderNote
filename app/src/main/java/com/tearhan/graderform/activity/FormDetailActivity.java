package com.tearhan.graderform.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tearhan.graderform.data.db.DBService;
import com.tearhan.graderform.R;


public class FormDetailActivity extends AppCompatActivity {

    private TextView tvContent;
    private Button btnPass;
    private Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_detail);
        tvContent = findViewById(R.id.tv_form_content_form_detail);
        btnPass = findViewById(R.id.btn_pass_form_detail);
        btnReturn = findViewById(R.id.btn_return_form_detail);
        final String formName = getIntent().getStringExtra("name");
        String content = DBService.getForm(formName);
        tvContent.setText(content);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormDetailActivity.this.finish();
            }
        });

        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(FormDetailActivity.this)
                        .setTitle("提示")
                        .setMessage("是否通过？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //删除表数据
                                DBService.deleteForm(formName);
                                FormDetailActivity.this.finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

    }

}

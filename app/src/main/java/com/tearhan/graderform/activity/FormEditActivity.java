package com.tearhan.graderform.activity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tearhan.graderform.data.db.DBService;
import com.tearhan.graderform.R;

public class FormEditActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etDetail;
    private Button btnConfirm;
    private Button btnCancel;
    private String formId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit);
        etTitle = findViewById(R.id.et_title_form_edit);
        etDetail = findViewById(R.id.et_content_form_edit);
        btnCancel = findViewById(R.id.btn_cancel_form_edit);
        btnConfirm = findViewById(R.id.btn_confirm_form_edit);
        initFormEditValue();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormEditActivity.this.finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String title = etTitle.getText().toString();
                final String content = etDetail.getText().toString();

                if ("".equals(title) || "".equals(content)) {
                    Toast.makeText(FormEditActivity.this, "标题或内容不能为空", Toast.LENGTH_LONG).show();
                    return;
                }

                new AlertDialog.Builder(FormEditActivity.this)
                        .setTitle("提示")
                        .setMessage("确定保存这条记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContentValues values = new ContentValues();
                                values.put("title", title);
                                values.put("content", content);
                                if (null==formId||"".equals(formId)){
                                    String formName="form_"+System.currentTimeMillis();
                                    values.put("formName",formName);
                                    DBService.addNote(values);
                                    //建表
                                    DBService.createForm(formName);
                                }else{
                                    DBService.updateNoteById(Integer.valueOf(formId),values);
                                }
                                Toast.makeText(FormEditActivity.this,"保存成功！",Toast.LENGTH_LONG).show();
                                FormEditActivity.this.finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });

    }

    private void initFormEditValue(){
        //获取传值
        long id=this.getIntent().getLongExtra("id",-1L);
        //数据
        if(id!=-1){
            formId=String.valueOf(id);
            Cursor cursor=DBService.queryNoteByid((int) id);
            if(cursor.moveToFirst()){
                etTitle.setText(cursor.getString(1));
                etDetail.setText(cursor.getString(2));
            }
        }
    }
}

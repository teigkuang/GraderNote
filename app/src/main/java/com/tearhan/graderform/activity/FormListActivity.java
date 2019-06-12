package com.tearhan.graderform.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.tearhan.graderform.data.db.DBService;
import com.tearhan.graderform.R;

public class FormListActivity extends AppCompatActivity {

    private ListView lsvForm;
    private Button btnAdd;
    private Cursor listItemCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list);
        lsvForm = findViewById(R.id.lsv_forms_form_list);
        btnAdd = findViewById(R.id.btn_add_form_list);
        btnAdd.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(FormListActivity.this, FormEditActivity.class);
                startActivity(intent);
                return true;
            }
        });
        //获取所有信息
        listItemCursor = DBService.queryAll();
        //加载
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(FormListActivity.this,
                R.layout.form_item, listItemCursor, new String[]{"title", "createTime"},
                new int[]{R.id.note_item_title, R.id.note_item_date},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);//内容监听
        lsvForm.setAdapter(adapter);

        initListNoteListener();
    }

    public void initListNoteListener() {
        //长按删除
        lsvForm.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                new AlertDialog.Builder(FormListActivity.this)
                        .setTitle("提示")
                        .setMessage("选择想进行操作？")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBService.deleteNoteById((int) id);
                                FormListActivity.this.onResume();
                                Toast.makeText(FormListActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("查看", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent=new Intent(FormListActivity.this,FormEditActivity.class);
                                intent.putExtra("id",id);
                                startActivity(intent);
                            }
                        })
                        .show();
                return true;
            }
        });
        //点击编辑
        lsvForm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FormListActivity.this, FormDetailActivity.class);
                //获取表名
                Cursor cursor = DBService.queryNoteByid((int) id);
                String formName = "";
                if (cursor.moveToFirst()) {
                    formName = cursor.getString(3);
                }
                intent.putExtra("name", formName);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listItemCursor != null) {
            //重新查询数据库
            listItemCursor.requery();
        }
    }
}

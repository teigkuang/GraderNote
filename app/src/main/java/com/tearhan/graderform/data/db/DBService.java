package com.tearhan.graderform.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tearhan.graderform.data.form.Creator;
import com.tearhan.graderform.data.form.DataDeal;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by teigkuang on 2019/6/12 0012.
 */

public class DBService {
    private static SQLiteDatabase db = null;

    //静态代码块
    static {
        //新建或者打开数据库
        db = SQLiteDatabase.openOrCreateDatabase("data/data/com.tearhan.graderform/FormMenu.db", null);
        String sql = "create table FormMenu(_id integer primary key autoincrement,title varchar(255),content TEXT,formName varChar(255),createTime varchar(25))";
        //判断是否存在表NoteBook，如果不存在跑出异常，捕捉异常创建表
        try {
            //查询第一行数据
            db.rawQuery("select count(1) from FormMenu", null);
        } catch (Exception e) {
            //创建表
            db.execSQL(sql);
        }
    }

    //建表
    public static void createForm(final String formName) {
        final SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("data/data/com.tearhan.graderform/" + formName + ".db", null);
        String sql = "create table " + formName + "(_id integer primary key autoincrement,content TEXT)";
        db.execSQL(sql);
        //写数据
        Creator creator = new Creator(8, 1, 1, 0, new DataDeal() {
            @Override
            public void saveData(String str) {
                ContentValues values = new ContentValues();
                values.put("content", str);
                db.insert(formName, null, values);
            }
        });
        //创建表
        creator.getGradingForm();
        db.close();
    }

    /**
     * 打开表
     *
     * @param formName
     */
    public static String getForm(String formName) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("data/data/com.tearhan.graderform/" + formName + ".db", null);
        Cursor cursor =  db.rawQuery("select * from "+formName,null);
        String content = "";
        if (cursor.moveToPosition(0)) {
            content = cursor.getString(1);
        }
        db.close();
        return content;
    }

    public static void deleteForm(String formName) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("data/data/com.tearhan.graderform/" + formName + ".db", null);
        Cursor cursor =  db.rawQuery("select * from "+formName,null);
        String id="";
        if(cursor.moveToPosition(0)){
            id=cursor.getString(0);
        }
        db.delete(formName,"_id= "+id,null);
        db.close();
    }

    //获取db
    public static SQLiteDatabase getSQLiteDatabase() {
        return db;
    }

    //查询所有
    public static Cursor queryAll() {
        return db.rawQuery("select * from FormMenu", null);
    }

    //通过id查询所有
    public static Cursor queryNoteByid(Integer id) {
        return db.rawQuery("select * from FormMenu where _id=" + id, null);
    }

    public static String queryFormName(Integer id) {
        return queryNoteByid(id).getString(3);
    }

    //删除
    public static void deleteNoteById(Integer id) {
        if (id == null) {
            return;
        }
        db.delete("FormMenu", "_id=" + id, null);
    }

    //更新数据
    public static void updateNoteById(Integer id, ContentValues values) {
        db.update("FormMenu", values, "_id=" + id, null);
    }

    //添加数据
    public static void addNote(ContentValues values) {
        values.put("createTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.CHINA).format(System.currentTimeMillis()));
        db.insert("FormMenu", null, values);
    }
}

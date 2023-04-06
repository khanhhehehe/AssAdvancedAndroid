package khanhnqph27525.fpoly.assignment_nangcao.Helper_Course;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbSinhVien extends SQLiteOpenHelper {
    final static String DB_NAME = "db_sinhvien";
    final static int VERSION = 1;
    public MyDbSinhVien(Context context){
        super(context,DB_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_table_lop = "CREATE TABLE tb_lop (idlop INTEGER NOT NULL,malop TEXT NOT NULL,tenlop TEXT,PRIMARY KEY(idlop AUTOINCREMENT))";
        db.execSQL(sql_table_lop);

        sql_table_lop = "CREATE TABLE tb_svien (id INTEGER NOT NULL,masv TEXT NOT NULL,hoten TEXT NOT NULL,ngaysinh TEXT NOT NULL,sdt TEXT NOT NULL,idlop INTEGER NOT NULL,PRIMARY KEY(id AUTOINCREMENT))";
        db.execSQL(sql_table_lop);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

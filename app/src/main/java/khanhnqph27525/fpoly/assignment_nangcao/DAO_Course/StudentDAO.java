package khanhnqph27525.fpoly.assignment_nangcao.DAO_Course;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

import khanhnqph27525.fpoly.assignment_nangcao.DTO_Course.Lophoc;
import khanhnqph27525.fpoly.assignment_nangcao.DTO_Course.Student;
import khanhnqph27525.fpoly.assignment_nangcao.Helper_Course.MyDbSinhVien;

public class StudentDAO {
    SQLiteDatabase database;
    MyDbSinhVien myDbSinhVien;
    public StudentDAO(Context context){
        myDbSinhVien = new MyDbSinhVien(context);
    }
    public void open(){
        database = myDbSinhVien.getWritableDatabase();
    }
    public void close(){
        myDbSinhVien.close();
    }
    public long AddSv(Student student){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Student.CLM_MASV,student.getMasv());
        contentValues.put(Student.CLM_HOTEN,student.getHoten());
        contentValues.put(Student.CLM_NGAYSINH,student.getNgaysinh());
        contentValues.put(Student.CLM_SDT,student.getSdt());
        contentValues.put(Lophoc.CLM_ID,student.getIdlop());

        long res = database.insert(Student.TB_SV,null,contentValues);
        return res;
    }
    public ArrayList<Student> selectSV(){
        ArrayList<Student> dssv = new ArrayList<>();
        String []sql_sv = new String[]{Student.CLM_IDSV,Student.CLM_MASV,Student.CLM_HOTEN,Student.CLM_NGAYSINH,Student.CLM_SDT,Lophoc.CLM_ID};
        Cursor cursor = database.query(Student.TB_SV,sql_sv,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            String masv = cursor.getString(1);
            String hoten = cursor.getString(2);
            String ngaysinh = cursor.getString(3);
            String sdt = cursor.getString(4);
            int idlop = cursor.getInt(5);

            Student student = new Student();
            student.setId(id);
            student.setMasv(masv);
            student.setHoten(hoten);
            student.setNgaysinh(ngaysinh);
            student.setSdt(sdt);
            student.setIdlop(idlop);

            dssv.add(student);
            cursor.moveToNext();
        }
        return dssv;
    }
    public int UpdateSv(Student student){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Student.CLM_MASV,student.getMasv());
        contentValues.put(Student.CLM_HOTEN,student.getHoten());
        contentValues.put(Student.CLM_NGAYSINH,student.getNgaysinh());
        contentValues.put(Student.CLM_SDT,student.getSdt());
        contentValues.put(Lophoc.CLM_ID,student.getIdlop());

        int res = database.update(Student.TB_SV,contentValues,"id = "+student.getId(),null);
        return res;
    }
    public int DeleteSv(Student student){
        return database.delete(Student.TB_SV,"id = "+student.getId(),null);
    }
}

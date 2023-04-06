package khanhnqph27525.fpoly.assignment_nangcao.Activity_Course;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.HashMap;

import khanhnqph27525.fpoly.assignment_nangcao.Adapter_Course.StudentAdapter;
import khanhnqph27525.fpoly.assignment_nangcao.DAO_Course.ClassDao;
import khanhnqph27525.fpoly.assignment_nangcao.DAO_Course.StudentDAO;
import khanhnqph27525.fpoly.assignment_nangcao.DTO_Course.Lophoc;
import khanhnqph27525.fpoly.assignment_nangcao.DTO_Course.Student;
import khanhnqph27525.fpoly.assignment_nangcao.R;

public class List_Student extends AppCompatActivity {
    ListView lv_sinhvien;
    ArrayList<Lophoc> arrayList;
    ArrayList<Student> studentArrayList;
    ClassDao classDao;
    StudentAdapter studentAdapter;
    StudentDAO studentDAO;
    Spinner spn_lop;
    EditText ed_masv, ed_tensv, ed_ngaysinh, ed_sdt;
    Button btn_update_sv,btn_add_new_sv;
    Student currentsv = null;
    Dialog dialogdelete;
    public View layout_delete;
    LayoutInflater inflater;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        studentDAO.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dssv);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        layout_delete = inflater.inflate(R.layout.layout_delete, findViewById(R.id.layout_deleteclass));

        ed_masv = findViewById(R.id.ed_masv);
        ed_tensv = findViewById(R.id.ed_tensv);
        ed_ngaysinh = findViewById(R.id.ed_ngaysinh);
        ed_sdt = findViewById(R.id.ed_sdt);
        spn_lop = findViewById(R.id.spn_lop);
        btn_add_new_sv = findViewById(R.id.btn_add_new_sv);
        btn_update_sv = findViewById(R.id.btn_update_sv);
        btn_update_sv.setVisibility(View.INVISIBLE);

        classDao = new ClassDao(this);
        classDao.open();

        studentDAO = new StudentDAO(this);
        studentDAO.open();

        studentArrayList = studentDAO.selectSV();
        studentAdapter = new StudentAdapter(studentArrayList);
        lv_sinhvien = findViewById(R.id.lv_sinhvien);
        lv_sinhvien.setAdapter(studentAdapter);

        Spinner spin = findViewById(R.id.spn_lop);
        arrayList = classDao.selectAll();
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        for (Lophoc lophoc : arrayList){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("idlop",lophoc.getIdlop()+"");
            hashMap.put("malop",lophoc.getMalop());
            hashMap.put("tenlop",lophoc.getTenlop());
            list.add(hashMap);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list,R.layout.layout_item_spinner,new String[]{"tenlop"},new int[]{R.id.tv_tenclass});
        spin.setAdapter(adapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_selected = arrayList.get(position).getTenlop();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_add_new_sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMap = (HashMap<String, String>) spn_lop.getSelectedItem();
                Student student = new Student();
                student.setMasv(ed_masv.getText().toString());
                student.setHoten(ed_tensv.getText().toString());
                student.setNgaysinh(ed_ngaysinh.getText().toString());
                student.setSdt(ed_sdt.getText().toString());
                student.setIdlop(Integer.parseInt(hashMap.get("idlop")));

                if (ed_masv.getText().toString().isEmpty()||ed_tensv.getText().toString().isEmpty()||ed_ngaysinh.getText().toString().isEmpty()||ed_sdt.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    long res = studentDAO.AddSv(student);
                    if(res>0){
                        studentArrayList.clear();
                        studentArrayList.addAll(studentDAO.selectSV());
                        studentAdapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "Thêm sinh viên thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getBaseContext(), "Thêm sinh viên thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        lv_sinhvien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                currentsv = studentArrayList.get(position);
                registerForContextMenu(lv_sinhvien);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.options_class,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.sl_update:
                int index = (currentsv.getIdlop())-1;
                spn_lop.setSelection(index);
                ed_masv.setText(currentsv.getMasv());
                ed_tensv.setText(currentsv.getHoten());
                ed_ngaysinh.setText(currentsv.getNgaysinh());
                ed_sdt.setText(currentsv.getSdt());
                btn_update_sv.setVisibility(View.VISIBLE);
                btn_add_new_sv.setVisibility(View.INVISIBLE);
                btn_update_sv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, String> hashMap = (HashMap<String, String>) spn_lop.getSelectedItem();
                        int idlop_new = Integer.parseInt(hashMap.get("idlop"));
                        String masv_new = ed_masv.getText().toString();
                        String tensv_new = ed_tensv.getText().toString();
                        String ngaysinh_new = ed_ngaysinh.getText().toString();
                        String sdt_new = ed_sdt.getText().toString();

                        if(currentsv!=null&&(currentsv.getIdlop()!=index||!currentsv.getMasv().equalsIgnoreCase(masv_new)||!currentsv.getHoten().equalsIgnoreCase(tensv_new)||!currentsv.getNgaysinh().equalsIgnoreCase(ngaysinh_new)||!currentsv.getSdt().equalsIgnoreCase(sdt_new))){
                            currentsv.setIdlop(idlop_new);
                            currentsv.setMasv(masv_new);
                            currentsv.setHoten(tensv_new);
                            currentsv.setNgaysinh(ngaysinh_new);
                            currentsv.setSdt(sdt_new);
                            if (masv_new.isEmpty()||tensv_new.isEmpty()||ngaysinh_new.isEmpty()||sdt_new.isEmpty()){
                                Toast.makeText(getBaseContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                                btn_update_sv.setVisibility(View.INVISIBLE);
                            }else {
                                int res = studentDAO.UpdateSv(currentsv);
                                if(res>0){
                                    spn_lop.setSelection(0);
                                    ed_masv.setText("");
                                    ed_tensv.setText("");
                                    ed_ngaysinh.setText("");
                                    ed_sdt.setText("");
                                    studentArrayList.clear();
                                    studentArrayList.addAll(studentDAO.selectSV());
                                    studentAdapter.notifyDataSetChanged();
                                    Toast.makeText(getBaseContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                                    currentsv = null;
                                    btn_update_sv.setVisibility(View.INVISIBLE);
                                }else {
                                    Toast.makeText(getBaseContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                                    btn_update_sv.setVisibility(View.INVISIBLE);
                                }
                            }
                        }else {
                            Toast.makeText(getBaseContext(),"Không có gì cập nhật",Toast.LENGTH_SHORT).show();
                            btn_update_sv.setVisibility(View.INVISIBLE);
                        }
                        btn_add_new_sv.setVisibility(View.VISIBLE);
                    }
                });

                Toast.makeText(this,"Bạn đã chọn update",Toast.LENGTH_SHORT).show();
                break;
            case R.id.sl_delete:
                if (layout_delete.getParent()!=null){
                    ((ViewGroup) layout_delete.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setView(layout_delete);
                builder1.setTitle("Xóa");
                builder1.setIcon(android.R.drawable.ic_menu_delete);
                builder1.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int res = studentDAO.DeleteSv(currentsv);
                        if(res>0){
                            studentArrayList.clear();
                            studentArrayList.addAll(studentDAO.selectSV());
                            studentAdapter.notifyDataSetChanged();
                        }
                        Toast.makeText(getBaseContext(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogdelete.dismiss();
                    }
                });
                dialogdelete = builder1.create();
                dialogdelete.show();
        }
        return super.onContextItemSelected(item);
    }
}
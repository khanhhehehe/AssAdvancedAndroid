package khanhnqph27525.fpoly.assignment_nangcao.Activity_Course;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import khanhnqph27525.fpoly.assignment_nangcao.Adapter_Course.LopAdapter;
import khanhnqph27525.fpoly.assignment_nangcao.DAO_Course.ClassDao;
import khanhnqph27525.fpoly.assignment_nangcao.DTO_Course.Lophoc;
import khanhnqph27525.fpoly.assignment_nangcao.R;

public class QuanLySinhVien extends AppCompatActivity {
    public View layout_themlop;
    LayoutInflater inflater;
    Dialog dialogAdd;
    ClassDao classDao;
    ArrayList<Lophoc> arrayList;
    LopAdapter lopAdapter;

    public void ShowDialogAdd(View view){
        dialogAdd.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        classDao.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlysinhvien);

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        layout_themlop = inflater.inflate(R.layout.layout_themlop,(ViewGroup) findViewById(R.id.layout_addclass));

        if (layout_themlop.getParent()!=null){
            ((ViewGroup) layout_themlop.getParent()).removeAllViews();
        }

        final EditText ed_malop = layout_themlop.findViewById(R.id.ed_malop);
        final EditText ed_tenlop = layout_themlop.findViewById(R.id.ed_tenlop);

        Button btn_xoa = layout_themlop.findViewById(R.id.btn_xoatrang);
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_malop.setText("");
                ed_tenlop.setText("");
            }
        });


        Button btn_luulop = layout_themlop.findViewById(R.id.btn_luulop);
        btn_luulop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lophoc lophoc = new Lophoc();
                lophoc.setMalop(ed_malop.getText().toString());
                lophoc.setTenlop(ed_tenlop.getText().toString());
                if (ed_malop.getText().toString().toString().isEmpty()||ed_tenlop.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    long kq = classDao.addNew(lophoc);
                    if (kq>0){
                        Toast.makeText(getBaseContext(), "Lưu lớp thành công",Toast.LENGTH_SHORT).show();
                        arrayList.clear();
                        arrayList.addAll(classDao.selectAll());
                        lopAdapter.notifyDataSetChanged();
                        ed_malop.setText("");
                        ed_tenlop.setText("");
                    }else {
                        Toast.makeText(getBaseContext(), "Lưu lớp thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button btn_showclass = findViewById(R.id.btn_showclass);
        btn_showclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), List_Class.class);
                startActivity(intent);
            }
        });

        Button btn_quanlysv = findViewById(R.id.btn_quanlysv);
        btn_quanlysv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),List_Student.class));
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout_themlop);
        builder.setTitle("Add New Class");
        builder.setIcon(android.R.drawable.ic_input_add);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogAdd = builder.create();
        classDao = new ClassDao(this);
        classDao.open();

        arrayList = classDao.selectAll();
        lopAdapter = new LopAdapter(arrayList);

    }
}
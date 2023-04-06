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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import khanhnqph27525.fpoly.assignment_nangcao.Adapter_Course.LopAdapter;
import khanhnqph27525.fpoly.assignment_nangcao.DAO_Course.ClassDao;
import khanhnqph27525.fpoly.assignment_nangcao.DTO_Course.Lophoc;
import khanhnqph27525.fpoly.assignment_nangcao.R;

public class List_Class extends AppCompatActivity {
    public View layout_updatelop;
    public View layout_deletelop;
    LayoutInflater inflater;
    Dialog dialogUpdate;
    Dialog dialogDelete;
    ClassDao classDao;
    ArrayList<Lophoc> arrayList;
    LopAdapter lopAdapter;
    ListView lv_dslop;
    Lophoc current = null;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dslop);

//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            layout_updatelop = inflater.inflate(R.layout.layout_updatelop,findViewById(R.id.layout_updateclass));
            layout_deletelop = inflater.inflate(R.layout.layout_delete,findViewById(R.id.layout_deleteclass));

            classDao = new ClassDao(this);
            classDao.open();

            arrayList = classDao.selectAll();
            lopAdapter = new LopAdapter(arrayList);
            lv_dslop = findViewById(R.id.lv_dslop);
            lv_dslop.setAdapter(lopAdapter);
            arrayList.clear();
            arrayList.addAll(classDao.selectAll());
            lopAdapter.notifyDataSetChanged();

            lv_dslop.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    current = arrayList.get(position);
                    registerForContextMenu(lv_dslop);
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
                if (layout_updatelop.getParent()!=null){
                    ((ViewGroup) layout_updatelop.getParent()).removeAllViews();
                }
                final EditText ed_malop_new = layout_updatelop.findViewById(R.id.ed_malop_new);
                final EditText ed_tenlop_new = layout_updatelop.findViewById(R.id.ed_tenlop_new);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(layout_updatelop);
                builder.setTitle("Update");
                builder.setIcon(android.R.drawable.arrow_up_float);
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Button btn_xoatrang_new = layout_updatelop.findViewById(R.id.btn_xoatrang_new);
                btn_xoatrang_new.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ed_malop_new.setText("");
                        ed_tenlop_new.setText("");
                    }
                });

                Button btn_capnhat = layout_updatelop.findViewById(R.id.btn_capnhat);
                btn_capnhat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String malop = ed_malop_new.getText().toString();
                        String tenlop = ed_tenlop_new.getText().toString();
                        if(!(malop.equalsIgnoreCase(current.getMalop()))||!(tenlop.equalsIgnoreCase(current.getTenlop()))){
                            current.setMalop(malop);
                            current.setTenlop(tenlop);
                            if (ed_malop_new.getText().toString().isEmpty()||ed_tenlop_new.getText().toString().isEmpty()){

                                Toast.makeText(getBaseContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
                            }else {
                                int res = classDao.UpdateRow(current);
                                if(res>0){
                                    ed_malop_new.setText("");
                                    ed_tenlop_new.setText("");
                                    arrayList.clear();
                                    arrayList.addAll(classDao.selectAll());
                                    lopAdapter.notifyDataSetChanged();
                                    current=null;
                                    Toast.makeText(getBaseContext(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getBaseContext(),"Cập nhật thất bại",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else {
                            Toast.makeText(getBaseContext(),"Không có gì được cập nhật",Toast.LENGTH_SHORT).show();
                        }
                        dialogUpdate.dismiss();
                    }
                });
                dialogUpdate = builder.create();
                dialogUpdate.show();
                break;
            case R.id.sl_delete:
                if (layout_deletelop.getParent()!=null){
                    ((ViewGroup) layout_deletelop.getParent()).removeAllViews();
                }
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setView(layout_deletelop);
                builder1.setTitle("Xóa");
                builder1.setIcon(android.R.drawable.ic_menu_delete);
                builder1.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int res = classDao.DeleteRow(current);
                        if(res>0){
                            arrayList.clear();
                            arrayList.addAll(classDao.selectAll());
                            lopAdapter.notifyDataSetChanged();
                        }
                        Toast.makeText(getBaseContext(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogDelete.dismiss();
                    }
                });
                dialogDelete = builder1.create();
                dialogDelete.show();
        }
        return super.onContextItemSelected(item);
    }
}
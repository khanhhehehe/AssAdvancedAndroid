package khanhnqph27525.fpoly.assignment_nangcao.Adapter_Course;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import khanhnqph27525.fpoly.assignment_nangcao.DTO_Course.Student;
import khanhnqph27525.fpoly.assignment_nangcao.R;

public class StudentAdapter extends BaseAdapter {
    final ArrayList<Student> studentArrayList;
    public StudentAdapter(ArrayList<Student> studentArrayList){this.studentArrayList = studentArrayList;}

    @Override
    public int getCount() {
        return studentArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return studentArrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item_sv;
        if(convertView == null){
            item_sv = View.inflate(parent.getContext(), R.layout.row_sinhvien,null);
        }else {
            item_sv = convertView;
        }

        Student student = studentArrayList.get(position);

        TextView tv_sttstudent = item_sv.findViewById(R.id.tv_sttsv);
        TextView tv_mastudent = item_sv.findViewById(R.id.tv_masv);
        TextView tv_tenstudent = item_sv.findViewById(R.id.tv_hoten);
        TextView tv_ngaysinhstudent = item_sv.findViewById(R.id.tv_ngaysinh);
        TextView tv_sdtstudent = item_sv.findViewById(R.id.tv_sdt);
        TextView tv_lopstudent = item_sv.findViewById(R.id.tv_idlop);

        tv_sttstudent.setText(student.getId()+". ");
        tv_mastudent.setText(student.getMasv());
        tv_tenstudent.setText(student.getHoten());
        tv_ngaysinhstudent.setText(student.getNgaysinh());
        tv_sdtstudent.setText(student.getSdt());
        tv_lopstudent.setText("Lớp số: "+student.getIdlop()+"");

        return item_sv;
    }
}

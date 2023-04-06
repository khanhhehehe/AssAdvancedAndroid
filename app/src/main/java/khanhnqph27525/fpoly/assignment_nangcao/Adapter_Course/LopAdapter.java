package khanhnqph27525.fpoly.assignment_nangcao.Adapter_Course;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import khanhnqph27525.fpoly.assignment_nangcao.DTO_Course.Lophoc;
import khanhnqph27525.fpoly.assignment_nangcao.R;

public class LopAdapter extends BaseAdapter {
    final ArrayList<Lophoc> arrayList;
    public LopAdapter(ArrayList<Lophoc> arrayList){this.arrayList = arrayList;}
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getIdlop();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item_view;
        if (convertView==null){
            item_view = View.inflate(parent.getContext(), R.layout.layout_dslop,null);
        }else {
            item_view = convertView;
        }
        Lophoc lophoc = arrayList.get(position);
        TextView tv_stt = item_view.findViewById(R.id.tv_stt);
        TextView tv_malop = item_view.findViewById(R.id.tv_malop);
        TextView tv_tenlop = item_view.findViewById(R.id.tv_tenlop);

        tv_stt.setText(lophoc.getIdlop()+"");
        tv_malop.setText(lophoc.getMalop());
        tv_tenlop.setText(lophoc.getTenlop());

        return item_view;
    }
}

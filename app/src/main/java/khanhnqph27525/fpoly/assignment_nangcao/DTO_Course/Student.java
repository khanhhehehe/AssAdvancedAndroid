package khanhnqph27525.fpoly.assignment_nangcao.DTO_Course;

public class Student {
    int id;
    String masv;
    String hoten;
    String ngaysinh;
    String sdt;
    int idlop;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public int getIdlop() {
        return idlop;
    }

    public void setIdlop(int idlop) {
        this.idlop = idlop;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    public static final String TB_SV = "tb_svien";
    public static final String CLM_IDSV = "id";
    public static final String CLM_MASV = "masv";
    public static final String CLM_HOTEN = "hoten";
    public static final String CLM_NGAYSINH = "ngaysinh";
    public static final String CLM_SDT = "sdt";
}

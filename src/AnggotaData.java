public class AnggotaData {
    private int idAnggota;
    private String nama;
    private String alamat;
    private String telepon;

    // Constructor
    public AnggotaData(int idAnggota, String nama, String alamat, String telepon) {
        this.idAnggota = idAnggota;
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
    }

    // Getter dan Setter
    public int getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }
}

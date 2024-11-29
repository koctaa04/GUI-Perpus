public class PeminjamanData {
    private int idPeminjaman;
    private int idAnggota;
    private String namaAnggota;
    private int idBuku;
    private String judulBuku;
    private String tanggalPinjam;
    private String tanggalKembali;

    // Constructor
    public PeminjamanData(int idPeminjaman, int idAnggota, String namaAnggota, int idBuku, String judulBuku, String tanggalPinjam, String tanggalKembali) {
        this.idPeminjaman = idPeminjaman;
        this.idAnggota = idAnggota;
        this.namaAnggota = namaAnggota;
        this.idBuku = idBuku;
        this.judulBuku = judulBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }

    // Getter dan Setter untuk idPeminjaman
    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    // Getter dan Setter untuk idAnggota
    public int getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }

    // Getter dan Setter untuk namaAnggota
    public String getNamaAnggota() {
        return namaAnggota;
    }

    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }

    // Getter dan Setter untuk idBuku
    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    // Getter dan Setter untuk judulBuku
    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    // Getter dan Setter untuk tanggalPinjam
    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    // Getter dan Setter untuk tanggalKembali
    public String getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(String tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }
}

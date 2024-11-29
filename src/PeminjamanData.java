public class PeminjamanData {
    private int id;
    private int idAnggota;  // Menambahkan idAnggota
    private int idBuku;     // Menambahkan idBuku
    private String namaAnggota;
    private String judulBuku;
    private String tanggalPinjam;
    private String tanggalKembali;

    // Konstruktor dengan parameter lengkap
    public PeminjamanData(int id, int idAnggota, int idBuku, String namaAnggota, String judulBuku, String tanggalPinjam, String tanggalKembali) {
        this.id = id;
        this.idAnggota = idAnggota;
        this.idBuku = idBuku;
        this.namaAnggota = namaAnggota;
        this.judulBuku = judulBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }

    // Getter untuk setiap field
    public int getId() {
        return id;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public String getTanggalKembali() {
        return tanggalKembali;
    }
}

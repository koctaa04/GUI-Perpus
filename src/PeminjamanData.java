public class PeminjamanData {
    private int id;
    private int idAnggota; // id Anggota bertipe int
    private String namaAnggota;
    private int idBuku;  // id Buku bertipe int
    private String judulBuku;
    private String tanggalPinjam;
    private String tanggalKembali;

    public PeminjamanData(int id, int idAnggota, String namaAnggota, int idBuku, String judulBuku, String tanggalPinjam, String tanggalKembali) {
        this.id = id;
        this.idAnggota = idAnggota;
        this.namaAnggota = namaAnggota;
        this.idBuku = idBuku;
        this.judulBuku = judulBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalKembali = tanggalKembali;
    }

    // Getter dan setter
    public int getId() {
        return id;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public int getIdBuku() {
        return idBuku;
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

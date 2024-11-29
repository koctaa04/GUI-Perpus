import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class KategoriData {
    private IntegerProperty idKategori;
    private StringProperty nama;
    private StringProperty keterangan;

    // Constructor
    public KategoriData(int idKategori, String nama, String keterangan) {
        this.idKategori = new SimpleIntegerProperty(idKategori);
        this.nama = new SimpleStringProperty(nama);
        this.keterangan = new SimpleStringProperty(keterangan);
    }

    // Getter dan Setter untuk idKategori
    public int getIdKategori() {
        return idKategori.get();
    }

    public void setIdKategori(int idKategori) {
        this.idKategori.set(idKategori);
    }

    // Getter dan Setter untuk nama
    public String getNama() {
        return nama.get();
    }

    public void setNama(String nama) {
        this.nama.set(nama);
    }

    // Getter dan Setter untuk keterangan
    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String keterangan) {
        this.keterangan.set(keterangan);
    }

    // Property getter untuk integrasi dengan TableView
    public IntegerProperty idKategoriProperty() {
        return idKategori;
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }
}

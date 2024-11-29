import javafx.beans.property.*;

public class BukuData {
    private final IntegerProperty idBuku;
    private final StringProperty kategori;
    private final StringProperty judul;
    private final StringProperty penulis;
    private final StringProperty penerbit;

    public BukuData(int idBuku, String kategori, String judul, String penulis, String penerbit) {
        this.idBuku = new SimpleIntegerProperty(idBuku);
        this.kategori = new SimpleStringProperty(kategori);
        this.judul = new SimpleStringProperty(judul);
        this.penulis = new SimpleStringProperty(penulis);
        this.penerbit = new SimpleStringProperty(penerbit);
    }

    // Getter methods
    public int getIdBuku() {
        return idBuku.get();
    }

    public String getKategori() {
        return kategori.get();
    }

    public String getJudul() {
        return judul.get();
    }

    public String getPenulis() {
        return penulis.get();
    }

    public String getPenerbit() {
        return penerbit.get();
    }

    // Property methods for TableView binding
    public IntegerProperty idBukuProperty() {
        return idBuku;
    }

    public StringProperty kategoriProperty() {
        return kategori;
    }

    public StringProperty judulProperty() {
        return judul;
    }

    public StringProperty penulisProperty() {
        return penulis;
    }

    public StringProperty penerbitProperty() {
        return penerbit;
    }
}

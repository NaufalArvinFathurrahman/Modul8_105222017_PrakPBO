abstract class Karyawan {
    protected String nama;

    public Karyawan(String nama) {
        this.nama = nama;
    }

    public abstract double hitungGaji();
}

class Programmer extends Karyawan {
    private double gajiPokok;

    public Programmer(String nama, double gajiPokok) {
        super(nama);
        this.gajiPokok = gajiPokok;
    }

    @Override
    public double hitungGaji() {
        return gajiPokok;
    }
}

public class App {
    public static void main(String[] args) {
        Programmer programmer = new Programmer("Andi", 8000000.0);
        System.out.println("Nama Karyawan: " + programmer.nama);
        System.out.println("Total Gaji: Rp" + programmer.hitungGaji());
    }
}

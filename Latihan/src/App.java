import java.util.ArrayList;
import java.util.List;

abstract class Pembayaran {
    protected String namaPembayar;
    protected double nominal;

    public Pembayaran(String namaPembayar, double nominal) {
        this.namaPembayar = namaPembayar;
        this.nominal = nominal;
    }

    public void tampilkanDetail() {
        System.out.println("Nama Pembayar: " + namaPembayar);
        System.out.println("Nominal Transaksi: Rp" + nominal);
    }

    public abstract void prosesPembayaran();
}

interface Keamanan {
    boolean autentikasi();
}

class KartuKredit extends Pembayaran implements Keamanan {
    private String nomorKartu;

    public KartuKredit(String namaPembayar, double nominal, String nomorKartu) {
        super(namaPembayar, nominal);
        this.nomorKartu = nomorKartu;
    }

    @Override
    public void prosesPembayaran() {
        double biayaAdmin = nominal * 0.02;
        double totalTagihan = nominal + biayaAdmin;
        System.out.println("Biaya Admin (2%): Rp" + biayaAdmin);
        System.out.println("Total Tagihan: Rp" + totalTagihan);
    }

    @Override
    public boolean autentikasi() {
        System.out.println("Autentikasi PIN berhasil.");
        return true;
    }
}

class EWallet extends Pembayaran implements Keamanan {
    private String nomorHP;

    public EWallet(String namaPembayar, double nominal, String nomorHP) {
        super(namaPembayar, nominal);
        this.nomorHP = nomorHP;
    }

    @Override
    public void prosesPembayaran() {
        System.out.println("Total Tagihan (Tanpa biaya admin): Rp" + nominal);
    }

    @Override
    public boolean autentikasi() {
        System.out.println("Autentikasi berhasil.");
        return true;
    }
}

public class App {
    public static void main(String[] args) {
        List<Pembayaran> daftarPembayaran = new ArrayList<>();
        daftarPembayaran.add(new KartuKredit("Budi", 500000.0, "1234-5678-9012"));
        daftarPembayaran.add(new EWallet("Siti", 150000.0, "08123456789"));

        for (Pembayaran p : daftarPembayaran) {
            System.out.println("---------------------------------");
            p.tampilkanDetail();
            
            if (p instanceof Keamanan) {
                Keamanan k = (Keamanan) p;
                if (k.autentikasi()) {
                    p.prosesPembayaran();
                }
            }
        }
        System.out.println("---------------------------------");
    }
}

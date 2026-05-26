abstract class LayananPengiriman {
    protected String noResi;
    protected double beratBarang;
    protected double jarakTempuh;

    public LayananPengiriman(String noResi, double beratBarang, double jarakTempuh) {
        this.noResi = noResi;
        this.beratBarang = beratBarang;
        this.jarakTempuh = jarakTempuh;
    }

    public void cetakResi() {
        System.out.println("Nomor Resi: " + noResi);
        System.out.println("Berat Barang: " + beratBarang + " kg");
        System.out.println("Jarak Tempuh: " + jarakTempuh + " km");
    }

    public abstract double hitungOngkosKirim();
}

interface LacakKargo {
    void updateStatus(String status);
    String cekLokasiTerakhir();
}

interface Asuransi {
    double hitungPremi(double nilaiBarang);
    default void cetakPolis() {
        System.out.println("Polis Asuransi aktif: Menanggung kehilangan dan kerusakan fisik sebesar 100% dari nilai barang.");
    }
}

class PengirimanDarat extends LayananPengiriman implements LacakKargo {
    private String jenisTruk;
    private String statusSaatIni;

    public PengirimanDarat(String noResi, double beratBarang, double jarakTempuh, String jenisTruk) {
        super(noResi, beratBarang, jarakTempuh);
        this.jenisTruk = jenisTruk;
        this.statusSaatIni = "Menunggu Kurir";
    }

    @Override
    public double hitungOngkosKirim() {
        double tarif = (beratBarang * 5000) + (jarakTempuh * 2000);
        if (jenisTruk.equalsIgnoreCase("Tronton")) {
            tarif += 150000;
        }
        return tarif;
    }

    @Override
    public void updateStatus(String status) {
        this.statusSaatIni = status;
    }

    @Override
    public String cekLokasiTerakhir() {
        return this.statusSaatIni;
    }
}

class PengirimanUdara extends LayananPengiriman implements LacakKargo, Asuransi {
    private String nomorPenerbangan;
    private String statusSaatIni;
    private double nilaiBarang;

    public PengirimanUdara(String noResi, double beratBarang, double jarakTempuh, String nomorPenerbangan, double nilaiBarang) {
        super(noResi, beratBarang, jarakTempuh);
        this.nomorPenerbangan = nomorPenerbangan;
        this.nilaiBarang = nilaiBarang;
        this.statusSaatIni = "Menunggu Jadwal Penerbangan";
    }

    public double getNilaiBarang() {
        return nilaiBarang;
    }

    @Override
    public double hitungOngkosKirim() {
        return (beratBarang * 25000) + (jarakTempuh * 5000);
    }

    @Override
    public double hitungPremi(double nilaiBarang) {
        return nilaiBarang * 0.03;
    }

    @Override
    public void updateStatus(String status) {
        this.statusSaatIni = status;
    }

    @Override
    public String cekLokasiTerakhir() {
        return this.statusSaatIni;
    }
}

public class App {
    public static void main(String[] args) {
        PengirimanDarat darat = new PengirimanDarat("DRT-001", 50, 100, "Tronton");
        PengirimanUdara udara = new PengirimanUdara("UDR-999", 10, 800, "GA-123", 5000000);

        darat.updateStatus("Sedang di jalan tol Cipali");
        udara.updateStatus("Transit di Bandara Soekarno-Hatta");

        LayananPengiriman[] daftarPengiriman = {darat, udara};

        for (LayananPengiriman layanan : daftarPengiriman) {
            System.out.println("==================================================");
            layanan.cetakResi();

            if (layanan instanceof LacakKargo) {
                LacakKargo kargo = (LacakKargo) layanan;
                System.out.println("Lokasi Terakhir: " + kargo.cekLokasiTerakhir());
            }

            double ongkirDasar = layanan.hitungOngkosKirim();
            double totalTagihan = ongkirDasar;
            System.out.println("Ongkos Kirim Dasar: Rp " + ongkirDasar);

            if (layanan instanceof Asuransi) {
                Asuransi asuransi = (Asuransi) layanan;
                asuransi.cetakPolis();
                
                if (layanan instanceof PengirimanUdara) {
                    PengirimanUdara pu = (PengirimanUdara) layanan;
                    double premi = asuransi.hitungPremi(pu.getNilaiBarang());
                    totalTagihan += premi;
                    System.out.println("Premi Asuransi: Rp " + premi);
                }
            }

            System.out.println("Total Tagihan Keseluruhan: Rp " + totalTagihan);
            System.out.println("==================================================\n");
        }
    }
}

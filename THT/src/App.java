import java.util.Scanner;

// Interface berfungsi sebagai kerangka wajib. Setiap class yang memakai interface ini 
// WAJIB memiliki fungsi serang(), bertahan(), dan gunakanItem().
interface AksiBertarung {
    int serang();
    void bertahan();
    void gunakanItem();
}

// Class abstract adalah fondasi dasar. Tidak bisa dibuat menjadi objek langsung (tidak bisa di-new).
abstract class Karakter implements AksiBertarung {
    
    // Encapsulation: Menggunakan 'protected' agar data ini disembunyikan dari class luar,
    // tapi tetap bisa dipakai oleh class anak turunannya (Pahlawan & Monster).
    protected String nama;
    protected int hp;
    protected int baseDamage;
    protected boolean isDefending;

    public Karakter(String nama, int hp, int baseDamage) {
        this.nama = nama;
        this.hp = hp;
        this.baseDamage = baseDamage;
        this.isDefending = false; 
    }

    // Getter dan Setter untuk mengakses dan mengubah data protected dengan aman.
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getBaseDamage() { return baseDamage; }
    public void setBaseDamage(int baseDamage) { this.baseDamage = baseDamage; }

    public boolean isDefending() { return isDefending; }
    public void setDefending(boolean isDefending) { this.isDefending = isDefending; }

    // Logika perhitungan saat menerima serangan.
    public void terimaDamage(int damage) {
        if (isDefending) {
            damage = damage / 2; // Jika status bertahan aktif, damage dipotong 50%
            isDefending = false; // Status bertahan hilang setelah diserang
        }
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0; // Memastikan HP tidak pernah minus
        }
    }

    // Abstract method: Method kosong yang detail isinya WAJIB dibuat oleh class anaknya.
    public abstract void tampilkanStatus();
}

// Class Pahlawan adalah turunan (anak) dari Karakter. Mewarisi semua data dan fungsinya.
class Pahlawan extends Karakter {
    private int mana;
    private int level;

    public Pahlawan(String nama, int hp, int baseDamage, int mana, int level) {
        super(nama, hp, baseDamage); // super() memanggil constructor dari class induk (Karakter)
        this.mana = mana;
        this.level = level;
    }

    // Method Overriding: Menimpa method serang() dari induk untuk disesuaikan khusus Pahlawan.
    @Override
    public int serang() {
        return baseDamage * level;
    }

    // Method Overloading: Membuat method dengan nama sama (serang) tapi jumlah parameternya berbeda.
    // Ini digunakan untuk sistem skill.
    public int serang(String namaSkill, int manaCost) {
        if (this.mana >= manaCost) {
            this.mana -= manaCost;
            System.out.println(this.nama + " mengeluarkan skill: " + namaSkill + "!");
            return baseDamage * level * 2; // Damage 2x lipat
        } else {
            System.out.println("Mana tidak cukup untuk " + namaSkill + "!");
            return 0; 
        }
    }

    // Menimpa method bertahan() untuk Pahlawan.
    @Override
    public void bertahan() {
        this.isDefending = true;
        System.out.println(this.nama + " bersiaga! Pertahanan meningkat.");
    }

    // Menimpa method gunakanItem() untuk Pahlawan.
    @Override
    public void gunakanItem() {
        this.hp += 30;
        System.out.println(this.nama + " meminum Potion. HP pulih 30 poin!");
    }

    @Override
    public void tampilkanStatus() {
        System.out.println("=== STATUS PAHLAWAN ===");
        System.out.println("Nama  : " + nama);
        System.out.println("Level : " + level);
        System.out.println("HP    : " + hp);
        System.out.println("Mana  : " + mana);
        System.out.println("=======================");
    }
}

// Class Monster adalah turunan (anak) lain dari Karakter.
class Monster extends Karakter {
    private String jenisMonster;

    public Monster(String nama, int hp, int baseDamage, String jenisMonster) {
        super(nama, hp, baseDamage);
        this.jenisMonster = jenisMonster;
    }

    @Override
    public int serang() {
        return baseDamage; // Monster menyerang dengan damage biasa
    }

    @Override
    public void bertahan() {
        this.hp += 10;
        System.out.println(this.nama + " (" + jenisMonster + ") menghindar dan memulihkan 10 HP!");
    }

    @Override
    public void gunakanItem() {
        System.out.println("Monster tidak tahu cara menggunakan item.");
    }

    @Override
    public void tampilkanStatus() {
        System.out.println("=== STATUS MONSTER ===");
        System.out.println("Nama  : " + nama + " [" + jenisMonster + "]");
        System.out.println("HP    : " + hp);
        System.out.println("======================");
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("   SELAMAT DATANG DI DUNGEON SURVIVAL");
        System.out.println("=========================================");
        System.out.print("Masukkan nama Pahlawan Anda: ");
        String namaPemain = scanner.nextLine();

        // Pembuatan objek Pahlawan
        Pahlawan hero = new Pahlawan(namaPemain, 150, 10, 50, 2);

        // Polymorphism Array: Menyimpan berbagai jenis objek Monster ke dalam satu wadah Array.
        Monster[] daftarMonster = new Monster[3];
        daftarMonster[0] = new Monster("Slime", 40, 5, "Lendir");
        daftarMonster[1] = new Monster("Goblin", 70, 12, "Humanoid");
        daftarMonster[2] = new Monster("Orc Boss", 150, 25, "Raksasa");

        boolean isVictory = false;

        System.out.println("\n" + hero.getNama() + " memasuki Dungeon yang gelap...");

        // Nested Loop (For): Perulangan luar untuk memanggil monster satu per satu dari Array.
        for (int i = 0; i < daftarMonster.length; i++) {
            Monster musuh = daftarMonster[i];
            System.out.println("\n*** PERTANDINGAN " + (i + 1) + " DIMULAI! ***");
            System.out.println(musuh.getNama() + " muncul dari kegelapan!");

            // Nested Loop (While): Perulangan dalam sebagai sistem pertarungan.
            // Akan terus berputar selama hero dan monster sama-sama hidup (HP > 0).
            while (hero.getHp() > 0 && musuh.getHp() > 0) {
                System.out.println("\n-----------------------------------------");
                hero.tampilkanStatus();
                musuh.tampilkanStatus();
                
                System.out.println("Pilih Aksi:");
                System.out.println("1. Serang Biasa");
                System.out.println("2. Gunakan Skill (Ultimate Slash - Cost: 20 Mana)");
                System.out.println("3. Bertahan / Heal");
                System.out.print("Pilihan (1/2/3): ");
                
                int pilihan = 0;
                if (scanner.hasNextInt()) {
                    pilihan = scanner.nextInt();
                } else {
                    scanner.next(); 
                }

                int heroDamage = 0;
                // Switch Case untuk menentukan aksi Pahlawan berdasarkan input user.
                switch (pilihan) {
                    case 1:
                        heroDamage = hero.serang(); // Memanggil method serang biasa
                        System.out.println(hero.getNama() + " menyerang dengan senjatanya!");
                        break;
                    case 2:
                        heroDamage = hero.serang("Ultimate Slash", 20); // Memanggil method serang (Overloading)
                        break;
                    case 3:
                        hero.bertahan();
                        hero.gunakanItem();
                        heroDamage = 0;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid! Membuang giliran.");
                        break;
                }

                if (heroDamage > 0) {
                    System.out.println(musuh.getNama() + " terkena " + heroDamage + " damage!");
                    musuh.terimaDamage(heroDamage); // Monster menerima damage
                }

                // Giliran serangan Monster jika belum mati
                if (musuh.getHp() > 0) {
                    System.out.println("\n-- Giliran " + musuh.getNama() + " --");
                    // Logika AI acak: 30% kemungkinan monster akan bertahan, 70% menyerang.
                    if (Math.random() < 0.3) {
                        musuh.bertahan();
                    } else {
                        int monsterDamage = musuh.serang();
                        System.out.println(musuh.getNama() + " menyerang dengan ganas!");
                        System.out.println(hero.getNama() + " terkena " + monsterDamage + " damage!");
                        hero.terimaDamage(monsterDamage); // Hero menerima damage
                    }
                }
            } 

            // Cek kondisi setelah pertarungan dengan 1 monster selesai.
            if (hero.getHp() <= 0) {
                System.out.println("\n" + hero.getNama() + " gugur dalam pertempuran...");
                break; // Keluar dari perulangan for secara paksa jika Pahlawan mati.
            } else {
                System.out.println("\n" + musuh.getNama() + " berhasil dikalahkan!");
                // Jika i sama dengan panjang array dikurangi 1, berarti ini monster terakhir.
                if (i == daftarMonster.length - 1) {
                    isVictory = true; 
                }
            }
        } 

        System.out.println("\n=========================================");
        if (isVictory && hero.getHp() > 0) {
            System.out.println("SELAMAT! " + hero.getNama() + " berhasil menamatkan Dungeon Survival!");
            System.out.println("Anda adalah Pahlawan Sejati!");
        } else {
            System.out.println("GAME OVER!");
            System.out.println("Dungeon terlalu kuat untuk diselesaikan saat ini.");
        }
        System.out.println("=========================================");
        
        scanner.close();
    }
}

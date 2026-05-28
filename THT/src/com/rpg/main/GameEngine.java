package com.rpg.main;

import com.rpg.entitas.Pahlawan;
import com.rpg.entitas.Monster;
import java.util.Scanner;

/**
 * LAPORAN PRAKTIKUM: THT MODUL 8
 * CLASS MAIN (GameEngine)
 * 
 * Merupakan titik eksekusi utama. Menggunakan Array polymorphism
 * dan Nested Loop (Perulangan bersarang) untuk memproses sistem Battle
 * tipe Survival antara Pahlawan melawan sejumlah Monster secara berurutan.
 */
public class GameEngine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("   SELAMAT DATANG DI DUNGEON SURVIVAL");
        System.out.println("=========================================");
        System.out.print("Masukkan nama Pahlawan Anda: ");
        String namaPemain = scanner.nextLine();

        // Inisialisasi Pahlawan
        Pahlawan hero = new Pahlawan(namaPemain, 150, 10, 50, 2);

        // Mode Survival: Pembuatan Array berisikan 3 Monster yang berbeda-beda
        Monster[] daftarMonster = new Monster[3];
        daftarMonster[0] = new Monster("Slime", 40, 5, "Lendir");
        daftarMonster[1] = new Monster("Goblin", 70, 12, "Humanoid");
        daftarMonster[2] = new Monster("Orc Boss", 150, 25, "Raksasa");

        boolean isVictory = false;

        System.out.println("\n" + hero.getNama() + " memasuki Dungeon yang gelap...");

        // Nested Loop: For-Loop bagian luar untuk menggilir antrean Monster
        for (int i = 0; i < daftarMonster.length; i++) {
            Monster musuh = daftarMonster[i];
            System.out.println("\n*** PERTANDINGAN " + (i + 1) + " DIMULAI! ***");
            System.out.println(musuh.getNama() + " muncul dari kegelapan!");

            // Nested Loop: While-Loop bagian dalam sebagai Battle Loop (Berjalan selama keduanya hidup)
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
                    scanner.next(); // Membersihkan input yang tidak valid
                }

                // Giliran Pahlawan
                int heroDamage = 0;
                switch (pilihan) {
                    case 1:
                        heroDamage = hero.serang();
                        System.out.println(hero.getNama() + " menyerang dengan senjatanya!");
                        break;
                    case 2:
                        // Memanggil Method Overloading
                        heroDamage = hero.serang("Ultimate Slash", 20);
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

                // Memberikan damage dari Pahlawan ke Monster
                if (heroDamage > 0) {
                    System.out.println(musuh.getNama() + " terkena " + heroDamage + " damage!");
                    musuh.terimaDamage(heroDamage);
                }

                // Giliran Monster (Jika Monster masih hidup setelah diserang Pahlawan)
                if (musuh.getHp() > 0) {
                    System.out.println("\n-- Giliran " + musuh.getNama() + " --");
                    // AI Monster sederhana: 30% Bertahan, 70% Menyerang
                    if (Math.random() < 0.3) {
                        musuh.bertahan();
                    } else {
                        int monsterDamage = musuh.serang();
                        System.out.println(musuh.getNama() + " menyerang dengan ganas!");
                        System.out.println(hero.getNama() + " terkena " + monsterDamage + " damage!");
                        hero.terimaDamage(monsterDamage);
                    }
                }
            } // Akhir dari Battle Loop (While)

            // Pemeriksaan setelah Battle Loop selesai
            if (hero.getHp() <= 0) {
                System.out.println("\n" + hero.getNama() + " gugur dalam pertempuran...");
                break; // Hentikan for-loop jika mati
            } else {
                System.out.println("\n" + musuh.getNama() + " berhasil dikalahkan!");
                // Jika ini monster terakhir, tandai sebagai kemenangan total
                if (i == daftarMonster.length - 1) {
                    isVictory = true;
                }
            }
        } // Akhir dari Array Loop (For)

        // Penentuan Akhir Permainan menggunakan If-Else
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

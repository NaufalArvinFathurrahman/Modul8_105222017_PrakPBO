package com.rpg.entitas;

import com.rpg.arena.Karakter;

/**
 * Subclass lain dari Karakter. Memiliki perilaku yang sangat berbeda
 * dari Pahlawan saat mengimplementasikan metode yang sama (Polymorphism).
 */
public class Monster extends Karakter {
    private String jenisMonster;

    public Monster(String nama, int hp, int baseDamage, String jenisMonster) {
        super(nama, hp, baseDamage);
        this.jenisMonster = jenisMonster;
    }

    @Override
    public int serang() {
        // Monster hanya menghasilkan damage biasa
        return baseDamage;
    }

    @Override
    public void bertahan() {
        // Perilaku berbeda dari pahlawan: Monster menyembuhkan dirinya sendiri
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

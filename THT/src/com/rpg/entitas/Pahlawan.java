package com.rpg.entitas;

import com.rpg.arena.Karakter;

/**
 * Subclass yang mewarisi Karakter. Mendemonstrasikan Polymorphism melalui
 * Method Overriding pada metode-metode induknya, serta Method Overloading
 * pada metode serang() versi spesifik Pahlawan.
 */
public class Pahlawan extends Karakter {
    // Encapsulation tambahan khusus subclass
    private int mana;
    private int level;

    public Pahlawan(String nama, int hp, int baseDamage, int mana, int level) {
        super(nama, hp, baseDamage); // Memanggil constructor induk
        this.mana = mana;
        this.level = level;
    }

    // Method Overriding: Implementasi spesifik untuk Pahlawan
    @Override
    public int serang() {
        return baseDamage * level;
    }

    // Method Overloading: Metode dengan nama yang sama tapi parameter berbeda
    // Menunjukkan fleksibilitas skill/serangan
    public int serang(String namaSkill, int manaCost) {
        if (this.mana >= manaCost) {
            this.mana -= manaCost;
            System.out.println(this.nama + " mengeluarkan skill: " + namaSkill + "!");
            return baseDamage * level * 2; // Damage besar
        } else {
            System.out.println("Mana tidak cukup untuk " + namaSkill + "!");
            return 0; // Gagal menyerang
        }
    }

    @Override
    public void bertahan() {
        this.isDefending = true;
        System.out.println(this.nama + " bersiaga! Pertahanan meningkat.");
    }

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

package com.rpg.arena;

/**
 * Kelas abstrak ini menjadi superclass (induk) bagi semua karakter.
 * Mengimplementasikan interface AksiBertarung, namun membiarkan metodenya
 * untuk diimplementasikan secara spesifik oleh subclass-nya.
 * Menerapkan Encapsulation dengan access modifier protected dan setter-getter.
 */
public abstract class Karakter implements AksiBertarung {
    // Encapsulation: Atribut dilindungi dengan protected agar hanya bisa diakses subclass
    protected String nama;
    protected int hp;
    protected int baseDamage;
    protected boolean isDefending;

    public Karakter(String nama, int hp, int baseDamage) {
        this.nama = nama;
        this.hp = hp;
        this.baseDamage = baseDamage;
        this.isDefending = false; // Default false
    }

    // Getter dan Setter untuk menegakkan aturan Encapsulation
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public int getHp() { return hp; }
    public void setHp(int hp) { this.hp = hp; }

    public int getBaseDamage() { return baseDamage; }
    public void setBaseDamage(int baseDamage) { this.baseDamage = baseDamage; }

    public boolean isDefending() { return isDefending; }
    public void setDefending(boolean isDefending) { this.isDefending = isDefending; }

    /**
     * Logika utama penerimaan damage.
     * Jika karakter sedang bertahan, damage berkurang 50%.
     * Memastikan HP tidak turun di bawah 0.
     */
    public void terimaDamage(int damage) {
        if (isDefending) {
            damage = damage / 2;
            isDefending = false; // Status bertahan hilang setelah menerima pukulan
        }
        
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }
    }

    // Abstract method yang wajib didefinisikan ulang (Overriding) oleh subclass
    public abstract void tampilkanStatus();
}

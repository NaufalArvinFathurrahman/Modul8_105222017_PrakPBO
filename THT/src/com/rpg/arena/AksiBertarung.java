package com.rpg.arena;

/**
 * Interface ini mendefinisikan kerangka standar (kontrak) yang harus 
 * diimplementasikan oleh setiap entitas yang bisa bertarung di arena.
 * Ini menunjukkan konsep abstraksi murni dalam PBO.
 */
public interface AksiBertarung {
    int serang();
    void bertahan();
    void gunakanItem();
}

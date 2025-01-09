package me.mathsanalysis.lifesteal.database.adapter;

public interface IDatabase {

    void connect();
    void disconnect();

    void increment();
    void decrement();

    void save(IPlayerData playerData);
}

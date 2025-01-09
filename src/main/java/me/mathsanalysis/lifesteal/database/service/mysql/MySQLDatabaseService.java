package me.mathsanalysis.lifesteal.database.service.mysql;

import com.zaxxer.hikari.HikariConfig;
import me.mathsanalysis.lifesteal.database.adapter.IDatabase;
import me.mathsanalysis.lifesteal.database.adapter.IPlayerData;
import me.mathsanalysis.lifesteal.database.service.mysql.data.MySQLPlayerData;

public class MySQLDatabaseService implements IDatabase {

    @Override
    public void connect() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/lifesteal");
    }

    @Override
    public void disconnect() {

    }

    @Override
    public void increment() {

    }

    @Override
    public void decrement() {

    }

    @Override
    public void save(IPlayerData playerData) {
        if (playerData instanceof MySQLPlayerData mysqlPlayerData){
            mysqlPlayerData.updateProfile();
        }
    }

}

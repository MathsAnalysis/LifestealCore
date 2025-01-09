package me.mathsanalysis.lifesteal.database.core;

import me.mathsanalysis.lifesteal.database.adapter.IDatabase;
import me.mathsanalysis.lifesteal.database.service.mongo.MongoDatabaseService;
import me.mathsanalysis.lifesteal.database.service.mysql.MySQLDatabaseService;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

@Getter
public class DatabaseManager {

    private final List<IDatabase> DATABASES;

    private DatabaseManager() {
        this.DATABASES = Lists.newArrayList();

        registerDatabase();
    }

    private void registerDatabase() {
        DATABASES.add(new MySQLDatabaseService());
        DATABASES.add(new MongoDatabaseService());
    }

}

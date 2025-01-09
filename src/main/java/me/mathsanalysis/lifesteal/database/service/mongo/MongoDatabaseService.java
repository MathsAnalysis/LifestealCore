package me.mathsanalysis.lifesteal.database.service.mongo;

import me.mathsanalysis.lifesteal.database.adapter.IDatabase;
import me.mathsanalysis.lifesteal.database.adapter.IPlayerData;
import me.mathsanalysis.lifesteal.database.service.mongo.data.MongoPlayerData;

public class MongoDatabaseService implements IDatabase {

    @Override
    public void connect() {

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
        if (playerData instanceof MongoPlayerData mongoPlayerData){
            mongoPlayerData.updateProfile();
        }
    }
}


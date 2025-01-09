package me.mathsanalysis.lifesteal;

import com.sun.security.auth.login.ConfigFile;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public class Lifesteal {

    private static Lifesteal instance;

    private BukkitAudiences audiences;

    private JavaPlugin plugin;

    private

    public Lifesteal(JavaPlugin plugin){
        this.plugin = plugin;
    }

    public void init(){
        instance = this;

        registerConfig();
        registerDatabase();
        registerManager();
        registerHook();
        registerListener();
        registerOther();
    }

    public void stop(){


        instance = null;
    }



    private void registerConfig(){

    }

    private void registerDatabase(){

    }

    private void registerManager(){
        this.audiences = BukkitAudiences.create(plugin);

    }

    private void registerHook(){

    }

    private void registerListener(){

    }

    public void registerOther(){

    }


    public static Lifesteal get(){
        return instance;
    }
}

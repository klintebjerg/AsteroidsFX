package io.asteroids.core;

import io.asteroids.common.IEntitySPI;
import io.asteroids.common.ISystemSPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@Configuration(proxyBeanMethods = false)
public class GameConfig {

    @Bean
    public List<ISystemSPI> systemSPIs() {
        List<ISystemSPI> all = new ArrayList<>();
        ServiceLoader.load(ISystemSPI.class).stream()
                .map(ServiceLoader.Provider::get)
                .forEach(all::add);
        all.addAll(PluginLoader.loadSystemSPIs());
        return all;
    }

    @Bean
    public List<IEntitySPI> entitySPIs() {
        List<IEntitySPI> all = new ArrayList<>();
        ServiceLoader.load(IEntitySPI.class).stream()
                .map(ServiceLoader.Provider::get)
                .forEach(all::add);
        all.addAll(PluginLoader.loadEntitySPIs());
        return all;
    }

    @Bean
    public Game game(List<ISystemSPI> systemSPIs, List<IEntitySPI> entitySPIs) {
        return new Game(systemSPIs, entitySPIs);
    }
}

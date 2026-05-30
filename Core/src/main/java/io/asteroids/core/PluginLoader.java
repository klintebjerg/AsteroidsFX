package io.asteroids.core;

import io.asteroids.common.IEntitySPI;
import io.asteroids.common.ISystemSPI;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

public class PluginLoader {

    private static final Path PLUGINS_DIR = Path.of("plugins");

    public static List<ISystemSPI> loadSystemSPIs() {
        return load(ISystemSPI.class);
    }

    public static List<IEntitySPI> loadEntitySPIs() {
        return load(IEntitySPI.class);
    }

    private static <S> List<S> load(Class<S> serviceType) {
        if (!Files.exists(PLUGINS_DIR)) return List.of();

        try {
            Set<String> moduleNames = findModuleNames();
            if (moduleNames.isEmpty()) return List.of();

            ModuleFinder finder = ModuleFinder.of(PLUGINS_DIR);
            ModuleLayer parent  = ModuleLayer.boot();
            Configuration cf    = parent.configuration().resolve(
                    finder, ModuleFinder.of(), moduleNames);
            ModuleLayer pluginLayer = parent.defineModulesWithOneLoader(
                    cf, ClassLoader.getSystemClassLoader());

            Set<Module> pluginModules = pluginLayer.modules();
            return ServiceLoader.load(pluginLayer, serviceType)
                    .stream()
                    .filter(p -> pluginModules.contains(p.type().getModule()))
                    .map(ServiceLoader.Provider::get)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("[PluginLoader] Failed to load plugins: " + e.getMessage());
            return List.of();
        }
    }

    private static Set<String> findModuleNames() throws IOException {
        try (var paths = Files.list(PLUGINS_DIR)) {
            return paths
                    .filter(p -> p.toString().endsWith(".jar"))
                    .flatMap(jar -> ModuleFinder.of(jar).findAll().stream())
                    .map(m -> m.descriptor().name())
                    .collect(Collectors.toSet());
        }
    }
}

package dk.sdu.cbse.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static Map<Class<?>, Collection<Object>> services = new HashMap<>();
    public static <T> void addService(Class<T> type, T service) {
        services.computeIfAbsent(type, k -> new ArrayList<>()).add(service);
    }

    public static <T> Collection<T> getServices(Class<T> type) {
        return (Collection<T>) services.get(type);
    }
}

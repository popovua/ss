package storage;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class Storage {

    private static Storage instance;
    private static Map<Context, Map<String, Optional<?>>> storage;
    private static final Context DEFAULT_CONTEXT = Context.SUITE;
    private static Context actualContext = DEFAULT_CONTEXT;

    private Storage() {
        storage = new HashMap<>();
        storage.put(Context.SUITE, new HashMap<>());
        storage.put(Context.CLASS, new HashMap<>());
        storage.put(Context.TEST, new HashMap<>());
        storage.put(Context.CUSTOM, new HashMap<>());
    }

    private static Map<String, Optional<?>> getActualStorage() {
        return storage.get(actualContext);
    }

    private static boolean containsKey(String key) {
        Map<String, Optional<?>> actualStorage = getActualStorage();
        if (!actualStorage.containsKey(key)) {
            log.trace("Key '{}' is not used in Storage", key);
            return false;
        }
        return true;
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public static void rememberThe(String key, Object value) {
        log.trace("Put into memory: key '{}', value '{}', storageContext '{}'", key, value, actualContext.name());
        Map<String, Optional<?>> actualStorage = getActualStorage();
        actualStorage.put(key, Optional.ofNullable(value));
    }

    public static String whatIsThe(String key) {
        Map<String, Optional<?>> actualStorage = getActualStorage();
        if (containsKey(key)) {
            return actualStorage.get(key).map(Object::toString).orElseThrow(() -> new StorageException("Avoid getting null from Storage"));
        }
        throw new StorageException("Key '" + key + "' not found");
    }

    public static <T> T whatIsTheObject(String key, Class<T> tClass) {
        Map<String, Optional<?>> actualStorage = getActualStorage();
        if (containsKey(key)) {
            return actualStorage.get(key).map(tClass::cast).orElseThrow(() -> new StorageException("Avoid getting null from Storage"));
        }
        throw new StorageException("Key '" + key + "' not found");
    }

    public static void resetContext() {
        actualContext = DEFAULT_CONTEXT;
    }

    public static void forgetThe(String key) {
        log.trace("Remove from memory: key '{}'", key);
        if (containsKey(key)) {
            getActualStorage().put(key, Optional.empty());
        }
    }

    public static void clearContext() {
        getActualStorage().clear();
    }

    public static void setContext(Context context) {
        actualContext = context;
    }

    public static Context getActualContext() {
        return actualContext;
    }

    public static Context getDefaultContext() {
        return DEFAULT_CONTEXT;
    }
}
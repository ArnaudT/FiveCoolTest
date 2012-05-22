package database.java;

import database.StorageException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DummyDBJava is the java implementation of our DataStore
 *
 * @author Arnaud Tanguy <arnaud@fivecool.net>
 *         Date: 22/05/2012
 */
public enum DummyDBJava {
    INSTANCE;
    private final Map<String, Object> db = new ConcurrentHashMap<String, Object>();

    /**
     * Put the string value of a key
     *
     * @param key
     * @param value
     * @throws StorageException
     */
    public void put(String key, String value) throws StorageException {
        double test = Math.random() * (15 - 0);
        if (test < 2)
            throw new StorageException("Simulated store failure " + value);
        db.put(key, value);
    }

    /**
     * Get the value of a key
     *
     * @param key
     * @return
     * @throws StorageException
     */
    public String get(String key) throws StorageException {
        double test = Math.random() * (15 - 0);
        if (test < 1)
            throw new StorageException("Simulated store failure " + key);
        synchronized (this) {
            if (db.containsKey(key))
                return db.get(key).toString();
            else
                return null;
        }
    }

    /**
     * Increment the integer value of a key by the given amount
     *
     * @param key
     * @param amount
     * @return
     * @throws StorageException
     */
    public Long increment(String key, Long amount) throws StorageException {
        double test = Math.random() * (15 - 0);
        if (test < 1)
            throw new StorageException("Simulated store failure " + amount);
        synchronized (this) {
            if (db.containsKey(key)) {
                Object oldValue = db.get(key);
                if (oldValue instanceof Long) {
                    Long newValue = amount + (Long) oldValue;
                    db.put(key, newValue);
                    return newValue;
                } else {
                    throw new StorageException("value of key " + key + " is not a counter");
                }
            } else {
                db.put(key, amount);
                return amount;
            }
        }
    }
}

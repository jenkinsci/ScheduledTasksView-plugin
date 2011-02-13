/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hudson.plugins.scheduled_tasks_view;

/**
 * KeyValue class. Used to store values corresponding to a key, without checking
 * for other KeyValue objects using repeated keys.
 * @author andre
 */
public class KeyValue<K, V> implements KeyValuePairable<K, V> {
    private K key;
    private V value;

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Return the key
     * @return key
     */
    public K getKey() {
        return key;
    }

    /**
     * Return the value
     * @return value
     */
    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object instanceof KeyValue<?, ?>) {
            KeyValue<?, ?> kv = (KeyValue<?, ?>)object;
            return (key == null ? kv.getKey() == null : key.equals(kv.getKey())) &&
                   (value == null ? kv.getValue() == null : value.equals(kv.getValue()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) ^
               (value == null ? 0 : value.hashCode());
    }
}

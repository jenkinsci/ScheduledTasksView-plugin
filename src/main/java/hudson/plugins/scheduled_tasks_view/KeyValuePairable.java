/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hudson.plugins.scheduled_tasks_view;

/**
 * Key/value interface
 * @author andre
 */
public interface KeyValuePairable<K, V> {
    K getKey();
    V getValue();
}

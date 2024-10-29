package assn07;

import java.util.*;
import java.lang.Math;


public class PasswordManager<K, V> implements Map<K, V> {
    private static final String MASTER_PASSWORD = "password321";
    private Account[] _passwords;

    public PasswordManager() {
        _passwords = new Account[50];
    }

    // TODO: put
    @Override
    public void put(K key, V value) {

        if (_passwords[getIndex(key)] == null) {
            // if no chain started, this can be the first
            _passwords[getIndex(key)] = new Account(key, value);
        } else {
            Account<K, V> node = _passwords[getIndex(key)];
            // find last node
            while (node.getNext() != null) {
                if (node.getWebsite().equals(key)) {
                    node.setPassword(value);
                    return;
                }
                node = node.getNext();
            }
            if (node.getWebsite().equals(key)) {
                node.setPassword(value);
                return;
            }
            // then make last node point to added node (so added is last)
            node.setNext(new Account<K, V>(key, value));
        }
    }
    @Override
    public V get(K key) {
        // returns the value associated with the given key.
        //  If the key is not in the array, return null.

        // we know the index of the key
        int index = getIndex(key);

        // now check if the index is null

        if (_passwords[index] != null) {
            Account<K, V> node = _passwords[getIndex(key)];
            // going through the linked nodes
            while (node != null) {
                if (node.getWebsite().equals(key)) {
                    return node.getPassword();
                } else {
                    node = node.getNext();
                }
            }
        }
        return null;
    }
    @Override
    public int size() {
        int full = 0;
        for (int i = 0; i < _passwords.length; i++) {
            if (_passwords[i] != null) {
                Account node = _passwords[i];
                while (node != null) {
                    full++;
                    node = node.getNext();
                }
            }
        }
        return full;
    }

    @Override
    public Set<K> keySet() {
        // returns a Set of all the keys (websites) contained in this map.
        Set allKeys = new HashSet(); // error here lol
        for (int i = 0; i < _passwords.length; i++) {
            if (_passwords[i] != null) {
                Account node = _passwords[i];
                while (node != null) {
                    allKeys.add(node.getWebsite());
                    node = node.getNext();
                }
            }
        }
        return allKeys;
    }

    @Override
    public V remove(K key) {
        // 'remove' removes the Key and value pair from the map and returns the removed value.
        // If the key is not in the array, return null.
        int i = getIndex(key);
        V pass = null;
        if (_passwords[i] != null) {
            Account<K, V> node = _passwords[i];
            Account<K, V> before = _passwords[i];

            if (node.getWebsite().equals(key)) {
                pass = (V) node.getPassword();
                _passwords[i] = node.getNext();
                return pass;
            }
            // now i'm set up that node is being checked, and 'before' holds the node before it
            node = node.getNext();
            while (node != null) {
                if (node.getWebsite().equals(key)) {
                    pass = (V) node.getPassword();
                    // if i found the key, then set the before's next to the node's next (removes node from chain)
                    before.setNext(node.getNext());
                    return pass;
                }
                if (node.getNext() != null) {
                    before = node;
                    node = node.getNext();
                } else {
                    break;
                }
            }
        }
        return null;
    }
    @Override
    public List<K> checkDuplicate(V value) {
        // returns an Arraylist of the website names
        // that have a password matching the given value
        ArrayList<K> websites = new ArrayList<K>();
        for (int i = 0; i < _passwords.length; i++) {
            if (_passwords[i] != null) {
                Account<K, V> node = _passwords[i];
                while (node != null) {
                    if (node.getPassword().equals(value)) {
                        websites.add(node.getWebsite());
                    }
                    node = node.getNext();
                }
            }
        }
        return websites;
    }

    @Override
    public boolean checkMasterPassword(String enteredPassword) {

        if (enteredPassword.equals(MASTER_PASSWORD)) {
            return true;
        } else {
            return false;
        }
    }

    public int getIndex(K key) {

        int hashVal = Math.abs(key.hashCode()) % 50;
        return hashVal;
    }

    @Override
    public String generatesafeRandomPassword(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        if (targetStringLength < 4) {
            targetStringLength = 4;
        }

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    // Used for testing
    public Account[] getPasswords() {
        return _passwords;
    }
}
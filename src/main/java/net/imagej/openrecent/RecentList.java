package net.imagej.openrecent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Recently opened list with no duplicates.
 * When you add an item, it moves to the front. 
 * If capacity is exceeded the oldest entry is dropped.
 */
public class RecentList {

    private final int maxSize = 10;
    /* Ordered list */
    private final Deque<String> deque = new LinkedList<String>();
    /* Hashset to check presence of the file already */
    private final Set<String> set = new HashSet<String>();

    private static final File RECENT_FILE = new File(System.getProperty("user.home"), ".imagej2/open-recent.txt");

    /* maintain single list acroos application */
    public static final RecentList instance = new RecentList();

    /**  
     * private constructor, so that only one instance maintained through getInstance
     */
    private RecentList() {
        loadRecents();
    }

    public static RecentList getInstance() {
        return instance;
    }

    /*
     * Load the recent list from a file.
     */
    private void loadRecents() {
        File dir = RECENT_FILE.getParentFile();
        if (!dir.exists()) dir.mkdirs();
        if (!RECENT_FILE.isFile()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(RECENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                add(line);
            }
        } catch (IOException e) {
            // ignore errors
        }
    }

    /*
     * save the recent list to a file.
     */
    private void saveRecents() {
        File dir = RECENT_FILE.getParentFile();
        if (!dir.exists()) dir.mkdirs();
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(RECENT_FILE)))) {
            for (String path : getItems()) {
                pw.println(path);
            }
        } catch (IOException e) {
            // ignore errors
        }
    }

    /**
     * Add an item to the list.
     * If it already existed, it’s removed then re‑added at front.
     * If it’s new and capacity is full, the oldest element is evicted.
     */
    public void add(String item) {
        // If already present, remove it so we can re‑insert at front
        if (set.contains(item)) {
            deque.remove(item);
        } else if (deque.size() >= maxSize) {
            // Evict oldest
            String last = deque.removeLast();
            set.remove(last);
        }
        // Add to front
        deque.addFirst(item);
        set.add(item);
        saveRecents();
    }

    /**
     * return a copied list most recent first
     */
    public List<String> getItems() {
        return new ArrayList<String>(deque);
    }

    public  void clear() {
        deque.clear();
        set.clear();
    }

    
}
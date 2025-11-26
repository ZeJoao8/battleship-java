package battleship.model;

import java.util.Arrays;
import java.util.List;

public class Menu {
    private final List<String> entries;
    private int current = 0;

    public Menu() { this.entries = Arrays.asList("Play", "Settings", "Quit"); }

    public String getEntry(int i) { return entries.get(i); }
    public int getNumberEntries() { return entries.size(); }
    public boolean isSelected(int i) { return current == i; }

    public void next() { current = (current + 1) % entries.size(); }
    public void previous() { current = (current - 1 + entries.size()) % entries.size(); }

    public boolean isSelectedPlay() { return current == 0; }
    public boolean isSelectedSettings() { return current == 1; }
    public boolean isSelectedQuit() { return current == 2; }
}

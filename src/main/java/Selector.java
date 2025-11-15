public class Selector {
    private int selectedIndex;
    private final int size;

    public Selector(int size) {
        this.size = size;
        this.selectedIndex = 0;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void previous() {
        selectedIndex = (selectedIndex - 1 + size) % size;
    }

    public void next() {
        selectedIndex = (selectedIndex + 1) % size;
    }

    public void setIndex(int index) {
        if(index >= 0 && index < size) {
            selectedIndex = index;
        }
    }
}
package moises.com.usersapp.tools;

import moises.com.usersapp.R;

public enum LayoutType {
    GRID(R.drawable.svg_view_list),
    LIST(R.drawable.svg_view_grid);

    private final int icon;

    LayoutType(int icon){
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }
}

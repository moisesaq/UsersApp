package moises.com.usersapp.tools;

import moises.com.usersapp.R;

public enum LayoutManagerType {
    GRID_LAYOUT_MANAGER(R.drawable.svg_view_list),
    LINEAR_LAYOUT_MANAGER(R.drawable.svg_view_grid);

    private final int icon;

    LayoutManagerType(int icon){
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }
}

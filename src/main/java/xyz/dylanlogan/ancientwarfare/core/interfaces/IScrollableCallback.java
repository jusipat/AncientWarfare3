package xyz.dylanlogan.ancientwarfare.core.interfaces;

/**
 * GUI's / composite elements should implement this and register with
 * a scroll-bar to be informed when the scrolled view changes
 *
 * 
 */
public interface IScrollableCallback {

    public void onScrolled(int newTop);

}

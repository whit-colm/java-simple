package io.whits.javadev.simple;

public interface MenuOption {
    public String getName();
    public String getDescription();
    public String[] getFlags();
    public void run();
}

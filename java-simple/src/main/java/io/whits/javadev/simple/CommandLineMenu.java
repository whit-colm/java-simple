package io.whits.javadev.simple;

import java.util.ArrayList;

public class CommandLineMenu implements MenuOption {
    private String name;
    private String description;
    private String infoText;
    //private String[] flags;
    private ArrayList<MenuOption> options;

    /**
     * <p><strong></strong></p>
     * @param n the name of the CLM, used when being called from another CLM
     * @param d the description of the CLM, used when being called from another CLM
     * @param i The text to be displayed on the menu.
     * @param f CLI flags, used if CLI flag parsing is implemented.
     * @param o The MenuOptions this CLM can run
     */
    public CommandLineMenu(String n, String d, String i, String[] f, ArrayList<MenuOption> o) {
        this.name = n;
        this.description = d;
        //this.flags = f;
        this.infoText = i;
        this.options = o;
    }

    public void run() {
        while (true) {
            /*
            * TODO: Implement some way of dynamically being able to skip menus if
            * a valid jcommander tag is passed.
            */
            System.out.printf("%s\n", this.description);
            System.out.printf("%s\n\n", this.infoText);
            for (int k = 0; k < this.options.size(); k++) {
                System.out.printf("[%d]\t%s - %s\n", 
                    k+1, 
                    this.options.get(k).getName(),
                    this.options.get(k).getDescription());
            }
            System.out.printf("[0]\tLeave this menu");
            // This feels like a bad practice, but I'm too rushed to think of a better way
            int selection = StaticSmartScanner.smartForceNextInt(
                "Select an option", 
                0, 
                this.options.size());
            
            // 0 is always 'back/exit', menus should be able to be returned to.
            if (selection == 0) {
                return;
            }
            this.options.get(selection-1).run();
        }

    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getInfoText() {
        return this.infoText;
    }
    
    /**
     * Currently returns a useless value. Will be updated once I care enough
     * to figure out jcommander
     * @return a 0 length string array.
     */
    public String[] getFlags() {
        return new String[0];
    }
}

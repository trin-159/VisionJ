package net.imagej.openrecent;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.ui.UIService;
import org.scijava.plugin.Plugin;

import java.net.URL;

/*
 * Add Open Recent menu and load it from disk, if any exist from previous sessions
 */
@Plugin(type = Command.class, menuPath = "File>Open Recent", headless = false)
public class OpenRecentCommand implements Command {

    @Parameter
    private UIService uiService;

        @Parameter(initializer = "initialize")
    private RecentList recentList;

    /* get recent list from file and add to the menu */
 

    @Override
    public void run() {
        // uiService.showDialog("No recent files available.");
    }

}
package net.imagej.openrecent;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.ui.UIService;
import org.scijava.plugin.Plugin;

import java.net.URL;

/*
 * Add Open Recent menu and load it from disk, if any exist from previous sessions
 */
@Plugin(type = Command.class, menuPath = "File>Open Recent", headless = false, initializer = "initialize")
public class OpenRecentCommand implements Command {

    @Parameter
    private UIService uiService;

        @Parameter(initializer = "initialize")
    private RecentList recentList;

    /* get recent list from file and add to the menu */
    public RecentList initialize() {
        recentList = RecentList.getInstance();
        URL iconURL = getClass().getResource("/icons/puzzle-22.png");
        RecentMenuUpdater.updateRecentMenu(uiService, recentList, iconURL);
        return recentList;
    }

    @Override
    public void run() {
        // uiService.showDialog("No recent files available.");
    }

}
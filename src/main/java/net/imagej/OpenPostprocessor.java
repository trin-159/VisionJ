package net.imagej;

import org.scijava.module.Module;
import org.scijava.module.process.AbstractPostprocessorPlugin;
import org.scijava.module.process.PostprocessorPlugin;
import org.scijava.plugin.Plugin;

import org.scijava.plugin.Parameter;
import org.scijava.ui.UIService;

import java.net.URL;

/*
 * Called as Post processing for any plugin
 * This capture Open call, and adds the opened file to RecentList
 */
@Plugin(type = PostprocessorPlugin.class)
public class OpenPostprocessor extends AbstractPostprocessorPlugin {

  @Parameter
  private UIService uiService;

  /* Single instance of RecentList is used everywhere to keep in sync */
  private static RecentList recentList = RecentList.getInstance();

  /*
   * Mehod called after plugin is processed
   */
  @Override
  public void process(Module module) {

    /* check if the plugin is open -> "Select Path" tells that */
    String label = module.getInfo().getLabel();
    if (label == null || !label.equals("Select path")) {
      return;
    }
    
    /* "path" input has file opened in a list */
    Object input = module.getInput("path");

    /* 
     *convert Object list into string list and get first one,currently opened file
     * And add to RecentList
     */
    if (input instanceof String[]) {
      String[] inputList = (String[]) input;
      if (inputList.length > 0) {
        String fileName = inputList[0];
        recentList.add(fileName);
      }
    }

    URL iconURL = getClass().getResource("/icons/puzzle-22.png");

    /* update the menu with new list */
    RecentMenuUpdater.updateRecentMenu(uiService, recentList, iconURL);

  }

}
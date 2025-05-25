package net.imagej;

import java.io.File;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import org.scijava.ui.UIService;
import org.scijava.ui.UserInterface;

import ij.IJ;
import ij.ImagePlus;

/*
 * Utility to update Open Recent menu with recent list
 * uiService = scijava UI service
 * recentList = list with recently accessed files, most recent first
 * iconURL = icon to use in menu
 */

public class RecentMenuUpdater {

    public static void updateRecentMenu(UIService uiService, RecentList recentList, URL iconURL) {
    
        SwingUtilities.invokeLater(() -> {
        UserInterface ui = uiService.getDefaultUI();
        org.scijava.ui.ApplicationFrame appFrame = ui.getApplicationFrame();
        JFrame frame = (JFrame) appFrame;
        JMenuBar mb = frame.getJMenuBar();
        JMenu fileMenu = findMenu(mb, "File");

        /* remove existing menu and add new one */
        removeExistingSubmenu(fileMenu, "Open Recent");

        JMenu openRecent = new JMenu("Open Recent");

        for (String filePath : recentList.getItems()) {
            JMenuItem recentFileItem = new JMenuItem(new File(filePath).getName());
            if (iconURL != null)
              recentFileItem.setIcon(new javax.swing.ImageIcon(iconURL));
            recentFileItem.addActionListener(e -> {
              // using IJ to openImage
            ImagePlus imp = IJ.openImage(filePath);
            if (imp != null) imp.show();
            });
            openRecent.add(recentFileItem);
        }
        fileMenu.add(openRecent);
        mb.revalidate();
        mb.repaint();
        
        });
    }
    private static JMenu findMenu(JMenuBar mb, String title) {
        for (int i = 0; i < mb.getMenuCount(); i++) {
          JMenu m = mb.getMenu(i);
          if (m != null && title.equals(m.getText())) return m;
        }
        return null;
      }
    
      private static void removeExistingSubmenu(JMenu parent, String text) {
        for (java.awt.Component c : parent.getMenuComponents()) {
          if (c instanceof JMenu && text.equals(((JMenu) c).getText())) {
            parent.remove(c);
            return;
          }
          if (c instanceof JMenuItem && text.equals(((JMenuItem) c).getText())) {
            parent.remove(c);
            return;
          }
        }
      }
    
}
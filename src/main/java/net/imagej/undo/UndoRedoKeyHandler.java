package net.imagej.undo;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

// Handles keyboard shortcuts for undo and redo operations.
@Plugin(type = UndoRedoService.class)
public class UndoRedoKeyHandler {
    @Parameter
    private UndoRedoService undoRedoService;

    public UndoRedoKeyHandler() {
        // Add undo shortcut (Command+Shift+[)
        undoRedoService.registerCommand(91, 262144 | 64, "undo", () -> { 
            if (undoRedoService.canUndo()) {
                undoRedoService.undo();
            }
        });

        // Add redo shortcut (Command+Shift+])
        undoRedoService.registerCommand(93, 262144 | 64, "redo", () -> {
            if (undoRedoService.canRedo()) {
                undoRedoService.redo();
            }
        });
    }

    public void set(final UndoRedoService value) {
        this.undoRedoService = value;
    }

    public UndoRedoService get() {
        return undoRedoService;
    }

    // public void showLabel(final boolean show) {
    //    
    // }

    // public void setEnabled(final boolean enabled) {
    //     
    // }

    // public void setToolTipText(final String text) {
    //     
    // }
}

package net.imagej.undo;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

/**
 * Handles keyboard shortcuts for undo and redo operations.
 *
 * @author Trin
 */
@Plugin(type = UndoRedoService.class)
public class UndoRedoKeyHandler {
    @Parameter
    private UndoRedoService undoRedoService;

    public UndoRedoKeyHandler() {
        // Add undo shortcut (Command-;)
        undoRedoService.registerCommand(59, 262144, "undo", () -> {
            if (undoRedoService.canUndo()) {
                undoRedoService.undo();
            }
        });

        // Add redo shortcut (Command-')
        undoRedoService.registerCommand(39, 262144, "redo", () -> {
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

    public void showLabel(final boolean show) {
        // No-op for this implementation
    }

    public void setEnabled(final boolean enabled) {
        // No-op for this implementation
    }

    public void setToolTipText(final String text) {
        // No-op for this implementation
    }
}

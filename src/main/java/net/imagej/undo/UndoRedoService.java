package net.imagej.undo;

import java.util.ArrayList;
import java.util.List;
import org.scijava.Context;
import org.scijava.service.Service;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.PluginInfo;

/**
 * Service for managing undo/redo operations in ImageJ.
 *
 * @author Your Name
 */
@Plugin(type = Service.class)
public class UndoRedoService implements Service {
    private final CommandStack commandStack = new CommandStack();
    private boolean isRecording = true;
    private Context context;
    private PluginInfo<?> pluginInfo;

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Context context() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void setPriority(double priority) {
        // No-op since we don't need to prioritize this service
    }

    @Override
    public double getPriority() {
        // Return default priority
        return 0.0;
    }

    @Override
    public void setInfo(PluginInfo<?> info) {
        this.pluginInfo = info;
    }

    @Override
    public PluginInfo<?> getInfo() {
        return pluginInfo;
    }

    /**
     * Adds a command to the undo stack.
     */
    public void addCommand(Command command) {
        if (!isRecording) return;
        commandStack.addCommand(command);
    }

    /**
     * Undoes the last command.
     */
    public void undo() {
        if (commandStack.canUndo()) {
            commandStack.undo();
        }
    }

    /**
     * Redoes the last undone command.
     */
    public void redo() {
        if (commandStack.canRedo()) {
            commandStack.redo();
        }
    }

    /**
     * Checks if there are commands that can be undone.
     */
    public boolean canUndo() {
        return commandStack.canUndo();
    }

    /**
     * Checks if there are commands that can be redone.
     */
    public boolean canRedo() {
        return commandStack.canRedo();
    }

    /**
     * Starts recording commands.
     */
    public void startRecording() {
        isRecording = true;
    }

    /**
     * Stops recording commands.
     */
    public void stopRecording() {
        isRecording = false;
    }

    /**
     * Clears the undo/redo stack.
     */
    public void clear() {
        commandStack.clear();
    }

    /**
     * Command interface for undo/redo operations.
     */
    public interface Command {
        void execute();
        void undo();
        void redo();
    }

    /**
     * Stack implementation for managing commands.
     */
    private static class CommandStack {
        private final List<Command> commands = new ArrayList<>();
        private int undoIndex = -1;
        private int redoIndex = -1;

        public void addCommand(Command command) {
            commands.add(command);
            command.execute();
            redoIndex = -1; // Clear redo stack when new command is added
            undoIndex = commands.size() - 1;
        }

        public void undo() {
            if (undoIndex >= 0) {
                commands.get(undoIndex).undo();
                redoIndex = undoIndex;
                undoIndex--;
            }
        }

        public void redo() {
            if (redoIndex >= 0 && redoIndex < commands.size()) {
                commands.get(redoIndex).redo();
                undoIndex = redoIndex;
                redoIndex++;
            }
        }

        public boolean canUndo() {
            return undoIndex >= 0;
        }

        public boolean canRedo() {
            return redoIndex >= 0 && redoIndex < commands.size();
        }

        public void clear() {
            commands.clear();
            undoIndex = -1;
            redoIndex = -1;
        }
    }
}

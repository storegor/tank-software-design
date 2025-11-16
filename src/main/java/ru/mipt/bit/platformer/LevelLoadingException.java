package ru.mipt.bit.platformer;

public class LevelLoadingException extends RuntimeException {
    private final String filePath;
    private final int line;
    private final int column;

    public LevelLoadingException(String message, String filePath, int line, int column) {
        super(message + " at " + filePath + ":" + (line + 1) + ":" + (column + 1));
        this.filePath = filePath;
        this.line = line;
        this.column = column;
    }

    public LevelLoadingException(String message, String filePath) {
        super(message + " at " + filePath);
        this.filePath = filePath;
        this.line = -1;
        this.column = -1;
    }

    public LevelLoadingException(String message, Throwable cause, String filePath) {
        super(message + " at " + filePath, cause);
        this.filePath = filePath;
        this.line = -1;
        this.column = -1;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}

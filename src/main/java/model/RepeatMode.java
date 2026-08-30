package model;

public enum RepeatMode {
    OFF,
    REPEAT_ONE,
    REPEAT_ALL;

    public String getDisplayName() {
        switch (this) {
            case OFF:
                return "Khong lap";
            case REPEAT_ONE:
                return "Lap 1 bai";
            case REPEAT_ALL:
                return "Lap ca playlist";
            default:
                return "";
        }
    }
}
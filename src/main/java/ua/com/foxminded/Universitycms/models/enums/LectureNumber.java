package ua.com.foxminded.Universitycms.models.enums;

public enum LectureNumber {
    FIRST("08:00 - 09:20"),
    SECOND("09:30 - 10:50"),
    THIRD("11:00 - 12:20"),
    FOURTH("12:30 - 13:50"),
    FIFTH("14:00 - 15:20"),
    SIXTH("15:30 - 16:50"),
    SEVENTH("17:00 - 18:20"),
    EIGHTH("18:30 - 19:50"),
    NINTH("20:00 - 21:20");

    public static final String LECTURE_TIME = " 1h 20m";
    private final String time;

    LectureNumber(String time) {
        this.time = time;
    }

    public String getTime() {
        return time + LECTURE_TIME;
    }
}
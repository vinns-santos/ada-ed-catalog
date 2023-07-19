package tech.ada.ed.project.catalog.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum GenreEnum {
    ACTION("action"),
    ANIMATION("animation"),
    COMEDY("comedy"),
    CRIME("crime"),
    DOCUMENTATION("documentation"),
    DRAMA("drama"),
    EUROPEAN("european"),
    FAMILY("family"),
    FANTASY("fantasy"),
    HISTORY("history"),
    HORROR("horror"),
    MUSIC("music"),
    REALITY("reality"),
    ROMANCE("romance"),
    SCIFI("scifi"),
    SPORT("sport"),
    THRILLER("thriller"),
    WAR("war"),
    WESTERN("western"),
    UNDEFINED("");

    private static final Map<String, GenreEnum> BY_LABEL = new HashMap<>();

    static {
        for (GenreEnum e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }
    private String label;

    GenreEnum(String label) {
        this.label = label;
    }

    public static GenreEnum valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}
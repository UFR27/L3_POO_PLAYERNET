package fr.pantheonsorbonne.miage.model;

import java.util.Collections;
import java.util.Map;

public record GameCommand(String name, String body, Map<String, String> params) {
    public GameCommand(String name, String body) {
        this(name, body, Collections.EMPTY_MAP);
    }

    public GameCommand(String name) {
        this(name, "", Collections.EMPTY_MAP);
    }
}

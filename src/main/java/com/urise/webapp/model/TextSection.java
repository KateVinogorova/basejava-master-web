package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    public static final TextSection EMPTY = new TextSection("");

    private String sectionData;

    public TextSection() {
    }

    public TextSection(String description) {
        Objects.requireNonNull(description, "description must not be null");
        this.sectionData = description;
    }

    public String getSectionData() {
        return sectionData;
    }

    @Override
    public String toString() {
        return sectionData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return sectionData.equals(that.sectionData);
    }

    @Override
    public int hashCode() {
        return sectionData.hashCode();
    }
}

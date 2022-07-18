package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    public static final ListSection EMPTY = new ListSection("");

    private final List<String> sectionData;

    public ListSection() {
        sectionData = new ArrayList<>();
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> sectionData) {
        Objects.requireNonNull(sectionData, "sectionData must not be null");
        this.sectionData = sectionData;
    }

    public void addListItem(String listItem) {
        sectionData.add(listItem);
    }

    public List<String> getSectionData() {
        return sectionData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : sectionData) {
            sb.append("<li class=\"listSectionPoints\">").append(s).append("</li>");
        }
        return "<ul class=\"listSection\">" + sb + "</ul>";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;
        return sectionData.equals(that.sectionData);
    }

    @Override
    public int hashCode() {
        return sectionData.hashCode();
    }
}

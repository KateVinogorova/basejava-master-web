package com.urise.webapp.model;

import java.io.Serializable;

public enum ContactType implements Serializable {
    PHONE("Тел."),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Skype") {
        @Override
        protected String valueToLink(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    MAIL("Почта") {
        @Override
        protected String valueToLink(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        protected String valueToLink(String value) {
            return "<a href='linkedin:" + value + "'>" + value + "</a>";
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        protected String valueToLink(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow") {
        @Override
        protected String valueToLink(String value) {
            return "<a href='stackoverflow:" + value + "'>" + value + "</a>";
        }
    },
    HOME_PAGE("Домашняя страница") {
        @Override
        protected String valueToLink(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle(ContactType type) {
        return type.title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return title + ": " + valueToLink(value);
    }

    protected String valueToLink(String value) {
        return value;
    }
}

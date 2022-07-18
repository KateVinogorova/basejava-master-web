package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<Organization> sectionData;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations);
        this.sectionData = organizations;
    }

    public List<Organization> getSectionData() {
        return sectionData;
    }

    public void setOrganisation(Organization organization) {
        sectionData.add(organization);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;
        return sectionData.equals(that.sectionData);
    }

    @Override
    public int hashCode() {
        return sectionData.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Organization o : sectionData) {
            sb.append("<li class=\"organizationSectionPoints\">").append(o.toString()).append("</li>");
        }
        return "<ul class=\"organizationSection\">" + sb + "</ul>";
    }
}

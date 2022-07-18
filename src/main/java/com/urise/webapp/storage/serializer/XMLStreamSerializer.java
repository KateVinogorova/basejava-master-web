package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.XMLParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XMLStreamSerializer implements StreamSerializer {
    private final XMLParser xmlParser;

    public XMLStreamSerializer() {
        xmlParser = new XMLParser(Resume.class,
                Organization.class,
                Organization.Position.class,
                Link.class,
                TextSection.class,
                ListSection.class,
                OrganizationSection.class);
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream)) {
            xmlParser.marchall(resume, writer);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);) {
            return xmlParser.unmarshall(reader);
        }
    }
}

package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeUTF(resume.getUuid());//write uuid
            dataOutputStream.writeUTF(resume.getFullName()); // write fullName
            Map<ContactType, String> contacts = resume.getContacts();
            dataOutputStream.writeInt(contacts.size()); // write number of contacts
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) { // write key and value of each contact
                dataOutputStream.writeUTF(String.valueOf(entry.getKey()));
                dataOutputStream.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dataOutputStream.writeInt(sections.size());// write number of sections
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                dataOutputStream.writeUTF(String.valueOf(sectionType));//write section type
                AbstractSection section = entry.getValue();
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE://for text sections
                        dataOutputStream.writeUTF(((TextSection) section).getSectionData());//write text sections
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS://for list sections
                        List<String> sectionData = ((ListSection) section).getSectionData();
                        dataOutputStream.writeInt(sectionData.size());//write number of items in list section
                        for (String content : sectionData) {
                            dataOutputStream.writeUTF(content);//write each item of list section
                        }
                        break;
                    case EDUCATION:
                    case EXPERIENCE://for organization sections
                        List<Organization> organizations = ((OrganizationSection) section).getSectionData();
                        dataOutputStream.writeInt(organizations.size());//write number of organisations in section
                        for (Organization o : organizations) {
                            Link link = o.getLink();
                            dataOutputStream.writeUTF(link.getName());//write link name
                            dataOutputStream.writeUTF(link.getUrl() + "");//write url
                            List<Organization.Position> positions = o.getPositionList();
                            dataOutputStream.writeInt(positions.size());//write number of positions of organisation
                            for (Organization.Position position : positions) {
                                writeDate(dataOutputStream, position.getStartDate());//write start date
                                writeDate(dataOutputStream, position.getEndDate());//write end date
                                dataOutputStream.writeUTF(position.getTitle());//write title
                                dataOutputStream.writeUTF(position.getDescription() + "");//write description
                            }
                        }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dataInputStream.readUTF(), dataInputStream.readUTF());// read uuid and fullName
            int size = dataInputStream.readInt();//read number of contacts
            for (int i = 0; i < size; i++) {// read key and value of each contact
                resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
            }
            int numberOfSections = dataInputStream.readInt();// read number of sections
            for (int i = 0; i < numberOfSections; i++) {
                SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());//read section type
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE://for text sections
                        resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));//read text sections
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS://for list sections
                        int sectionDataSize = dataInputStream.readInt();//read number of items in list section
                        List<String> sectionData = new ArrayList<>(sectionDataSize);
                        for (int j = 0; j < sectionDataSize; j++) {
                            sectionData.add(dataInputStream.readUTF());//read each item of list section
                        }
                        resume.addSection(sectionType, new ListSection(sectionData));
                        break;
                    case EDUCATION:
                    case EXPERIENCE://for organization sections
                        int numOfOrganizations = dataInputStream.readInt();//read number of organisations in section
                        List<Organization> organizations = new ArrayList<>(numOfOrganizations);
                        for (int j = 0; j < numOfOrganizations; j++) {
                            Link link = new Link(dataInputStream.readUTF(), dataInputStream.readUTF());//read link name and url
                            int positionListSize = dataInputStream.readInt();//read number of positions of organisation
                            List<Organization.Position> positions = new ArrayList<>(positionListSize);
                            for (int x = 0; x < positionListSize; x++) {
                                LocalDate startDate = DateUtil.of(dataInputStream.readInt(),
                                        Month.of(dataInputStream.readInt()));//read start date
                                LocalDate endDate = DateUtil.of(dataInputStream.readInt(),
                                        Month.of(dataInputStream.readInt()));//read end date
                                String title = dataInputStream.readUTF();//read title
                                String description = dataInputStream.readUTF();//read description
                                positions.add(new Organization.Position(startDate, endDate, title, description));
                            }
                            organizations.add(new Organization(link, positions));
                        }
                        resume.addSection(sectionType, new OrganizationSection(organizations));
                }
            }
            return resume;
        }
    }

    private static void writeDate(DataOutputStream dos, LocalDate localDate) {
        try {
            dos.writeInt(localDate.getYear());
            dos.writeInt(localDate.getMonth().getValue());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.model.Organization.Position;
import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;
import static java.time.Month.*;

public class ResumeTestData {

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(PHONE, "+7(921) 855-0482");
        resume.addContact(SKYPE, "grigory.kislin");
        resume.addContact(MAIL, "gkislin@yandex.ru");
        resume.addContact(LINKEDIN, "");
        resume.addContact(GITHUB, "");
        resume.addContact(STACKOVERFLOW, "");
        resume.addContact(HOME_PAGE, "");

        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web " +
                "и Enterprise технологиям");
        resume.addSection(OBJECTIVE, objective);

        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры.");
        resume.addSection(PERSONAL, personal);


        ListSection achievement = new ListSection();
        achievement.addListItem("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). " +
                "Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.addListItem("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.addListItem("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: " +
                "Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, " +
                "интеграция CIFS/SMB java сервера.");
        achievement.addListItem("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.addListItem("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов " +
                "(SOA-base архитектура, JAX-WS, JMS, AS Glassfish). " +
                "Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. " +
                "Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.addListItem("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.addSection(ACHIEVEMENT, achievement);

        ListSection qualification = new ListSection();
        qualification.addListItem("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualification.addListItem("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualification.addListItem("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        qualification.addListItem("MySQL, SQLite, MS SQL, HSQLDB");
        qualification.addListItem("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualification.addListItem("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualification.addListItem("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, " +
                "Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, " +
                "GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, " +
                "Selenium (htmlelements).");
        qualification.addListItem("Python: Django.");
        qualification.addListItem("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualification.addListItem("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualification.addListItem("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, " +
                "SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, " +
                "OAuth1, OAuth2, JWT.");
        qualification.addListItem("Инструменты: Maven + plugin development, Gradle, настройка Ngnix, администрирование " +
                "Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualification.addListItem("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                "архитектурных шаблонов, UML, функционального программирования");
        qualification.addListItem("Родной русский, английский \"upper intermediate\"");
        resume.addSection(QUALIFICATIONS, qualification);

        Position javaOnlineProjectsPosition = new Position(DateUtil.of(2013, OCTOBER), LocalDate.now(),
                "Автор проекта.", "Создание, организация и проведение " +
                "Java онлайн проектов и стажировок.");
        Organization javaOnlineProjects = new Organization("Java Online Projects", null, javaOnlineProjectsPosition);

        Position wrikePosition = new Position(DateUtil.of(2014, OCTOBER), DateUtil.of(2016, JANUARY),
                "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы " +
                "управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). " +
                "Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Organization wrike = new Organization("Wrike", null, wrikePosition);

        Position RITCenterPosition = new Position(DateUtil.of(2012, APRIL), DateUtil.of(2014, OCTOBER),
                "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: " +
                "релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), " +
                "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. " +
                "Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения " +
                "(почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера " +
                "документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, " +
                "Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting " +
                "via ssh tunnels, PL/Python");
        Organization RITCenter = new Organization("RIT Center", null, RITCenterPosition);

        Position luxoftPosition = new Position(DateUtil.of(2010, DECEMBER), DateUtil.of(2012, APRIL),
                "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, " +
                "GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. " +
                "Реализация RIA-приложения для администрирования, мониторинга и анализа результатов " +
                "в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, " +
                "Commet, HTML5.");
        Organization luxoft = new Organization("Luxoft (Deutsche Bank)", null, luxoftPosition);

        Position yotaPosition = new Position(DateUtil.of(2008, JUNE), DateUtil.of(2010, DECEMBER),
                "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные " +
                "Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                "Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента " +
                "(Python/ Jython, Django, ExtJS)");
        Organization yota = new Organization("Yota", null, yotaPosition);

        Position enkataPosition = new Position(DateUtil.of(2007, MARCH), DateUtil.of(2008, JUNE),
                "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, " +
                "Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        Organization enkata = new Organization("Enkata", null, enkataPosition);

        Position siemensPosition = new Position(DateUtil.of(2005, JANUARY), DateUtil.of(2007, FEBRUARY),
                "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, " +
                "реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        Organization siemens = new Organization("Siemens AG", null, siemensPosition);

        Position alcatelPosition = new Position(DateUtil.of(1997, SEPTEMBER), DateUtil.of(2005, JANUARY),
                "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение " +
                "ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        Organization alcatel = new Organization("Alcatel", null, alcatelPosition);
        List<Organization> experienceOrganizations = Arrays.asList(javaOnlineProjects, wrike, RITCenter, luxoft,
                yota, enkata, siemens);
        OrganizationSection experienceSection = new OrganizationSection(experienceOrganizations);
        resume.addSection(EXPERIENCE, experienceSection);

        Position courseraPosition = new Position(DateUtil.of(2013, MARCH), DateUtil.of(2013, MAY),
                "\"Functional Programming Principles in Scala\" by Martin Odersky", "");
        Organization coursera = new Organization("Coursera", null, courseraPosition);

        Position luxoftEducationPosition = new Position(DateUtil.of(2011, MARCH), DateUtil.of(2011, APRIL),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null);
        Organization luxoftEducation = new Organization("Luxoft", null, luxoftEducationPosition);

        Position siemensEducationPosition = new Position(DateUtil.of(2005, JANUARY), DateUtil.of(2005, APRIL),
                "3 месяца обучения мобильным IN сетям (Берлин)", null);
        Organization siemensEducation = new Organization("Siemens AG", null, siemensEducationPosition);

        Position alcatelEducationPosition = new Position(DateUtil.of(1997, SEPTEMBER), DateUtil.of(1998, MARCH),
                "6 месяцев обучения цифровым телефонным сетям (Москва)", null);
        Organization alcatelEducation = new Organization("Alcatel", null, alcatelEducationPosition);

        Position universityPosition1 = new Position(DateUtil.of(1993, SEPTEMBER), DateUtil.of(1996, JULY),
                "Аспирантура (программист С, С++)", "");

        Position universityPosition2 = new Organization.Position(DateUtil.of(1987, SEPTEMBER), DateUtil.of(1993, JULY),
                "Инженер (программист Fortran, C)", null);
        Organization university = new Organization("Санкт-Петербургский национальный исследовательский " +
                "университет информационных технологий, механики и оптики", null, universityPosition1,
                universityPosition2);
        List<Organization> educationOrganizations = Arrays.asList(coursera, luxoftEducation, siemensEducation,
                alcatelEducation, university);
        OrganizationSection educationSection = new OrganizationSection(educationOrganizations);
        resume.addSection(EDUCATION, educationSection);

        return resume;
    }
}

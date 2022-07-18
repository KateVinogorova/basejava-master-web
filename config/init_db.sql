create table resumes.public.resume
(
    uuid      CHAR(36) PRIMARY KEY NOT NULL,
    full_name text
);

create table resumes.public.contact
(
    id          SERIAL PRIMARY KEY,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    resume_uuid CHAR(36) NOT NULL,
    CONSTRAINT contact_resume_uuid_fk FOREIGN KEY
        (resume_uuid) REFERENCES resume (uuid) ON DELETE CASCADE
);

create table resumes.public.section
(
    id          SERIAL PRIMARY KEY,
    type        TEXT     NOT NULL,
    value       TEXT     NOT NULL,
    resume_uuid CHAR(36) NOT NULL,
    CONSTRAINT contact_resume_uuid_fk FOREIGN KEY
        (resume_uuid) REFERENCES resume (uuid) ON DELETE CASCADE
);
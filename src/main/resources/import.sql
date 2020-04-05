CREATE TABLE companies (
    id serial PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    money INTEGER NOT NULL,
    currentProjectId INTEGER,
    userId INTEGER
);

CREATE TABLE designers (
   id serial PRIMARY KEY,
   workerId INTEGER NOT NULL,
   companyId INTEGER REFERENCES companies (id)
);

CREATE TABLE programmers (
   id serial PRIMARY KEY,
   workerId INTEGER NOT NULL,
   companyId INTEGER REFERENCES companies (id)
);

CREATE TABLE marketers (
   id serial PRIMARY KEY,
   workerId INTEGER NOT NULL,
   companyId INTEGER REFERENCES companies (id)
);
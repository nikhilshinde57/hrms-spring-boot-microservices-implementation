--DROP ROLE IF EXISTS hrms;

DO
$do$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_catalog.pg_roles  -- SELECT list can be empty for this
      WHERE  rolname = 'hrms') THEN

      CREATE ROLE hrms LOGIN PASSWORD 'hrms';
   END IF;
END
$do$;

--CREATE ROLE hrms WITH
--LOGIN
--SUPERUSER
--CREATEDB
--CREATEROLE
--INHERIT
--REPLICATION
--CONNECTION LIMIT -1
--PASSWORD 'hrms';

CREATE DATABASE organization_service
    WITH
    OWNER = hrms
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE DATABASE employee_service
    WITH
    OWNER = hrms
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE DATABASE leave_service
    WITH
    OWNER = hrms
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

--Run this below command on above created databases to enable UUID on it.
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
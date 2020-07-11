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

CREATE DATABASE oauth2_authentication_service
    WITH
    OWNER = hrms
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
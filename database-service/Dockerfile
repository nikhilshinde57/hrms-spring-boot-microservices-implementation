FROM postgres:latest
MAINTAINER Nikhil Shinde <nikhilshinde57@gmail.com>
COPY db-setup-script.sql /docker-entrypoint-initdb.d/
RUN chmod a+r /docker-entrypoint-initdb.d/*
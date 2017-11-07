-- Admin - admin@admin.com:admin
-- User - enabled@user.com:password
-- Disabled - disabled@user.com:password

USE FacebookPhotosSecurityDB;

INSERT INTO User (username, email, password, enabled, lastPasswordResetDate) VALUES ('admin', 'admin@admin.com', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', true, PARSEDATETIME('28-10-2017', 'dd-MM-yyyy'));
INSERT INTO User (username, email, password, enabled, lastPasswordResetDate) VALUES ('user', 'enabled@user.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, PARSEDATETIME('28-10-2017','dd-MM-yyyy'));
INSERT INTO User (username, email, password, enabled, lastPasswordResetDate) VALUES ('disabled', 'disabled@user.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', false, PARSEDATETIME('28-10-2017','dd-MM-yyyy'));

INSERT INTO Authority (name) VALUES ('ROLE_USER');
INSERT INTO Authority (name) VALUES ('ROLE_ADMIN');

INSERT INTO User_Authority (idUser, idAuthority) VALUES (1, 1);
INSERT INTO User_Authority (idUser, idAuthority) VALUES (1, 2);
INSERT INTO User_Authority (idUser, idAuthority) VALUES (2, 1);
INSERT INTO User_Authority (idUser, idAuthority) VALUES (3, 1);
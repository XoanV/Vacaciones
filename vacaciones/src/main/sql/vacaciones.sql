drop database if exists Vacaciones;

create database Vacaciones;

use Vacaciones;

create table Usuario (
	id_usuario int primary key auto_increment,
    email varchar(80) not null,
    clave varchar(100) not null, 
    nombre varchar(20) not null,
    apellidos varchar(30) not null,
    tipo_usuario enum('GESTOR', 'EMPLEADO') not null,
    centro enum('CORUÑA', 'SANTIAGO', 'FERROL', 'LUGO', 'OURENSE') not null,
    clave_reseteada boolean default false
);

create table PeticionVacaciones (
	id_peticion int primary key auto_increment,
    fecha_inicio date not null,
    fecha_fin date not null,
    año int not null,
    comentario varchar(100),
    estado enum('APROBADA', 'PENDIENTE', 'RECHAZADA') not null,
    usuario int not null,
    foreign key (usuario) references Usuario(id_usuario)
);

INSERT INTO Usuario (id_usuario, email, clave, nombre, apellidos, tipo_usuario, centro) values (1, 'rafael.martinez@academiapostal.es', 'AAAAAAAAAAAAA', 'Rafael', 'Martinez Serrano', 'GESTOR', 'CORUÑA'),
						   (2, 'gabriel.maceira@academiapostal.es', 'AAAAAAAAAAAAA', 'Gabriel', 'Maceira', 'GESTOR', 'CORUÑA'),
						   (3, 'pepe.perez@academiapostal.es', 'AAAAAAAAAAAAA', 'Pepe', 'Pérez', 'GESTOR', 'FERROL'),
						   (4, 'manolo.veiga@academiapostal.es', 'AAAAAAAAAAAAA', 'Monolo', 'Veiga', 'GESTOR', 'LUGO')
drop database if exists Vacaciones;

create database Vacaciones;

use Vacaciones;

create table Configuracion (
	id_configuracion int primary key auto_increment,
	color varchar(7) not null
);

create table Usuario (
	id_usuario int primary key auto_increment,
    email varchar(80) not null unique,
    clave varchar(100) not null, 
    nombre varchar(20) not null,
    apellidos varchar(30) not null,
    tipo_usuario enum('GESTOR', 'EMPLEADO') not null,
    centro enum('CORUÑA', 'SANTIAGO', 'FERROL', 'LUGO', 'OURENSE') not null,
    clave_reseteada boolean default false,
    configuracion int not null unique,
    foreign key (configuracion) references Configuracion(id_configuracion)
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

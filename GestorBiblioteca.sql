CREATE TABLE roles (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO roles(nombre_rol) VALUES
('Administrador'),
('Bibliotecario'),
('Estudiante');
----------------------------------------------------------
CREATE TABLE categorias (
    id_categoria SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);
INSERT INTO categorias(nombre) VALUES
('Programacion'),
('Base de Datos'),
('Redes'),
('Matematicas'),
('Fisica'),
('Literatura');
--------------------------------------------------------
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    id_rol INTEGER NOT NULL,
    CONSTRAINT fk_usuario_rol
    FOREIGN KEY(id_rol)
    REFERENCES roles(id_rol)
);

INSERT INTO usuarios (nombre,contrasena,id_rol) VALUES
('Administrador','admin123',1),
('Bibliotecario','biblio123',2);

SELECT * FROM usuarios;
-----------------------------------------------------------------
CREATE TABLE estudiantes(
    id_estudiante SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    carrera VARCHAR(100),
    id_usuario INTEGER UNIQUE,
    CONSTRAINT fk_estudiante_usuario
    FOREIGN KEY(id_usuario)
    REFERENCES usuarios(id_usuario)
);
SELECT * FROM estudiantes;
------------------------------------------------------------------------
CREATE TABLE libros(
    id_libro SERIAL PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    id_categoria INTEGER,
    CONSTRAINT fk_libro_categoria
    FOREIGN KEY(id_categoria)
    REFERENCES categorias(id_categoria)
);
SELECT * FROM libros;
------------------------------------------------------------------------
CREATE TABLE prestamos(
    id_prestamo SERIAL PRIMARY KEY,
    id_estudiante INTEGER NOT NULL,
    id_libro INTEGER NOT NULL,
    fecha_prestamo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_prestamo_estudiante
    FOREIGN KEY(id_estudiante)
    REFERENCES estudiantes(id_estudiante),
    CONSTRAINT fk_prestamo_libro
    FOREIGN KEY(id_libro)
    REFERENCES libros(id_libro)
);
ALTER TABLE prestamos
ADD COLUMN estado VARCHAR(20)
DEFAULT 'Activo';

UPDATE prestamos
SET estado='Activo'
WHERE estado IS NULL;

SELECT * FROM prestamos
--------------------------------------------------------------------------------
CREATE TABLE multas (
    id_multa SERIAL PRIMARY KEY,
    id_prestamo INTEGER NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    motivo VARCHAR(200),
    estado VARCHAR(20) DEFAULT 'Pendiente',
    fecha_pago TIMESTAMP,
    CONSTRAINT fk_multa_prestamo
    FOREIGN KEY(id_prestamo)
    REFERENCES prestamos(id_prestamo)
);
DROP TABLE multas
SELECT * FROM multas;



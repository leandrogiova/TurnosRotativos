                  API de Turnos Rotativos - Instructivo

--> Observaciones y más al final del documento.



Instructivo

Este instructivo proporciona una descripción general de la API de Turnos Rotativos y cómo utilizarla para gestionar 
empleados, conceptos y jornadas. 

La API incluye las siguientes clases principales:



Empleado: Esta clase almacena información sobre los empleados, incluyendo su ID, nombre, apellido, número de documento, email, fecha de nacimiento, fecha de ingreso y fecha de creación.

Concepto: La clase Concepto contiene información sobre los tipos de conceptos, como su ID, nombre, si es laborable, horas de trabajo mínimo y horas de trabajo máximo.

Jornada: La clase Jornada registra las jornadas laborales de los empleados, incluyendo su ID, ID del empleado, número de documento, nombre completo, ID del concepto, nombre del concepto, fecha y horas trabajadas.

A continuación, se detallan los métodos disponibles para interactuar con estas clases:

Empleado:

agregarEmpleado: Agrega un nuevo empleado con los datos proporcionados.
obtenerTodosLosEmpleados: Obtiene una lista de todos los empleados registrados.
obtenerEmpleado: Obtiene los detalles de un empleado específico según su ID.
actualizarEmpleado: Actualiza los datos de un empleado existente.
eliminarEmpleado: Elimina un empleado según su ID.
Concepto:

obtenerTodosLosConceptos: Obtiene una lista de todos los conceptos registrados.
Jornada:

agregarJornada: Agrega una nueva jornada laboral con las validaciones correspondientes, como verificar si un empleado ya tiene una jornada con el mismo concepto en la misma fecha.
obtenerTodasLasJornadas: Obtiene una lista de todas las jornadas registradas.
A continuación, se presentan algunos ejemplos de uso de la API:

Ejemplo 1: Agregar un Empleado

--------------------Request:
POST http://localhost:8080/empleado
Body: raw JSON
{
  "nroDocumento": 30415654,
  "nombre": "German",
  "apellido": "Zotella",
  "email": "gzotella@gmail.com",
  "fechaNacimiento": "1998-08-06",
  "fechaIngreso": "2019-06-04"
}

--------------------Response: 201 Created
{
    "id": 1,
    "nroDocumento": 30415654,
    "nombre": "German",
    "apellido": "Zotella",
    "email": "gzotella@gmail.com",
    "fechaNacimiento": "1998-08-06",
    "fechaIngreso": "2019-06-04",
    "fechaCreacion": "2023-09-11"
}


Ejemplo 2: Obtener Empleados
//Se probo la búsqueda con más de 1 registro. Los registros fueron cargados desde data.sql

--------------------Request: 
GET http://localhost:8080/empleado
Body: none


--------------------Response: 200 OK 
[
    {
        "id": 1,
        "nroDocumento": 30415654,
        "nombre": "German",
        "apellido": "Zotella",
        "email": "gzotella@gmail.com",
        "fechaNacimiento": "1998-08-06",
        "fechaIngreso": "2019-06-04",
        "fechaCreacion": "2023-09-11"
    },
    {
        "id": 10,
        "nroDocumento": 35111111,
        "nombre": "Silvio",
        "apellido": "SGGiovacchini",
        "email": "silvio@gmail.com",
        "fechaNacimiento": "1991-10-10",
        "fechaIngreso": "2022-10-10",
        "fechaCreacion": "2023-01-10"
    },
    {
        "id": 11,
        "nroDocumento": 40276969,
        "nombre": "Leandro",
        "apellido": "Giovacchini",
        "email": "leogiova4@gmail.com",
        "fechaNacimiento": "1998-01-26",
        "fechaIngreso": "2022-01-01",
        "fechaCreacion": "2021-01-10"
    },
    {
        "id": 12,
        "nroDocumento": 38828888,
        "nombre": "Pablo",
        "apellido": "Baaaaa",
        "email": "pabloaaaaa@gmail.com",
        "fechaNacimiento": "1992-08-18",
        "fechaIngreso": "2008-08-08",
        "fechaCreacion": "2008-08-09"
    }
]



Ejemplo 3: Obtener Información del empleado por ID
//Se probo la búsqueda con más de 1 registro. Los registros fueron cargados desde data.sql

--------------------Request: 
PUT http://localhost:8080/empleado/{IdEmpleado}
-- Ejemplo http://localhost:8080/empleado/1
Body: none


--------------------Response: 200 OK 
{
    "id": 1,
    "nroDocumento": 30415654,
    "nombre": "German",
    "apellido": "Zotella",
    "email": "gzotella@gmail.com",
    "fechaNacimiento": "1998-08-06",
    "fechaIngreso": "2019-06-04",
    "fechaCreacion": "2023-09-11"
}


Ejemplo 4: Actualizar empleado por ID
//Se probo la búsqueda con más de 1 registro. Los registros fueron cargados desde data.sql

--------------------Request: 
PUT http://localhost:8080/empleado/1
Body: raw JSON

{
  "nroDocumento": 30415654,
  "nombre": "G",
  "apellido": "Z",
  "email": "gzotella@gmail.com",
  "fechaNacimiento": "2000-08-06",
  "fechaIngreso": "2000-06-04",
  "fechaCreacion": "2023-09-01"
}

--------------------Response: 200 OK
{
    "id": 1,
    "nroDocumento": 30415654,
    "nombre": "G",
    "apellido": "Z",
    "email": "gzotella@gmail.com",
    "fechaNacimiento": "2000-08-06",
    "fechaIngreso": "2000-06-04",
    "fechaCreacion": "2023-09-11"
}


Ejemplo 5: Eliminar empleado por ID
--------------------Request: 
DELETE http://localhost:8080/empleado/1
Body: none


--------------------Response: 204 NO CONTENT



------------------------------------------------------------------------------------
------------------------------------------------------------------------------------
------------------------------------------------------------------------------------


Ejemplos sobre Conceptos


Ejemplo 6: Obtener Conceptos
--------------------Request: 
GET  http://localhost:8080/concepto
Body: none
--------------------Response: 200 OK

[
    {
        "nombre": "Turno Normal",
        "laborable": true,
        "hsMinimo": 6,
        "hsMaximo": 8
    },
    {
        "nombre": "Turno Extra",
        "laborable": true,
        "hsMinimo": 2,
        "hsMaximo": 6
    },
    {
        "nombre": "Dia Libre",
        "laborable": false
    },
    {
        "nombre": "Turno RecontraExtra",
        "laborable": true,
        "hsMinimo": 12,
        "hsMaximo": 18
    }
]



------------------------------------------------------------------------------------
------------------------------------------------------------------------------------
------------------------------------------------------------------------------------


Ejemplo 7: Crear Jornada
--------------------Request: 
POST  http://localhost:8080/jornada
Body: raw JSON

{
  "idEmpleado": 1,
  "idConcepto": 1,
  "fecha": "2023-01-01",
  "horasTrabajadas": 8
}

--------------------Response: 201 CREATED

{
    "nroDocumento": 30415654,
    "nombreCompleto": "German Zotella",
    "concepto": "Turno Normal",
    "fecha": "2023-01-01",
    "horasTrabajadas": 8
}

Ejemplo 8: Obtener Jornadas

  -- Para obtener todas las jornadas: GET http://localhost:8080/jornada

  -- Para obtener todas las jornadas de un empleado: GET  http://localhost:8080/jornada?nrodocumento=10

  -- Para obtener todas las jornadas de una fecha: GET http://localhost:8080/jornada?fecha=2023-01-01

  -- Para obtener todas las jornadas de un empleado en una fecha especifica: GET http://localhost:8080/jornada?nrodocumento=10&fecha=2023-01-01


--------------------Response: 200 OK
[
  {
      "nroDocumento": 30415654,
      "nombreCompleto": "German Zotella",
      "concepto": "Turno Normal",
      "fecha": "2023-01-01",
      "horasTrabajadas": 8
    }
]







ACLARACIONES Y MAS



ATENCIÓN: Corrección en el Ejemplo "POST EMPLEADO"

El campo "nroDocumento" en la clase "Empleado" debe ser referenciado como
 "nroDocumento" al realizar una solicitud POST a "http://localhost:8080/empleado". 
 Se ha identificado un error tipográfico en la documentación proporcionada, 
 donde se menciona "nroDocumentro" en lugar de "nroDocumento".

Corrección del Ejemplo Proporcionado:
json
{
  "nroDocumento": 30415654,
  "nombre": "German",
  "apellido": "Zotella",
  "email": "gzotella@gmail.com",
  "fechaNacimiento": "1998-08-06",
  "fechaIngreso": "2019-06-04"
}
Conclusión: usar "nroDocumento" para evitar errores.


¡Aclaracion!
En la Clase 'EmpleadoController' y 'eliminarEmpleado' en el metodo 'obtenerEmpleado' se recibe 
un String como id ya que se entiende que si en la URI llega un caracter se debe lanzar una excepción

En el Stoplight muestra que el empleadoId es de tipo String

Si el id contiene caracteres se lanzara una excepción explicando la situación

Ejemplo: 
GET http://localhost:8080/empleado/117879sfasdfasd

Response:
{
    "timestamp": "2023-09-11T04:40:16.131+00:00",
    "message": "El id del empleado en la URI contiene caracteres."
}


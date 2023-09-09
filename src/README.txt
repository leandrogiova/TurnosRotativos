
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
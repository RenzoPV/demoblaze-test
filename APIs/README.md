# APIs – PetStore

Este proyecto contiene una prueba automatizada de diferentes endpoints de la API de PetStore: https://petstore.swagger.io.

El escenario validado contempla las siguientes acciones:

- Añadir una mascota a la tienda
- Consultar la mascota ingresada previamente (Búsqueda por ID)
- Actualizar el nombre de la mascota y el estatus de la mascota a "sold"
- Consultar la mascota modificada por estatus (Búsqueda por status)

## Instrucciones de ejecución

1. Abrir una consola (CMD o PowerShell).
2. Dirigirse a la ruta raíz del proyecto.
3. Ejecutar el siguiente comando:

   ```bash
   mvn test
4. Al finalizar la ejecución, abrir el reporte generado por Karate DSL:

   ```bash
   target/karate-reports/karate-summary.html
   Este reporte permite visualizar los pasos ejecutados, los resultados obtenidos y la trazabilidad completa de la prueba ejecutada.

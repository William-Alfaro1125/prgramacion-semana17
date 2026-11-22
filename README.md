# Programación III — Semana 17
## Concurrencia: varias tareas a la vez con Thread y Runnable

Proyecto práctico basado en la guía de trabajo de la Semana 17 (Unidad V, contenidos 5.1 y 5.2). Preparado para **JDK 21**.

## Estructura

```text
prgramacion-semana17/
├── README.md
├── .gitignore
└── semana17/
    ├── hilos/
    │   ├── HiloCajero.java
    │   ├── TareaReporte.java
    │   ├── StartVsRun.java
    │   ├── SumaParalela.java
    │   ├── SinJoin.java
    │   └── Tiempos.java
    ├── swing/
    │   ├── VentanaCongelada.java
    │   └── VentanaFluida.java
    ├── web/
    │   ├── Socio.java
    │   ├── RepositorioSocios.java
    │   ├── SocioNoEncontradoException.java
    │   ├── DatoInvalidoException.java
    │   └── ServidorCooperativa.java
    └── ejercicios/
        ├── CuentaRegresiva.java
        └── ServidorTotal.java
```

## Requisitos

- JDK 21 instalado.
- Terminal o IDE Java (IntelliJ IDEA, NetBeans o VS Code).
- No se necesitan dependencias externas. `HttpServer` pertenece al módulo `jdk.httpserver`.

## Compilar todo el proyecto

Desde la carpeta raíz:

### Windows (PowerShell)

```powershell
New-Item -ItemType Directory -Force out | Out-Null
$files = Get-ChildItem -Recurse semana17 -Filter *.java | ForEach-Object { $_.FullName }
javac --add-modules jdk.httpserver -encoding UTF-8 -d out $files
```

### Linux / macOS

```bash
mkdir -p out
javac --add-modules jdk.httpserver -encoding UTF-8 -d out $(find semana17 -name "*.java")
```

## Ejecutar los ejemplos

Ejecuta cada comando desde la raíz del proyecto, después de compilar:

```bash
java -cp out semana17.hilos.HiloCajero
java -cp out semana17.hilos.TareaReporte
java -cp out semana17.hilos.StartVsRun
java -cp out semana17.hilos.SumaParalela
java -cp out semana17.hilos.SinJoin
java -cp out semana17.hilos.Tiempos
java -cp out semana17.swing.VentanaCongelada
java -cp out semana17.swing.VentanaFluida
java -cp out semana17.ejercicios.CuentaRegresiva
```

Las clases Swing necesitan un entorno gráfico.

## Probar el servidor HTTP

Inicia:

```bash
java --add-modules jdk.httpserver -cp out semana17.web.ServidorCooperativa
```

Abre estas rutas en el navegador:

| Ruta | Resultado esperado |
|---|---|
| `http://localhost:8080/socios` | HTTP 200: lista JSON de socios |
| `http://localhost:8080/socio?id=2` | HTTP 200: socio Luis |
| `http://localhost:8080/socio?id=9` | HTTP 404: socio inexistente |
| `http://localhost:8080/socio?id=abc` | HTTP 400: identificador inválido |
| `http://localhost:8080/total` | HTTP 200: cantidad de socios y saldo total |
| `http://localhost:8080/hilo` | HTTP 200: nombre del hilo que atendió la petición |

Para detener el servidor, usa `Ctrl + C`.

### Servidor independiente del ejercicio 3

Detén el servidor anterior y ejecuta:

```bash
java --add-modules jdk.httpserver -cp out semana17.ejercicios.ServidorTotal
```

Prueba `http://localhost:8080/total`.

## Evidencias solicitadas en la guía

Para completar la entrega académica, toma tus propias capturas de:

1. Las tres respuestas de búsqueda: socio existente (200), socio inexistente (404) e identificador no numérico (400).
2. La ruta `/socios` y la ruta `/total`.
3. La ventana `VentanaFluida` mientras muestra el progreso.
4. La ejecución de los ejemplos de hilos en la consola.

Las capturas no se incluyen porque deben corresponder a ejecuciones reales en tu computadora.

## Notas

- `VentanaCongelada` reproduce deliberadamente el problema de bloquear el hilo de eventos de Swing (EDT).
- `VentanaFluida` mueve el trabajo lento a un hilo trabajador y actualiza los componentes mediante `SwingUtilities.invokeLater`.
- El servidor usa `Executors.newFixedThreadPool(4)` para atender solicitudes concurrentes.
- El repositorio usa datos de ejemplo en memoria; no requiere una base de datos.

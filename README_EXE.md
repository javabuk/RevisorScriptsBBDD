# SQL Encoding Converter - Generador de Ejecutable .exe

Este proyecto permite crear un ejecutable `.exe` independiente sin dependencias de JDK/JRE.

## Opciones para crear el .exe

### Opción 1: Usando GraalVM Native Image (Recomendado)

**Ventajas:**
- Ejecutable completamente independiente
- Sin necesidad de JDK/JRE instalado
- Inicio muy rápido
- Pequeño tamaño (~40-50 MB)

**Requisitos:**
1. Descargar GraalVM for Windows:
   - Descargar desde: https://www.graalvm.org/downloads/
   - Seleccionar "GraalVM Community" o "Enterprise"
   - Versión Java 11 o superior
   - Sistema: Windows x64

2. Instalar GraalVM:
   - Extraer en: `C:\graalvm` (o cualquier ruta sin espacios)
   - Configurar variable de entorno `JAVA_HOME`:
     ```powershell
     $env:JAVA_HOME = "C:\graalvm\graalvm-jdk-11"
     ```

3. Instalar Visual Studio Build Tools (requerido para compilar):
   - Descargar desde: https://visualstudio.microsoft.com/visual-cpp-build-tools/
   - Instalar "Desktop development with C++"

4. Compilar el proyecto:
   ```powershell
   cd c:\Area\Formacion\Java\RevisorScriptsBBDD
   mvn clean native:compile
   ```

5. El ejecutable se generará en:
   ```
   target\sql-converter.exe
   ```

**Uso:**
```powershell
target\sql-converter.exe "C:\ruta\a\tus\scripts\sql"
```

---

### Opción 2: Usando Excelsior JET (Alternativa Premium)

**Ventajas:**
- Excelente rendimiento
- Ejecutable muy compacto

**Desventajas:**
- Licencia de pago (aunque tiene trial gratuita)

Más información: https://www.excelsiorjet.com/

---

### Opción 3: Usando Launch4j (Solución Simple)

**Ventajas:**
- Herramienta simple y gratuita
- No requiere compilación nativa

**Desventajas:**
- Requiere JRE en el sistema del usuario
- Se puede bundlear el JRE (aumenta tamaño)

**Pasos:**

1. Descargar Launch4j desde: http://launch4j.sourceforge.net/

2. Compilar el JAR:
   ```powershell
   mvn clean package
   ```

3. Crear archivo de configuración `launch4j.xml`:
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <launch4jConfig>
     <dontWrapJar>false</dontWrapJar>
     <headerType>console</headerType>
     <jar>target\sql-converter-jar-with-dependencies.jar</jar>
     <outfile>target\sql-converter.exe</outfile>
     <errTitle>SQL Converter Error</errTitle>
     <cmdLine></cmdLine>
     <chdir>.</chdir>
     <priority>normal</priority>
     <downloadUrl>https://www.oracle.com/java/technologies/downloads/</downloadUrl>
     <supportUrl>https://github.com/javabuk/RevisorScriptsBBDD</supportUrl>
     <customProcName>false</customProcName>
     <stayAlive>false</stayAlive>
     <manifest></manifest>
     <icon></icon>
     <jre>
       <path></path>
       <bundledJre64Bit>false</bundledJre64Bit>
       <bundledJreAsFallback>false</bundledJreAsFallback>
       <minVersion>11</minVersion>
       <maxVersion></maxVersion>
       <jdkPreference>preferJre</jdkPreference>
       <runtimeBits>64/32</runtimeBits>
     </jre>
   </launch4jConfig>
   ```

---

### Opción 4: Distribuir con JRE Embebido

Si deseas crear un ejecutable que incluya la JRE:

```powershell
# 1. Descargar JRE 11 desde Oracle
# 2. Copiar en el proyecto: src\main\resources\jre

# 3. Usar Launch4j o similar para crear wrapper
```

---

## Recomendación Final

**Para máxima compatibilidad sin dependencias: Usa GraalVM Native Image (Opción 1)**

Es la mejor solución porque:
- Genera un ejecutable completamente independiente
- No necesita JRE en el sistema destino
- Rápido inicio
- Tamaño razonable

---

## Troubleshooting

### Error al compilar con GraalVM

Si obtienes error de compilación:

1. Verifica que Visual Studio Build Tools esté correctamente instalado:
   ```powershell
   where cl.exe
   ```

2. Ejecuta desde Developer Command Prompt de Visual Studio

3. Si aún hay problemas, usa la Opción 3 (Launch4j)

---

## Archivos Modificados

- `pom.xml` - Añadido plugin de GraalVM Native Image
- `src/main/resources/META-INF/native-image/...` - Configuración de native-image


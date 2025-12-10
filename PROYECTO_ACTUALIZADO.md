# ✅ Proyecto SQL Encoding Converter - Ahora con opciones de .exe

## Estado del Proyecto

El proyecto ha sido actualizado para permitir generar ejecutables `.exe` independientes sin depender de JDK/JRE.

---

## 📊 Resumen de Cambios

### Archivos Modificados:
1. **pom.xml** - Añadido soporte para GraalVM Native Image (desactivado por defecto)

### Archivos Creados:
1. **build-native.ps1** - Script PowerShell para compilación automatizada
2. **launch4j.xml** - Configuración alternativa para Launch4j
3. **src/main/resources/META-INF/native-image/** - Configuración de GraalVM
4. **README_EXE.md** - Documentación técnica detallada
5. **QUICK_START_EXE.md** - Guía rápida para usuarios
6. **CAMBIOS_REALIZADOS.md** - Este archivo resumido

---

## 🚀 Cómo Generar el .exe

### OPCIÓN 1: GraalVM Native Image (Recomendado - Sin dependencias de Java)

**Instalación (una sola vez):**
```powershell
# 1. Descargar GraalVM desde: https://www.graalvm.org/downloads/
#    (Seleccionar "Windows x64" y Java 11 o superior)

# 2. Extraer en C:\graalvm\

# 3. Instalar Visual Studio Build Tools:
#    https://visualstudio.microsoft.com/visual-cpp-build-tools/
#    (Seleccionar "Desktop development with C++")

# 4. Configurar JAVA_HOME en PowerShell:
$env:JAVA_HOME = "C:\graalvm\graalvm-jdk-11"
```

**Compilación:**
```powershell
cd c:\Area\Formacion\Java\RevisorScriptsBBDD
.\build-native.ps1
```

**Resultado:**
- Ejecutable: `target\sql-converter.exe` (~40-50 MB)
- Totalmente independiente
- Se ejecuta sin Java instalado

**Uso:**
```powershell
.\target\sql-converter.exe "C:\ruta\a\tus\scripts\sql"
```

---

### OPCIÓN 2: Launch4j (Más simple - Requiere Java en cliente)

**Instalación (una sola vez):**
1. Descargar desde: http://launch4j.sourceforge.net/
2. Extraer en cualquier carpeta

**Compilación:**
```powershell
cd c:\Area\Formacion\Java\RevisorScriptsBBDD

# Compilar el JAR
mvn clean package

# Abrir Launch4j y seleccionar launch4j.xml
# Hacer clic en "Build" (botón engranaje)
```

**Resultado:**
- Ejecutable: `target\sql-converter.exe` (~5 MB)
- Requiere Java 11+ en la máquina cliente
- Compilación muy rápida

**Uso:**
```powershell
.\target\sql-converter.exe "C:\ruta\a\tus\scripts\sql"
```

---

## 📋 Comparativa de Opciones

| Característica | GraalVM | Launch4j | JAR Directo |
|---|:---:|:---:|:---:|
| **Independencia de Java** | ✅ | ❌ | ❌ |
| **Tamaño ejecutable** | 40-50 MB | 5-10 MB | 500 KB |
| **Velocidad inicio** | Muy rápida | Normal | Normal |
| **Setup inicial** | Medio | Fácil | N/A |
| **Tiempo compilación** | 5-10 min | 1 min | 30 seg |
| **Requiere Visual Studio** | ✅ | ❌ | ❌ |
| **Recomendado para** | Distribución | Testing rápido | Desarrollo |

---

## 📁 Estructura del Proyecto

```
RevisorScriptsBBDD/
├── src/
│   ├── main/
│   │   ├── java/com/revisor/scripts/
│   │   │   └── SqlEncodingConverter.java
│   │   └── resources/META-INF/native-image/
│   │       └── com.revisor/sql-encoding-converter/
│   │           └── native-image.properties
│   └── test/
├── target/
│   ├── sql-converter.exe                    (generado)
│   ├── sql-converter-jar-with-dependencies.jar
│   └── sql-encoding-converter-1.0.0.jar
│
├── pom.xml                      (MODIFICADO)
├── build-native.ps1             (NUEVO)
├── launch4j.xml                 (NUEVO)
│
├── README_EXE.md                (NUEVO - Documentación)
├── QUICK_START_EXE.md           (NUEVO - Guía rápida)
├── CAMBIOS_REALIZADOS.md        (NUEVO - Este archivo)
│
└── test_sql_files/
    ├── script1.sql
    └── script2.sql
```

---

## ✨ Características de la Aplicación

El ejecutable generado realiza las siguientes operaciones:

1. **Acepta ruta como parámetro** desde línea de comandos
2. **Recorre recursivamente** todos los archivos de la carpeta
3. **Identifica archivos .sql** con cualquier encoding
4. **Convierte a UTF-8 con BOM** automáticamente
5. **Normaliza saltos de línea a LF** (estilo Linux/Unix)
6. **Imprime detalladamente** cada operación realizada
7. **Muestra** archivos procesados y cambios realizados

### Ejemplo de uso:
```powershell
# Con GraalVM (no requiere Java)
.\target\sql-converter.exe "C:\mis\proyectos\scripts\sql"

# Con Launch4j (requiere Java 11+)
.\target\sql-converter.exe "C:\mis\proyectos\scripts\sql"

# O con JAR original
java -jar target\sql-converter-jar-with-dependencies.jar "C:\mis\proyectos\scripts\sql"
```

### Salida esperada:
```
╔════════════════════════════════════════════════════════╗
║  SQL Encoding Converter - Procesando ficheros SQL      ║
╚════════════════════════════════════════════════════════╝

Directorio: C:\mis\proyectos\scripts\sql

Se encontraron 5 fichero(s) SQL:

─ C:\mis\proyectos\scripts\sql\usuarios.sql
  Encoding actual: UTF-8 (sin BOM)
  Cambios realizados:
    ✓ Encoding cambiado de UTF-8 (sin BOM) a UTF-8 con BOM
    ✓ Saltos de línea normalizados a LF (Linux)
  Tamaño: 2048 bytes

[... más archivos ...]

╔════════════════════════════════════════════════════════╗
║  Proceso completado exitosamente                       ║
╚════════════════════════════════════════════════════════╝
```

---

## 🔧 Compilación Normal (Sigue Funcionando)

El proyecto sigue compilándose normalmente sin necesidad de GraalVM:

```powershell
# Compilar JAR tradicional (funciona sin cambios)
mvn clean package

# JAR con todas las dependencias
java -jar target\sql-converter-jar-with-dependencies.jar "C:\ruta"
```

---

## 📖 Documentación

Para más información, consulta:

- **QUICK_START_EXE.md** - Guía rápida paso a paso
- **README_EXE.md** - Documentación técnica completa
- **pom.xml** - Configuración del proyecto

---

## 🎯 Siguiente Paso

**Elige una opción y sigue los pasos:**

1. **Si prefieres máxima independencia:**
   → Lee QUICK_START_EXE.md - Opción A (GraalVM)

2. **Si prefieres máxima simplicidad:**
   → Lee QUICK_START_EXE.md - Opción B (Launch4j)

3. **Si tienes dudas:**
   → Lee README_EXE.md (documentación completa)

---

## ✅ Validación

Para verificar que todo está configurado correctamente:

```powershell
# 1. Verificar Maven
mvn -v

# 2. Compilar JAR (siempre funciona)
mvn clean package

# 3. Verificar estructura de configuración
Get-ChildItem -Recurse src/main/resources/META-INF/native-image

# 4. Revisar configuración
Get-Content pom.xml | Select-String "graalvm"
```

---

## 🆘 Solución de Problemas

### El build normal falla
```powershell
# Limpiar completamente y reintentar
mvn clean
mvn package
```

### GraalVM no compila
1. Verifica que `C:\Program Files\Microsoft Visual Studio\2022\...` está instalado
2. Ejecuta desde "Developer Command Prompt for Visual Studio"
3. Usa Launch4j como alternativa

### Launch4j no encuentra el JAR
1. Primero: `mvn clean package`
2. Luego abre launch4j.xml
3. Verifica que apunta a: `target\sql-converter-jar-with-dependencies.jar`

---

## 📝 Notas Finales

- ✅ El proyecto es 100% compatible con versiones previas
- ✅ JAR sigue funcionando como antes
- ✅ Nueva funcionalidad de .exe es opcional
- ✅ Documentación completa incluida
- ✅ Scripts automatizados para facilitar compilación

**¡El proyecto está listo para generar ejecutables Windows!**


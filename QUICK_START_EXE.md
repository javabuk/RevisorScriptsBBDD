# GUÍA RÁPIDA: Crear ejecutable .exe

## La forma más fácil (Recomendada para principiantes)

### Opción A: Con GraalVM Native Image (Sin dependencias de Java)

**Paso 1: Descargar GraalVM**
1. Ve a: https://www.graalvm.org/downloads/
2. Descarga "GraalVM for Windows" (Java 11 o superior)
3. Extrae en: `C:\graalvm`

**Paso 2: Instalar Visual Studio Build Tools**
1. Descarga desde: https://visualstudio.microsoft.com/visual-cpp-build-tools/
2. Ejecuta el instalador
3. Selecciona: "Desktop development with C++"
4. Instala completamente

**Paso 3: Ejecutar el build**
```powershell
# Abre PowerShell como administrador y ejecuta:
$env:JAVA_HOME = "C:\graalvm\graalvm-jdk-11"
cd c:\Area\Formacion\Java\RevisorScriptsBBDD
mvn clean native:compile
```

**Resultado:**
- Se generará: `target\sql-converter.exe`
- Puedes ejecutarlo directamente sin Java
- Ejemplo: `.\target\sql-converter.exe "C:\mis\scripts\sql"`

---

## Opción B: Con Launch4j (Más simple, pero requiere Java en la máquina destino)

**Paso 1: Compilar el JAR**
```powershell
cd c:\Area\Formacion\Java\RevisorScriptsBBDD
mvn clean package
```

**Paso 2: Descargar Launch4j**
- Descarga desde: http://launch4j.sourceforge.net/
- Extrae en una carpeta

**Paso 3: Usar Launch4j**
1. Abre `Launch4j`
2. Abre el archivo: `launch4j.xml` (en la raíz del proyecto)
3. Haz clic en "Build" (el botón de engranaje)
4. Se generará: `target\sql-converter.exe`

**Ventaja:** Mucho más rápido que GraalVM
**Desventaja:** Requiere que el usuario tenga Java 11+ instalado

---

## Comparación

| Característica | GraalVM Native | Launch4j |
|---|---|---|
| Tamaño ejecutable | ~40-50 MB | ~5 MB |
| Requiere Java en el cliente | NO | SÍ |
| Velocidad de compilación | Lenta (5-10 min) | Rápida (1 min) |
| Velocidad de ejecución | Muy rápida | Normal |
| Complejidad de setup | Media | Baja |

---

## Solución de problemas

### "mvn: No se reconoce"
- Instala Maven desde: https://maven.apache.org/download.cgi
- Añade Maven a tu PATH

### "cl.exe no encontrado"
- Ejecuta desde: "Developer Command Prompt for Visual Studio"
- O configura manualmente las variables de VCVARS

### "El compilador nativo falla"
- Instala Visual Studio Build Tools con soporte C++
- Usa la Opción B (Launch4j) como alternativa

---

## Archivos útiles en el proyecto

- `README_EXE.md` - Documentación completa
- `build-native.ps1` - Script automático para compilar
- `launch4j.xml` - Configuración para Launch4j

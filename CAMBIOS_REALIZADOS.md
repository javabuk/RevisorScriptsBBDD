# Resumen de Cambios - Generación de Ejecutable .exe

## Cambios Realizados

### 1. **Actualización del `pom.xml`**
   - Añadido plugin `native-maven-plugin` de GraalVM
   - Versión: 0.9.28
   - Configurado para compilar ejecutables nativos sin fallback

### 2. **Archivos de Configuración de GraalVM**
   - Creado: `src/main/resources/META-INF/native-image/com.revisor/sql-encoding-converter/native-image.properties`
   - Define opciones de compilación nativa

### 3. **Scripts de Compilación**
   - Creado: `build-native.ps1`
   - Script PowerShell automático que:
     - Verifica GraalVM y Maven
     - Compila el JAR
     - Compila el ejecutable nativo
     - Maneja errores y proporciona soluciones

### 4. **Configuración para Launch4j**
   - Creado: `launch4j.xml`
   - Configuración alternativa más simple (sin native image)
   - Genera `.exe` que requiere Java en el sistema destino

### 5. **Documentación**
   - **README_EXE.md**: Documentación técnica completa con todas las opciones
   - **QUICK_START_EXE.md**: Guía rápida paso a paso para principiantes

---

## Opciones de Compilación

### ✅ **OPCIÓN 1: GraalVM Native Image (Recomendado)**

**Ventajas:**
- Ejecutable 100% independiente
- Sin necesidad de JDK/JRE en el cliente
- Arranque muy rápido
- Ejecutable autónomo

**Tamaño esperado:** 40-50 MB

**Pasos rápidos:**
1. Descargar GraalVM desde https://www.graalvm.org/downloads/
2. Instalar Visual Studio Build Tools (C++ tools)
3. Configurar JAVA_HOME y ejecutar:
   ```powershell
   $env:JAVA_HOME = "C:\ruta\a\graalvm"
   mvn clean native:compile
   ```
4. Resultado: `target\sql-converter.exe`

### ✅ **OPCIÓN 2: Launch4j (Más Simple)**

**Ventajas:**
- Setup muy simple
- Compilación rápida
- Sin dependencias complicadas

**Desventajas:**
- Requiere Java 11+ en la máquina cliente

**Pasos rápidos:**
1. Compilar JAR: `mvn clean package`
2. Descargar Launch4j desde http://launch4j.sourceforge.net/
3. Abrir `launch4j.xml` con Launch4j
4. Haz clic en "Build"
5. Resultado: `target\sql-converter.exe`

---

## Estructura de Directorios Nueva

```
RevisorScriptsBBDD/
├── src/
│   └── main/
│       ├── java/com/revisor/scripts/
│       │   └── SqlEncodingConverter.java
│       └── resources/
│           └── META-INF/native-image/
│               └── com.revisor/sql-encoding-converter/
│                   └── native-image.properties
├── target/
│   ├── sql-converter.exe          ← Ejecutable (generado)
│   ├── sql-converter-jar-with-dependencies.jar
│   └── classes/
├── pom.xml                        ← Actualizado
├── build-native.ps1               ← Nuevo: Script de compilación
├── launch4j.xml                   ← Nuevo: Config alternativa
├── README_EXE.md                  ← Nuevo: Documentación completa
├── QUICK_START_EXE.md            ← Nuevo: Guía rápida
└── test_sql_files/
    ├── script1.sql
    └── script2.sql
```

---

## Uso del Ejecutable

Una vez compilado:

```powershell
# Opción 1: Con GraalVM
.\target\sql-converter.exe "C:\mis\scripts\sql"

# Opción 2: Con Launch4j (requiere Java)
.\target\sql-converter.exe "C:\mis\scripts\sql"
```

Ambas opciones funcionan exactamente igual.

---

## Comparativa Completa

| Aspecto | GraalVM Native | Launch4j | JAR Original |
|--------|---|---|---|
| **Independencia de Java** | ✅ SÍ | ❌ NO | ❌ NO |
| **Tamaño** | 40-50 MB | 5-10 MB | ~500 KB |
| **Velocidad inicio** | Muy rápida | Normal | Normal |
| **Complejidad setup** | Media | Baja | Nula |
| **Tiempo compilación** | 5-10 min | 1 min | 30 seg |
| **Requiere Visual Studio** | ✅ SÍ | ❌ NO | ❌ NO |

---

## Próximos Pasos

### Para crear el .exe:

**Si quieres la mejor solución (sin dependencias):**
1. Lee: `QUICK_START_EXE.md`
2. Ejecuta: `.\build-native.ps1`

**Si quieres la solución más simple (con Java):**
1. Lee: `QUICK_START_EXE.md` - Opción B
2. Usa Launch4j

### Para más información:
- Documentación completa: `README_EXE.md`
- Configuración del proyecto: `pom.xml`

---

## Verificación

Para verificar que los cambios son correctos:

```powershell
cd c:\Area\Formacion\Java\RevisorScriptsBBDD

# Compilar JAR (esto funcionará sin cambios)
mvn clean package

# Verificar estructura
Get-ChildItem -Recurse src/main/resources/META-INF/native-image
```

---

## Soporte

Si necesitas ayuda:

1. **Verificar instalación de Maven:**
   ```powershell
   mvn -v
   ```

2. **Verificar instalación de Java:**
   ```powershell
   java -version
   ```

3. **Para problemas con GraalVM:**
   - Usa Launch4j como alternativa
   - Lee documentación oficial: https://www.graalvm.org/native-image/


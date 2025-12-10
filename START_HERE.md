# 🚀 REFERENCIA RÁPIDA

## En 30 segundos

Tu proyecto **SQL Encoding Converter** ahora puede ser:

### ✨ Ejecutable independiente (sin Java)
```powershell
.\build-native.ps1
# Resultado: target\sql-converter.exe
```

### ✨ Ejecutable simple (con Java)
```
1. mvn clean package
2. Abrir launch4j.xml con Launch4j
3. Clic en "Build"
```

### ✨ JAR tradicional (como antes)
```powershell
mvn clean package
java -jar target\sql-converter-jar-with-dependencies.jar "C:\ruta"
```

---

## Archivo de Inicio Recomendado

👉 **Empieza aquí:** `QUICK_START_EXE.md`

---

## Archivos Importantes

| Archivo | Contenido |
|---------|-----------|
| **pom.xml** | Configuración (modificado) |
| **build-native.ps1** | Script compilación GraalVM |
| **launch4j.xml** | Config Launch4j |
| **src/main/.../native-image.properties** | Config GraalVM |

---

## Requisitos por Opción

| Opción | Requisitos | Tiempo |
|--------|-----------|--------|
| GraalVM | GraalVM + VS Build Tools | 5-10 min |
| Launch4j | Launch4j + Java 11+ | 1 min |
| JAR | Maven + Java 11+ | 1 min |

---

## Verifica que todo está bien

```powershell
cd c:\Area\Formacion\Java\RevisorScriptsBBDD
mvn clean package
# Debe compilar sin errores
```

✅ **¡Todo listo!**

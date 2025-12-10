# 📚 ÍNDICE DE DOCUMENTACIÓN - SQL Encoding Converter

## 🎯 Comienza aquí según tu necesidad:

### Si eres usuario final y quieres usar la aplicación:
→ **[QUICK_START_EXE.md](./QUICK_START_EXE.md)**
- Guía paso a paso para generar el .exe
- 2 opciones simples a elegir
- Tiempo estimado: 20-30 minutos

### Si eres desarrollador y quieres entender el proyecto:
→ **[PROYECTO_ACTUALIZADO.md](./PROYECTO_ACTUALIZADO.md)**
- Resumen ejecutivo de cambios
- Estructura del proyecto
- Opciones de compilación
- Ejemplos de uso

### Si necesitas documentación técnica completa:
→ **[README_EXE.md](./README_EXE.md)**
- Análisis detallado de todas las opciones
- Instrucciones detalladas para cada método
- Solución de problemas
- Información de troubleshooting

### Si quieres ver qué cambió exactamente:
→ **[CAMBIOS_REALIZADOS.md](./CAMBIOS_REALIZADOS.md)**
- Lista de archivos modificados/creados
- Detalles de cada cambio
- Estructura de directorios nueva

---

## 📁 Archivos del Proyecto

### Configuración (Modificados):
- **pom.xml** - Añadido soporte para GraalVM Native Image

### Configuración (Nuevos):
- **launch4j.xml** - Config para generar .exe con Launch4j
- **build-native.ps1** - Script automatizado para compilación nativa
- **src/main/resources/META-INF/native-image/** - Config de GraalVM

### Documentación (Nuevos):
- **README_EXE.md** - Documentación técnica
- **QUICK_START_EXE.md** - Guía rápida
- **CAMBIOS_REALIZADOS.md** - Resumen de cambios
- **PROYECTO_ACTUALIZADO.md** - Estado general del proyecto
- **README_INDICE.md** - Este archivo

### Código (Sin cambios):
- **src/main/java/com/revisor/scripts/SqlEncodingConverter.java** - Aplicación principal
- **test_sql_files/** - Archivos de prueba

---

## 🚀 Guía Rápida de Inicio (30 segundos)

### Opción A: Ejecutable totalmente independiente (sin Java)
```powershell
# 1. Descargar GraalVM: https://www.graalvm.org/downloads/
# 2. Instalar Visual Studio Build Tools (Desktop development with C++)
# 3. Ejecutar:
$env:JAVA_HOME = "C:\ruta\a\graalvm"
cd c:\Area\Formacion\Java\RevisorScriptsBBDD
.\build-native.ps1

# Resultado: target\sql-converter.exe (sin dependencias de Java)
```

### Opción B: Ejecutable más simple (requiere Java en cliente)
```powershell
# 1. Descargar Launch4j: http://launch4j.sourceforge.net/
# 2. Compilar: mvn clean package
# 3. Abrir launch4j.xml con Launch4j y hacer clic en Build

# Resultado: target\sql-converter.exe (requiere Java 11+)
```

---

## 📊 Comparativa Rápida

| Necesitas | Recomendación | Documento | Tiempo Setup |
|-----------|---------------|-----------|---|
| Máxima independencia | GraalVM | QUICK_START_EXE.md | 30 min |
| Setup más simple | Launch4j | QUICK_START_EXE.md | 10 min |
| Detalles técnicos | README_EXE.md | README_EXE.md | 20 min lectura |
| Solo JAR tradicional | mvn package | (ninguno necesario) | 1 min |

---

## ✨ Lo que Puedes Hacer Ahora

✅ **Generar ejecutable .exe independiente** (sin Java instalado)  
✅ **Generar ejecutable .exe simple** (con Launch4j)  
✅ **Seguir usando el JAR tradicional** (como antes)  
✅ **Compilar el código normalmente** (sin cambios)  

---

## 🔧 Verificación Rápida

```powershell
# Verificar que todo está en su lugar
cd c:\Area\Formacion\Java\RevisorScriptsBBDD

# Compilar JAR (debería funcionar sin cambios)
mvn clean package

# Verificar que la estructura de GraalVM está presente
Get-ChildItem -Recurse src/main/resources/META-INF/native-image

# Verificar pom.xml tiene el plugin de GraalVM
Select-String "native-maven-plugin" pom.xml
```

---

## 📝 Resumen de Cambios Clave

1. **pom.xml**: Añadido plugin `native-maven-plugin` v0.9.28 (desactivado por defecto)
2. **build-native.ps1**: Script PowerShell para compilación automatizada
3. **launch4j.xml**: Config para alternativa más simple
4. **Configuración GraalVM**: Añadida en `src/main/resources/META-INF/native-image/`
5. **Documentación**: 4 archivos nuevos con instrucciones detalladas

---

## 🎓 Próximos Pasos

1. **Lee**: QUICK_START_EXE.md (5 min)
2. **Elige**: Opción A (GraalVM) u Opción B (Launch4j)
3. **Ejecuta**: Los pasos descritos en tu opción elegida
4. **Usa**: El .exe generado sin dependencias

---

## ❓ Preguntas Frecuentes

**¿Puedo seguir usando el JAR?**  
Sí, nada cambió. Sigue funcionando: `java -jar target/sql-converter-jar-with-dependencies.jar`

**¿Cuál es la diferencia entre GraalVM y Launch4j?**  
- GraalVM: .exe independiente, sin Java necesario, 40-50 MB, compilación lenta (5-10 min)
- Launch4j: .exe simple, requiere Java, 5-10 MB, compilación rápida (1 min)

**¿Necesito cambiar mi código?**  
No. El código es 100% compatible. Solo cambió la configuración de compilación.

**¿Qué opción recomiendan?**  
GraalVM si distribuyes a otros usuarios. Launch4j si es solo para testing rápido.

**¿Es obligatorio cambiar a .exe?**  
No. El JAR sigue funcionando perfectamente como antes.

---

## 📞 Soporte

Si tienes problemas:
1. Consulta README_EXE.md - sección "Troubleshooting"
2. Verifica que Maven está instalado: `mvn -v`
3. Verifica que Java está disponible: `java -version`

---

**¡Proyecto actualizado y listo para crear ejecutables!** 🎉


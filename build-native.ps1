#!/usr/bin/env powershell
<#
.SYNOPSIS
    Script para compilar la aplicación SQL Encoding Converter a ejecutable .exe nativo

.DESCRIPTION
    Este script automatiza la creación del ejecutable usando GraalVM Native Image

.EXAMPLE
    .\build-native.ps1

.NOTES
    Requiere:
    - GraalVM instalado y configurado en JAVA_HOME
    - Visual Studio Build Tools con soporte para C++
#>

param(
    [switch]$Clean = $false,
    [string]$GraalVMPath = ""
)

# Colores para output
$ErrorColor = "Red"
$SuccessColor = "Green"
$InfoColor = "Cyan"

function Write-Success {
    param([string]$Message)
    Write-Host "✓ $Message" -ForegroundColor $SuccessColor
}

function Write-Error-Custom {
    param([string]$Message)
    Write-Host "✗ $Message" -ForegroundColor $ErrorColor
}

function Write-Info {
    param([string]$Message)
    Write-Host "ℹ $Message" -ForegroundColor $InfoColor
}

Write-Host ""
Write-Host "╔════════════════════════════════════════════════════════╗" -ForegroundColor Cyan
Write-Host "║  SQL Encoding Converter - Build Native Executable      ║" -ForegroundColor Cyan
Write-Host "╚════════════════════════════════════════════════════════╝" -ForegroundColor Cyan
Write-Host ""

# Verificar si estamos en el directorio correcto
if (!(Test-Path "pom.xml")) {
    Write-Error-Custom "Error: No se encontró pom.xml. Ejecuta este script desde la raíz del proyecto."
    exit 1
}

# Verificar Java/GraalVM
Write-Info "Verificando GraalVM..."
$javaHome = $env:JAVA_HOME
if ([string]::IsNullOrEmpty($javaHome)) {
    if ($GraalVMPath -ne "") {
        $env:JAVA_HOME = $GraalVMPath
        Write-Info "JAVA_HOME configurado a: $GraalVMPath"
    } else {
        Write-Error-Custom "Error: JAVA_HOME no está configurado"
        Write-Info "Por favor configura JAVA_HOME apuntando a tu instalación de GraalVM"
        Write-Info "Ejemplo: `$env:JAVA_HOME = 'C:\graalvm\graalvm-jdk-11'"
        exit 1
    }
}

# Verificar versión de Java
Write-Info "Versión de Java:"
& java -version 2>&1
$lastExitCode = $?
if (!$lastExitCode) {
    Write-Error-Custom "Error: No se puede ejecutar java"
    exit 1
}

# Verificar Maven
Write-Info "Verificando Maven..."
$mvnVersion = mvn -v 2>&1 | Select-Object -First 1
if ($LASTEXITCODE -ne 0) {
    Write-Error-Custom "Error: Maven no está disponible. Asegúrate de que Maven esté instalado."
    exit 1
}
Write-Success "Maven encontrado: $mvnVersion"

# Limpiar si es necesario
if ($Clean) {
    Write-Info "Limpiando compilaciones anteriores..."
    mvn clean
    if ($LASTEXITCODE -ne 0) {
        Write-Error-Custom "Error al limpiar"
        exit 1
    }
    Write-Success "Limpieza completada"
}

# Compilar proyecto
Write-Info "Compilando proyecto..."
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Error-Custom "Error al compilar el JAR"
    exit 1
}
Write-Success "Compilación JAR completada"

# Compilar nativo
Write-Info "Compilando ejecutable nativo (esto puede tardar varios minutos)..."
mvn -Dgraalvm.buildtools.skip=false native:compile
if ($LASTEXITCODE -ne 0) {
    Write-Error-Custom "Error al compilar nativo"
    Write-Error-Custom ""
    Write-Info "Posibles soluciones:"
    Write-Info "1. Verifica que Visual Studio Build Tools esté instalado"
    Write-Info "2. Instala 'Desktop development with C++'"
    Write-Info "3. Ejecuta este script desde Developer Command Prompt de Visual Studio"
    Write-Info "4. Asegúrate de que GraalVM está correctamente configurado"
    exit 1
}

# Verificar que el .exe fue creado
if (Test-Path "target\sql-converter.exe") {
    Write-Host ""
    Write-Host "╔════════════════════════════════════════════════════════╗" -ForegroundColor Green
    Write-Host "║  ¡Compilación completada exitosamente!                 ║" -ForegroundColor Green
    Write-Host "╚════════════════════════════════════════════════════════╝" -ForegroundColor Green
    Write-Host ""
    Write-Success "Ejecutable creado: target\sql-converter.exe"
    Write-Host ""
    Write-Info "Uso:"
    Write-Host "  .\target\sql-converter.exe `"C:\ruta\a\tus\scripts\sql`""
    Write-Host ""
    Write-Info "Información del ejecutable:"
    $exeInfo = Get-Item "target\sql-converter.exe"
    Write-Host "  Tamaño: $($exeInfo.Length / 1MB -as [int]) MB"
    Write-Host "  Ubicación: $($exeInfo.FullName)"
    Write-Host ""
} else {
    Write-Error-Custom "Error: El archivo .exe no fue creado"
    exit 1
}

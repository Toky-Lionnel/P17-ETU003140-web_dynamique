@echo off
:: Variables
set SRC_DIR=src 
set BUILD_DIR=build
set CLASSES_DIR=%BUILD_DIR%\WEB-INF\classes
set LIB_DIR=lib
set TOMCAT_WEBAPPS=C:\apache-tomcat-10.1.28\webapps
set WAR_NAME=ETU003140.war

:: Étape 1 : Nettoyer l'ancien build
echo Nettoyage des fichiers existants...
if exist %BUILD_DIR% (
    rmdir /s /q %BUILD_DIR%
)
if exist %WAR_NAME% (
    del %WAR_NAME%
)

:: Étape 2 : Créer la structure des dossiers
echo Creation de la structure des dossiers...
mkdir %BUILD_DIR%
mkdir %BUILD_DIR%\WEB-INF
mkdir %CLASSES_DIR%

:: Étape 3 : Compilation des fichiers Java
echo Compilation des fichiers Java...
javac -d %CLASSES_DIR% -cp "%LIB_DIR%\*" src\main\java\*.java

if %errorlevel% neq 0 (
    echo Erreur lors de la compilation. Verifiez votre code Java.
    pause
    exit /b %errorlevel%
)

:: Étape 4 : Copier le fichier web.xml
echo Copie de web.xml...
copy "src\main\webapp\WEB-INF\web.xml" "%BUILD_DIR%\WEB-INF\"

:: Copier le fichier html
echo Copie du fichier html...
copy "*html" "%BUILD_DIR%\"

:: Copier le fichier jsp
echo Copie des fichiers jsp...
copy "*.jsp" "%BUILD_DIR%\"

:: Étape 5 : Création du fichier .war
echo Creation de l'archive .war...
jar -cvf ../%WAR_NAME% -C %BUILD_DIR% .


if %errorlevel% neq 0 (
    echo Erreur lors de la creation du fichier .war.
    pause
    exit /b %errorlevel%
)

:: Étape 6 : Déploiement sur Tomcat
echo Deploiement sur Tomcat...
cd ..
copy %WAR_NAME% %TOMCAT_WEBAPPS%

echo Deploiement termine avec succes !
pause

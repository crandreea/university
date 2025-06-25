@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  ProjectServer startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and PROJECT_SERVER_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\ProjectServer-1.0-SNAPSHOT.jar;%APP_HOME%\lib\ProjectPersistance-1.0-SNAPSHOT.jar;%APP_HOME%\lib\ProjectNetworking-1.0-SNAPSHOT.jar;%APP_HOME%\lib\ProjectServices-1.0-SNAPSHOT.jar;%APP_HOME%\lib\ProjectModel-1.0-SNAPSHOT.jar;%APP_HOME%\lib\hibernate-community-dialects-6.4.4.Final.jar;%APP_HOME%\lib\hibernate-core-6.4.4.Final.jar;%APP_HOME%\lib\hibernate-validator-8.0.0.Final.jar;%APP_HOME%\lib\jakarta.el-4.0.2.jar;%APP_HOME%\lib\sqlite-jdbc-3.45.0.0.jar;%APP_HOME%\lib\log4j-core-2.24.3.jar;%APP_HOME%\lib\log4j-api-2.24.3.jar;%APP_HOME%\lib\javafx-fxml-23-mac-aarch64.jar;%APP_HOME%\lib\javafx-fxml-23.jar;%APP_HOME%\lib\javafx-controls-23-mac-aarch64.jar;%APP_HOME%\lib\javafx-controls-23.jar;%APP_HOME%\lib\javafx-graphics-23-mac-aarch64.jar;%APP_HOME%\lib\javafx-graphics-23.jar;%APP_HOME%\lib\javafx-base-23-mac-aarch64.jar;%APP_HOME%\lib\javafx-base-23.jar;%APP_HOME%\lib\jakarta.persistence-api-3.1.0.jar;%APP_HOME%\lib\jakarta.transaction-api-2.0.1.jar;%APP_HOME%\lib\jboss-logging-3.5.0.Final.jar;%APP_HOME%\lib\hibernate-commons-annotations-6.0.6.Final.jar;%APP_HOME%\lib\jandex-3.1.2.jar;%APP_HOME%\lib\classmate-1.5.1.jar;%APP_HOME%\lib\byte-buddy-1.14.11.jar;%APP_HOME%\lib\jaxb-runtime-4.0.2.jar;%APP_HOME%\lib\jaxb-core-4.0.2.jar;%APP_HOME%\lib\jakarta.xml.bind-api-4.0.0.jar;%APP_HOME%\lib\jakarta.inject-api-2.0.1.jar;%APP_HOME%\lib\antlr4-runtime-4.13.0.jar;%APP_HOME%\lib\jakarta.validation-api-3.0.2.jar;%APP_HOME%\lib\jakarta.el-api-4.0.0.jar;%APP_HOME%\lib\slf4j-api-1.7.36.jar;%APP_HOME%\lib\spring-context-6.1.14.jar;%APP_HOME%\lib\spring-aop-6.1.14.jar;%APP_HOME%\lib\spring-beans-6.1.14.jar;%APP_HOME%\lib\spring-expression-6.1.14.jar;%APP_HOME%\lib\spring-core-6.1.14.jar;%APP_HOME%\lib\grpc-netty-shaded-1.54.0.jar;%APP_HOME%\lib\grpc-protobuf-1.54.0.jar;%APP_HOME%\lib\grpc-stub-1.54.0.jar;%APP_HOME%\lib\proto-google-common-protos-2.9.0.jar;%APP_HOME%\lib\protobuf-java-3.22.0.jar;%APP_HOME%\lib\javax.annotation-api-1.3.2.jar;%APP_HOME%\lib\angus-activation-2.0.0.jar;%APP_HOME%\lib\jakarta.activation-api-2.1.1.jar;%APP_HOME%\lib\spring-jcl-6.1.14.jar;%APP_HOME%\lib\micrometer-observation-1.12.11.jar;%APP_HOME%\lib\grpc-core-1.54.0.jar;%APP_HOME%\lib\grpc-protobuf-lite-1.54.0.jar;%APP_HOME%\lib\grpc-api-1.54.0.jar;%APP_HOME%\lib\guava-31.1-android.jar;%APP_HOME%\lib\error_prone_annotations-2.18.0.jar;%APP_HOME%\lib\perfmark-api-0.25.0.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\txw2-4.0.2.jar;%APP_HOME%\lib\istack-commons-runtime-4.1.1.jar;%APP_HOME%\lib\micrometer-commons-1.12.11.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\checker-qual-3.12.0.jar;%APP_HOME%\lib\j2objc-annotations-1.3.jar;%APP_HOME%\lib\gson-2.9.0.jar;%APP_HOME%\lib\annotations-4.1.1.4.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.21.jar;%APP_HOME%\lib\grpc-context-1.54.0.jar


@rem Execute ProjectServer
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %PROJECT_SERVER_OPTS%  -classpath "%CLASSPATH%" StartRpcServer %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable PROJECT_SERVER_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%PROJECT_SERVER_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

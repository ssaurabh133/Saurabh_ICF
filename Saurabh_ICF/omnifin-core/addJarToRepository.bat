REM SET JAR_FILE_NAME=posadapterconf.jar
REM SET ARTIFACT_ID=posadapterconf

SET JAR_FILE_NAME=sun.misc.base64decoder.jar
SET ARTIFACT_ID=sun.misc.base64decoder
SET ARTIFACT_VERSION=1.9
REM poi-ooxml-3.6-20091214
REM poi-ooxml-schemas-3.6-20091214

SET PATH_TO_FILE=D:\WorkSpaceNewBranch\IndostarICF\INDOSTAR_ICF_DEV\omnifin-core\common\app\components\infra\src\main\webapp\WEB-INF\lib\%JAR_FILE_NAME%
SET GROUP_ID=com.a3s.omnifin.lib.external
SET PACKAGING=jar

mvn install:install-file -Dfile=%PATH_TO_FILE% -DgroupId=%GROUP_ID% -DartifactId=%ARTIFACT_ID% -Dversion=%ARTIFACT_VERSION% -Dpackaging=%PACKAGING%

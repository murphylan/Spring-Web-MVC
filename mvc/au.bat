set local_driver=%cd:~0,2%
set local_path=%cd%

call mvn clean install eclipse:eclipse -Dmaven.test.skip=true

%local_driver%
cd %local_path%

pause 
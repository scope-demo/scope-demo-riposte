./gradlew clean

echo "[TESTS] Waiting for the server...."
until [ "`curl --silent --show-error --connect-timeout 1 -I http://riposte-server:8080|grep 'HTTP'`" != "" ];
do
  echo --- sleeping for 10 seconds
  sleep 10
done

echo "[TESTS] Executing Instrumented Tests..."
./gradlew functionalTest -DremoteTestEnv=docker

echo "[TESTS] Complete"
# !/bin/bash

APP_HOME=/data/Project/backend
APP_MAIN=xxx.jar

pid=0

getPID(){
   javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
   if [ -n "$javaps" ]; then
      pid=`echo $javaps | awk '{print $1}'`
   else
      pid=0
   fi
}

getServerStatus(){
   getPID
   echo "==============================================================================================="
   if [ $pid -ne 0 ]; then
      echo "$APP_MAIN is running(PID=$pid)"
      echo "==============================================================================================="
   else
      echo "$APP_MAIN is not running"
      echo "==============================================================================================="
   fi
}

getServerStatus
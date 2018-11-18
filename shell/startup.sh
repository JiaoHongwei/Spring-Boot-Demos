# !/bin/bash
JAVA_HOME="/opt/jvm/jdk1.8.0_141"
APP_HOME="/data/Project/backend"
APP_LOG="$APP_HOME/logs"
APP_LIBS="$APP_HOME/libs"
APP_MAIN="xxx.jar"

# JVM启动参数
# -server：一定要作为第一个参数，多个CPU时性能佳
# -Xloggc：记录GC日志，建议写成绝对路径，如此便可在任意目录下执行该shell脚本
#JAVA_OPTS="-server -Xms1048m -Xmx1048m -Xloggc:$APP_HOME/logs/gc.log"
JAVA_OPTS="-server -Xms1048m -Xmx1048m"

# 加载依赖到classpath
#for lib in ${APP_HOME}/libs/*.jar;
#   do CLASSPATH=${lib}:"${CLASSPATH}";
#done

#for setting in ${APP_HOME}/config/*.setting;
#   do CLASSPATH=config/${setting##*/}:"${CLASSPATH}";
#done

#CLASSPATH=log4j.properties:"${CLASSPATH}"
#CLASSPATH=${CLASSPATH}:.:${APP_HOME}/dailyload.jar

# 初始化参数
#if [ -n "$1" ] ;
#then
#   dt=$1
#   if ! (echo $dt | grep -Eq "[0-9]{4}-[0-9]{2}-[0-9]{2}" && date -d $dt +%Y%m%d >/dev/null 2>&1;)
#   then
#       echo "param can not convert to date : $dt"
#       exit 1
#   fi
#else
#   dt=`date -d "0 days ago" "+%Y-%m-%d"`
#fi

args="$*"

# 初始化进程ID（0表示未启动）
pid=0

# 获取Java应用的PID
getPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        pid=`echo $javaps | awk '{print $1}'`
    else
        pid=0
    fi
}

# 启动Java应用程序
# ------------------------------------------------------------------------------------------------------
# 1、调用getPID()函数，刷新$pid全局变量
# 2、若程序已经启动（$pid不等于0），则提示程序已启动
# 3、若程序未被启动，则执行启动命令
# 4、启动命令执行后，再次调用getPID()函数
# 5、若步骤4执行后，程序的PID不等于0，则打印Success，反之打印Failed
# 注意：[echo -n]表示打印字符后不换行
# 注意：[nohup command > /path/nohup.log &]是将作业输出到nohup.log，否则它会输出到该脚本目录下的nohup.out中
# ------------------------------------------------------------------------------------------------------
startup(){
    getPID
    echo "==============================================================================================="
    if [ $pid -ne 0 ]; then
        echo "$APP_MAIN already started(PID=$pid)"
        echo "==============================================================================================="
    else
        echo -n "Starting $APP_MAIN"

        #方式1 可执行jar包(jar的MANIFEST.MF文件已指定主函数和依赖)
        #nohup $JAVA_HOME/bin/java $JAVA_OPTS -jar dailyload.jar $dt > $APP_LOG/nohup.log &
        nohup java -Dloader.path=$APP_LIBS -jar $APP_HOME/xxx.jar > /dev/null &

        #方式2 不可执行jar包(需自己加载依赖到classpath)
        #nohup $JAVA_HOME/bin/java $JAVA_OPTS -Dfile.encoding=UTF-8 -classpath $CLASSPATH $APP_MAIN $args > $APP_LOG/nohup.log &
        #nohup $JAVA_HOME/bin/java $JAVA_OPTS -Dfile.encoding=UTF-8 -classpath $CLASSPATH $APP_MAIN $args > /dev/null &

        getPID
        if [ $pid -ne 0 ]; then
            echo "(PID=$pid)...[Success]"
            echo "==============================================================================================="
        else
            echo "[Failed]"
            echo "==============================================================================================="
        fi
    fi
}

# 启动
startup
#!/bin/sh

PORT=19081

SERVER_NAME=tool-box-service.jar

AT=dev

CONF_PATH=./conf/application.yml,./conf/application-dev.yml

server_pid=0

check_pid(){
    javaps=`ps -ef  | grep $SERVER_NAME |grep -v grep `

    if [ -n "$javaps" ]; then
        server_pid=`echo $javaps | awk '{print $2}'`
    else
        server_pid=0
    fi
}

start(){
    check_pid

    if [ $server_pid -ne 0 ]; then
        echo "================================"
        echo "warn:$SERVER_NAME already started! (pid=$server_pid)"
        echo "================================"
    else
        echo -n "Starting $SERVER_NAME..."

		java -Xms1024m -Xmx1024m  -Dserver.port=$PORT -Dloader.path=./lib/ -jar $SERVER_NAME --spring.profiles.active=$AT --spring.config.location=$CONF_PATH --publicKey=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKtjXqIZD7PaJPYoKiArTIFc74OakbplY/Iu7pr1atcP5f0Dm8Ot3TiUoQEbxX6u5ivIHclwKYe/PEWAJl4WYl0CAwEAAQ==  >/dev/null 2>&1 &
        
        check_pid
        if [ $server_pid -ne 0 ]; then
            echo "(pid=$server_pid) [OK]"
        else
            echo "[Failed]"
        fi       
    fi
}

stop(){
    check_pid

    if [ $server_pid -ne 0 ]; then
        echo -n "Stopping $SERVER_NAME...(pid=$server_pid) "
		kill $server_pid
		sleep 2
        if [ $? -eq 0 ];then
            echo "[OK]"
        else
            echo "[Failed]"
        fi

        check_pid
        if [ $server_pid -ne 0 ]; then
            stop
        fi
    else
        echo "================================"
        echo "warn:$SERVER_NAME is not running"
        echo "================================"
    fi
}

status(){
    check_pid

    if [ $server_pid -ne 0 ]; then
        echo "$SERVER_NAME is running! (pid=$server_pid)"
    else
        echo "$SERVER_NAME is not running"
    fi
}

case "$1" in
    'start')
        start
        ;;
    'stop')
        stop
        ;;
    'restart')
        stop
        start
        ;;
    'status')
        status
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status}"
exit 1
esac
exit 0
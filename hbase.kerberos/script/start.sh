export JAVA_HOME=/usr/java/jdk1.6.0_31
export APP_HOME=/opt/test/me.huiwen.example.hbase.kerberos-0.0.1-SNAPSHOT
export CLASSPATH=$APP_HOME/main
for f in $APP_HOME/lib/*.jar
do
 export CLASSPATH=$CLASSPATH:$f
 # do something on $f
done
echo $CLASSPATH
$JAVA_HOME/bin/java -classpath $CLASSPATH -Dsun.security.krb5.debug=true -Djava.security.auth.login.config=./main/jaas.conf me.huiwen.example.hbase.kerberos.HBaseTest

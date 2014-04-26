export HBASE_OPTS="$HBASE_OPTS -Djava.security.auth.login.config=jaas.conf"
export HBASE_OPTS="-Xmx268435456 -XX:+HeapDumpOnOutOfMemoryError -XX:+UseConcMarkSweepGC -XX:-CMSConcurrentMTEnabled -XX:+CMSIncrementalMode $HBASE_OPTS"
export HBASE_CLASSPATH=`echo $HBASE_CLASSPATH | sed -e "s|$ZOOKEEPER_CONF:||"`

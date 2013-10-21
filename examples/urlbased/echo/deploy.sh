#!/bin/bash
FQN=$(readlink -f $0)
DIRNAME=$(dirname ${FQN})
container=$1
scripts=$(readlink -f $DIRNAME/../../../scripts)

function deployGlassfish {
	$scripts/gf-deploy-application ${DIRNAME}/echo-connector/target/echo-connector.rar
	$scripts/gf-create-connector-connection-pool echo echo-connector com.ja.rsc.echo.api.EchoConnectionFactory Url=http://url1 Username=username1 Password=password1
	$scripts/gf-create-connector-resource echo
	$scripts/gf-create-connector-connection-pool echo2 echo-connector com.ja.rsc.echo.api.EchoConnectionFactory Url=http://url2 Username=username2 Password=password2
	$scripts/gf-create-connector-resource echo2
	$scripts/gf-deploy-application ${DIRNAME}/demo-app/target/demo-app.war
}

function deployJBoss {
	[[ -z "$JBOSS_HOME" ]] && echo "JBOSS_HOME not defined" && exit 1
	$scripts/jb-deploy-application ${DIRNAME}/echo-connector/target/echo-connector.rar
	sleep 5
	$scripts/jb-create-resource-adapter echo-connector.rar 
	$scripts/jb-create-connection-definition echo echo-connector.rar com.ja.rsc.echo.EchoManagedConnectionFactory Url=http://url1 Username=username1 Password=password1
	$scripts/jb-create-connection-definition echo2 echo-connector.rar com.ja.rsc.echo.EchoManagedConnectionFactory Url=http://url2 Username=username2 Password=password2
	$scripts/jb-reload
	sleep 5
	$scripts/jb-deploy-application ${DIRNAME}/demo-app/target/demo-app.war
}

case "$container" in
"glassfish")
	deployGlassfish
	;;
"jboss")
	deployJBoss
	;;
*)
	echo "usage $0 {glassfish|jboss}"
	;;
esac

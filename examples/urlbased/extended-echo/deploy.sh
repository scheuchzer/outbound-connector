#!/bin/bash
FQN=$(readlink -f $0)
DIRNAME=$(dirname ${FQN})
container=$1
scripts=$(readlink -f $DIRNAME/../../../scripts)

function deployGlassfish {
	$scripts/gf-deploy-application extended-echo-connector/target/extended-echo-connector.rar
	$scripts/gf-create-connector-connection-pool extendedEcho extended-echo-connector com.ja.rsc.extendedecho.api.ExtendedEchoConnectionFactory Url=http://url1 Username=username1 Password=password1 Token=token1
	$scripts/gf-create-connector-resource extendedEcho
	$scripts/gf-create-connector-connection-pool extendedEcho2 extended-echo-connector com.ja.rsc.extendedecho.api.ExtendedEchoConnectionFactory Url=http://url2 Username=username2 Password=password2 token2
	$scripts/gf-create-connector-resource extendedEcho2
	$scripts/gf-deploy-application extended-demo-app/target/extended-demo-app.war
}

function deployJBoss {
	[[ -z "$JBOSS_HOME" ]] && echo "JBOSS_HOME not defined" && exit 1
	$scripts/jb-deploy-application extended-echo-connector/target/extended-echo-connector.rar
	sleep 5
	$scripts/jb-create-resource-adapter extended-echo-connector.rar 
	$scripts/jb-create-connection-definition extendedEcho extended-echo-connector.rar com.ja.rsc.extendedecho.ExtendedEchoManagedConnectionFactory Url=http://url1 Username=username1 Password=password1 Token=token1
	$scripts/jb-create-connection-definition extendedEcho2 extended-echo-connector.rar com.ja.rsc.extendedecho.ExtendedEchoManagedConnectionFactory Url=http://url2 Username=username2 Password=password2 Token=token2
	$scripts/jb-reload
	sleep 5
	$scripts/jb-deploy-application extended-demo-app/target/extended-demo-app.war
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

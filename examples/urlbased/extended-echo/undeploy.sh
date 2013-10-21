#!/bin/bash
FQN=$(readlink -f $0)
DIRNAME=$(dirname ${FQN})
container=$1
scripts=$(readlink -f $DIRNAME/../../../scripts)
function undeployGlassfish {
	$scripts/gf-undeploy-application extended-demo-app
	$scripts/gf-delete-connector-resource extendedEcho
	$scripts/gf-delete-connector-connection-pool extendedEcho
	$scripts/gf-delete-connector-resource extendedEcho2
	$scripts/gf-delete-connector-connection-pool extendedEcho2
}

function undeployJBoss {
	[[ -z "$JBOSS_HOME" ]] && echo "JBOSS_HOME not defined" && exit 1
	$scripts/jb-undeploy-application extended-demo-app.war
	$scripts/jb-delete-resource-adapter extended-echo-connector.rar
	$scripts/jb-undeploy-application extended-echo-connector.rar
}

case "$container" in
"glassfish")
	undeployGlassfish
	;;
"jboss")
	undeployJBoss
	;;
*)
	echo "usage $0 {glassfish|jboss}"
	;;
esac

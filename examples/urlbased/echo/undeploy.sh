#!/bin/bash
FQN=$(readlink -f $0)
DIRNAME=$(dirname ${FQN})
container=$1
scripts=$(readlink -f $DIRNAME/../../../scripts)
function undeployGlassfish {
	$scripts/gf-undeploy-application demo-app
	$scripts/gf-delete-connector-resource echo
	$scripts/gf-delete-connector-connection-pool echo
	$scripts/gf-delete-connector-resource echo2
	$scripts/gf-delete-connector-connection-pool echo2
}

function undeployJBoss {
	[[ -z "$JBOSS_HOME" ]] && echo "JBOSS_HOME not defined" && exit 1
	$scripts/jb-undeploy-application demo-app.war
	$scripts/jb-delete-resource-adapter echo-connector.rar
	$scripts/jb-undeploy-application echo-connector.rar
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

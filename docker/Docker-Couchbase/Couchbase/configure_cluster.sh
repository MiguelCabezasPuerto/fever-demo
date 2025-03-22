#!/bin/bash

COUCH_HOST=127.0.0.1
COUCH_PORT=8091
COUCH_USER=fever_admin
COUCH_PASSWORD=fever_admin
COUCH_BUCKET_EVENTS=EVENTS
COUCH_CLUSTER_RAMSIZE=3006
COUCH_CLUSTER_INDEX_RAMSIZE=512
COUCH_BUCKET_RAMSIZE=1024
COUCH_HOST_URL=couchbase://localhost
COUCH_CLUSTER_HEALTH_OK="healthy active"


# Enables job control
set -m


# Check if couchbase server is up
check_db() {
  curl --silent http://127.0.0.1:8091/pools > /dev/null
  echo $?
}

# Check if cluster is ready
check_cluster() {
  result=$(curl -u $COUCH_USER:$COUCH_PASSWORD --write-out %{http_code} --silent --output /dev/null http://127.0.0.1:8091/pools/default/)
  echo $result
}

# Check if bucket is available
check_bucket() {
  status_code=$(curl -u $COUCH_USER:$COUCH_PASSWORD --write-out %{http_code} --silent --output /dev/null http://127.0.0.1:8091/pools/default/buckets/$1)
  echo $status_code
}


echo "Put couchbase-server in background"

/entrypoint.sh couchbase-server &

# Wait until it's ready
until [[ $(check_db) = 0 ]]; do
  >&2 echo "Waiting for Couchbase Server to be available ..."
  sleep 1
done

if [ $(check_cluster) = 404 ]
then
  echo "$(date +"%T") Init cluster ........."
  couchbase-cli cluster-init -c $COUCH_HOST --cluster-username $COUCH_USER --cluster-password $COUCH_PASSWORD --services data,index,query --cluster-ramsize $COUCH_CLUSTER_RAMSIZE --cluster-index-ramsize $COUCH_CLUSTER_INDEX_RAMSIZE
else
  echo "$(date +"%T") Cluster already initialized ........."
fi

# Create user
echo "$(date +"%T") Creating user ........."
couchbase-cli user-manage -c $COUCH_HOST:$COUCH_PORT -u $COUCH_USER -p $COUCH_PASSWORD --set --rbac-username $COUCH_USER--rbac-password $COUCH_PASSWORD --rbac-name $COUCH_USER --roles bucket_full_access[*] --auth-domain local

# Create the buckets
echo "$(date +"%T") Creating buckets ........."
# Create bucket for operations
if [ $(check_bucket $COUCH_BUCKET_EVENTS) = 404 ]
then
  echo "$(date +"%T") Creating bucket for events ........."
  couchbase-cli bucket-create -c $COUCH_HOST:$COUCH_PORT --username $COUCH_USER --password $COUCH_PASSWORD --bucket $COUCH_BUCKET_EVENTS --bucket-type couchbase --bucket-ramsize $COUCH_BUCKET_RAMSIZE --enable-flush 0
else
   echo "$(date +"%T") Bucket for events already initialized ........."
fi

# Need to wait until query service is ready to process N1QL queries
echo "$(date +"%T") Waiting ........."
sleep 60


echo "Put couchbase-server in foreground"
fg 1

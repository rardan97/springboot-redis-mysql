#!/bin/sh
# wait-for-it.sh: wait for MySQL and Redis to be ready before running command

set -e

MYSQL_HOST="$1"
REDIS_HOST="$2"
shift 2
CMD="$@"

echo "Waiting for MySQL at $MYSQL_HOST:3306..."
until nc -z "$MYSQL_HOST" 3306; do
  echo "MySQL is unavailable - sleeping"
  sleep 3
done
echo "MySQL is up!"

echo "Waiting for Redis at $REDIS_HOST:6379..."
until redis-cli -h "$REDIS_HOST" ping | grep -q PONG; do
  echo "Redis not ready yet - sleeping"
  sleep 3
done
echo "Redis is up!"

echo "All dependencies are up - executing command:"
exec "$@"
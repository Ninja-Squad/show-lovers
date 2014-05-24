# Kill old container
echo "kill old container"
docker kill $(cat docker.pid)

# Build new image
echo "build image"
docker build -t ninjasquad/tp-ng .

# Run image
echo "run image"
DOCKER_CONTAINER=$(docker run -p 8081:8081 -d ninjasquad/tp-ng)

# Save container id
rm docker.pid
echo "$DOCKER_CONTAINER" >> docker.pid
# Kill old container
echo "kill old container"
sudo docker kill $(cat docker.pid)

# Build new image
echo "build image"
sudo docker build -t ninjasquad/tp-ng .

# Run image
echo "run image"
DOCKER_CONTAINER=$(sudo docker run -p 80:80 -d ninjasquad/tp-ng)

# Save container id
rm docker.pid
echo "$DOCKER_CONTAINER" >> docker.pid
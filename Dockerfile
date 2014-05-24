from base
maintainer Cédric Exbrayat <cedric@ninja-squad.com>

# Install prerequisites
run apt-get update
run apt-get install -y software-properties-common

# Install java8
run add-apt-repository -y ppa:webupd8team/java
run apt-get update
run echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
run apt-get install -y --force-yes oracle-java8-installer

# Install tools
run apt-get install -y maven git

# Build fluent-http
run git clone https://github.com/CodeStory/fluent-http.git
run cd fluent-http && mvn clean install -DskipTests

# Add sources
add ./server ./server
workdir ./server

# Build project
run mvn clean compile -DskipTests

# Expose the http port
expose 8081

# Launch server on start
cmd mvn exec:java

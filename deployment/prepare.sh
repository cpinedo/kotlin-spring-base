!/bin/sh

mkdir keys
cd keys

openssl req -x509 -sha256 -nodes -days 365 -newkey rsa:2048 -keyout privateKey.key -out certificate.crt
openssl dhparam 2048 -out dhparam.pem

cd ..
./gradlew clean build docker
cd deployment

docker-compose up


docker build -t vellerzheng/storage-web .

docker run --restart=always --name=storage-web --link file-server:8764  -v /opt/data/fileShareDir/:/opt/data/fileShareDir/ -p 8765:8765 -t vellerzheng/storage-web

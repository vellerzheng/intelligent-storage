

docker build -t vellerzheng/file-server .

docker run --restart=always --name=file-server --link storage-web:8765 -v /opt/data/fileShareDir/:/opt/data/fileShareDir/ -p 8764:8764 -t vellerzheng/file-server

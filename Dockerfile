FROM docker.io/library/nginx:alpine
COPY *.png /usr/share/nginx/html
EXPOSE 80

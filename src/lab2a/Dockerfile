FROM debian:11-slim
RUN apt-get update && apt-get install -y nginx && rm -rf /var/lib/apt/lists/*
RUN mkdir -p /usr/share/nginx/html
COPY products.txt /usr/share/nginx/html/products.txt
EXPOSE 80
CMD ["nginx","-g","daemon off"]
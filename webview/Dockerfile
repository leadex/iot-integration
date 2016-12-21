FROM nginx:1.11.6

MAINTAINER Maxim Ivanov <maxim.ivanov@leadexsystems.ru>

# Configs
RUN rm -f /etc/nginx/conf.d/*
COPY assets/conf.d /etc/nginx/conf.d

# Site content
COPY dist/ /var/www/

# Dockerfile
FROM node:21-alpine3.18

# create destination directory
RUN mkdir -p /usr/src/nuxt-app
WORKDIR /usr/src/nuxt-app

# update and install dependency
RUN apk update && apk upgrade
RUN apk add git

# Install dependencies
COPY package-lock.json /usr/src/nuxt-app/
COPY package.json /usr/src/nuxt-app/
COPY yarn.lock /usr/src/nuxt-app/
RUN npm install

# Copy over the rest
# Configuration and files
COPY app.vue /usr/src/nuxt-app/
COPY tailwind.config.js /usr/src/nuxt-app/
COPY tsconfig.json /usr/src/nuxt-app/
COPY nuxt.config.ts /usr/src/nuxt-app/
# Other folders
COPY assets /usr/src/nuxt-app/assets
COPY components /usr/src/nuxt-app/components
COPY layouts /usr/src/nuxt-app/layouts
COPY middleware /usr/src/nuxt-app/middleware
COPY pages /usr/src/nuxt-app/pages
COPY public /usr/src/nuxt-app/public
COPY server /usr/src/nuxt-app/server
COPY store /usr/src/nuxt-app/store
RUN npm run build

EXPOSE 3000

ENV NUXT_HOST=0.0.0.0
ENV NUXT_PORT=3000

CMD ["node", ".output/server/index.mjs"]

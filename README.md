# Receptai

## Deployment

To setup a deployment-ready instance of the project, you will need to use Docker.
After installing it, simply navigate to the project root and run the following command:

```bash
docker compose up --build
```

After containers start up, the website will be available on http://localhost/ over port 80 by default. The API can be accessed under http://localhost/api/.

## Development

...

## API docs

In the future, documentation will be accessible over Swagger. For the time being, this is contains a short documentation of all endpoints.

### 🔓 POST /api/v1/user/login
### 🔓 POST /api/v1/user/register
### 🔒 GET  /api/v1/user/me

### 🔓 GET  /api/v1/recipe/list
### 🔓 GET  /api/v1/recipe/get/{id}
### 🔒 POST /api/v1/recipe/create

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
Returns a JWT token to be used for authorized requests.
### 🔓 POST /api/v1/user/register
Registers a user with the specified details and returns some slim account information on success.
### 🔒 GET  /api/v1/user/me
Returns personal data of the current user.

### 🔓 GET  /api/v1/recipe/list
Returns an array of all recipe objects.
### 🔓 GET  /api/v1/recipe/get/{id}
Returns recipe information for the specified recipe ID.
### 🔒 POST /api/v1/recipe/create
Creates a recipe in the database.

---
Anything that is not marked as a path variable takes arguments as a JSON formatted body (POST methods)

Internally in the code they are defined as DTOs which get automatically serialized and deserialized between JSON and Java objects.

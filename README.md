# Recipe website

The project is a recipe website, where users can create and view recipes, leave feedback,
get useful cooking tips and inspiration and search for recipes.

## The Team

Tasty Bytes team consists out of 3 members:
- Dominykas Svetikas
- Erikas Laužadis
- Dovydas Mašinauskis

## Technical task

Technical tasks that have been attempted to be implemented can be described as:

### Recipe creation
Users should be able to create their own profiles (name, surname, profile picture), leave comments and rate recipes.
Users should be able to create, edit and manage their recipes in the editor panel, and see feedback and reviews left by users.

### Admin panel
A user with admin rights should be able to see and edit all recipes, comments, profiles, etc. on the page. These need to be administered and deleted.

### Viewing recipes
Recipes should be able to be viewed by users: the author, ingredients, preparation instructions, photos of the result, video of the preparation, nutritional value, preparation time, number of servings should be clearly displayed.

Users should be able to leave a review, rate, score.
The recipe should be easy and intuitive to print.
The user should be able to share the recipe intuitively by copying the url.

### Recipe suggestions
The user should be offered relevant recipes based on the recipes they have viewed.

The current recipe offers should be displayed on the home page of the website and at the bottom of the specific recipe page.

### Browse, search for recipes
Recipes should be sortable by name, date, etc.

Users should be able to search for recipes by name, various attributes such as dish, ingredients, cooking time, etc., and select filters (vegan, gluten-free, etc.).

Recipes should be able to be grouped into different categories or tags such as starters, soups, salads, main courses, desserts, etc.

## Architecture

From top-down view, the project uses a layered architecture using Docker containers to run MySQL database, Spring Boot backend, Vue + Nuxt frontend and Nginx to proxy requests to frontend and API under the same umbrella.

A very simplified overview would look like so:
![image](https://github.com/Tasty-Bytes-Team/Receptai/assets/32238647/054f3236-f1c8-409b-9859-62b0c10a8d5e)

## Testing

Tests have been written for both frontend and backend and are available under the framework-specific directories under `Receptai-server` and `Receptai.client` directories.

Test status can be inspected by checking GitHub workflows associated with push commits.

## Deployment and development setup guide

### Deployment

To setup a deployment-ready instance of the project, you will need to use Docker.
After installing it, simply navigate to the project root and run the following command:

```bash
docker compose up --build
```

After containers start up, the website will be available on http://localhost/ over port 80 by default. The API can be accessed under http://localhost/api/.

There will be an admin account created by default with these credentials: email: `admin@localhost` and password: `admin`.

### Development


#### Frontend

For **frontend** development, you can run the `docker compose -f docker/... up --build` to boot up the database, backend and PHPMyAdmin. Alternatively, follow the steps provided under backend section.

After you have backend running, you can start the frontend by running these commands:

```sh
npm install
```

```sh
npm run dev
```

#### Backend

The API backend is written in Java Spring Boot. You need to have at least Java 17 installed to run the backend. The API uses Gradle build system so to boot up a version
of the backend, you can run the following command:

```sh
./gradlew bootRun
```

The server will become available at `localhost:8080`.

Note that you will need to have an active MySQL database configured in `application.properties` file.
For your convenience, a database docker compose file is provided at the server root in `testing-db.yaml`. You can run it with the following command:

```sh
docker compose -f testing-db.yaml up
```

This will spin up a MySQL database at port 3306 and a PHPMyAdmin instance at port 8081.

The backend uses Liquidbase for database schema versioning. Changelogs are stored in backend resources and are automatically loaded for migration on application boot.

For now, the changelogs are created through an Intellij plugin called JPA Buddy by creating a difference between a running database and the current entity models in the code.

### API docs

In the future, documentation will be accessible over Swagger. For the time being, this is contains a short documentation of all endpoints.

#### 🔓 POST /api/v1/user/login
Returns a JWT token to be used for authorized requests.
#### 🔓 POST /api/v1/user/register
Registers a user with the specified details and returns some slim account information on success.
#### 🔒 GET  /api/v1/user/me
Returns personal data of the current user.

#### 🔓 GET  /api/v1/recipe/list
Returns an array of all recipe objects.
#### 🔓 GET  /api/v1/recipe/get/{id}
Returns recipe information for the specified recipe ID.
#### 🔒 POST /api/v1/recipe/create
Creates a recipe in the database.

---
Anything that is not marked as a path variable takes arguments as a JSON formatted body (POST methods)

Internally in the code they are defined as DTOs which get automatically serialized and deserialized between JSON and Java objects.

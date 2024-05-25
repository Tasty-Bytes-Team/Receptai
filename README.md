# Recipe website

The project is a recipe website, where users can create and view recipes, leave feedback,
get useful cooking tips and inspiration and search for recipes.

## The Team

Tasty Bytes team consists out of 3 members:
- Dominykas Svetikas
- Erikas Laužadis
- Dovydas Mašinauskis

## WIKI - How to use
This wiki is your one-stop guide to navigating and using all the features our recipe website has to offer.

### Getting Started

- **Creating an Account:** Head to the signup page and register with your email address to create a free profile. This unlocks the ability to save recipes, create your own, and leave reviews.
- **Logging In:** After creating an account, you'll use a login form to access the website whenever you return.
- **Editing your account:** You can edit your name, profile picture, and password by going to the ``/user/profile`` page. Keep in mind that changes may take a while to appear everywhere.

### Browsing all recipes

- **Explore Recipes:** Discover a wide variety of recipes using our search bar, sorting options (by name, date, etc.), or browse by category (starters, main courses, desserts, etc.).
- **Recommendations and Featured Recipes:** Find inspiration on our homepage, which showcases recommended recipes. You can also find recipe suggestions at the bottom of any recipe page.
- **Print Recipes:** Found a recipe you love? Click the print button at the top of the recipe page to easily print it.

### Creating and Sharing Your Culinary Creations

- **Adding Recipes:** Share your culinary genius! Click on the "Create" button in your user dashboard (``/user/dashboard``) or the "Create new recipe" option in your user profile menu. Fill in the details like ingredients, preparation instructions, cooking time, and servings. You can also add photos and even a video to showcase your dish!
- **Editing and Managing Recipes:** Made a mistake or want to update your recipe? No problem! Easily edit your creations and manage them within your profile's "My Recipes" page (``/user/dashboard/my-recipes``).

### User Reviews and Ratings

- **Leaving Feedback:** Help out your fellow foodies by leaving reviews and ratings for recipes you've tried. Share your thoughts and experiences!
- **Reading Reviews:** See what other users think before you try a recipe. Reviews offer valuable insights and tips.

### For Admins Only

- **Admin Panel:** If you've been granted admin privileges, you'll have access to a special panel to manage all user-generated content, including recipes, comments, and profiles. This allows you to maintain the quality of the website's content.

## Technical task

This document outlines the technical functionalities planned for the recipe website:

### User Accounts
- **Account Creation:** Users can register and create their own profiles.
- **Login:** Existing users can log in with their credentials.

![Animation2](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/ebdf16c8-52c7-4232-9279-738796ec010d)

### User Profiles
- **Profile Editing:** Users can edit their profile information, including name, profile picture, and password.

![Animation](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/e4f3ea94-4004-4f52-9569-c957f85dd3b0)

### Recipe Management
- **Recipe Creation:** Users can create new recipes, including text descriptions, ingredients, preparation instructions, and media (photos and videos).
- **Recipe Editing:** Users can edit and update their existing recipes.
- **Recipe Feedback:** Users can view feedback and reviews left on their recipes by other users.

![Animation3](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/0fea02ba-414b-4eda-aedd-3966b491854e)

### Admin Panel
- **Content Management:** Admin users can view, edit, and delete all recipes, comments, create tags and categories.

![Animation4](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/37e66304-6130-41ad-a7c2-dadc71d185b7)

### Recipe Viewing
- **Detailed Information:** Recipe pages display comprehensive information, including author, ingredients, preparation instructions, photos of the final dish, optional preparation videos, nutritional values, cooking time, and number of servings.

![Animation11](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/1bd3a690-eb91-481d-bfda-99e30a835cb7)

### Review System
- **User Reviews:** Users can leave reviews, ratings for published recipes.

![image](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/25b095d2-db68-4e5f-bcf9-0c2ce41900f9)

### Recipe Recommendations
- **Personalized Suggestions:** The system recommends recipes to users based on their viewing recipe.
- **Display:** Recommended recipes are showcased on the homepage and at the bottom of individual recipe pages.

![image](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/82d2efc5-1f3a-4361-8fe2-acd0de9b8f05)
![image](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/faa3ede0-2c22-4052-9e6f-bd8ed3834873)

### Recipe Search and Browsing
- **Sorting and Filtering:** Recipes can be sorted by various criteria like name, date, etc.
- **Search Functionality:** Users can search for recipes by name, dish type, ingredients, cooking time.
- **Recipe Categories:** Recipes can be categorized into groups or tagged with labels for easier browsing (starters, soups, salads, main courses, desserts, etc.).

![Animation12](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/21bc1366-9af7-4504-b38f-6a628876c5fe)

## Architecture

From top-down view, the project uses a layered architecture using Docker containers to run MySQL database, Spring Boot backend, Vue + Nuxt frontend and Nginx to proxy requests to frontend and API under the same umbrella.

A very simplified overview would look like so:

![image](https://github.com/Tasty-Bytes-Team/Receptai/assets/32238647/054f3236-f1c8-409b-9859-62b0c10a8d5e)

## Testing

Tests have been written for both frontend and backend and are available under the framework-specific directories under `Receptai-server` and `Receptai.client` directories.

Test status can be inspected by checking GitHub workflows associated with push commits.

![image](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/3b9315d0-577d-409e-a0a0-8db03437d0e0)
![image](https://github.com/Tasty-Bytes-Team/Receptai/assets/45915900/11ae854d-b127-40dc-be32-95525d36b7f6)

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

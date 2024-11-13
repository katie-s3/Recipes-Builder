# Recipes-Builder
 An application that uses a React frontend and Java backend to build and store recipes. 


## Frontend
The frontend is built using React Bootstrap. 
#### Key Features
- **Recipe Card**: A dynamically rendered component that displays all saved recipes in a grid layout. 


## Backend
The backend is built using the Spring framework with Gradle as the build tool, following the MVC pattern. 
#### Key Features
- **Recipe Processing**: Recipes are parsed from a provided URL using the Spoonacular API. If a recipe is successfully parsed and not found as an existing recipe, it is saved to the SQLite database. 

## Database
The application uses SQLite as the database engine. 

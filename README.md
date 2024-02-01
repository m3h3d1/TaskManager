# TaskManager
 
## URL
API URL: http://localhost:8181
Frontend URL: http://localhost:5173
---
## API Documentation

This document provides information about the APIs.

## Authentication

All endpoints require authentication through the use of a token. You can obtain a token by signing in through the [Sign In API](#sign-in).

## User Endpoints

### Sign In

- **Endpoint:** `/api/login`
- **Method:** `POST`
- **Description:** Authenticate a user.
- **Request:**
  - Body:
    ```json
    {
      "username": "your_username",
      "password": "your_password"
    }
    ```
- **Response:**
  - Status Code: `200 OK`
  - Body:
    ```json
    {
      "token": "your_access_token",
      "username": "your_username",
      "role": "user_role"
    }
    ```

### Sign Up (User)

- **Endpoint:** `/api/register`
- **Method:** `POST`
- **Description:** Register a new user.
- **Request:**
  - Body:
    ```json
    {
      "username": "new_username",
      "password": "new_password"
    }
    ```
- **Response:**
  - Status Code: `201 Created`
  - Body: `"Account successfully registered"`

### Sign Up (Admin)

- **Endpoint:** `/api/register/admin`
- **Method:** `POST`
- **Description:** Register a new admin.
- **Request:**
  - Body:
    ```json
    {
      "username": "admin_username",
      "password": "admin_password"
    }
    ```
  - Query Parameter:
    - `permissionToken`: "123456" (required)
- **Response:**
  - Status Code: `201 Created`
  - Body: `"Account successfully registered"`

### Get All Users

- **Endpoint:** `/user/all`
- **Method:** `GET`
- **Description:** Get a list of all users.
- **Response:**
  - Status Code: `200 OK`
  - Body: List of user details

## Task Endpoints

### Create Task

- **Endpoint:** `/api/tasks`
- **Method:** `POST`
- **Description:** Create a new task.
- **Request:**
  - Body:
    ```json
    {
      "title": "Task Title",
      "description": "Task Description"
    }
    ```
- **Response:**
  - Status Code: `201 Created`
  - Body: Task details

### Get All Tasks

- **Endpoint:** `/api/tasks`
- **Method:** `GET`
- **Description:** Get a list of all tasks for the authenticated user.
- **Response:**
  - Status Code: `200 OK`
  - Body: List of task details

### Update Task

- **Endpoint:** `/api/tasks/{id}`
- **Method:** `PUT`
- **Description:** Update an existing task.
- **Request:**
  - Path Parameter:
    - `id`: UUID of the task
  - Body:
    ```json
    {
      "title": "Updated Task Title",
      "description": "Updated Task Description"
    }
    ```
- **Response:**
  - Status Code: `200 OK`
  - Body: Updated task details

### Delete Task

- **Endpoint:** `/api/tasks/{id}`
- **Method:** `DELETE`
- **Description:** Delete an existing task.
- **Request:**
  - Path Parameter:
    - `id`: UUID of the task
- **Response:**
  - Status Code: `200 OK`
  - Body: `"Task deleted successfully"`

# Backend Systems WS24/25: Project "Social Media Platform"

This project is the 5th portfolio task for the course "Backend Systems" by Prof. Dr. Peter Braun at the THWS.

# Participants:

| GitHub Username                                | Matriculation Number |
|------------------------------------------------|----------------------|
| [@zitrusgelb](https://github.com/zitrusgelb)   | 5123031              |
| [@timknt](https://github.com/timknt)           | 5123101              |
| [@Jakob-H-DEV](https://github.com/Jakob-H-DEV) | 5123113              |
| [@CyanideLion](https://github.com/CyanideLion) | 6824016              |

# Starting Integration Tests

The integration tests for the API can be started with

```shell script
  mvn verify
```

# Manually Testing with Docker & Postman

To manually run tests (sending custom requests), first you need to build a docker container:

```shell script
  docker build -f .\src\main\docker\Dockerfile.jvm -t quarkus/backendsystems-project-jvm .
```

After doing that, we can run the container with:

```shell script
  docker run -p 127.0.0.1:8080:8080 --name Backendsystems-Project-Social-Media --pull missing quarkus/backendsystems-project-jvm:latest 
```

The container can then be reached by sending a request to `http://localhost:8080/` or `http://127.0.0.1:8080/`. Please
note the following requirements for sending custom requests.

## Available paths

| Path               | Available HTTP-Methods  | Results                                                                |
|--------------------|-------------------------|------------------------------------------------------------------------|
| `posts`            | `GET`, `POST`           | Requesting all posts or publishing a new post                          |
| `posts/{id}`       | `GET`, `PUT`, `DELETE`  | Requesting, updating or deleting a specific post                       |
| `posts/populate`   | `POST`                  | Creating a dummy Post and User for testing purposes                    |
| `posts/{id}/likes` | `GET`, `POST`, `DELETE` | Requesting all Likes from a Post, liking a Post or removing a like     |
| `users`            | `GET`                   | Requesting all users                                                   |
| `users/me`         | `GET`                   | Requesting the logged-in users profile                                 |   
| `users/{username}` | `GET`                   | Requesting a specific user profile                                     |
| `tags`             | `GET`                   | Requesting a list of all tags                                          |
| `tags/{id}`        | `GET`, `PUT`, `DELETE`  | Requesting a list of posts with requested tag, updating or deleting it |

### Query Parameters

For the routes `posts`, `users` and `tags`, it is possible to send query parameters to limit the result set by either
searching for specific results or paginate all results.

| Query Parameter | Results                                                                    |
|-----------------|----------------------------------------------------------------------------|
| `q`             | Searching for content(`posts`) or names (`users`and `tags`)                |
| `offset`        | Requesting posts with a certain offset, this enables pagination            |
| `limit`         | Limiting the amount of results sent to the client, this enables pagination |

## Authentication

For certain requests, authentication is necessary. Especially when creating, updating or deleting a post, the user needs
to be authenticated by a JWT token.

Our API uses the THWS-Auth-provider (https://staging.api.fiw.thws.de/auth/api/users/me). To obtain a JWT token, we need
to send a GET-request to the aforementioned URL, setting the authorization within Postman to "Basic Auth", with the
normal THWS-credentials as Username & Password. In the response headers, we find the `X-fhws-jwt-token`. We copy the
value of this token to the Authorization tab of the request we want to send, select "Bearer" as the Auth Type and paste
our token into the field. This allows us to send authenticated requests.

Please note, that the JWT token expires after a set time. After that time, you will need to obtain and send a new token
through the same method. This is a security policy within the THWS-Auth provider.

## Request Headers

For most requests, the default headers Postman sets will suffice. However, when updating or deleting posts, we need the
`If-Match` header, to confirm that we are trying to delete the latest version of the post.

We can also send conditional GET-requests, using the `If-None-Match` header. This allows us to request a post only when
it has been updated.

## Request Body

For every request that requires a body, the request body needs to be in JSON-format. More details are mentioned below.

### `POST`-request to `posts`

The request body needs to contain the values `content`, `tag` and `replyToId`. Both `tag` and `replyToId` may be `null`.

```json lines
{
  "content": "The dark side is a path to many abilities some consider to be unnatural",
  "tagName": "Star Wars",
  "replyToId": 1
}

```

or

```json lines
{
  "content": "Have you ever heard the tragedy of Darth Plagueis the Wise?",
  "tagName": null,
  "replyToId": null
}
```

### `PUT`-request to `posts`

The request body needs to contain the values of the post that should be changed, with the values that the user wants to
update.

```json lines
{
  "id": 1,
  "content": "This is the new content!",
  "createdAt": "2025-02-08T11:52:03.011281",
  "user": {
    "id": 1,
    "username": "k69420",
    "displayName": "Anakin Skywalker",
    "posts": null,
    "likes": null
  },
  "tag": {
    "id": 1,
    "name": "TagToChangeTo",
    "posts": null
  },
  "replyTo": null,
  "version": 1
}
```

### `DELETE`-request to `posts`

The request body needs to contain the JSON-representation of the post, that should be deleted

```json lines
{
  "id": 1,
  "content": "This is where the fun begins!",
  "createdAt": "2025-02-08T11:52:03.011281",
  "user": {
    "id": 1,
    "username": "k69420",
    "displayName": "Anakin Skywalker",
    "posts": null,
    "likes": null
  },
  "tag": {
    "id": 1,
    "name": "Star Wars",
    "posts": null
  },
  "replyTo": null,
  "version": 1
}
```
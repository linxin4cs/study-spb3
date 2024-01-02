# Frontend and Backend Separation Project Template
English | [中文](./README.zh-CN.md) 

This template includes basic functionalities like login, registration, password reset, etc., and can be further developed for specific application scenarios.

- Login feature (supports username, email login)
- User registration (via email verification)
- Reset password (through email)

## Login Features:

1. Users can access other pages only after a successful login.
2. If a user has not logged in, they will be automatically redirected to the login page.
3. If a user requests a non-existent page, they are forced to the login page; if already logged in, redirect to the homepage.

Solution:

1. Send requests to the backend directly, regardless of login status.
2. If the request is successful, then the user is logged in.
3. If the request fails, then the user is not logged in, redirect to the login page.

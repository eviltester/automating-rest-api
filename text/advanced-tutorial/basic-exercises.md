
---

## Exercises: Basic Concepts and Tooling

- assuming the Testing App Running at [http://localhost:4567/listicator/]
- run the example tests `RestAssuredBasicsTest`
- get used to the API
    - read the docs and experiment with Postman, Insomnia
    - GET, POST, DELETE, OPTIONS and POST
- run the example tests through a proxy `RestAssuredProxyTest`
- replay requests through a proxy
- get used to the app and explore the HTTP Client functionality and explore the API based on its documentation

---

## Optional Exercises: Or Test REST Listicator in Buggy mode

`java -jar compendium-of-test-apps-1-3-3.jar -bugfixes=false`

- The system has been coded with some known bugs
- these are all fixed by default.
- start with `-bugfixes=false` to have known bugs
- See if you can find them

You can run the app twice on different ports to compare output, use the command line argument `-port` to start up the application on a different port e.g. `-port=1234` would start the app on port `1234`



# SECTION - Testing a REST API

---

# Overview of Section - Testing a REST API

- how to model an API
- testing ideas
- interactive discussion

---

# Testing different from Technology and Tooling

- at this point we have discussed technology and tooling
- time to discuss testing

---

# What would we test?

- ideas?

---

# What are the architecture risks?

- Client -> Web Server -> App Server -> App
- Do we understand the architecture?

---

# What are the capacity risks?

- Performance?
- Load Testing?

---

# What are the security risks?

- Authentication
- Authorisation
- Injection

---

# Data Risks

- minimum data in requests - missing fields, headers
- not enough data in requests
- wrong format data: json, xml, length, null, empty
- malformed data
- consistency? query params across requests?
- are defaults correct?
- duplicate data in payloads?
- headers: missing, malformed, too many, duplicate

---

# Document your testing

- How can you document your testing?

---

# Other Risks or Common Issues?

---

# Exercise: Think through testing

- Read the requirements etc. for REST Listicator.
- Create some test ideas
- Look at the existing testing conducted
- Any ideas from that?
- Test REST Listicator
- Document and Track your Testing in a lightweight fashion

---

# Exercise: Test REST Listicator in Buggy mode

`java -jar rest-list-system.jar -bugfixes=false`

- The system has been coded with some known bugs
- these are all fixed by default.
- start with `-bugfixes=false` to have known bugs
- See if you can find them

You can run the app twice on different ports to compare output, use the command line argument `-port` to start up the application on a different port e.g. `-port=1234` would start the app on port `1234`

---


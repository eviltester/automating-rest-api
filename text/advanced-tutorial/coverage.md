---

# SECTION - COVERAGE

A discussion section.

---

## What does coverage mean in terms of API testing?

- what does coverage mean?
- is coverage important?
- how would you measure it?
- could you automate tracking?
- discuss

---

## Possible Points

- verbs - have all verbs been used?
- endpoints - has each end point been hit? with all verbs?
- query parameters - have all combinations of query params been tried?
    - query params from other end points?
- headers
- authentication approaches
- etc.

---

## Automating Coverage Tracking

In the past I have:

- run all API tests through a proxy
- Browsermob proxy https://bmp.lightbody.net/
- exported a HAR file
- read HAR file and processed it to check:
    - what verbs used?
    - have all verbs been used on all endpoints?
    - etc.
- generate a report

This was all custom code for client projects, no public source code.



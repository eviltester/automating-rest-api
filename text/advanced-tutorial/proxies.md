---

# SECTION - HTTP Proxies

---

## Overview of Section - HTTP Proxies

- What is an HTTP Proxy?
- Example HTTP Proxies?
- Why use an HTTP Proxy?
- How to direct REST Client through Proxy?
    - Inspect Traffic
    - Filter Traffic (System Proxies)
    - Port Config
    - Replay Request
- Fuzzing

---

## What is an HTTP Proxy?

- HTTP Proxy captures HTTP Traffic
- Allows replay of requests
- Allows manipulation of responses

---

## Which proxies?

- [Fiddler](http://www.telerik.com/fiddler)
    - Windows (Beta: Linux, Mac)
- [Charles](https://www.charlesproxy.com/)
    - Commercial but allows 30 mins in 'shareware' mode
- [BurpSuite](https://portswigger.net/burp)
    - Free edition good enough for API Testing
- [Owasp ZAP](https://www.owasp.org/index.php/OWASP_Zed_Attack_Proxy_Project)
    - Open Source

Fiddler & Charles act as System Proxies making them easy to use with Postman.

---

## Why use when Testing API?

- Record requests
- Create evidence of your testing
- Replay requests outside of client tool
- Fuzzing

When Automating?

- view requests sent to aid debugging and coverage

---

## Using a Proxy When Automating with REST Assured

~~~~~~~~
RestAssured.proxy("localhost", 8080);
~~~~~~~~

- RestAssured is a singleton
- all requests will be made through proxy after this is run
- setting multiple times does not matter

see `RestAssuredProxyTest`


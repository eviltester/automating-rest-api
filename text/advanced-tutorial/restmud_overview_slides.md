---

## RestMud is a Browser Based Text Adventure Game

A normal text adventure game would look like:

~~~~~~~~
> look
You Look: There is an exit to the east
and a torch on the ground.
> take torch
You take the torch.
> go e
You go east. It is too dark to see.
> illuminate
You switch on the torch and can
see exits to the west and east.
~~~~~~~~

---

A RestMud session would look like:

~~~~~~~~
> GET /player/bob/look
You look
> GET /player/bob/take/torch
You took: torch. You now have the A Torch
> GET /player/bob/go/e
You Go East
> GET /player/bob/illuminate
Good work. You illuminated the 'A Torch
~~~~~~~~

---

## /user/verb/noun

| Verbs: | Nouns |
|----|----|
| - illuminate, darken | |
| - go, open, close | e, w, n, s |
| - inventory | |
| - hoard, polish, use | _itemid_ |
| - say | _something_ |
| - take, drop | _itemid_ |
| - help | |

_There may be other verbs_

---

## Hoard

When you see your 'hoard' in the location you can hoard treasure to score points e.g.

`/player/bob/hoard/_treasureid_`

Some treasures are 'junk' and score -ve points

- `/score`
- `/scores`

Polish things for more points when hoarding.

- `/inspect/_itemid_` to find out what it scores - but this costs points

---

## Tips

- use dev tools and inspect the code
- make a map (the wiz might turn off the lights)
- `hoard` to score points

---

## Demo of GUI

- start game
    - http://http://restmud.herokuapp.com
- register or login
    - http://http://restmud.herokuapp.com/register
    - http://http://restmud.herokuapp.com/login
- use GUI to 'click' on verbs
- look at the URL
- inspect the DOM
   - look for `span` with IDs
- change URL directly when no link on screen

---

## API

The 'Rest' in RestMud means it has a 'REST' interface.

- `GET`
- `POST`

---

### API GET Requests

`GET`

- `http://_host_/api/player/_username_/_verb_/_noun_`

e.g.

~~~~~~~~
GET http://localhost:4567/api/player/user/messages
GET http://localhost:4567/api/player/user/take/crown_1
~~~~~~~~

---

### API POST Requests

`POST`

- http://_host_/api/player/_username_ 
    - body
    - `{"verb":"inspect", "nounphrase":"cloth_2"}`
    - `{"verb":"look"}`

e.g.

~~~~~~~~
POST http://localhost:4567/api/player/user
{"verb":"look"}
~~~~~~~~

---

### API Register


`POST Register`

- http://_host_/api/register
	- body
    - `{"username":"user", "displayname":"Mighty User", "password":"password"}`
    - **if game requires a 'secret code'**
        - add field `secret`

e.g.
~~~~~~~~
POST http://localhost:4567/api/register
{"username":"user", "displayname":"Mighty User",
"password":"password", "secret":"iwejwjwjw"}
~~~~~~~~

- session cookie automatically logged in for user after registering

---

#### API Register Response

~~~~~~~~
{
  "status": "success",
  "data": {
    "X-RESTMUD-USER-AUTH": 
         "be75b9a3-2b71-4b28-a92b-0295da34c814",
    "successMessage": 
         "Registered user user as 'Mighty User'",
    "username": "user"
   }
}
~~~~~~~~

- can add a custom header `"X-RESTMUD-USER-AUTH"` with GUID above as security to not require login

---

### API Login

`POST Login`

- http://_host_/api/login
    - body
    - `{"username":"user", "password":"password"}`

e.g.

~~~~~~~~
POST http://localhost:4567/api/login
{"username":"user", "password":"password"}
~~~~~~~~

---

#### API Login Response

~~~~~~~~
{
  "status": "success",
  "data": {
    "resultoutput": {
      "lastactionstate": "success",
      "lastactionresult": "user bob2 is now logged in"
    },
    "userDetails": {
      "displayName": "Mighty User",
      "userName": "user",
      "authToken": "be75b9a3-2b71-4b28-a92b-0295da34c814"
    }
  }
}
~~~~~~~~

- login response has a cookie that can be used for authentication

---

### Message Authentication

- send the `JSESSIONID` cookie set on register or login or from GUI Session
- send the `X-RESTMUD-USER-AUTH` custom header in the HTTP request
- use basic auth with registerd username and password

---

## Recommended API Usage Tool

I use [postman](https://www.getpostman.com/) for most of my interactive REST API usage.

- https://www.getpostman.com

---

## API Demo

- login
- look
- take stuff
- etc.

see also [github.com/eviltester/restmudbots](https://github.com/eviltester/restmudbots)



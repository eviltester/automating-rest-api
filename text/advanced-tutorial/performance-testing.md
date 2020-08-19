---

## SECTION - PERFORMANCE TESTING

A discussion section

---

## Performance Testing

- is performance testing an API important?
- what does it mean?
- what risks are there for APIs?
- what tools have you used?
- how would you performance test an API?

---

## Possible Open Source Tools

- Artillery
    - https://artillery.io/docs/getting-started/
- JMeter
    - https://jmeter.apache.org/
    - https://github.com/flood-io/ruby-jmeter
    - http://gettaurus.org/
- Gatling
    - https://gatling.io/

---

## Artillery Example

- `brew install node`
- `npm install -g artillery`
- To run through a proxy

~~~~~~~~
 HTTP_PROXY='http://localhost:8080' artillery run example.yml
~~~~~~~~

- to generate output

~~~~~~~~
artillery run example.yml -o output.json
~~~~~~~~

- to generate a report

~~~~~~~~
artillery report output.json
~~~~~~~~

---

## Artillery Example - example.yml

~~~~~~~~
config:
  target: 'http://localhost:4567/listicator'
  phases:
    - duration: 20
      arrivalRate: 10
  processor: "./javascript-helpers.js"
scenarios:
  - flow:
    - get:
        url: "/lists"
    - function: "millisTitle"
    - post: # create a list
        headers:
          Authorization: "Basic YWRtaW46cGFzc3dvcmQ="
        url: "/lists"
        json:
          title: "{{ title }}"
        capture:
          - header: "location"
            as: "newlistguid"
    - get:
        url: "{{ newlistguid }}"
        ifTrue: "newlistguid"
~~~~~~~~

---

## JavaScript Helpers .js

~~~~~~~~
module.exports = {
    millisTitle: millisTitle
  }
  
function millisTitle(context, events, done){
    context.vars['title'] =  'my title ' +
                             new Date().valueOf();
    return done();
}
~~~~~~~~

---

## Model Based Testing Approaches - Bots

- custom approach
- can potentially be performed in other tools when you understand how they work
- for RestMud I created player bots
   - implement different play strategies
   - maintain their own state
   - run in separate threads
- I use for exploratory multi user testing

see [github.com/eviltester/restmudbots](https://github.com/eviltester/restmudbots)

---

## A Simple Model Based Test Bot

~~~~~~~~
@Test
public void createAMultiUserRestMudTestBot(){

    RestMudTestBot myFirstBot = new RestMudTestBot(
                "http://restmud.herokuapp.com")
            .setUserName("zzzamd64Windows811488121657822");
    myFirstBot.needsToRegisterOnSystem(true);
    myFirstBot.setRegistrationSecretCode("secretcode");
    myFirstBot.setPassword("password");
    myFirstBot.register();
    myFirstBot.login();

// ...
~~~~~~~~

---

## A Simple Model Based Test Bot Continued

~~~~~~~~
    myFirstBot.addActionStrategy(
            new WalkerStrategy().canOpenDoors(true));
    myFirstBot.addActionStrategy(new MadHoarderStrategy());
    myFirstBot.addActionStrategy(new RandomTakerStrategy()); 
    myFirstBot.addActionStrategy(new RandomUseStrategy()); 
    myFirstBot.addActionStrategy(
            new AllDoorOpenerStrategy());

    myFirstBot.addWaitingStrategy(
            new RandomWaitStrategy().waitTimeBetween(0,100));

    for(int x=0;x<2000;x++) {
        myFirstBot.executeARandomActionStrategy();
        myFirstBot.waitAWhile();
    }
}
~~~~~~~~

---

## Code Structure Walkthrough

- `RestMudTestBot` - basic object that represents a player
- `ThreadedRestMudTestBot` - represents a player as a thread
- Bots use strategies which are either
   - Action strategies - `RestMudBotStrategy`
   - Waiting strategies - `RestMudBotWaitingStrategy`
- A Simple API abstraction `RestMudApi`
- A generic command abstraction `CommandExecutor`
- I used also for testing end point case testing with the `ChangeCaseifier`

see [github.com/eviltester/restmudbots](https://github.com/eviltester/restmudbots)

---

## Performance / Stress Testing Recap

- Can be a very 'static' process
- Strategic Performance Testing can be hard to maintain
- Code Based DSLs:
  - easy to version control and maintain
  - support tactical testing
- Performance Testing can be exploratory
- Continuous Testing should limit variation to allow comparison between runs
- Bot Modelling - custom, flexible

---

## Suggested Exercises

- Experiment with Artillery
    - install node
    - install Artillery
    - try the script in the `performance/artillery` folder
    - run it through a proxy
    - expand the script, create a new script
        - make more calls
        - create new users
        - etc.
- Examine the bots in [github.com/eviltester/restmudbots](https://github.com/eviltester/restmudbots)
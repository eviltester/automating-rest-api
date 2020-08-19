require 'ruby-jmeter'

/* untested */

test do
    threads count: 10, rampup: 5, duration 20 do

        auth username: 'admin', password: 'password'

        visit name: 'REST API', url: 'http://localhost:4567/listicator/lists'

        list = {title:"my title"}

        post name: 'create a list',
             url: "/lists",
             raw_body: list.to_json do
                extract: name: 'newlistguid', regex: %q{.*"Location":"([^"]+)".*}
        end

        visit name: 'get list', url: 'http://localhost:4567/listicator${newlistguid}'

    end
end.jmx
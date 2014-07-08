# How to run the example

##Create a google app and add a client
Create an client with the redirect URL  http://127.0.0.1:8080/oauth2Callback

##Add google oauth details

Create a file gradle.properties in <USER_HOME>/.gradle with the following content:

    simpleauthexample.google.scope=https://www.googleapis.com/auth/userinfo.email h$
    simpleauthexample.google.clientId=<your client id>
    simpleauthexample.google.secret=<your client secret>
    simpleauthexample.google.callbackUrl=http://127.0.0.1:8080/oauth2Callback    
    simpleauthexample.google.state=
    
    
##Start server

- Execute gradle jettyRun
- Go to http://localhost:8080/
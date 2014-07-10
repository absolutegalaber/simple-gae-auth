#How to use
In order to use this implementation, you have to 

- Implement the interface IRepositoryService (With the required repositories based on your preferred persist technology)
- Extend the listener RepositoryServiceInitializer and add your class to your web.xml
- Extend the servlet ShadowRedirectServlet and add your class to your web.xml
- Add the Servlet ShadowCallbackServlet to your web.xml 

- Add the filter ShadowTokenFilter to your web.xml with the filter mapping pointing to your protected resources

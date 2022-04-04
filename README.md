# springSecurityDemoJWT

### Dependencies
For further reference, please consider the following sections:

* spring-boot-starter-security
* spring-boot-starter-web
* jjwt -io.jsonwebtoken - library to create jwt and also validate existing jwt
* jaxb-api - library required because on jaxb-dependencies of jjwt .Any Java9+ will not have this provided in the JDK any more


### Details

##JWTUtil - abstract all the JWT related class
- create JWT based on the user details object -  public String generateToken(UserDetails userDetails)
- builder pattern used by Jwt API - set the claims and the Subject - private String createToken(Map<String, Object> claims, String subject) 
    Subject - is the person who is being authenticated - who was successfuly authenticated in this case
- when someone authenticates create a JWT from it after succesfull auth    
   
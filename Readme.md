# Info
| Thing  | Value           |
|------- | ----------------|
| Java   | AWS Corretto 17 |


# Runnig live
Use with a *unprivleged user* in the ec2
```sh
$> java -jar target/myApp.jar --spring.profiles.active=live,db,secrets
```


# Secrets
You need to add a few secret configurations to the resource folder and to your own AWS creds config

## Resources

Add `src\main\resources\application-db.properties`
```s
### DB Connection
spring.datasource.url=jdbc:mysql://
spring.datasource.username=
spring.datasource.password=
```

Add `src\main\resources\application-secrets.properties`
```s
### Auth0
auth0.application_id=1ALS7P2lwcK5RT8BtIoZUtY2niqcfTh8
auth0.domain=dev-omuu4lwn.us.auth0.com
```

## AWS

Add this profile into your `.aws/config`

```profile
[profile pokeapi]
region=af-south-1
...
```

and/or to your `.aws/credentials`

```profile
[pokeapi]
aws_access_key_id=
aws_secret_access_key=
...
```

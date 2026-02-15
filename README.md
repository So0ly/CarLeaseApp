# Car Leasing app
A simple car leasing app. With some shortcomings.
Below is a list of features that should be added in a professional version.

# Stuff to add
- frontend (obviously; currently it's accessible only via Postman/Curl) - Either full-on NextJS app or just thymeleaf templates
- Auth + RBAC - currently it's a hardcoded user for prototyping purposes
- Full CRUD for cars, reservations and users - currently it's just POST for reservations
- ~~Better exception handling~~
- More descriptive responses in controllers
- DDL constraints (pre-set column sizes, data uniqueness)
- Sensitive stuff into envs/secrets
- OpenAPI
- Main entity object similar to Quarkus` PanacheEntity (concept below) - all other models would extend it
- Container stack (docker/podman, k8s, helm)

```java
@Entity  
@Getter  
@Inheritance(strategy= InheritanceType.TABLE_PER_CLASS)  
public abstract class BaseEntity {  
@Id  
@GeneratedValue
private Long id;
}
```
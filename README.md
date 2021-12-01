# SFG Recipe app

## History
* Create Spring Boot project.
* Index Page:
    * learn.sfg.sfgrecipe.controllers.IndexController, index.html.
* One To One JPA Relationships
    * @javax.persistence.Entity
    * @javax.persistence.OneToOne
    * javax.persistence.CascadeType
    * @javax.persistence.Lob
* One to Many JPA Relationships
    * @javax.persistence.OneToMany
* JPA Enumerations
    * @javax.persistence.Enumerated
    * javax.persistence.EnumType.STRING
* Many to Many JPA Relationships
    * @javax.persistence.ManyToMany
    * @javax.persistence.JoinTable
    * @javax.persistence.JoinColumn
* Creating Spring Data Repositories
    * org.springframework.data.repository.CrudRepository
* Database initialization with Spring (see details below)
    * Property: `spring.jpa.hibernate.ddl-auto` (none, validate, update, create, create-drop)
    * Spring initialization: `schema.sql` and `data.sql`
    * If using data.sql then must set `spring.jpa.defer-datasource-initialization=true`
* Spring Data JPA Query Methods
* Assignment: Display List of Recipes on Index page 
    * Update data.sql
    * Add DataLoader - bootstrap class
    * Add RecipeService
    * Update IndexController, index.html
* Refactor using Project Lombok
    * @Data, @EqualsAndHashCode(exclude), @ToString(exclude)
    * @Slf4j

### Initialize with Hibernate
* Data can be loaded from `import.sql`
    * Hibernate feature (not Spring specific)
    * Must be on root of classpath
    * Only executed if Hibernate's ddl-auto property is set to `create` or `create-drop`
    
### Initialize with Spring
* Spring's DataSource initializer via Spring Boot will by default load `schema.sql` and `data.sql` from the root
* By default, `data.sql` scripts are run before Hibernate is initialized
* Must set `spring.jpa.defer-datasource-initialization=true` property
* Spring Boot will also load from `schema-${platform}.sql` and `data-${platform}.sql`
    * Must set `spring.datasource.platform`
* May __conflict__ with hibernate.ddl-auto property
    * Should use setting of `none` or `validate`

### Testing Spring Framework Applications
* JUnit tests
* Mockito tests
* Mockito Argument Capture

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

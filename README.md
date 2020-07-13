This project is basically the same as JSP-Servlets-JSTL-Project, but instead of JDBC connection, I use Hibernate framework. 
JSP, Servlets and JSTL are used as well.
Hibernate is configured with hibernate.cfg.xml file and connection pool is set there, not through Tomcat configuraions. 
Entities are declared and mapping is done using annotations. 
@OneToMany and @ManyToOne mapping are required and, of course, the specifications of Hibernate
required some modifications to the code, compared to the previous project: new properties in the Java classes to be able 
to set up the relationship between tables.
The CASCADE types are configured so deleting the referred record deletes dependants, but not otherwise.
 
In this project I've added import functionality, so Owners, Cars and Services can be imported from csv file (import Services not yet implemented). 
There's file format simple validation in the front-end, with Javascript, while in back-end errors cause by data format within the file
are managed. 
In front-end, user chooses with radio buttons what he's importing from file.
Also, while importing, it's checked whether the record being imported is already in the DB or not, to avoid duplicates.
Also, while importing cars (services yet not implemented), as each car references an owner, it's checked whether the owner_id of the car being imported
does exist (import is performed and DB updated) or not (import is discarded and no DB update takes place).
    

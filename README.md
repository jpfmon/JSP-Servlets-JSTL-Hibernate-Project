This project is basically the same as JSP-Servlets-JSTL-Project, but instead of JDBC connection, I use Hibernate framwork. 
JSP, Servlets and JSTL are used as well.
Hibernate is configured with hibernate.cfg.xml file, and with annotations in the classes. 
@OneToMany and @ManyToOne mapping are required and, of course, the specifications of Hibernate
required some modifications to the code, compared to the previous project: new properties in the Java classes to be able 
to set up the relationship between tables.
The CASCADE types are configured so deleting the referred record deletes dependants, but not otherwise. 

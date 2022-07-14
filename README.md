# FileLoc

Local File Transfer and storage web client-server application using Spring Security ,Spring Data JPA and Vaadin UI framework.

Enables users to transfer files between devices connected to same local network without requiring any internet connection. Users can manipulate files as stated by their role on the app. Three roles are defined for which users are assigned to restrict certain behaviours like deleting , uploading , or downloading files.

Roles are SUPERUSER, DOWNLOADERS AND UPLAODERS.
Superusers have all the privileges app can offer.
Downloaders can only download files that have been uploaded by other roles.
Uploaders can only upload files.

Used frameworks and project libraries
Spring security -> Authentication and authorization implementation. User passwords are secured by using built-in spring security encryption libraries(Scrypt,Bcrypt).
Spring data -> To enable persistence layer
Vaadin -> UI framework for java. (Great tool to make UI layer incorporated to and managed by backend code.)

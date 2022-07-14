# FileLoc

Local File Transfer and storage web client-server application using Spring Security ,Spring Data JPA and Vaadin UI framework.

Enables users to transfer files between devices connected to same local network without requiring any internet connection. Files are stored in server file system and indexed in database . Users can manipulate files as stated by their role on the app. Three roles are defined for which users are assigned to restrict certain behaviours like deleting , uploading , or downloading files.

Roles are SUPERUSER, DOWNLOADER AND UPLAODER.
Superusers have all the privileges app can offer.
Downloaders can only download files that have been uploaded by other roles.
Uploaders can only upload files.

Used frameworks and project libraries
Spring security -> Authentication and authorization implementation. User passwords are secured by using built-in spring security encryption libraries(Scrypt,Bcrypt).
Spring data -> To enable persistence layer
Vaadin -> UI framework for java. (Great tool to make UI layer incorporated to and managed by backend code.)



Some screenshots from app.
Main page
![main page](https://user-images.githubusercontent.com/25362869/178932994-9f80e88f-a771-419c-bee0-c820ee91d55e.png)
Login page
![login](https://user-images.githubusercontent.com/25362869/178933362-6d1cfb30-027a-43c0-aac2-db78520d2672.png)
Register page
![register](https://user-images.githubusercontent.com/25362869/178933399-e80904b8-da40-4ea2-9162-4b686208e1ad.png)
File download section
![download section](https://user-images.githubusercontent.com/25362869/178933444-849d4c6d-2a8c-4cd0-ae04-8a2d6eeeb496.png)


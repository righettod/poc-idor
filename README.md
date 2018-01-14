# Introduction

It's the code repository of the OWASP cheatsheet [Insecure Direct Object Reference Prevention Cheat Sheet](https://www.owasp.org/index.php/Insecure_Direct_Object_Reference_Prevention_Cheat_Sheet).

# Run

Use the **Run Application** running configuration from Intellij project.

The application is then exposed on http://localhost:8080

```
$ curl http://localhost:8080/movies

{
  "F498AB2AFB450684FB0378AD6D87F10C3B1826AA":"Avengers",
  "24D8FB4F39241C8E63F3CF9E82F2D644594CBB4B":"StarWars",
  "F1244AD6A71A9C6C9E08BA6D819D119FBD7944D0":"Jumanji"
}


$ curl http://localhost:8080/movies/F1244AD6A71A9C6C9E08BA6D819D119FBD7944D0

{
  "name":"Jumanji",
  "creationYear":2005,
  "creator":"Fox"
}
```

# Breizhcamp Sessionize CLI

The aim of this project is to provide a CLI so as to generate :
* PDF cards
* JSON files for the website

## Requirement

* Java > 11

## How to 

### How generate cards file
* Generate jar :
  ```bash
  ./mvnw clean package 
  ```
* Get the Sessionize identifier of the API with the name `programme-json`
* Export the previous identifier as environment variable 
    ```bash
    export SESSIONIZE_ID=XXXXX
   ```
* To diplay all cli option's
    ```bash
    java -jar  target/breizhcamp-sessionize-cli-0.0.1-SNAPSHOT.jar --help
   ```
* To generate the list of speaker
    ```bash
    java -jar  target/breizhcamp-sessionize-cli-0.0.1-SNAPSHOT.jar speaker
   ```
* To generate the schedule file
    ```bash
    java -jar  target/breizhcamp-sessionize-cli-0.0.1-SNAPSHOT.jar schedule
   ```
* To generate the file with all talks
    ```bash
    java -jar  target/breizhcamp-sessionize-cli-0.0.1-SNAPSHOT.jar talk
   ```
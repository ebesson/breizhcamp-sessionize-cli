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
  ` ./mvnw clean package `
   ```
* Get the Sessionize identifier of the API with the name `json_all_export`
* Export the previous identifier as environment variable 
    ```bash
    export SESSIONIZE_ID=XXXXX
   ```
* Launch the cli
    ```bash
    java -jar  target/breizhcamp-sessionize-cli-0.0.1-SNAPSHOT.jar card
   ```
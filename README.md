# Sainsbury's Tech Test

![[link](https://travis-ci.org/Shaunwild97/sainsburys-serverside-test)](https://travis-ci.org/Shaunwild97/sainsburys-serverside-test.svg?branch=master)

### Building

Execute
```bash
mvn clean package
```

### Testing

Execute
```bash
mvn test
```

### Run

Execute
```bash
java -jar target/sainsburys-tech-test-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Travis

Travis is used to ensure this project is always passing and gives the fancy build shield at the top.

[See here](https://travis-ci.org/Shaunwild97/sainsburys-serverside-test) for Travis info.

## Libraries

- Jsoup - Parsing and scanning of HTML
- Jsonb - Marshalling/ Unmarshalling of JSON
- Lombok - Replaces Getters/ Setters and Constructors with Annotations
- JodaMoney - For handling currency
# How to

1. Launch the server using `./gradlew bootRun`
1. POST a message to http://localhost:8080 with body  as 

```json
{
  "scooters": [1, 2, 3],
  "P": 1,
  "C": 2
}
```

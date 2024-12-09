﻿# mobile-backend-example

#Para probar

Clonar el repositorio y desde el directorio del proyecto ejecutar: ./gradlew.bat bootRun (Windows) o ./gradlew bootRun (linux/mac)

Una vez levantado, ejecutar con curl las peticiones de prueba (los ejemplos están en PowerShell)

#usuarios

curl -Uri "http://localhost:8080/users" -Headers @{Authorization="Basic "+[Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("bob_wilson:pass789"))}

curl -Uri "http://localhost:8080/users/1" -Headers @{Authorization="Basic "+[Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("bob_wilson:pass789"))}

$body = @{
    username="newuser"
    password="pass123"
    email="new@example.com"
    firstName="New"
    lastName="User"
} | ConvertTo-Json

curl -Uri "http://localhost:8080/users" -Method Post -Body $body -Headers @{
    Authorization="Basic "+[Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("bob_wilson:pass789"))
    "Content-Type"="application/json"
}

$updateBody = @{
    username="updateduser"
    password="newpass123"
    email="updated@example.com"
    firstName="Updated"
    lastName="User"
} | ConvertTo-Json

curl -Uri "http://localhost:8080/users/1" -Method Put -Body $updateBody -Headers @{
    Authorization="Basic "+[Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("bob_wilson:pass789"))
    "Content-Type"="application/json"
}

curl -Uri "http://localhost:8080/users/1" -Method Delete -Headers @{Authorization="Basic "+[Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("bob_wilson:pass789"))}


#autos

curl -Uri "http://localhost:8080/cars" -Headers @{Authorization="Basic "+[Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("bob_wilson:pass789"))}

curl -Uri "http://localhost:8080/cars/1" -Headers @{Authorization="Basic "+[Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("bob_wilson:pass789"))}

$carBody = @{
    brand="Toyota"
    model="Camry"
    modelYear=2023
    country="Japan"
} | ConvertTo-Json

curl -Uri "http://localhost:8080/cars" -Method Post -Body $carBody -Headers @{
    Authorization="Basic "+[Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("bob_wilson:pass789"))
    "Content-Type"="application/json"
}

$updateCarBody = @{
    brand="Toyota"
    model="Corolla"
    modelYear=2024
    country="Japan"
} | ConvertTo-Json

curl -Uri "http://localhost:8080/cars/1" -Method Put -Body $updateCarBody -Headers @{
    Authorization="Basic "+[Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("bob_wilson:pass789"))
    "Content-Type"="application/json"
}

curl -Uri "http://localhost:8080/cars/1" -Method Delete -Headers @{Authorization="Basic "+[Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes("bob_wilson:pass789"))}

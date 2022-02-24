# NuBank
Capital Gains Challenge

# General information
The api has two endpoints which have the same response, both have a different implementation to evaluate different concepts.
Locally these would be the enpoints
- localhost:8080/api/v1/tax
- localhost:8080/api/v2/tax
# Practical guide for using the Capital Gains API
- In the delivery folder you will find a subfolder called PostmanCollections there you will find the files to make requests through postman which contain the necessary structure.
- In the ArchitectureDiagrams folder you can find high level information for the api like class diagrams and aws deployment proposal
# Architecture
- The implemented architecture is based on the hexagonal model of ports and adapters. 
- DDD is implemented by freeing the domain from dependencies
# ** Steps to launch the application **
- Import the application in the ide that you use frequently 
- Run the following command gradle clean build 
- Go to the main class CapitalGainApplication and run the project 
- The application is deployed through port 8080, make sure you have this port free 
- Import the postman collections and execute the request changing the body with the number and operations you want
- If you don't use postman here you find a curl that can be useful (Only takes the value inside the ** )
- --> ** curl --location --request POST 'localhost:8080/api/v1/tax' \
  --header 'Content-Type: application/json' \
  --data-raw '{
  "transactionList": [
  {
  "operation": "buy",
  "unit-cost": 10,
  "quantity": 100
  },
  {
  "operation": "buy",
  "unit-cost": 15,
  "quantity": 50
  },
  {
  "operation": "sell",
  "unit-cost": 15,
  "quantity": 50
  }
  ]
  }' **



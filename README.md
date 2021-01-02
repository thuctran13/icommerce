<h1>Introduction

i-commerce is an online shopping application which allows customer to view, search and add review to specific product.
Besides, back office users can manage to add, update or delete product, review.

<h2>Requirement

- Spring boot
- Java 8
- jq https://stedolan.github.io/jq/download/

<h2>Target Architecture - see image icommerce-architect.png

- infrastructure
    - Discovery server: Netflix Eureka
    - Dynamic routing & Load Balancer: Netflix Ribbon
    - Edge Server: Netflix Zuul
- Core:
    - Product Service
    - Review Service
- Composite Service
    - Composite Service

<h2>Start Application

- install java jdk 1.8
- build project:
```bash
./build-all.sh
```
- start infrastructure server:
- Discovery server
```bash
cd support/discovery-server/
./gradlew bootRun
```
- Edge Server
```bash
cd support/discovery-server/
./gradlew bootRun
```
- Product Service
```bash
cd core/product-service
./gradlew bootRun
```
- Review-service
```bash
cd core/review-service
./gradlew bootRun
```
- Composite Service
```bash
cd product-composite-service
./gradlew bootRun
```

After all services started successfully, check instances registered with Eureka at http://localhost:8761 or execute following command
```bash
curl -s -H "Accept: application/json" http://localhost:8761/eureka/apps | jq '.applications.application[] | {service: .name, ip: .instance[].ipAddr, port: .instance[].port, status: .instance[].status, healthCheckUrl: .instance[].healthCheckUrl}'
```

- For testing, you have to ensure all services are up. 

<h2> API Explaination
<h3> Product Composite service. See target architecture, after receiving request from consumer, this request will forward call to core services (either product or review service)

- Create product:
```bash
curl --location --request POST 'http://localhost:8765/productcomposite/product/' \
--header 'Content-Type: application/json' \
--data '{
	"name":"iPhone12",
	"description":"Apple product, smartphone",
	"price":"1000",
	"weight":"400",
    "state":"CREATED",
    "status": "ACTIVE"	
}' | jq .

curl --location --request POST 'http://localhost:8765/productcomposite/product/' \
--header 'Content-Type: application/json' \
--data '{
	"name":"Galaxy S10",
	"description":"Samsung product, smartphone",
	"price":"1000",
	"weight":"400",
    "state":"CREATED",
    "status": "ACTIVE"	
}' | jq .
```

- Update product
```bash
curl --location --request PUT 'http://localhost:8765/productcomposite/product/1' \
--header 'Content-Type: application/json' \
--data '{
    "description": "top smartphone 2020",
    "price": "800"
}' | jq .
```

- Find all products
```bash
curl --location --request GET 'http://localhost:8765/productcomposite/product/' | jq .
```

- Find product by id:
```bash
curl --location --request GET 'http://localhost:8765/productcomposite/product/1' | jq .
```

- Search product: this will find all products by keyword matching name or description
```bash
curl --location --request GET 'http://localhost:8765/productcomposite/product/find?keyword=iphone12' | jq .
```

- Delete product 
```bash
curl --location --request DELETE 'http://localhost:8765/productcomposite/product/1'
```

- Create review
```bash
curl --location --request POST 'http://localhost:8765/productcomposite/review/' \
--header 'Content-Type: application/json' \
--data '{
    "title":"Good product",
    "description": "This product is best product ever, bla bla",
    "productId": "2"
}' | jq .

curl --location --request POST 'http://localhost:8765/productcomposite/review/' \
--header 'Content-Type: application/json' \
--data '{
    "title":"OK product",
    "description": "well package, 5 stars",
    "productId": "2"
}'  | jq .
```

- Update review:
```bash
curl --location --request PUT 'http://localhost:8765/productcomposite/review/1' \
--header 'Content-Type: application/json' \
--data '{
    "description": "Well package, look good in general",
    "productId": "2"
}'
```

- Delete review:
```bash
curl --location --request DELETE 'http://localhost:8765/productcomposite/review/1'
```

- Find all reviews
```bash
curl --location --request GET 'http://localhost:8765/productcomposite/review/' | jq .
```

Find review by ID, id=1 in this case
```bash
curl --location --request GET 'http://localhost:8765/productcomposite/review/2' | jq .
```

Find reviews by product id, productId = 1 in this case
```bash
curl --location --request GET 'http://localhost:8765/productcomposite/review/findByProduct/2' | jq .
```

<h3> Product Service (INTERNAL SERVICE). 

This will not allowed from outside but by internal calls.

- Service class: `ProductService.java`
- Test class: `ProductServiceApplicationTests.java`
- API:
    - Create product
    - Update product
    - Delete product
    - Find all product
    - Find product by id
    - Search product
    
<h3> Review Service (INTERNAL SERVICE). 

This will not allowed from outside but by internal calls.

- Service class: `ReviewService.java`
- Test class: `ReviewServiceApplicationTests.java`
- API:
    - Create review
    - Update review
    - Delete review
    - Find all review
    - Find review by id
    - Find reviews by product id

<h2> Service flow:

- External requests: there's no direct communication between public consumers and core services. These requests need to go throw compposite service. Config for this is defined in project `edge-server` class `application.yml`.
- Internal requests: all services in composite and core layer are allowed to make connections.  

<h2>Use cases
<h3> Admin/Backoffice User

- Admin user allows to to manage product via product api. She could modify description as well as price instantly to keep product info up to date.
- She could also able to manage review added by customer. Any spam message can be deleted by using review service. This is amazing api to get rid of from product's spam.

<h3> Customer/Shopper

- As a valuable customer, I would like to  search all products in app, search by product name or description by using keyword.
- Additionally, I can add review to each product. This will help to rate product quality.
- All reviews rated by customer will be available in product details (see api `Find product by id`).
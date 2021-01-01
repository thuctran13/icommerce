<h1>Introduction

i-commerce is an online shopping application which allows customer to view, search and add review to specific product.
Besides, back office users can manage to add, update or delete product, review.

<h2>Requirement

- Spring boot
- Java 8
- jq https://stedolan.github.io/jq/download/

<h2>Target Architecture

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
./gradlew build-all.sh
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

<h2> API Explaination
<h3> Product Service

- Service class: `ProductService.java`
- Test class: `ProductServiceApplicationTests`
- Create product:
```bash
curl --location --request POST 'http://localhost:8765/product/product/' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name":"iPhone 12",
	"description":"Apple product, smartphone",
	"price":"1000",
	"weight":"400",
    "state":"CREATED",
    "status": "ACTIVE"	
}'

curl --location --request POST 'http://localhost:8765/product/product/' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name":"Galaxy S10",
	"description":"Samsung product, smartphone",
	"price":"1000",
	"weight":"400",
    "state":"CREATED",
    "status": "ACTIVE"	
}'
```

- Update product: id will be the one created earlier, in this case id = 1
```bash
curl --location --request PUT 'http://192.168.248.136:8765/product/product/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "description": "top smartphone 2020",
    "price": "800"
}'
```

- Delete product: 
```bash
curl --location --request DELETE 'http://192.168.248.136:8765/product/product/1'
```

- Find all products:
```bash
curl --location --request GET 'http://192.168.248.136:8765/product/product/'
```

- Find product by id:
```bash
curl --location --request GET 'http://192.168.248.136:8765/product/product/1'
```

- Search product: this will find all products by keyword matching name or description
```bash
curl --location --request GET 'http://192.168.248.136:8765/product/product/find?keyword=iphone12'
```

<h3> Review Service

- Service class: `ReviewService.java`
- Test class: `ReviewServiceApplicatonTests.java`
- Create review
```bash
curl --location --request POST 'http://192.168.248.136:8765/review/review/' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title":"Good product",
    "description": "This product is best product ever, bla bla",
    "productId": "1"
}'
```

- Update review:
```bash
curl --location --request PUT 'http://192.168.248.136:8765/review/review/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "description": "Well package, look good in general",
    "productId": "1"
}'
```

- Delete review:
```bash
curl --location --request DELETE 'http://192.168.248.136:8765/review/review/1'
```

<h3> Composite Service

- Service class: `ProductCompositeService.java`

- Find all products
```bash
curl --location --request GET 'http://192.168.248.136:8765/productcomposite/product'
```

- Find product by id
```bash
curl --location --request GET 'http://192.168.248.136:8765/productcomposite/product/1'
```

- Search product: this will find all products by keyword matching name or description
```bash
curl --location --request GET 'http://192.168.248.136:8765/productcomposite/product?keyword=iPhone12'
```
- Add review by customer
```bash
curl --location --request POST 'http://192.168.248.136:8765/productcomposite/product/review/createByCustomer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title" : "Product OK",
    "description": "Best experiences ever whohooo",
    "productId": "1"
}'
```

<h2>Use cases
<h3> Admin/Backoffice User

- Admin user allows to to manage product via product api. She could modify description as well as price instantly to keep product info up to date.
- She could also able to manage review added by customer. Any spam message can be deleted by using review service. This is amazing api to get rid of from product spam.

<h3> Customer/Shopper

- As a valuable customer, I search all products in shop, search by product name of description by using `composite service`.
- Additionally, I can add review to each product. This will help to rate product quality.
- All reviews rated by customer will be available in product details (see api `Find product by id`).


# icommerce
i-commerce MVP


- Eureka get app list 
```bash
curl -s -H "Accept: application/json" http://localhost:8761/eureka/apps | jq '.applications.application[] | {service: .name, ip: .instance[].ipAddr, port: .instance[].port."$"}'
```

- create product
```bash
curl -X POST \
  http://localhost:8765/product/product \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: ee1f3caa-fae1-ceee-f875-a55117efb136' \
  -d '{
	"name":"iPhone12",
	"description":"Latest Apple Product",
	"price":"1000",
	"weight":"400"	
}'
```
- update
```bash
curl --location --request PUT 'http://192.168.248.136:8765/product/product/1' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name":"iPhone12 PRO"
}'
```

- delete
```bash
curl --location --request DELETE 'http://192.168.248.136:8765/product/product/1'
```

- find all
```bash
curl --location --request GET 'http://192.168.248.136:8765/product/product/'
```

- find by id
```bash
curl --location --request GET 'http://192.168.248.136:8765/product/product/1'
```
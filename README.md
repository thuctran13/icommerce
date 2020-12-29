# icommerce
i-commerce MVP


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
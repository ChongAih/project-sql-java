# SQL (Customer)
* When a customer updates their contact phone number, what query should we run in order to save that update to the database?
```agsl
UPDATE tblSubscriptionInfo
SET customer_contact_phone = 123
WHERE customer_id = 1
```

* We've noted that the phone number update feature in the web application is too slow, and have identified that the 
update query is the primary bottleneck. What could we do to speed up this query?

There are few things we can do to resolve this. The most straightforward and easy way is to add index to the customer_id 
such that an index mapping from data to disk location is created for fast data lookup. However, as data volume explodes, 
the index might take up too many space within a single disk. In order to resolve that, we can shard the database so that 
customers can be more distributed to different shards so that the burden can be relieved.

* number of subscribers whose subscriptions will be ending in 2023
```agsl
SELECT 
  COUNT(*) AS cnt
FROM 
  tblSubscriptionInfo
WHERE 
  subscription_end_date >= '2023-01-01' 
  AND subscription_end_date < '2024-01-01';
```

* number of subscribers who have subscribed for more than 3 months in 2022;
```agsl
SELECT 
  COUNT(*) AS cnt
FROM 
  tblSubscriptionInfo
WHERE 
  subscription_start_date >= '2022-01-01' 
  AND subscription_start_date < '2023-01-01'
  AND subscription_end_date >= subscription_start_date + INTERVAL '3 months';
```

* subscribers who have subscribed for more than two products;
```agsl
SELECT 
  customer_id
FROM tblSubscriptionInfo
GROUP BY customer_id
HAVING COUNT(DISTINCT product_id) > 2;
```

* product with the most/2ndmost/3rdmost number of subscribers in 2022;
```agsl
WITH cnt_subscribers AS (
  SELECT 
    product_id, 
    product_name, 
    COUNT(DISTINCT customer_id) AS num_subscribers
  FROM tblSubscriptionInfo
  WHERE 
    subscription_start_date >= '2022-01-01' 
    AND subscription_start_date < '2023-01-01'
  GROUP BY product_id, product_name
),
rank_product AS (
  SELECT
    product_id,
    product_name,
    RANK() OVER (ORDER BY num_subscribers) AS rn
  FROM cnt_subscribers
)
SELECT 
  product_id, product_name
FROM 
  rank_product
WHERE rn <= 3;
```

* number of subscribers who have re-subscribed more than once for each product;
```
WITH resubscribed AS (
	SELECT 
	  customer_id,
	  product_id,
	  COUNT(*) AS cnt
	FROM 
	  tblSubscriptionInfo
	GROUP BY customer_id, product_id
	HAVING COUNT(*) > 1
)
SELECT
	product_id,
	COUNT(DISTINCT customer_id) AS cnt_resubscribed
FROM resubscribed
GROUP BY product_id;
```

* subscribers who have re-subscribed a higher version of the product in 2023 - for example Autocad 2022 to Autocad 2023.
```agsl
WITH product_prefix_suffix AS (
  SELECT
    customer_id,
    SUBSTRING(product_name FROM '^[a-zA-Z]+') AS product,
    SUBSTRING(product_name FROM '[0-9]+$') AS version
  FROM 
    tblSubscriptionInfo
  WHERE 
    subscription_start_date >= '2023-01-01' 
    AND subscription_start_date < '2024-01-01'
),
resubscribed AS (
  SELECT
    customer_id,
    product,
    ARRAY_AGG (
      version ORDER BY version DESC
    ) AS versions
  FROM 
    product_prefix_suffix
  GROUP BY customer_id, product
  HAVING COUNT(DISTINCT version) > 1
)
SELECT 
  *
FROM 
  resubscribed
WHERE versions[0] >= versions[1];
```
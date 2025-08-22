-- Super Mass Gainer 5.4kg -> 1000,1500,2000,3000
INSERT INTO app_product_available_quantities (app_product_id, available_quantities)
SELECT p.id, q.qty
FROM app_product p
         JOIN (SELECT 1000 AS qty UNION ALL SELECT 1500 UNION ALL SELECT 2000 UNION ALL SELECT 3000) q ON 1=1
WHERE p.product_name = 'Super Mass Gainer 5.4kg'
  AND NOT EXISTS (
    SELECT 1 FROM app_product_available_quantities x
    WHERE x.app_product_id = p.id AND x.available_quantities = q.qty
);

-- Whey Core Protein 5kg -> 500,1000,2000,3000,4000,5000
INSERT INTO app_product_available_quantities (app_product_id, available_quantities)
SELECT p.id, q.qty
FROM app_product p
         JOIN (SELECT 500 AS qty UNION ALL SELECT 1000 UNION ALL SELECT 2000 UNION ALL SELECT 3000 UNION ALL SELECT 4000 UNION ALL SELECT 5000) q ON 1=1
WHERE p.product_name = 'Whey Core Protein 5kg'
  AND NOT EXISTS (
    SELECT 1 FROM app_product_available_quantities x
    WHERE x.app_product_id = p.id AND x.available_quantities = q.qty
);

-- Gold whey standard 2,270kg -> 500,1000,1500,2000
INSERT INTO app_product_available_quantities (app_product_id, available_quantities)
SELECT p.id, q.qty
FROM app_product p
         JOIN (SELECT 500 AS qty UNION ALL SELECT 1000 UNION ALL SELECT 1500 UNION ALL SELECT 2000) q ON 1=1
WHERE p.product_name = 'Gold whey standard 2,270kg'
  AND NOT EXISTS (
    SELECT 1 FROM app_product_available_quantities x
    WHERE x.app_product_id = p.id AND x.available_quantities = q.qty
);
-- Creatine -> 500,1000,1500
INSERT INTO app_product_available_quantities (app_product_id, available_quantities)
SELECT p.id, q.qty
FROM app_product p
         JOIN (
    SELECT 50 AS qty
    UNION ALL SELECT 100
    UNION ALL SELECT 150
    UNION ALL SELECT 200
    UNION ALL SELECT 250
) q ON 1=1
WHERE
  -- Use the exact product name you see in phpMyAdmin:
    p.product_name = 'Créatine Monohydrate Micronisée - Biotech USA'
  -- or safer: p.product_name LIKE 'Cr%atine%'  (works with/without accents)
  AND NOT EXISTS (
    SELECT 1
    FROM app_product_available_quantities x
    WHERE x.app_product_id = p.id AND x.available_quantities = q.qty
);

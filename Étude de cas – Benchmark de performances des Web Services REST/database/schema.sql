-- Schema PostgreSQL pour l'étude de performance REST
-- Compatible PostgreSQL 14+

CREATE DATABASE rest_benchmark;
\c rest_benchmark;

-- Table des catégories
CREATE TABLE category (
   id BIGSERIAL PRIMARY KEY,
   code VARCHAR(32) UNIQUE NOT NULL,
   name VARCHAR(128) NOT NULL,
   updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Table des produits/items
CREATE TABLE item (
   id BIGSERIAL PRIMARY KEY,
   sku VARCHAR(64) UNIQUE NOT NULL,
   name VARCHAR(128) NOT NULL,
   price NUMERIC(10,2) NOT NULL,
   stock INT NOT NULL,
   category_id BIGINT NOT NULL REFERENCES category(id),
   updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- Index pour optimiser les performances
CREATE INDEX idx_item_category ON item(category_id);
CREATE INDEX idx_item_updated_at ON item(updated_at);
CREATE INDEX idx_category_code ON category(code);
CREATE INDEX idx_item_sku ON item(sku);

-- Fonction pour mettre à jour updated_at automatiquement
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = NOW();
   RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers pour mettre à jour updated_at
CREATE TRIGGER update_category_updated_at BEFORE UPDATE ON category
   FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_item_updated_at BEFORE UPDATE ON item
   FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Vues pour les statistiques
CREATE VIEW category_stats AS
SELECT 
    c.id,
    c.code,
    c.name,
    COUNT(i.id) as item_count,
    AVG(i.price) as avg_price,
    SUM(i.stock) as total_stock
FROM category c
LEFT JOIN item i ON c.id = i.category_id
GROUP BY c.id, c.code, c.name;

-- Index sur la vue pour de meilleures performances
CREATE INDEX idx_category_stats ON item(category_id, price, stock);
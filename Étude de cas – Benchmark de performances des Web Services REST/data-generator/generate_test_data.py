#!/usr/bin/env python3
"""
Générateur de données de test pour l'étude de benchmark REST
Génère 2,000 catégories et 100,000 produits pour les tests de performance
"""

import psycopg2
import random
import string
from datetime import datetime, timedelta
from faker import Faker
import csv
import json

# Configuration de la base de données
DB_CONFIG = {
    'host': 'localhost',
    'port': 5432,
    'database': 'rest_benchmark',
    'user': 'benchmark_user',
    'password': 'benchmark_pass'
}

# Paramètres de génération
NUM_CATEGORIES = 2000
NUM_ITEMS = 100000
ITEMS_PER_CATEGORY_AVG = NUM_ITEMS // NUM_CATEGORIES

fake = Faker('fr_FR')

def generate_sku():
    """Génère un SKU unique"""
    return ''.join(random.choices(string.ascii_uppercase + string.digits, k=8))

def generate_category_code():
    """Génère un code de catégorie unique"""
    return 'CAT' + ''.join(random.choices(string.ascii_uppercase + string.digits, k=6))

def generate_category_data():
    """Génère les données de catégories"""
    categories = []
    codes_used = set()
    
    print(f"Génération de {NUM_CATEGORIES} catégories...")
    
    for i in range(NUM_CATEGORIES):
        # Code unique
        code = generate_category_code()
        while code in codes_used:
            code = generate_category_code()
        codes_used.add(code)
        
        # Nom de catégorie réaliste
        category_name = fake.catch_phrase()
        
        categories.append({
            'id': i + 1,
            'code': code,
            'name': category_name,
            'updated_at': fake.date_time_between(start_date='-1y', end_date='now')
        })
        
        if (i + 1) % 100 == 0:
            print(f"  {i + 1}/{NUM_CATEGORIES} catégories générées")
    
    return categories

def generate_item_data(categories):
    """Génère les données de produits"""
    items = []
    skus_used = set()
    
    print(f"Génération de {NUM_ITEMS} produits...")
    
    for i in range(NUM_ITEMS):
        # SKU unique
        sku = generate_sku()
        while sku in skus_used:
            sku = generate_sku()
        skus_used.add(sku)
        
        # Sélection aléatoire d'une catégorie
        category = random.choice(categories)
        
        # Génération des données produit
        item = {
            'id': i + 1,
            'sku': sku,
            'name': fake.catch_phrase(),
            'price': round(random.uniform(5.0, 999.99), 2),
            'stock': random.randint(0, 1000),
            'category_id': category['id'],
            'updated_at': fake.date_time_between(start_date='-6m', end_date='now')
        }
        
        items.append(item)
        
        if (i + 1) % 5000 == 0:
            print(f"  {i + 1}/{NUM_ITEMS} produits générés")
    
    return items

def insert_categories(conn, categories):
    """Insert les catégories en base"""
    cursor = conn.cursor()
    
    print("Insertion des catégories en base...")
    
    insert_query = """
    INSERT INTO category (code, name, updated_at) 
    VALUES (%s, %s, %s)
    """
    
    batch_size = 1000
    for i in range(0, len(categories), batch_size):
        batch = categories[i:i + batch_size]
        data = [(cat['code'], cat['name'], cat['updated_at']) for cat in batch]
        cursor.executemany(insert_query, data)
        conn.commit()
        print(f"  {min(i + batch_size, len(categories))}/{len(categories)} catégories insérées")
    
    cursor.close()

def insert_items(conn, items):
    """Insert les produits en base"""
    cursor = conn.cursor()
    
    print("Insertion des produits en base...")
    
    insert_query = """
    INSERT INTO item (sku, name, price, stock, category_id, updated_at)
    VALUES (%s, %s, %s, %s, %s, %s)
    """
    
    batch_size = 5000
    for i in range(0, len(items), batch_size):
        batch = items[i:i + batch_size]
        data = [(item['sku'], item['name'], item['price'], 
                item['stock'], item['category_id'], item['updated_at']) 
                for item in batch]
        cursor.executemany(insert_query, data)
        conn.commit()
        print(f"  {min(i + batch_size, len(items))}/{len(items)} produits insérés")
    
    cursor.close()

def generate_jmeter_test_data(categories, items):
    """Génère les fichiers CSV pour JMeter"""
    print("Génération des fichiers CSV pour JMeter...")
    
    # IDs de catégories pour les tests
    with open('jmeter_category_ids.csv', 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(['category_id'])
        for cat in random.sample(categories, min(500, len(categories))):
            writer.writerow([cat['id']])
    
    # IDs de produits pour les tests
    with open('jmeter_item_ids.csv', 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(['item_id'])
        for item in random.sample(items, min(1000, len(items))):
            writer.writerow([item['id']])
    
    # Payloads légers (1KB) pour POST/PUT
    with open('jmeter_light_payloads.csv', 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(['name', 'price', 'stock', 'description'])
        for _ in range(500):
            name = fake.catch_phrase()
            price = round(random.uniform(5.0, 999.99), 2)
            stock = random.randint(0, 1000)
            # Description ~1KB
            description = fake.text(max_nb_chars=1000)
            writer.writerow([name, price, stock, description])
    
    # Payloads lourds (5KB) pour les tests de charge
    with open('jmeter_heavy_payloads.csv', 'w', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(['name', 'price', 'stock', 'description', 'specifications'])
        for _ in range(200):
            name = fake.catch_phrase()
            price = round(random.uniform(5.0, 999.99), 2)
            stock = random.randint(0, 1000)
            # Description + spécifications ~5KB
            description = fake.text(max_nb_chars=3000)
            specifications = fake.text(max_nb_chars=2000)
            writer.writerow([name, price, stock, description, specifications])
    
    print("Fichiers CSV générés:")
    print("  - jmeter_category_ids.csv (500 IDs)")
    print("  - jmeter_item_ids.csv (1000 IDs)")
    print("  - jmeter_light_payloads.csv (500 payloads ~1KB)")
    print("  - jmeter_heavy_payloads.csv (200 payloads ~5KB)")

def main():
    """Fonction principale"""
    print("=== Générateur de données de test REST Benchmark ===")
    print(f"Target: {NUM_CATEGORIES} catégories, {NUM_ITEMS} produits")
    print()
    
    try:
        # Connexion à la base
        print("Connexion à PostgreSQL...")
        conn = psycopg2.connect(**DB_CONFIG)
        print("✓ Connecté")
        
        # Génération des données
        categories = generate_category_data()
        items = generate_item_data(categories)
        
        # Insertion en base
        insert_categories(conn, categories)
        insert_items(conn, items)
        
        # Génération des fichiers JMeter
        generate_jmeter_test_data(categories, items)
        
        # Statistiques finales
        cursor = conn.cursor()
        cursor.execute("SELECT COUNT(*) FROM category")
        cat_count = cursor.fetchone()[0]
        
        cursor.execute("SELECT COUNT(*) FROM item")
        item_count = cursor.fetchone()[0]
        
        cursor.execute("""
        SELECT c.name, COUNT(i.id) as item_count 
        FROM category c 
        LEFT JOIN item i ON c.id = i.category_id 
        GROUP BY c.id, c.name 
        ORDER BY item_count DESC 
        LIMIT 5
        """)
        top_categories = cursor.fetchall()
        
        print(f"\n=== Génération terminée ===")
        print(f"✓ {cat_count} catégories créées")
        print(f"✓ {item_count} produits créés")
        print(f"\nTop 5 des catégories par nombre de produits:")
        for cat_name, count in top_categories:
            print(f"  - {cat_name}: {count} produits")
        
        conn.close()
        
    except Exception as e:
        print(f"❌ Erreur: {e}")
        return 1
    
    return 0

if __name__ == "__main__":
    exit(main())
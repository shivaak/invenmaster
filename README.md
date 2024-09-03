### `Invenmaster Application`


# Invenmaster Application

## Building docker image
```bash
docker build -t invenmaster:latest .
```

## Running the application

- `docker-compose.prod.yml`: Docker Compose file for the production environment.
- `docker-compose.staging.yml`: Docker Compose file for the staging environment.

## Setting Up Networks

Ensure that the following Docker networks are created, as the PostgreSQL and ELK stack containers should be running in these networks:

```bash
docker network create shared_db_network
docker network create shared_elk_network
```

## Environment Variables

We use environment variables to manage sensitive data like database URLs, usernames, and passwords.

### Production Environment Variables


```env
export PROD_DB_URL=jdbc:postgresql://<POSTGRES_HOST>:5432/<DB_NAME>
export PROD_DB_USERNAME=<USERNAME>
export PROD_DB_PASSWORD=<PWD>

```

### Staging Environment Variables

```env
export STAGING_DB_URL=jdbc:postgresql://<POSTGRES_HOST>:5432/<DB_NAME>
export STAGING_DB_USERNAME=<USERNAME>
export STAGING_DB_PASSWORD=<PWD>
```

## Running the Application

### Production

1. Ensure that the PostgreSQL and ELK containers are running in the `shared_db_network` and `shared_elk_network` networks.
2. Run the Docker Compose setup:

   ```bash
   docker-compose -p invenmaster-prod -f docker-compose.prod.yaml up -d
   ```

### Staging

1. Ensure that the PostgreSQL and ELK containers are running in the `shared_db_network` and `shared_elk_network` networks.
2. Run the Docker Compose setup:

   ```bash
   docker-compose -p invenmaster-staging -f docker-compose.staging.yaml up -d
   ```

## Stopping the Application

### Production
   ```bash
   docker-compose -p invenmaster-prod -f docker-compose.prod.yaml down
   ```

### Staging

   ```bash
   docker-compose -p invenmaster-staging -f docker-compose.staging.yaml down
   ```
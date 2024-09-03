### `Invenmaster Application`


# Invenmaster Application

## Building docker image
```bash
docker build -t invenmaster:latest .
```

## Running the application

- `docker-compose.prod.yml`: Docker Compose file for the production environment.
- `docker-compose.dev.yml`: Docker Compose file for the dev environment.
- `docker-compose.local.yml`: Docker Compose file for the local environment.

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

### Dev Environment Variables

```env
export DEV_DB_URL=jdbc:postgresql://<POSTGRES_HOST>:5432/<DB_NAME>
export DEV_DB_USERNAME=<USERNAME>
export DEV_DB_PASSWORD=<PWD>
```

## Running the Application

### Production

1. Ensure that the PostgreSQL and ELK containers are running in the `shared_db_network` and `shared_elk_network` networks.
2. Run the Docker Compose setup:

   ```bash
   docker-compose -p invenmaster-prod -f docker-compose.prod.yaml up -d
   ```

### Dev

1. Ensure that the PostgreSQL and ELK containers are running in the `shared_db_network` and `shared_elk_network` networks.
2. Run the Docker Compose setup:

   ```bash
   docker-compose -p invenmaster-dev -f docker-compose.dev.yaml up -d
   ```

### Local

1. Ensure that the PostgreSQL and ELK containers are running in the `shared_db_network` and `shared_elk_network` networks.
2. Run the Docker Compose setup:

   ```bash
   docker-compose -p invenmaster-local -f docker-compose.local.yaml up -d
   ```
   
## Stopping the Application

### Production
   ```bash
   docker-compose -p invenmaster-prod -f docker-compose.prod.yaml down
   ```

### Dev

   ```bash
   docker-compose -p invenmaster-dev -f docker-compose.dev.yaml down
   ```

### Local

   ```bash
   docker-compose -p invenmaster-local -f docker-compose.local.yaml down
   ```
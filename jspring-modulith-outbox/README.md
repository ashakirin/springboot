# jspring-modulith-outbox

Demo of the **Transactional Outbox pattern** with **Spring Modulith** event publication and
RabbitMQ externalization. eCommerce domain: a `POST /orders` request creates an `Order` entity
and publishes an `OrderCompleted` event in one transaction. A local `inventory` listener and a
RabbitMQ externalization listener each get their own outbox row in the same transaction.

## Stack

- Java 25
- Spring Boot 4.0.6
- Spring Modulith 2.0.6 (`spring-modulith-starter-jdbc`, `spring-modulith-events-amqp`)
- PostgreSQL 17 (Order table + outbox table)
- RabbitMQ 4 (with management UI)
- Maven, Docker / Docker Compose

## Run

```bash
docker compose up --build
```

Services start in this order:

- `postgres` on `localhost:5432`
- `rabbitmq` on `localhost:5672` (AMQP) and `localhost:15672` (UI, `guest` / `guest`)
- `order-service` on `localhost:8080`

## Place an order

Use [`requests.http`](./requests.http) from your IDE (IntelliJ / VS Code), or:

```bash
curl -X POST http://localhost:8080/orders \
  -H 'Content-Type: application/json' \
  -d '{
    "customerEmail": "alice@example.com",
    "productSku": "BOOK-1234",
    "quantity": 2,
    "totalAmount": 39.98
  }'
```

Response: `201 Created` with `Location: /orders/<uuid>`.

## What to look for

In the `order-service` logs:

```
Inventory: decremented stock for SKU BOOK-1234 by 2 (order ...)
```

In RabbitMQ management UI (`http://localhost:15672`):

- Exchange `orders.completed` (topic) — declared by the app at startup.
- After `POST /orders`, message count on the exchange increases. Bind any queue to it to consume.

In Postgres:

```sql
SELECT id, listener_id, status, completion_attempts, publication_date
FROM event_publication;        -- in-flight rows; empty when everything is processed
SELECT count(*) FROM event_publication_archive;   -- completed rows (archive completion mode)
```

## Documentation

[modulith-outbox.md](./modulith-outbox.md) explains the outbox pattern and how Spring Modulith
implements it, with diagrams and pointers to the relevant code in this repo.

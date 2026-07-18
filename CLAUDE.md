# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A learning project for the FPoly **jav102** course: a Jakarta EE web app (WAR) using **Hibernate ORM 6.4.5 as the JPA provider** against **Microsoft SQL Server**. The current work is DB/entity modeling — a drinks-ordering domain (users, categories, drinks, bills, and bill line items). The web layer is still just a scaffold (`HelloServlet`).

## Commands

The project uses the Maven wrapper. `JAVA_HOME` is not set in this environment but the wrapper resolves Java via the system `java` on PATH, so commands work as-is.

```bash
./mvnw clean compile      # compile
./mvnw clean package      # build the WAR (target/jav102-su26-1.0-SNAPSHOT.war)
```

- **Run the JPA bootstrap:** `org.example.jav102su26.utils.JdbcMain#main` — opening an `EntityManager` triggers Hibernate schema generation (see gotcha below). Run it from the IDE, or via an exec goal if configured.
- **Tests:** JUnit 5 is on the classpath but there is **no `src/test` yet** — `./mvnw test` does nothing. When adding tests, put them under `src/test/java` and run a single one with `./mvnw test -Dtest=ClassName#methodName`.
- **Deploy:** it's a WAR — deploy `target/*.war` to a servlet 6.0 container (Tomcat 10+, since it uses the `jakarta.*` namespace, not `javax.*`). `HelloServlet` is mapped at `/hello-servlet`.

## Critical: schema is dropped and recreated on every run

`src/main/resources/META-INF/persistence.xml` sets:

```
jakarta.persistence.schema-generation.database.action = drop-and-create
```

Every time the `EntityManagerFactory` is built (i.e. every app/`JdbcMain` start), **all tables are dropped and recreated from the entity mappings — all data is wiped.** This is why entity annotations *are* the schema; there are no migration files. If you need data to persist across runs, change this action to `none` or `update`.

After schema creation it runs `jakarta.persistence.sql-load-script-source = create.sql` (`src/main/resources/create.sql`), which reseeds categories, users, drinks, bills, and bill line items on every run. When editing it: **`INSERT`s only** (Hibernate creates the tables from the entities), one statement per line, **no `GO`** and no multi-line statements (Hibernate's single-line script extractor), and use column names matching the entity fields (e.g. `fullName`, `createdAt`, `category_id`). Keep insert order FK-safe (categories/users → drinks → bills → bills_drinks) and each bill's `total` equal to the sum of its line-item prices.

## Database connection

Hardcoded in `persistence.xml`: SQL Server at `localhost:1433`, database `jav102_su26`, user `sa` / password `123456`, with `encrypt=true;trustServerCertificate=true`. The database must exist before running (Hibernate creates tables, not the database).

## Architecture

Package root: `org.example.jav102su26`.

- **`entity/`** — JPA entities and their enums. This is the heart of the project right now.
- **`utils/`** — two independent DB-access paths:
  - `EntityManagerUtils` — the JPA path. Holds a single static `EntityManagerFactory` for persistence unit `"default"`; `getEntityManager()` hands out `EntityManager`s. This is the intended data-access route.
  - `DatabaseConnectionManager` — a separate raw-JDBC path (`DriverManager` connection). Parallel/legacy to JPA; not wired into the JPA layer.
  - `JdbcMain` — manual runner that constructs both and opens an `EntityManager`.
- **`HelloServlet`** — placeholder servlet; no real web layer yet.

### Entity model conventions

The `Bill`↔`Drink` relationship is modeled as an **association entity, not a plain `@ManyToMany`**, because the link carries data:

- `BillDrink` (table `bills_drinks`) is the line-item entity holding `quantity` and `price`, with `@ManyToOne` FKs to `Bill` and `Drink`.
- `Bill.billDrinks` owns the lifecycle: `@OneToMany(mappedBy="bill", cascade=ALL, orphanRemoval=true)` — saving/deleting a bill cascades to its lines. `Drink.billDrinks` is a read-only back-reference (no cascade), safe to remove if unused.
- Rule of thumb followed here: cascade/orphanRemoval live only on the *owning* parent side (`Bill`), never on shared master data (`Drink`).

Other conventions to preserve when editing entities:

- **Money is `BigDecimal(precision=12, scale=2)`** (`Bill.total`, `Drink.price`, `BillDrink.price`) — never `double`. Construct from strings, and multiply with `BigDecimal.valueOf(quantity)`.
- **Enums are `@Enumerated(EnumType.STRING)`** (`Role`, `BillStatus`) — stored as names, never ordinals.
- **Lombok `@Getter @Setter` (not `@Data`)** on entities, to avoid `equals`/`hashCode`/`toString` recursing through relationship fields. Keep it this way.
- `@ManyToOne` associations are explicitly `FetchType.LAZY`.
- Table names are plural (`bills`, `drinks`, `users`, `categories`, `bills_drinks`).
- IDs are surrogate `Integer` with `GenerationType.IDENTITY`.

## Environment notes

- Shell is PowerShell on Windows; a Bash tool is also available for POSIX scripts.

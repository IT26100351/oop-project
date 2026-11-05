# HomeFinder - SE1020 Group WD04

Separate **frontend** (HTML/CSS/JS) and **backend** (Java Spring Boot REST API + .txt files).

## Run it
1. **Start the backend**:
   - In IntelliJ: Open the `backend` folder (`File` > `Open` > choose `backend`), wait for Maven to load, and run `PortalApplication.java`.
   - Or from terminal: Run `mvn spring-boot:run` inside the `backend` directory.
2. **Open the application**:
   - Click this single working link in your browser: **[http://localhost:8080](http://localhost:8080)**
   *(The application serves both the frontend website and backend REST APIs unified on this single address — no different ports or extra servers required!)*
3. **Sample login**: `shaahid@example.com` / `pass123`

## Six members (each = package + manager + controller + .txt file, full CRUD)
| # | Member part | Package | File | URL |
|---|-------------|---------|------|-----|
| 1 | Users (signup/login) | user | users.txt | /api/users |
| 2 | Houses | house | houses.txt | /api/houses |
| 3 | Apartments | apartment | apartments.txt | /api/apartments |
| 4 | Lands | land | lands.txt | /api/lands |
| 5 | Reviews | review | reviews.txt | /api/reviews |
| 6 | Owners + images | owner, image | owners.txt, data/images | /api/owners, /api/images |

`common` package (Property, Address, FileHelper) is shared - agree on it together first.

## OOP map (for the viva)
- Encapsulation: private fields + getters/setters everywhere
- Constructors: every class
- Inheritance: House, Apartment, Land extend Property
- Polymorphism: each overrides getDescription()/toFileString()
- Composition: Property creates its own Address
- Aggregation: Property has an Owner (separate file, survives property deletion)
- Loops / if-else: managers search the lists, controllers validate input

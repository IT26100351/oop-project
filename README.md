# HomeFinder - SE1020 Group WD04

A real estate listing portal with a **Java Spring Boot** backend and a pure **HTML/CSS/JS** frontend.

---

## 📁 Project Structure

```
oop-project/
├── backend/                          ← Spring Boot Java backend (Maven)
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/shaahid/portal/
│       │   ├── PortalApplication.java   ← Main entry point (run this!)
│       │   ├── WebConfig.java           ← CORS + static file serving
│       │   ├── common/
│       │   │   ├── FileHelper.java      ← Shared file I/O helper
│       │   │   ├── Property.java        ← Abstract parent (Inheritance)
│       │   │   └── Address.java         ← Composition example
│       │   ├── user/      (Shaahid)     ← User, UserManager, UserController
│       │   ├── house/     (Amaya)       ← House, HouseManager, HouseController
│       │   ├── apartment/ (Hishadi)     ← Apartment, ApartmentManager, ApartmentController
│       │   ├── land/      (Senuka)      ← Land, LandManager, LandController
│       │   ├── review/    (Thasreef)    ← Review, ReviewManager, ReviewController
│       │   ├── owner/     (Dineth)      ← Owner, OwnerManager, OwnerController
│       │   └── image/     (Dineth)      ← ImageController (photo uploads)
│       └── resources/
│           └── application.properties
├── frontend/                         ← HTML/CSS/JS frontend
│   ├── index.html
│   ├── login.html
│   ├── signup.html
│   ├── success.html
│   ├── add-listing.html
│   ├── details.html
│   ├── css/
│   │   └── style.css
│   ├── js/
│   │   └── app.js
│   └── images/
│       └── logo.svg
├── data/                             ← Flat-file database (.txt files)
│   ├── users.txt
│   ├── houses.txt
│   ├── apartments.txt
│   ├── lands.txt
│   ├── owners.txt
│   ├── reviews.txt                   ← Created automatically on first review
│   ├── images/                       ← Uploaded property photos
│   └── backup/                       ← Sample/backup data files
└── tools/                            ← Optional dev servers (Node.js)
    ├── api-server.js                  ← Mock API (no Java needed)
    └── server.js                      ← Frontend dev proxy server
```

---

## 🚀 How to Run

### Option A — Java Backend (recommended)

1. **Open IntelliJ IDEA** → `File > Open` → select the **`backend`** folder
2. Wait for Maven to download dependencies (first time only)
3. Run **`PortalApplication.java`**
4. Open **[http://localhost:8080](http://localhost:8080)** in your browser

> The backend serves both the REST API (`/api/*`) AND the frontend pages on the same port 8080.

### Option B — From terminal

```bash
cd backend
mvn spring-boot:run
```

Then open **[http://localhost:8080](http://localhost:8080)**

### Sample login
- Email: `shaahid@example.com`
- Password: `pass123`

---

## 🧩 OOP Concepts Demonstrated

| Concept | Where |
|---------|-------|
| **Encapsulation** | Private fields + getters/setters in every class |
| **Inheritance** | `House`, `Apartment`, `Land` all extend `Property` |
| **Polymorphism** | Each overrides `getDescription()` and `toFileString()` |
| **Abstraction** | `Property` is abstract — cannot be created directly |
| **Composition** | `Property` creates its own `Address` |
| **Aggregation** | `Property` HAS an `Owner` (owner survives property deletion) |

---

## 🔗 REST API Endpoints

| Resource | URL | Methods |
|----------|-----|---------|
| Users | `/api/users` | GET, POST |
| Login | `/api/users/login` | POST |
| Signup | `/api/users/signup` | POST |
| Houses | `/api/houses` | GET, POST, PUT, DELETE |
| Apartments | `/api/apartments` | GET, POST, PUT, DELETE |
| Lands | `/api/lands` | GET, POST, PUT, DELETE |
| Reviews | `/api/reviews` | GET, POST, PUT, DELETE |
| Owners | `/api/owners` | GET, POST, PUT, DELETE |
| Images | `/api/images` | POST, DELETE |

---

## 👥 Team — WD04

| Member | Branch | Package | Responsibility |
|--------|--------|---------|---------------|
| Shaahid | `shaahid` | `user` | User signup/login, auth |
| Amaya | `Amaya` | `house` | House listings |
| Hishadi | `Hishadi` | `apartment` | Apartment listings |
| Senuka | `senuka` | `land` | Land listings |
| Thasreef | `Thasreef` | `review` | Property reviews |
| Dineth | `dineth` | `owner`, `image` | Owner contact + photo uploads |

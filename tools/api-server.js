const http = require('http');

const PORT = 3000;

const houses = [
  { id: "H1", type: "house", listingType: "Sale", price: 25000000, description: "Modern 4 Bedroom House", address: { location: "Colombo 7" }, imageName: "house1.jpg" },
  { id: "H2", type: "house", listingType: "Rent", price: 120000, description: "Cozy Family Home", address: { location: "Nugegoda" }, imageName: "house2.jpg" }
];

const apartments = [
  { id: "A1", type: "apartment", listingType: "Sale", price: 15000000, description: "Luxury 2BR Apartment", address: { location: "Mount Lavinia" }, imageName: "apartment1.jpg" },
  { id: "A2", type: "apartment", listingType: "Rent", price: 80000, description: "Studio Apartment with City View", address: { location: "Colombo 3" }, imageName: "apartment2.jpg" }
];

const lands = [
  { id: "L1", type: "land", listingType: "Sale", price: 5000000, description: "20 Perches Prime Land", address: { location: "Kandy" }, imageName: "land1.jpg" },
  { id: "L2", type: "land", listingType: "Sale", price: 2000000, description: "Agricultural Land", address: { location: "Galle" }, imageName: "land2.jpg" }
];

let users = []; // In-memory mock users

const server = http.createServer((req, res) => {
  // Add CORS headers so frontend on 3000 can fetch from 8080
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

  if (req.method === 'OPTIONS') {
    res.writeHead(204);
    res.end();
    return;
  }

  if (req.url.startsWith('/images/')) {
    const img = req.url.split('/')[2];
    const imageMap = {
      "house1.jpg": "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?w=800&q=80",
      "house2.jpg": "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=800&q=80",
      "apartment1.jpg": "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800&q=80",
      "apartment2.jpg": "https://images.unsplash.com/photo-1502672260266-1c1e39b4968b?w=800&q=80",
      "land1.jpg": "https://images.unsplash.com/photo-1500382017468-9049fed747ef?w=800&q=80",
      "land2.jpg": "https://images.unsplash.com/photo-1629196914285-b1458e658399?w=800&q=80"
    };

    const fallback = "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=800&q=80";

    // Redirect to direct unsplash image matching the type
    res.writeHead(302, { 'Location': imageMap[img] || fallback });
    res.end();
    return;
  }

  if (req.method === 'POST') {
    let body = '';
    req.on('data', chunk => body += chunk.toString());
    req.on('end', () => {
      if (req.url === '/api/users/signup') {
        const data = JSON.parse(body);
        if (!data.email || !data.password || !data.name) {
          res.writeHead(400, { 'Content-Type': 'text/plain' });
          return res.end("Please fill all fields.");
        }
        if (users.find(u => u.email === data.email)) {
          res.writeHead(400, { 'Content-Type': 'text/plain' });
          return res.end("Email already exists.");
        }
        const user = { id: String(users.length + 1), name: data.name, email: data.email, nic: data.nic, password: data.password };
        users.push(user);
        res.writeHead(200, { 'Content-Type': 'text/plain' });
        res.end("User created successfully");
      } else if (req.url === '/api/users/login') {
        const data = JSON.parse(body);
        const user = users.find(u => u.email === data.email && u.password === data.password);
        if (user) {
          res.writeHead(200, { 'Content-Type': 'application/json' });
          res.end(JSON.stringify({ id: user.id, name: user.name, email: user.email, nic: user.nic }));
        } else {
          res.writeHead(401, { 'Content-Type': 'text/plain' });
          res.end("Invalid email or password");
        }
      } else {
        res.writeHead(404, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({ error: "Not Found" }));
      }
    });
    return;
  }

  if (req.url === '/api/houses') {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify(houses));
  } else if (req.url === '/api/apartments') {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify(apartments));
  } else if (req.url === '/api/lands') {
    res.writeHead(200, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify(lands));
  } else {
    res.writeHead(404, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ error: "Not Found" }));
  }
});

server.listen(PORT, () => {
  console.log(`Mock Backend API running at http://localhost:${PORT}/`);
});

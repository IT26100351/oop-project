const http = require('http');
const fs = require('fs');
const path = require('path');

// Note: Running Java PortalApplication already serves both frontend and backend on http://localhost:8080/
// This Node server is an optional frontend server that forwards API/image requests to the backend.
const PORT = process.env.PORT || 3000;
const BACKEND_PORT = 8080;

const MIME_TYPES = {
  '.html': 'text/html',
  '.css': 'text/css',
  '.js': 'text/javascript',
  '.json': 'application/json',
  '.png': 'image/png',
  '.jpg': 'image/jpg',
  '.jpeg': 'image/jpg',
  '.svg': 'image/svg+xml',
  '.ico': 'image/x-icon'
};

const server = http.createServer((req, res) => {
  const pathname = req.url.split('?')[0];

  // Proxy /api/* and /images/* to the backend on port 8080
  if (pathname.startsWith('/api/') || pathname.startsWith('/images/')) {
    const options = {
      hostname: 'localhost',
      port: BACKEND_PORT,
      path: req.url,
      method: req.method,
      headers: req.headers
    };
    const proxyReq = http.request(options, (proxyRes) => {
      res.writeHead(proxyRes.statusCode, proxyRes.headers);
      proxyRes.pipe(res, { end: true });
    });
    proxyReq.on('error', () => {
      res.writeHead(502, { 'Content-Type': 'text/plain' });
      res.end('Backend server on port 8080 is not reachable. Please start PortalApplication.java.');
    });
    req.pipe(proxyReq, { end: true });
    return;
  }

  let filePath = path.join(__dirname, pathname === '/' ? 'index.html' : pathname);
  const extname = String(path.extname(filePath)).toLowerCase();
  const contentType = MIME_TYPES[extname] || 'application/octet-stream';

  fs.readFile(filePath, (error, content) => {
    if (error) {
      if (error.code === 'ENOENT') {
        res.writeHead(404, { 'Content-Type': 'text/html' });
        res.end('<h1>404 Not Found</h1>', 'utf-8');
      } else {
        res.writeHead(500);
        res.end('Server error: ' + error.code + '\n');
      }
    } else {
      res.writeHead(200, { 'Content-Type': contentType });
      res.end(content, 'utf-8');
    }
  });
});

server.listen(PORT, () => {
  console.log(`Frontend dev server running at http://localhost:${PORT}/`);
  console.log(`Backend API proxied to http://localhost:${BACKEND_PORT}/`);
  console.log(`Note: Running PortalApplication.java already hosts everything at http://localhost:8080/`);
});

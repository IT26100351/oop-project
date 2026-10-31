// ===== HomeFinder - Shared JavaScript (app.js) =====

// Single unified backend API URL:
// When served by the backend on http://localhost:8080, use same-origin relative path ("")
// When opened via file:// or another port, fallback to http://localhost:8080
const API = (window.location.protocol.startsWith("http") && (window.location.port === "8080" || window.location.port === ""))
  ? ""
  : "http://localhost:8080";

// ----- Login helpers: the logged-in user is remembered in the browser (localStorage) -----
function getUser() {
  try { return JSON.parse(localStorage.getItem("user")); } catch (e) { return null; }
}

function logout() {
  localStorage.removeItem("user");
  location.href = "index.html";
}

// When "Create a Listing" is clicked: logged in -> add page, otherwise -> login page
function createListing() {
  location.href = getUser() ? "add-listing.html" : "login.html";
}

// ----- Draw the top bar on every page. "active" is the link to highlight -----
function renderNavbar(active) {
  const user = getUser();
  const link = (page, text, key) =>
    `<a href="${page}" class="${active === key ? "active" : ""}">${text}</a>`;
  
  const right = user
    ? `<span>Hi, <b style="color:var(--gold)">${esc(user.name)}</b></span>
       <button class="linkbtn" onclick="logout()">Log out</button>`
    : `<a href="login.html" style="font-weight:600">Log in</a>
       <a href="signup.html" style="font-weight:600;color:var(--gold-light)">Sign up</a>`;

  const navEl = document.getElementById("navbar");
  if (navEl) {
    navEl.innerHTML = `
      <div class="topbar">
        <a href="index.html" class="logo">
          <span>Home<span class="gold-mark">Finder</span></span>
          <span class="logo-tagline">Prime Property Network</span>
        </a>
        <div class="nav">
          ${link("index.html", "Home", "home")}
          ${link("index.html?type=apartment", "Apartments", "apartment")}
          ${link("index.html?type=house", "Houses", "house")}
          ${link("index.html?type=land", "Lands", "land")}
          ${link("index.html#feedback", "Client Feedback", "feedback")}
          ${link("#contact", "Contact", "contact")}
        </div>
        <div class="nav-right">
          <div class="nav-hotline d-none d-md-flex">
            <span>📞</span> +94 11 269 9822
          </div>
          ${right}
          <button class="btn" onclick="createListing()">+ Create a Listing</button>
        </div>
      </div>`;
  }

  // Also render the rich luxury footer
  renderFooter();
}

// ----- Rich Luxury Footer (Prime Lands inspired) -----
function renderFooter() {
  const footerEl = document.getElementById("footer");
  if (!footerEl) return;
  footerEl.innerHTML = `
    <div class="footer" id="contact">
      <div class="footer-wrap">
        <div class="footer-grid">
          <div class="footer-brand">
            <h3>Home<span>Finder</span></h3>
            <p>
              Sri Lanka's premier destination for verified real estate. With over 30 years of trust, 
              we bring buyers and sellers together with complete legal transparency, certified titles, 
              and prime property choices across 18+ districts.
            </p>
            <div class="footer-socials">
              <a href="https://www.facebook.com/Primelands" target="_blank" class="footer-social-btn" title="Facebook">f</a>
              <a href="https://www.instagram.com/primelands/" target="_blank" class="footer-social-btn" title="Instagram">in</a>
              <a href="https://www.youtube.com/channel/UCE9avygAduQhRfIk8qkEVGQ" target="_blank" class="footer-social-btn" title="YouTube">yt</a>
              <a href="https://www.linkedin.com/company/prime-lands-pvt-ltd" target="_blank" class="footer-social-btn" title="LinkedIn">li</a>
            </div>
          </div>
          
          <div class="footer-col">
            <h4>Quick Links</h4>
            <ul class="footer-links">
              <li><a href="index.html">All Properties</a></li>
              <li><a href="index.html?type=house">Houses for Sale & Rent</a></li>
              <li><a href="index.html?type=apartment">Luxury Apartments</a></li>
              <li><a href="index.html?type=land">Prime Investment Lands</a></li>
              <li><a href="javascript:void(0)" onclick="createListing()">Post a Listing</a></li>
              <li><a href="login.html">Member Account</a></li>
            </ul>
          </div>

          <div class="footer-col">
            <h4>Prime Locations</h4>
            <ul class="footer-links">
              <li><a href="index.html?loc=Colombo">Colombo 03 & 07</a></li>
              <li><a href="index.html?loc=Kandy">Kandy Hill Country</a></li>
              <li><a href="index.html?loc=Negombo">Negombo Coastal Area</a></li>
              <li><a href="index.html?loc=Gampaha">Gampaha & Kadawatha</a></li>
              <li><a href="index.html?loc=Galle">Galle Southern Province</a></li>
              <li><a href="index.html?loc=Kurunegala">Kurunegala Central</a></li>
            </ul>
          </div>

          <div class="footer-col footer-newsletter">
            <h4>Head Office</h4>
            <p style="margin-bottom:8px">
              <b>Prime Group Headquarters</b><br>
              No. 75, D.S. Senanayake Mawatha, Borella, Colombo 08, Sri Lanka.
            </p>
            <p style="margin-bottom:14px">
              <b>Hotline:</b> +94 11 269 9822<br>
              <b>Email:</b> inquiries@homefinder.lk
            </p>
            <p><b>Subscribe for Prime Alerts:</b></p>
            <div class="newsletter-box">
              <input type="email" id="nlEmail" placeholder="Enter your email">
              <button class="btn" style="padding:10px 16px" onclick="subscribeNewsletter()">Join</button>
            </div>
            <div id="nlMsg" style="font-size:12px;color:var(--gold);margin-top:6px;display:none">✓ You're subscribed to new property launches!</div>
          </div>
        </div>

        <div class="footer-bottom">
          <div>
            © 2026 <b>HomeFinder Sri Lanka</b> · SE1020 Group WD04 · Built in association with Prime Property Network.
          </div>
          <div>
            100% Certified Title Deeds · ICTAD Major Construction Grade · ISO 9001:2015
          </div>
        </div>
      </div>
    </div>`;
}

function subscribeNewsletter() {
  const email = document.getElementById("nlEmail");
  const msg = document.getElementById("nlMsg");
  if (email && email.value && email.value.includes("@")) {
    email.value = "";
    if (msg) msg.style.display = "block";
    setTimeout(() => { if (msg) msg.style.display = "none"; }, 4000);
  } else {
    alert("Please enter a valid email address.");
  }
}

// ----- Small helpers -----
// esc() makes user text safe to show on the page
function esc(text) {
  return String(text ?? "").replace(/[&<>"']/g, c => ({
    "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;"
  }[c]));
}

function money(n) {
  return "Rs. " + Number(n).toLocaleString("en-US");
}

function icon(type) {
  return type === "house" ? "🏠" : type === "apartment" ? "🏢" : "🌴";
}

// Which backend URL belongs to which type
function endpointFor(type) {
  return type === "house" ? "/api/houses" : type === "apartment" ? "/api/apartments" : "/api/lands";
}

// An id starts with a letter: H = house, A = apartment, L = land
function typeFromId(id) {
  return id.startsWith("H") ? "house" : id.startsWith("A") ? "apartment" : "land";
}

// Get houses + apartments + lands and put them in ONE list
async function getAllProperties() {
  const [h, a, l] = await Promise.all([
    fetch(API + "/api/houses").then(r => r.json()),
    fetch(API + "/api/apartments").then(r => r.json()),
    fetch(API + "/api/lands").then(r => r.json())
  ]);
  return [...h, ...a, ...l];
}

// The HTML of one property card
function cardHTML(p) {
  const photo = p.imageName
    ? `style="background-image:url('${API}/images/${encodeURIComponent(p.imageName)}')"`
    : "";

  const typeLabel = p.type === "house" ? "House" : p.type === "apartment" ? "Apartment" : "Land";
  const listingClass = p.listingType === "Sale" ? "tag-sale" : "tag-rent";

  // Specs pills
  let specsHtml = "";
  if (p.size) {
    specsHtml += `<span class="spec-pill">📐 ${p.size} ${esc(p.sizeUnit || 'sqft')}</span>`;
  }
  if (p.rooms !== undefined && p.rooms > 0) {
    specsHtml += `<span class="spec-pill">🛏 ${p.rooms} Rooms</span>`;
  }
  if (p.floor !== undefined && p.floor > 0) {
    specsHtml += `<span class="spec-pill">🏢 Floor ${p.floor}</span>`;
  }

  return `
  <a class="card" href="details.html?id=${p.id}">
    <div class="photo" ${photo}>
      ${p.imageName ? "" : icon(p.type)}
      <div class="tags">
        <span class="tag ${listingClass}">${esc(p.listingType)}</span>
        <span class="tag">${typeLabel}</span>
      </div>
    </div>
    <div class="card-body">
      <div class="price">${money(p.price)}</div>
      <h3>${esc(p.description)}</h3>
      <div class="card-location">
        <span>📍</span> ${esc(p.address?.location || "Sri Lanka")} · <span style="font-size:12px;color:var(--muted)">${esc(p.address?.street || "")}</span>
      </div>
      <div class="card-specs">
        ${specsHtml}
      </div>
      <div class="card-footer-cta">
        <span>View Details</span>
        <span style="color:var(--gold-dark);font-size:16px">→</span>
      </div>
    </div>
  </a>`;
}

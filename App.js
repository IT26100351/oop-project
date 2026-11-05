// ===== HomeQuest - Shared JavaScript (app.js) =====

// Single unified backend API URL:
// When served by the backend on http://localhost:8080, use same-origin relative path ("")
// When opened via file:// or another port, fallback to http://localhost:8080
const API = (window.location.protocol.startsWith("http") && (window.location.port === "8080" || window.location.port === ""))
  ? ""
  : "http://localhost:8080";

// Official HomeQuest geometric architectural logo SVG
const LOGO_SVG = `<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 400 200" fill="currentColor" class="logo-icon-svg">
  <rect x="0" y="100" width="100" height="100" />
  <polygon points="100,0 200,0 200,100 150,150 100,100" />
  <polygon points="200,200 200,100 300,0 400,100 400,200" />
</svg>`;

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
    ? `<span>Hi, <b style="color:var(--gold-light)">${esc(user.name)}</b></span>
       <button class="linkbtn" onclick="logout()">Log out</button>`
    : `<a href="login.html" style="font-weight:600">Log in</a>
       <a href="signup.html" style="font-weight:600;color:var(--gold-light)">Sign up</a>`;

  const navEl = document.getElementById("navbar");
  if (navEl) {
    navEl.innerHTML = `
      <div class="topbar">
        <a href="index.html" class="logo">
          <div class="logo-symbol">${LOGO_SVG}</div>
          <div class="logo-text-wrap">
            <span class="logo-brand">Home<span class="gold-mark">Quest</span></span>
            <span class="logo-sub">PREMIUM REAL ESTATE</span>
          </div>
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
          ${right}
          <button class="btn" onclick="createListing()">+ Create a Listing</button>
        </div>
      </div>`;
  }

  // Also render the rich luxury glassmorphic footer
  renderFooter();
}

// ----- Rich Luxury Glassmorphic Footer -----
function renderFooter() {
  const footerEl = document.getElementById("footer");
  if (!footerEl) return;
  footerEl.innerHTML = `
    <div class="footer" id="contact">
      <div class="footer-wrap">
        <div class="footer-grid">
          <div class="footer-brand">
            <div class="footer-logo-row">
              <div class="footer-logo-symbol">${LOGO_SVG}</div>
              <h3>Home<span>Quest</span></h3>
            </div>
            <div class="brand-kicker" style="font-size:10px;padding:4px 12px;margin-bottom:14px">PREMIUM REAL ESTATE COMPANY</div>
            <p>
              Sri Lanka's foremost architectural property company. We bring clarity, legal excellence, 
              and natural harmony to real estate investments across the island.
            </p>
            <div class="footer-socials">
              <a href="https://www.facebook.com" target="_blank" class="footer-social-btn" title="Facebook">f</a>
              <a href="https://www.instagram.com" target="_blank" class="footer-social-btn" title="Instagram">in</a>
              <a href="https://www.youtube.com" target="_blank" class="footer-social-btn" title="YouTube">yt</a>
              <a href="https://www.linkedin.com" target="_blank" class="footer-social-btn" title="LinkedIn">li</a>
            </div>
          </div>
          
          <div class="footer-col">
            <h4>Curated Portfolios</h4>
            <ul class="footer-links">
              <li><a href="index.html">All Properties</a></li>
              <li><a href="index.html?type=house">Architect-Designed Houses</a></li>
              <li><a href="index.html?type=apartment">Luxury Sky Residencies</a></li>
              <li><a href="index.html?type=land">Prime Coastal & City Lands</a></li>
              <li><a href="javascript:void(0)" onclick="createListing()">Post a Listing</a></li>
              <li><a href="login.html">Client Portal</a></li>
            </ul>
          </div>

          <div class="footer-col">
            <h4>Prime Regions</h4>
            <ul class="footer-links">
              <li><a href="index.html?loc=Colombo">Colombo 03 & 07 Residencies</a></li>
              <li><a href="index.html?loc=Kandy">Kandy Hill Country Estates</a></li>
              <li><a href="index.html?loc=Negombo">Negombo Beachfront Villas</a></li>
              <li><a href="index.html?loc=Gampaha">Gampaha & Kadawatha Hubs</a></li>
              <li><a href="index.html?loc=Galle">Galle Southern Coast</a></li>
              <li><a href="index.html?loc=Kurunegala">Kurunegala Central Plots</a></li>
            </ul>
          </div>

          <div class="footer-col footer-newsletter">
            <h4>Headquarters</h4>
            <p style="margin-bottom:8px">
              <b>HomeQuest Prime Pavilion</b><br>
              No. 75, D.S. Senanayake Mawatha, Borella, Colombo 08, Sri Lanka.
            </p>
            <p style="margin-bottom:14px">
              <b>Hotline:</b> +94 11 269 9822<br>
              <b>Inquiries:</b> concierge@homequest.lk
            </p>
            <p><b>Join Private Launch Inquiries:</b></p>
            <div class="newsletter-box">
              <input type="email" id="nlEmail" placeholder="Enter your email address">
              <button class="btn" style="padding:10px 18px" onclick="subscribeNewsletter()">Join</button>
            </div>
            <div id="nlMsg" style="font-size:12px;color:var(--gold-light);margin-top:8px;display:none">✓ You are subscribed to HomeQuest private releases.</div>
          </div>
        </div>

        <div class="footer-bottom">
          <div>
            © 2026 <b>HomeQuest Real Estate Company</b> · SE1020 Group WD04 · All Rights Reserved.
          </div>
          <div>
            100% Verified Ownership · Clear Legal Deeds · Architectural Quality Standard
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

// Track how many times each property was opened
function recordPropertyOpen(propId) {
  if (!propId) return;
  try {
    const raw = localStorage.getItem("hq_property_views") || "{}";
    const views = JSON.parse(raw);
    views[propId] = (views[propId] || 0) + 1;
    localStorage.setItem("hq_property_views", JSON.stringify(views));
  } catch (e) {
    console.error("View tracking error", e);
  }
}

function getPropertyViews() {
  try {
    const raw = localStorage.getItem("hq_property_views");
    return raw ? JSON.parse(raw) : {};
  } catch (e) {
    return {};
  }
}

function getViewsCount(propId) {
  const views = getPropertyViews();
  return views[propId] || 0;
}

// Extract array of images from property (comma-separated string or array)
function getPropertyImages(p) {
  if (!p) return [];
  if (Array.isArray(p.images) && p.images.length > 0) {
    return p.images.map(s => String(s).trim()).filter(Boolean);
  }
  if (p.imageName && typeof p.imageName === "string") {
    return p.imageName.split(",").map(s => s.trim()).filter(Boolean);
  }
  return [];
}

// The HTML of one property card
function cardHTML(p) {
  const images = getPropertyImages(p);
  const primaryImg = images.length > 0 ? images[0] : "";
  const fallbackPhoto = p.type === 'house'
    ? 'https://images.unsplash.com/photo-1600585154340-be6161a56a0c?q=80&w=800'
    : p.type === 'apartment'
      ? 'https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?q=80&w=800'
      : 'https://images.unsplash.com/photo-1500382017468-9049fed747ef?q=80&w=800';
  let coverUrl = fallbackPhoto;
  if (primaryImg) {
    if (primaryImg.startsWith("http://") || primaryImg.startsWith("https://")) {
      coverUrl = primaryImg;
    } else {
      coverUrl = `${API}/images/${encodeURIComponent(primaryImg)}`;
    }
  }
  const photo = `style="background-image:url('${coverUrl}'), url('${fallbackPhoto}')"`;

  const typeLabel = p.type === "house" ? "House" : p.type === "apartment" ? "Apartment" : "Land";
  const listingClass = p.listingType === "Sale" ? "tag-sale" : "tag-rent";

  // Specs pills
  let specsHtml = "";
  if (p.size) {
    specsHtml += `<span class="spec-pill">📐 ${p.size} ${esc(p.sizeUnit || 'sqft')}</span>`;
  }
  if (p.rooms !== undefined && p.rooms > 0) {
    specsHtml += `<span class="spec-pill">🛏️ ${p.rooms} Rooms</span>`;
  }
  if (p.floor !== undefined && p.floor > 0) {
    specsHtml += `<span class="spec-pill">🏢 Floor ${p.floor}</span>`;
  }

  const multiBadge = images.length > 1
    ? `<span class="photo-count-badge">📷 ${images.length} Photos</span>`
    : "";

  return `
  <a class="card" href="details.html?id=${p.id}" onclick="recordPropertyOpen('${p.id}')">
    <div class="photo-wrap">
      <div class="photo" ${photo}>
        ${multiBadge}
        ${primaryImg ? "" : icon(p.type)}
        <div class="tags">
          <span class="tag ${listingClass}">${esc(p.listingType)}</span>
          <span class="tag">${typeLabel}</span>
        </div>
      </div>
    </div>
    <div class="card-body">
      <div class="price">${money(p.price)}</div>
      <h3>${esc(p.description)}</h3>
      <div class="card-location">
        <span>📍</span> ${esc(p.address?.location || "Sri Lanka")} · <span style="font-size:12px;color:var(--text-muted)">${esc(p.address?.street || "")}</span>
      </div>
      <div class="card-specs">
        ${specsHtml}
      </div>
      <div class="card-footer-cta">
        <span>Explore Property</span>
        <span class="cta-arrow">→</span>
      </div>
    </div>
  </a>`;
}

/* ============================================================
   MOTIONVFX 6247: 3D VENETIAN MULTI-SLICE TRANSITION
   Splits stage into vertical accordion slices with opposite vertical shifts,
   staggered 3D Y-axis rotation and dynamic specular light gloss.
   ============================================================ */
function runMotionVFX6247Transition(containerEl, oldUrl, newUrl, direction = 'next', onComplete = null) {
  if (!containerEl || !newUrl) {
    if (onComplete) onComplete();
    return;
  }
  if (!oldUrl || oldUrl === newUrl) {
    containerEl.style.backgroundImage = `url('${newUrl}')`;
    if (onComplete) onComplete();
    return;
  }

  const rect = containerEl.getBoundingClientRect();
  const width = Math.round(rect.width || containerEl.offsetWidth || 800);
  const height = Math.round(rect.height || containerEl.offsetHeight || 520);

  // Clean previous transition stage if active
  const existingStage = containerEl.querySelector('.mvfx-6247-stage');
  if (existingStage) existingStage.remove();

  const numSlices = 8;
  const sliceWidth = width / numSlices;
  const isNext = direction === 'next';

  const stage = document.createElement('div');
  stage.className = 'mvfx-6247-stage';

  for (let i = 0; i < numSlices; i++) {
    const slice = document.createElement('div');
    slice.className = 'mvfx-6247-slice';
    const leftOffset = -Math.round(i * sliceWidth);
    const delay = isNext ? (i * 0.05) : ((numSlices - 1 - i) * 0.05);

    slice.style.transition = `transform 0.65s cubic-bezier(0.2, 0.85, 0.25, 1) ${delay}s, opacity 0.65s cubic-bezier(0.2, 0.85, 0.25, 1) ${delay}s`;

    // Front face (current picture)
    const front = document.createElement('div');
    front.className = 'mvfx-slice-face mvfx-slice-front';
    front.style.backgroundImage = `url('${oldUrl}')`;
    front.style.backgroundSize = `${width}px ${height}px`;
    front.style.backgroundPosition = `${leftOffset}px center`;

    // Back face (next picture)
    const back = document.createElement('div');
    back.className = 'mvfx-slice-face mvfx-slice-back';
    back.style.backgroundImage = `url('${newUrl}')`;
    back.style.backgroundSize = `${width}px ${height}px`;
    back.style.backgroundPosition = `${leftOffset}px center`;

    // Gloss shadow layer
    const shadow = document.createElement('div');
    shadow.className = 'mvfx-slice-shadow';
    shadow.style.transition = `opacity 0.65s cubic-bezier(0.2, 0.85, 0.25, 1) ${delay}s`;

    slice.appendChild(front);
    slice.appendChild(back);
    slice.appendChild(shadow);
    stage.appendChild(slice);
  }

  containerEl.appendChild(stage);

  // Trigger 3D turn and vertical panel slide
  requestAnimationFrame(() => {
    requestAnimationFrame(() => {
      const slices = stage.querySelectorAll('.mvfx-6247-slice');
      slices.forEach((slice, idx) => {
        const vShift = (idx % 2 === 0 ? 1 : -1) * 16;
        const rotY = isNext ? 180 : -180;
        slice.style.transform = `translate3d(0, ${vShift}px, -24px) rotateY(${rotY}deg)`;
        const shadow = slice.querySelector('.mvfx-slice-shadow');
        if (shadow) shadow.style.opacity = '0.9';
      });
    });
  });

  const totalDuration = Math.round((numSlices * 50) + 650);
  setTimeout(() => {
    containerEl.style.backgroundImage = `url('${newUrl}')`;
    if (stage.parentNode) stage.remove();
    if (onComplete) onComplete();
  }, totalDuration);
}

/* ============================================================
   REAL ESTATE AGENCY VIDEO MODAL (MOTIONVFX 29429)
   ============================================================ */
const AGENCY_SHOWCASE_VIDEO_URL = "https://www.youtube.com/embed/LXb3EKWsInQ?autoplay=1&rel=0&modestbranding=1&showinfo=0";

const AGENCY_CHAPTERS = [
  { title: "🏡 Coastal Waterfront Villas", start: 0, time: "0:00" },
  { title: "🏢 The Azure Sky Penthouse", start: 75, time: "1:15" },
  { title: "🌴 Ceylon Sanctuary Estates", start: 160, time: "2:40" }
];

function ensureAgencyVideoModal() {
  let modal = document.getElementById("agencyVideoModal");
  if (modal) return modal;

  modal = document.createElement("div");
  modal.id = "agencyVideoModal";
  modal.className = "agency-video-modal-backdrop";
  modal.onclick = (e) => {
    if (e.target === modal) closeAgencyVideoModal();
  };

  const chapterButtons = AGENCY_CHAPTERS.map((ch, idx) => `
    <button class="chapter-btn ${idx === 0 ? 'active' : ''}" onclick="switchAgencyChapter(${ch.start}, this)">
      ${ch.title} <span style="opacity:0.6;font-size:10px">(${ch.time})</span>
    </button>
  `).join("");

  modal.innerHTML = `
    <div class="agency-video-modal-content" role="dialog" aria-modal="true">
      <div class="agency-modal-header">
        <div class="agency-modal-brand">
          <div class="agency-logo-symbol-wrap" style="width:36px;height:20px">${LOGO_SVG}</div>
          <div class="agency-modal-title">
            HomeQuest <b>Agency Reel</b> <span>· 4K CINEMATIC TOUR</span>
          </div>
        </div>
        <button class="agency-modal-close-btn" onclick="closeAgencyVideoModal()" title="Close video modal (Esc)">✕</button>
      </div>

      <div class="agency-modal-video-frame" id="agencyModalVideoContainer">
        <!-- Iframe injected on open to conserve bandwidth -->
      </div>

      <div class="agency-modal-chapters">
        <span style="font-family:'Space Mono',monospace;font-size:11px;color:var(--gold-light);display:flex;align-items:center;padding-right:6px">
          CHAPTERS:
        </span>
        ${chapterButtons}
      </div>

      <div class="agency-modal-footer">
        <div style="font-size:12px;color:var(--text-muted)">
          ✦ Certified Architectural Portfolios across Colombo, Kandy &amp; Southern Coastline.
        </div>
        <div style="display:flex;gap:10px">
          <button class="btn btn-glass" style="padding:8px 16px;font-size:13px" onclick="closeAgencyVideoModal();createListing()">+ Post Listing</button>
          <a class="btn" style="padding:8px 18px;font-size:13px" href="tel:+94112699822">📞 Concierge Hotline</a>
        </div>
      </div>
    </div>
  `;

  document.body.appendChild(modal);

  // Esc key listener
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape" && modal.classList.contains("open")) {
      closeAgencyVideoModal();
    }
  });

  return modal;
}

function openAgencyVideoModal() {
  const modal = ensureAgencyVideoModal();
  const frameContainer = document.getElementById("agencyModalVideoContainer");
  if (frameContainer) {
    frameContainer.innerHTML = `
      <iframe id="agencyVideoIframe"
        src="${AGENCY_SHOWCASE_VIDEO_URL}"
        title="HomeQuest Real Estate Agency Cinematic Tour"
        allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
        allowfullscreen>
      </iframe>
    `;
  }
  modal.classList.add("open");
  document.body.style.overflow = "hidden";
}

function closeAgencyVideoModal() {
  const modal = document.getElementById("agencyVideoModal");
  if (!modal) return;
  modal.classList.remove("open");
  const frameContainer = document.getElementById("agencyModalVideoContainer");
  if (frameContainer) {
    // Stop playback
    frameContainer.innerHTML = "";
  }
  document.body.style.overflow = "";
}

function switchAgencyChapter(startTime, btn) {
  const iframe = document.getElementById("agencyVideoIframe");
  if (iframe) {
    iframe.src = `https://www.youtube.com/embed/LXb3EKWsInQ?autoplay=1&start=${startTime}&rel=0&modestbranding=1`;
  }
  document.querySelectorAll(".chapter-btn").forEach(b => b.classList.remove("active"));
  if (btn) btn.classList.add("active");
}

const Shell = (() => {
  async function inject() {
    const navEl = document.getElementById("app-navbar");
    const footEl = document.getElementById("app-footer");
    if (navEl) navEl.innerHTML = await fetch("/shared/navbar.html").then(r => r.text());
    if (footEl) footEl.innerHTML = await fetch("/shared/footer.html").then(r => r.text());
    buildNav();
  }

  function buildNav() {
    const box = document.getElementById("navLinks");
    if (!box) return;
    const role = (sessionStorage.getItem("ac_role") || "").toUpperCase();
    const links = [
      ['Home','/index.html'],
      ['Find a Doctor','/doctors.html'],
      ['About','/about.html'],
      ['Contact','/contact.html'],
    ];
    if (role.includes("PATIENT")) links.push(['Patient Dashboard','/dashboards/patient.html']);
    if (role.includes("DOCTOR")) links.push(['Doctor Dashboard','/dashboards/doctor.html']);
    if (role.includes("ADMIN")) links.push(['Admin Dashboard','/dashboards/admin.html']);

    box.innerHTML = links.map(([label, href]) =>
      `<li class="nav-item"><a class="nav-link ${location.pathname===href?'active':''}" href="${href}">${label}</a></li>`
    ).join("");

    const authChunk = role ? `
      <li class="nav-item ms-lg-2"><button class="btn btn-ac-soft" id="logoutBtn">Logout</button></li>
    ` : `
      <li class="nav-item ms-lg-2"><a class="btn btn-ac-soft" href="/register.html">Register</a></li>
      <li class="nav-item"><a class="btn btn-ac" href="/login.html">Login</a></li>
    `;
    box.insertAdjacentHTML("beforeend", authChunk);
    document.getElementById("logoutBtn")?.addEventListener("click", Auth.logout);
  }

  function pageBg(path, light=false) {
    document.body.classList.add("ac-page-bg");
    if (light) document.body.classList.add("light");
    document.body.style.setProperty("--page-bg", `url('${path}')`);
  }

  function query(name) {
    return new URLSearchParams(location.search).get(name);
  }

  return { boot: inject, pageBg, query };
})();

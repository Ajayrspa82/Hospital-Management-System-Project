const Auth = (() => {
  function setSession(email, password, role) {
    sessionStorage.setItem("ac_email", (email || "").trim());
    sessionStorage.setItem("ac_password", password || "");
    sessionStorage.setItem("ac_role", (role || "").toUpperCase());
  }

  function clearSession() {
    sessionStorage.removeItem("ac_email");
    sessionStorage.removeItem("ac_password");
    sessionStorage.removeItem("ac_role");
    sessionStorage.removeItem("ac_patientId");
    sessionStorage.removeItem("ac_doctorId");
  }

  async function login(email, password) {
    const e = (email || "").trim();
    const p = password || "";
    const res = await Api.post("/api/auth/login", { email: e, password: p });
    setSession(e, p, res.role || "");
    return res;
  }

  async function register(payload) {
    return Api.post("/api/auth/register", {
      email: (payload.email || "").trim(),
      password: payload.password || "",
      role: (payload.role || "PATIENT").toUpperCase()
    });
  }

  function gotoRoleDashboard(role) {
    const r = (role || "").toUpperCase();
    if (r.includes("ADMIN")) location.href = "/dashboards/admin.html";
    else if (r.includes("DOCTOR")) location.href = "/dashboards/doctor.html";
    else location.href = "/dashboards/patient.html";
  }

  function logout() {
    clearSession();
    location.href = "/login.html";
  }

  return { login, register, gotoRoleDashboard, logout, clearSession };
})();

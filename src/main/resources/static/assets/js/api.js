const Api = (() => {
  const client = axios.create({
    baseURL: "",
    headers: { "Content-Type": "application/json" }
  });

  function getCreds() {
    return {
      email: sessionStorage.getItem("ac_email") || "",
      password: sessionStorage.getItem("ac_password") || "",
      role: sessionStorage.getItem("ac_role") || "",
      patientId: sessionStorage.getItem("ac_patientId") || "",
      doctorId: sessionStorage.getItem("ac_doctorId") || ""
    };
  }

  function authHeader() {
    const { email, password } = getCreds();
    if (!email || !password) return {};
    return { Authorization: "Basic " + btoa(`${email}:${password}`) };
  }

  client.interceptors.request.use((config) => {
    config.headers = { ...config.headers, ...authHeader() };
    return config;
  });

  client.interceptors.response.use(
    (res) => res,
    (error) => {
      if ([401, 403].includes(error?.response?.status)) {
        sessionStorage.clear();
        if (!location.pathname.endsWith("/login.html")) {
          location.href = "/login.html?msg=" + encodeURIComponent("Please login to continue.");
        }
      }
      return Promise.reject(error);
    }
  );

  function parse(res) { return res.data; }
  function get(url, config={}) { return client.get(url, config).then(parse); }
  function post(url, data, config={}) { return client.post(url, data, config).then(parse); }
  function put(url, data, config={}) { return client.put(url, data, config).then(parse); }
  function del(url, config={}) { return client.delete(url, config).then(parse); }

  function friendlyError(err) {
    const data = err?.response?.data;
    if (Array.isArray(data)) return data.join("\n");
    if (typeof data === "string") return data;
    return data?.message || err?.message || "Something went wrong. Please try again.";
  }

  function toast(message, type="info") {
    const holder = document.querySelector(".toast-container") || (() => {
      const div = document.createElement("div");
      div.className = "toast-container position-fixed top-0 end-0 p-3";
      document.body.appendChild(div);
      return div;
    })();

    const wrapper = document.createElement("div");
    wrapper.className = "toast toast-ac align-items-center border-0 show";
    wrapper.setAttribute("role", "alert");
    wrapper.innerHTML = `
      <div class="d-flex">
        <div class="toast-body">
          <strong class="text-capitalize">${type}:</strong> ${message}
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto"></button>
      </div>`;
    holder.appendChild(wrapper);
    wrapper.querySelector(".btn-close").onclick = () => wrapper.remove();
    setTimeout(() => wrapper.remove(), 3500);
  }

  function requireRole(...roles) {
    const role = (sessionStorage.getItem("ac_role") || "").toUpperCase();
    if (!roles.some(r => role.includes(r.toUpperCase()))) {
      location.href = "/login.html?msg=" + encodeURIComponent("Please login with the correct role.");
    }
  }

  return { client, get, post, put, del, toast, friendlyError, getCreds, requireRole };
})();

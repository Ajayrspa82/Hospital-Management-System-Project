const TestsPage = (() => {
  async function init() {
    const consultationId = new URLSearchParams(location.search).get("consultationId");
    const box = document.getElementById("testsList");
    if (!consultationId) {
      box.innerHTML = '<div class="empty-state">Open this page with consultationId=ID.</div>';
      return;
    }
    try {
      const list = await Api.get(`/api/medical-tests/consultation/${consultationId}`);
      box.innerHTML = list.map(t => `
        <div class="ac-card p-3 mb-3">
          <div class="d-flex justify-content-between"><strong>${t.testName}</strong><span class="badge-soft status-pending">${t.testStatus}</span></div>
          <div class="mt-2 small-muted">${t.description || "No description"}</div>
          <div class="mt-2"><strong>Result:</strong> ${t.result || "-"}</div>
        </div>`).join("") || '<div class="empty-state">No medical tests found.</div>';
    } catch (err) {
      box.innerHTML = `<div class="empty-state">${Api.friendlyError(err)}</div>`;
    }
  }
  return { init };
})();

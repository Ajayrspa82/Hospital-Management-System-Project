const PrescriptionsPage = (() => {
  async function init() {
    const consultationId = new URLSearchParams(location.search).get("consultationId");
    const box = document.getElementById("prescriptionsList");
    if (!consultationId) {
      box.innerHTML = '<div class="empty-state">Open this page with consultationId=ID.</div>';
      return;
    }
    try {
      const list = await Api.get(`/api/prescriptions/consultation/${consultationId}`);
      box.innerHTML = list.map(p => `
        <div class="ac-card p-3 mb-3">
          <div class="d-flex justify-content-between"><strong>${p.medicineName}</strong><span class="info-chip">${p.dosage || "-"}</span></div>
          <div class="mt-2"><strong>Duration:</strong> ${p.duration || p.durationDays || "-"}</div>
          <div class="mt-2 small-muted">${p.instructions || "Follow doctor advice."}</div>
        </div>`).join("") || '<div class="empty-state">No prescriptions found.</div>';
    } catch (err) {
      box.innerHTML = `<div class="empty-state">${Api.friendlyError(err)}</div>`;
    }
  }
  return { init };
})();

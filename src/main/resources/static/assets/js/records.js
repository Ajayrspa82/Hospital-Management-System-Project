const RecordsPage = (() => {
  async function init() {
    const patientId = new URLSearchParams(location.search).get("patientId") || sessionStorage.getItem("ac_patientId");
    const box = document.getElementById("recordsList");
    if (!patientId) {
      box.innerHTML = '<div class="empty-state">Open this page from a dashboard or pass ?patientId=ID.</div>';
      return;
    }
    try {
      const list = await Api.get(`/api/medical-records/patient/${patientId}/all`);
      box.innerHTML = list.map(r => `
        <div class="ac-card p-3 mb-3">
          <div class="d-flex justify-content-between"><strong>Record #${r.recordId}</strong><span class="small-muted">Consultation ${r.consultationId}</span></div>
          <div class="mt-2">${(r.notes || "-").replace(/</g,"&lt;")}</div>
        </div>`).join("") || '<div class="empty-state">No medical records found.</div>';
    } catch (err) {
      box.innerHTML = `<div class="empty-state">${Api.friendlyError(err)}</div>`;
    }
  }
  return { init };
})();

const DoctorApp = (() => {
  let doctors = [];
  let doctorId = Number(sessionStorage.getItem("ac_doctorId") || 0);
  let appointments = [];
  let consultations = [];

  async function init() {
    Api.requireRole("DOCTOR");
    doctors = await Api.get("/api/doctors");
    await chooseDoctorIfNeeded();
    await Promise.all([loadAppointments(), loadConsultations()]);
    bindDoctorForm();
  }

  async function chooseDoctorIfNeeded() {
    const picker = document.getElementById("doctorPickerWrap");
    if (!doctorId) picker.classList.remove("d-none");
    const select = document.getElementById("doctorPicker");
    select.innerHTML = '<option value="">Select your doctor profile</option>' + doctors.map(d => `<option value="${d.doctorId}" ${d.doctorId===doctorId?'selected':''}>${d.name} • ${d.specializationName || ""}</option>`).join("");
    document.getElementById("saveDoctorSelection").onclick = () => {
      const val = Number(select.value);
      if (!val) return Api.toast("Please select your doctor profile.", "error");
      doctorId = val;
      sessionStorage.setItem("ac_doctorId", String(val));
      picker.classList.add("d-none");
      loadAppointments(); loadConsultations();
    };
    const label = document.getElementById("doctorHeaderName");
    const doc = doctors.find(d => d.doctorId === doctorId);
    if (doc && label) label.textContent = doc.name;
  }

  async function loadAppointments() {
    if (!doctorId) return;
    appointments = (await Api.get("/api/appointments")).filter(a => a.doctorId === doctorId);
    const patients = await Api.get("/api/patients");
    const rows = appointments.sort((a,b)=>new Date(a.appointmentDate)-new Date(b.appointmentDate)).map(a => {
      const p = patients.find(x => x.patientId === a.patientId) || {};
      return `
      <tr class="${['PENDING','APPROVED'].includes((a.status||'').toUpperCase()) ? 'appointment-upcoming' : ''}">
        <td>${a.appointmentId}</td>
        <td>${p.fullName || ('Patient #' + a.patientId)}</td>
        <td>${p.mobileNumber || '-'}</td>
        <td>${a.reason || '-'}</td>
        <td>${fmtDateTime(a.appointmentDate)}</td>
        <td>${statusBadge(a.status)}</td>
        <td class="d-flex flex-wrap gap-2">
          <button class="btn btn-sm btn-success-soft" data-approve="${a.appointmentId}">Approve</button>
          <button class="btn btn-sm btn-danger-soft" data-reject="${a.appointmentId}">Reject</button>
          <button class="btn btn-sm btn-ac-soft" data-consult="${a.appointmentId}" data-patient="${a.patientId}">Consult</button>
        </td>
      </tr>`;
    }).join("");
    document.getElementById("doctorAppointments").innerHTML = rows || `<tr><td colspan="7"><div class="empty-state">No appointments assigned.</div></td></tr>`;
    bindTableActions();
  }

  async function loadConsultations() {
    if (!doctorId) return;
    consultations = await Api.get(`/api/consultations/doctor/${doctorId}`);
    const rows = consultations.sort((a,b)=>new Date(b.consultationDate)-new Date(a.consultationDate)).map(c => `
      <tr>
        <td>${c.consultationId}</td>
        <td>${c.patientId}</td>
        <td>${fmtDateTime(c.consultationDate)}</td>
        <td>${(c.symptoms || '-')}</td>
        <td>${(c.treatmentPlan || '-')}</td>
        <td class="d-flex gap-2 flex-wrap">
          <a class="btn btn-sm btn-ac-soft" href="/modules/medical-tests.html?consultationId=${c.consultationId}">Tests</a>
          <a class="btn btn-sm btn-ac-soft" href="/modules/prescriptions.html?consultationId=${c.consultationId}">Rx</a>
          <a class="btn btn-sm btn-ac-soft" href="/modules/medical-records.html?patientId=${c.patientId}">Records</a>
        </td>
      </tr>
    `).join("");
    document.getElementById("doctorConsultations").innerHTML = rows || `<tr><td colspan="6"><div class="empty-state">No completed consultations yet.</div></td></tr>`;
  }

  function bindTableActions() {
    document.querySelectorAll("[data-approve]").forEach(btn => btn.onclick = async () => {
      try { await Api.put(`/api/appointments/approve/${btn.dataset.approve}`, {}); Api.toast("Appointment approved.", "success"); await loadAppointments(); }
      catch(err){ Api.toast(Api.friendlyError(err), "error"); }
    });
    document.querySelectorAll("[data-reject]").forEach(btn => btn.onclick = async () => {
      try { await Api.put(`/api/appointments/reject/${btn.dataset.reject}`, {}); Api.toast("Appointment rejected.", "success"); await loadAppointments(); }
      catch(err){ Api.toast(Api.friendlyError(err), "error"); }
    });
    document.querySelectorAll("[data-consult]").forEach(btn => btn.onclick = () => {
      document.getElementById("consultAppointmentId").value = btn.dataset.consult;
      document.getElementById("consultPatientId").value = btn.dataset.patient;
      document.getElementById("consultDoctorId").value = doctorId;
      document.getElementById("consultationDate").value = new Date().toISOString().slice(0,16);
      document.getElementById("consultSection").scrollIntoView({behavior:"smooth"});
    });
  }

  function bindDoctorForm() {
    document.getElementById("consultForm")?.addEventListener("submit", async (e) => {
      e.preventDefault();
      try {
        const payload = {
          appointmentId: Number(document.getElementById("consultAppointmentId").value),
          patientId: Number(document.getElementById("consultPatientId").value),
          doctorId: Number(document.getElementById("consultDoctorId").value),
          symptoms: document.getElementById("symptoms").value.trim(),
          diagnosis: document.getElementById("diagnosis").value.trim(),
          physicalExamination: document.getElementById("physicalExamination").value.trim(),
          treatmentPlan: document.getElementById("treatmentPlan").value.trim(),
          doctorNotes: document.getElementById("doctorNotes").value.trim(),
          consultationDate: document.getElementById("consultationDate").value,
          recommendedTests: collectRows("testRows", (row) => ({
            testName: row.querySelector("[data-test-name]").value.trim(),
            testStatus: row.querySelector("[data-test-status]").value.trim() || "RECOMMENDED",
            description: row.querySelector("[data-test-desc]").value.trim(),
            result: row.querySelector("[data-test-result]").value.trim()
          })).filter(x => x.testName),
          prescriptions: collectRows("rxRows", (row) => ({
            medicineName: row.querySelector("[data-rx-name]").value.trim(),
            dosage: row.querySelector("[data-rx-dosage]").value.trim(),
            duration: row.querySelector("[data-rx-duration]").value.trim(),
            instructions: row.querySelector("[data-rx-inst]").value.trim()
          })).filter(x => x.medicineName)
        };
        const saved = await Api.post("/api/consultations", payload);
        await Api.post("/api/medical-records", {
          patientId: payload.patientId,
          consultationId: saved.consultationId,
          notes: [payload.diagnosis, payload.physicalExamination, payload.treatmentPlan, payload.doctorNotes].filter(Boolean).join(" | ")
        });
        Api.toast("Consultation saved successfully.", "success");
        e.target.reset();
        document.getElementById("testRows").innerHTML = rowTest();
        document.getElementById("rxRows").innerHTML = rowRx();
        await loadConsultations();
      } catch (err) {
        Api.toast(Api.friendlyError(err), "error");
      }
    });

    document.getElementById("addTestRow")?.addEventListener("click", () => {
      document.getElementById("testRows").insertAdjacentHTML("beforeend", rowTest());
    });
    document.getElementById("addRxRow")?.addEventListener("click", () => {
      document.getElementById("rxRows").insertAdjacentHTML("beforeend", rowRx());
    });
    document.addEventListener("click", (e) => {
      if (e.target.matches("[data-remove-row]")) e.target.closest(".row").remove();
    });
    if (document.getElementById("testRows")) document.getElementById("testRows").innerHTML = rowTest();
    if (document.getElementById("rxRows")) document.getElementById("rxRows").innerHTML = rowRx();
  }

  function rowTest() {
    return `<div class="row g-2 align-items-end mb-2">
      <div class="col-md-3"><input class="form-control" data-test-name placeholder="Test name"></div>
      <div class="col-md-2"><input class="form-control" data-test-status value="RECOMMENDED" placeholder="Status"></div>
      <div class="col-md-3"><input class="form-control" data-test-desc placeholder="Description"></div>
      <div class="col-md-3"><input class="form-control" data-test-result placeholder="Result"></div>
      <div class="col-md-1"><button type="button" class="btn btn-danger-soft w-100" data-remove-row>×</button></div>
    </div>`;
  }

  function rowRx() {
    return `<div class="row g-2 align-items-end mb-2">
      <div class="col-md-3"><input class="form-control" data-rx-name placeholder="Medicine"></div>
      <div class="col-md-2"><input class="form-control" data-rx-dosage placeholder="0-0-1 AF"></div>
      <div class="col-md-2"><input class="form-control" data-rx-duration placeholder="5 days"></div>
      <div class="col-md-4"><input class="form-control" data-rx-inst placeholder="Instructions"></div>
      <div class="col-md-1"><button type="button" class="btn btn-danger-soft w-100" data-remove-row>×</button></div>
    </div>`;
  }

  function collectRows(id, mapper) { return [...document.getElementById(id).children].map(mapper); }
  function fmtDateTime(v){ return v ? new Date(v).toLocaleString() : "-"; }
  function statusBadge(status) {
    const s=(status||"PENDING").toUpperCase();
    const c={PENDING:'status-pending',APPROVED:'status-approved',CANCELLED:'status-cancelled',REJECTED:'status-rejected',RESCHEDULED:'status-pending',COMPLETED:'status-completed'}[s] || 'status-pending';
    return `<span class="badge-soft ${c}">${s}</span>`;
  }

  return { init };
})();

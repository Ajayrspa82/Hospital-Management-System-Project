const PatientApp = (() => {
  let currentPatient = null;
  let doctors = [];
  let appointments = [];
  let consultations = [];
  let records = [];

  async function init() {
    Api.requireRole("PATIENT");
    await ensurePatientProfile();
    await Promise.all([loadDoctors(), loadAppointments(), loadConsultations(), loadRecords()]);
    bindForms();
  }

  async function ensurePatientProfile() {
    const email = sessionStorage.getItem("ac_email");
    const all = await Api.get("/api/patients");
    currentPatient = all.find(p => (p.email || "").toLowerCase() === (email || "").toLowerCase()) || null;
    const gate = document.getElementById("patientProfileGate");
    const main = document.getElementById("patientMain");
    const welcome = document.getElementById("patientWelcome");
    if (currentPatient) {
      sessionStorage.setItem("ac_patientId", currentPatient.patientId);
      if (gate) gate.classList.add("d-none");
      if (main) main.classList.remove("d-none");
      if (welcome) welcome.textContent = currentPatient.fullName;
      fillProfileForm(currentPatient);
    } else {
      if (gate) gate.classList.remove("d-none");
      if (main) main.classList.add("d-none");
    }
  }

  function fillProfileForm(p) {
    const map = { profileFullName:p.fullName, profileDob:p.dateOfBirth, profileGender:p.gender, profileMobile:p.mobileNumber, profileMedical:p.medicalHistory || "", profileEmail:p.email };
    Object.entries(map).forEach(([id,val]) => { const el=document.getElementById(id); if (el) el.value = val || ""; });
  }

  async function saveProfile(e) {
    e.preventDefault();
    const payload = {
      fullName: document.getElementById("profileFullName").value.trim(),
      dateOfBirth: document.getElementById("profileDob").value,
      gender: document.getElementById("profileGender").value,
      mobileNumber: document.getElementById("profileMobile").value.replace(/\D/g,"").slice(0,10),
      email: document.getElementById("profileEmail").value.trim(),
      medicalHistory: document.getElementById("profileMedical").value.trim()
    };
    try {
      if (currentPatient?.patientId) {
        currentPatient = await Api.put(`/api/patients/${currentPatient.patientId}`, payload);
        Api.toast("Patient profile updated successfully.", "success");
      } else {
        currentPatient = await Api.post("/api/patients", payload);
        Api.toast("Patient profile completed successfully.", "success");
        await ensurePatientProfile();
      }
      await loadAppointments();
      await loadConsultations();
      await loadRecords();
    } catch (err) {
      Api.toast(Api.friendlyError(err), "error");
    }
  }

  async function loadDoctors() {
    doctors = await Api.get("/api/doctors");
    renderDoctorCards(doctors);
    const doctorSelect = document.getElementById("appointmentDoctorId");
    if (doctorSelect) {
      doctorSelect.innerHTML = '<option value="">Choose doctor</option>' + doctors.map(d => `<option value="${d.doctorId}">${d.name} • ${d.specializationName || ""}</option>`).join("");
    }
  }

  function renderDoctorCards(list) {
    const box = document.getElementById("doctorResults");
    if (!box) return;
    if (!list.length) {
      box.innerHTML = '<div class="empty-state">No doctors found for the selected search.</div>';
      return;
    }
    box.innerHTML = list.map(d => `
      <div class="col-md-6 col-xl-4">
        <div class="ac-card p-4 h-100">
          <div class="d-flex justify-content-between align-items-start mb-3">
            <div>
              <div class="card-title-strong fs-5">${d.name}</div>
              <div class="small-muted">${d.specializationName || "Specialization not available"}</div>
            </div>
            <span class="info-chip">${d.experience || 0}+ yrs</span>
          </div>
          <div class="small-muted mb-2"><strong class="text-dark">Qualification:</strong> ${d.qualification || "-"}</div>
          <div class="small-muted mb-3"><strong class="text-dark">Designation:</strong> ${d.designation || "-"}</div>
          <button class="btn btn-ac w-100" data-book-doctor="${d.doctorId}">Book Appointment</button>
        </div>
      </div>
    `).join("");
    box.querySelectorAll("[data-book-doctor]").forEach(btn => btn.onclick = () => {
      document.getElementById("appointmentDoctorId").value = btn.dataset.bookDoctor;
      document.getElementById("bookSection").scrollIntoView({behavior:"smooth"});
    });
  }

  async function loadAppointments() {
    if (!currentPatient) return;
    appointments = (await Api.get("/api/appointments")).filter(a => a.patientId === currentPatient.patientId);
    const rows = appointments.sort((a,b) => new Date(b.appointmentDate) - new Date(a.appointmentDate)).map(a => `
      <tr class="${['PENDING','APPROVED'].includes((a.status||'').toUpperCase()) ? 'appointment-upcoming' : ''}">
        <td>${a.appointmentId}</td>
        <td>${doctorName(a.doctorId)}</td>
        <td>${fmtDateTime(a.appointmentDate)}</td>
        <td>${a.reason || "-"}</td>
        <td>${statusBadge(a.status)}</td>
        <td class="d-flex flex-wrap gap-2">
          <button class="btn btn-sm btn-ac-soft" data-reschedule="${a.appointmentId}">Reschedule</button>
          <button class="btn btn-sm btn-danger-soft" data-cancel="${a.appointmentId}">Cancel</button>
        </td>
      </tr>
    `).join("");
    document.getElementById("appointmentsTable").innerHTML = rows || `<tr><td colspan="6"><div class="empty-state">No appointments yet.</div></td></tr>`;
    bindAppointmentActions();
  }

  async function loadConsultations() {
    if (!currentPatient) return;
    consultations = await Api.get(`/api/consultations/patient/${currentPatient.patientId}`);
    const rows = consultations.sort((a,b) => new Date(b.consultationDate)-new Date(a.consultationDate)).map(c => `
      <tr>
        <td>${c.consultationId}</td>
        <td>${doctorName(c.doctorId)}</td>
        <td>${fmtDateTime(c.consultationDate)}</td>
        <td>${c.diagnosis || c.treatmentPlan || "-"}</td>
        <td><a href="/modules/prescriptions.html?consultationId=${c.consultationId}" class="btn btn-sm btn-ac-soft">Prescriptions</a></td>
      </tr>
    `).join("");
    document.getElementById("consultationTable").innerHTML = rows || `<tr><td colspan="5"><div class="empty-state">No completed consultations yet.</div></td></tr>`;
  }

  async function loadRecords() {
    if (!currentPatient) return;
    records = await Api.get(`/api/medical-records/patient/${currentPatient.patientId}/all`);
    const rows = records.map(r => `
      <tr>
        <td>${r.recordId}</td>
        <td>${r.consultationId}</td>
        <td>${(r.notes || "-").replace(/</g, "&lt;")}</td>
      </tr>
    `).join("");
    document.getElementById("recordsTable").innerHTML = rows || `<tr><td colspan="3"><div class="empty-state">No medical records added yet.</div></td></tr>`;
  }

  function bindForms() {
    document.getElementById("profileForm")?.addEventListener("submit", saveProfile);
    document.getElementById("bookForm")?.addEventListener("submit", async (e) => {
      e.preventDefault();
      if (!currentPatient) return Api.toast("Please complete patient profile first.", "error");
      try {
        await Api.post("/api/appointments/book", {
          patientId: currentPatient.patientId,
          doctorId: Number(document.getElementById("appointmentDoctorId").value),
          appointmentDate: document.getElementById("appointmentDate").value,
          reason: document.getElementById("appointmentReason").value.trim()
        });
        Api.toast("Appointment booked successfully.", "success");
        e.target.reset();
        await loadAppointments();
      } catch (err) {
        Api.toast(Api.friendlyError(err), "error");
      }
    });

    document.getElementById("doctorSearchForm")?.addEventListener("submit", (e) => {
      e.preventDefault();
      const term = document.getElementById("doctorSearchText").value.trim().toLowerCase();
      const list = doctors.filter(d =>
        (d.name || "").toLowerCase().includes(term) ||
        (d.specializationName || "").toLowerCase().includes(term) ||
        (d.designation || "").toLowerCase().includes(term)
      );
      renderDoctorCards(list);
    });
  }

  function bindAppointmentActions() {
    document.querySelectorAll("[data-cancel]").forEach(btn => btn.onclick = async () => {
      try { await Api.put(`/api/appointments/cancel/${btn.dataset.cancel}`, {}); Api.toast("Appointment cancelled.", "success"); await loadAppointments(); }
      catch(err){ Api.toast(Api.friendlyError(err), "error");}
    });
    document.querySelectorAll("[data-reschedule]").forEach(btn => btn.onclick = () => {
      const id = btn.dataset.reschedule;
      const newDate = prompt("Enter new appointment datetime (YYYY-MM-DDTHH:MM)", "");
      if (!newDate) return;
      Api.put(`/api/appointments/reschedule/${id}`, { appointmentId:Number(id), newDate, reason:"Rescheduled by patient" })
        .then(()=>{ Api.toast("Appointment rescheduled.", "success"); return loadAppointments(); })
        .catch(err=>Api.toast(Api.friendlyError(err), "error"));
    });
  }

  function doctorName(id) {
    return doctors.find(d => d.doctorId === id)?.name || `Doctor #${id}`;
  }
  function fmtDateTime(v){ return v ? new Date(v).toLocaleString() : "-"; }
  function statusBadge(status) {
    const s=(status||"PENDING").toUpperCase();
    const c={PENDING:'status-pending',APPROVED:'status-approved',CANCELLED:'status-cancelled',REJECTED:'status-rejected',RESCHEDULED:'status-pending',COMPLETED:'status-completed'}[s] || 'status-pending';
    return `<span class="badge-soft ${c}">${s}</span>`;
  }

  return { init };
})();

const AdminApp = (() => {
  async function init() {
    Api.requireRole("ADMIN");
    await Promise.all([loadDoctors(), loadPatients(), loadAppointments(), loadReports(), loadSpecializations()]);
    bindForms();
  }

  async function loadSpecializations() {
    const list = await Api.get("/api/specializations");
    const options = '<option value="">Choose specialization</option>' + list.map(s => `<option value="${s.specializationId}">${s.specializationName}</option>`).join("");
    document.getElementById("doctorSpecializationId").innerHTML = options;
  }

  async function loadDoctors() {
    const list = await Api.get("/api/admin/doctors");
    document.getElementById("adminDoctors").innerHTML = list.map(d => `
      <tr>
        <td>${d.doctorId}</td><td>${d.name}</td><td>${d.specializationName || '-'}</td><td>${d.experience}</td><td>${d.designation || '-'}</td>
        <td class="d-flex gap-2">
          <button class="btn btn-sm btn-ac-soft" data-edit-doctor='${JSON.stringify(d).replace(/'/g,"&#39;")}'>Edit</button>
          <button class="btn btn-sm btn-danger-soft" data-delete-doctor="${d.doctorId}">Delete</button>
        </td>
      </tr>
    `).join("") || `<tr><td colspan="6"><div class="empty-state">No doctors found.</div></td></tr>`;
    document.querySelectorAll("[data-delete-doctor]").forEach(btn => btn.onclick = async () => {
      try { await Api.del(`/api/admin/doctors/${btn.dataset.deleteDoctor}`); Api.toast("Doctor deleted.", "success"); await loadDoctors(); }
      catch(err){ Api.toast(Api.friendlyError(err), "error");}
    });
    document.querySelectorAll("[data-edit-doctor]").forEach(btn => btn.onclick = () => {
      const d = JSON.parse(btn.dataset.editDoctor);
      document.getElementById("doctorId").value = d.doctorId;
      document.getElementById("doctorName").value = d.name || "";
      document.getElementById("doctorQualification").value = d.qualification || "";
      document.getElementById("doctorExperience").value = d.experience || 0;
      document.getElementById("doctorDesignation").value = d.designation || "";
      document.getElementById("doctorSpecializationId").value = d.specializationId || "";
      document.getElementById("doctorFormTitle").textContent = "Update Doctor";
      document.getElementById("doctorFormSection").scrollIntoView({behavior:"smooth"});
    });
  }

  async function loadPatients() {
    const list = await Api.get("/api/admin/patients");
    document.getElementById("adminPatients").innerHTML = list.map(p => `
      <tr>
        <td>${p.patientId}</td><td>${p.fullName}</td><td>${p.mobileNumber}</td><td>${p.email}</td><td>${p.gender}</td>
        <td><button class="btn btn-sm btn-danger-soft" data-delete-patient="${p.patientId}">Delete</button></td>
      </tr>
    `).join("") || `<tr><td colspan="6"><div class="empty-state">No patients found.</div></td></tr>`;
    document.querySelectorAll("[data-delete-patient]").forEach(btn => btn.onclick = async () => {
      try { await Api.del(`/api/admin/patients/${btn.dataset.deletePatient}`); Api.toast("Patient deleted.", "success"); await loadPatients(); }
      catch(err){ Api.toast(Api.friendlyError(err), "error"); }
    });
  }

  async function loadAppointments() {
    const list = await Api.get("/api/admin/appointments");
    document.getElementById("adminAppointments").innerHTML = list.map(a => `
      <tr>
        <td>${a.appointmentId}</td><td>${a.patientId}</td><td>${a.doctorId}</td><td>${new Date(a.appointmentDate).toLocaleString()}</td><td>${a.reason || '-'}</td><td>${a.status || '-'}</td>
        <td><button class="btn btn-sm btn-ac-soft" data-reschedule-admin="${a.appointmentId}">Reschedule</button></td>
      </tr>
    `).join("") || `<tr><td colspan="7"><div class="empty-state">No appointments found.</div></td></tr>`;
    document.querySelectorAll("[data-reschedule-admin]").forEach(btn => btn.onclick = async () => {
      const newDate = prompt("Enter new appointment datetime (YYYY-MM-DDTHH:MM)", "");
      if (!newDate) return;
      try {
        await Api.put(`/api/appointments/reschedule/${btn.dataset.rescheduleAdmin}`, {
          appointmentId: Number(btn.dataset.rescheduleAdmin),
          newDate,
          reason:"Rescheduled by admin"
        });
        Api.toast("Appointment rescheduled.", "success");
        await loadAppointments();
      } catch(err){ Api.toast(Api.friendlyError(err), "error"); }
    });
  }

  async function loadReports() {
    const totalPatients = await Api.get("/api/reports/patients/total");
    const totalConsultations = await Api.get("/api/reports/consultations/total");
    document.getElementById("metricPatients").textContent = totalPatients;
    document.getElementById("metricConsultations").textContent = totalConsultations;
  }

  function bindForms() {
    document.getElementById("doctorForm")?.addEventListener("submit", async (e) => {
      e.preventDefault();
      const payload = {
        name: document.getElementById("doctorName").value.trim(),
        qualification: document.getElementById("doctorQualification").value.trim(),
        experience: Number(document.getElementById("doctorExperience").value || 0),
        designation: document.getElementById("doctorDesignation").value.trim(),
        specializationId: Number(document.getElementById("doctorSpecializationId").value)
      };
      try {
        const id = document.getElementById("doctorId").value;
        if (id) await Api.put(`/api/admin/doctors/${id}`, payload);
        else await Api.post("/api/admin/doctors", payload);
        Api.toast(id ? "Doctor updated." : "Doctor added.", "success");
        e.target.reset();
        document.getElementById("doctorId").value = "";
        document.getElementById("doctorFormTitle").textContent = "Add New Doctor";
        await loadDoctors();
      } catch (err) { Api.toast(Api.friendlyError(err), "error"); }
    });

    document.getElementById("monthlyReportForm")?.addEventListener("submit", async (e) => {
      e.preventDefault();
      const month = document.getElementById("reportMonth").value.trim();
      try {
        const data = await Api.get(`/api/reports/consultations/monthly?month=${encodeURIComponent(month)}`);
        document.getElementById("monthlyResult").innerHTML = `
          <div class="ac-card p-3">
            <div><strong>Month:</strong> ${data.month || month}</div>
            <div><strong>Doctor consultations:</strong> ${data.doctorConsultations || 0}</div>
          </div>`;
      } catch (err) { Api.toast(Api.friendlyError(err), "error"); }
    });
  }

  return { init };
})();

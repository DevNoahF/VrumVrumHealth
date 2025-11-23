import { getToken } from "./submit.js";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");

  form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (!email || !password) {
      alert("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
      alert("Por favor, insira um e-mail válido.");
      return;
    }

    try {
      const authToken = await getToken();

      const loginBody = { email: email, senha: password };
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        mode: "cors",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${authToken}`
        },
        body: JSON.stringify(loginBody)
      });

      const responseJson = await response.json();

      if (response.status !== 200) {
        // tratar erro de login
        alert("Erro ao fazer login.");
        return;
      }

      const role = responseJson.roles?.[0]?.authority;
      if (role === "ROLE_PACIENTE") {
        const id = await getId("http://localhost:8080/paciente", email, authToken);

        if (id != null) {
          console.log("Paciente id:", id)

          sessionStorage.setItem("pacienteId", id);
          window.location.href = "../user/homePage.html";
        } else {
          alert("Não foi possível localizar o paciente.");
        }
      }if (role === "ROLE_ADMIN") {

        const adminId = await getId("http://localhost:8080/adm", email, authToken);

        if (adminId != null) {

          console.log("Admin id:", adminId);
          sessionStorage.setItem("adminId", adminId);
          window.location.href = "../adm/homePageADM.html";
        }
      
      }
        if(role==="ROLE_MOTORISTA"){
        const motoristaId = await getId("http://localhost:8080/motorista", email, authToken);

        if(motoristaId !=null){
          console.log("Motorista id:", motoristaId);
          sessionStorage.setItem("motoristaId", motoristaId);
          window.location.href="../motorista/diarioDeBordo.html";
        }
      }
      
    } catch (err) {
      console.error("Erro no processo de login:", err);
      alert("Ocorreu um erro. Tente novamente.");
    }
  });
});

async function getId(url, email, token) {
  let i = 1;
  const maxAttempts = 1000;

  while (i <= maxAttempts) {
    const response = await fetch(`${url}/${i}`, {
      method: "GET",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      }
    });

    if (!response.ok) {
      console.log("Erro ou sem mais dados para ID:", i);
      break;
    }

    const dados = await response.json();

    if (dados.email === email) {
      console.log("Encontrado paciente/admin:", dados);
      return dados.id;
    }

    i += 1;
  }

  return null;
}



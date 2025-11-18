

document.addEventListener("DOMContentLoaded", () => {
  authToken="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJub3ZvLmFkbWluQHZydW0uY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJpYXQiOjE3NjM0ODg1MTIsImV4cCI6MTc2MzU3NDkxMn0.ixB68veSSorf3jADds0L7DtAVbEvi0NPdkl0VojgkZDcrYGaE-YS-CR69z__E20qFFZkTTC7PJ-D0ZXi4O_xmQ"
  const form = document.querySelector("form");

  form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.getElementById("email").value
    const password = document.getElementById("password").value

    // Validação simples
    if (!email || !password) {
      alert("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
      alert("Por favor, insira um e-mail válido.");
      return;
    }

    // Cria o objeto que será enviado
    const bodyData = {
      email: email,
      senha: password
    };

const response = await fetch("http://localhost:8080/auth/login", {
    method: "POST",
    mode: "cors",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${authToken}`
    },
    body: JSON.stringify(bodyData)
  });
  const responseJson = await response.json();
  console.log(responseJson.roles[0].authority)

  if (response.status === 200) {//Depois trocar para 200, matem no 400 pq senão não funciona
      if(responseJson.roles[0].authority==="ROLE_PACIENTE"){
          window.location.href = "../user/HomePage.html"; 
      }
      if(responseJson.roles[0].authority==="ROLE_ADMIN"){
        window.location.href="../adm/homePageADM.html"
      }
  }});

});

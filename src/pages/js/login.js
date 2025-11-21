document.addEventListener("DOMContentLoaded", () => {
  authToken=getToken()
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
    var bodyData = {
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

if (response.status === 200) {
  if (responseJson.roles[0].authority === "ROLE_PACIENTE") {
    let id = await getId("http://localhost:8080/paciente");
    if (id != null) {
        console.log(id)
        window.location.href="../user/homePage.html"
      }
  }

if (responseJson.roles[0].authority === "ROLE_ADMIN") {
  let id = await getId("http://localhost:8080/adm");
  console.log(await id)
  if(id!=null){
    console.log(id)
    window.location.href = "../adm/homePageADM.html";
  }
    }
    }
  
}
)})

async function getId(url) {
  let found = false;
  let i = 1;
  const email = document.getElementById("email").value;
  authToken=await getToken()

  while (!found) {
    const response = await fetch(`${url}/${i}`, {
      method: "GET",
      mode: "cors",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${authToken}`
      }
    });

    if (!response.ok) {
      console.log("Erro ou sem mais pacientes para ID:", i);
      break;
    }

    const dados = await response.json();

    if (dados.email === email) {
      const paciente = { id: dados.id, nome: dados.nome };
      console.log("Paciente encontrado:", paciente);
      return paciente.id;  // aqui retorna o id encontrado
    }

    i += 1;

    if (i > 1000) {
      console.log("Limite de tentativas atingido");
      break;
    }
  }

  // Se sair do loop sem encontrar:
  return null;  // ou `undefined`, dependendo do que você quer indicar
}


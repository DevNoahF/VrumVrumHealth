// Espera o carregamento do DOM
document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");

  form.addEventListener("submit", (event) => {
    event.preventDefault(); // Evita o envio padrão do formulário

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    // Validação simples
    if (!email || !password) {
      alert("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    // Validação de formato de e-mail (simples)
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
      alert("Por favor, insira um e-mail válido.");
      return;
    }

    // Simulação de login (apenas para teste)
    // Aqui depois você pode trocar pela integração com o backend
    if (email === "admin@vrumvrum.com" && password === "123456") {
      alert("Login realizado com sucesso! 🚀");
      window.location.href = "../HTML/controle.html"; // redireciona para a próxima página
    } else {
      alert("E-mail ou senha incorretos. Tente novamente.");
    }
  });
});


// login.js

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");
  
  form.addEventListener("submit", async (event) => {
    event.preventDefault(); // não deixa submeter o form do jeito tradicional

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // seu token de autenticação
    const authToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJub3ZvLmFkbWluQHZydW0uY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJpYXQiOjE3NjMyMzI3MzIsImV4cCI6MTc2MzMxOTEzMn0.L5Odlyi4SqiietCMKk6nIcf9SNJ3Jsq4KZ7PNoZVBLN5xtzFRj8GrUIih5hWpAXsqZzJ713qVBVzsG7S_Q1aZg";

    const body = {
      email,
      password
    };

    const url = "https://localhost:8080/auth/login"; // a URL para onde vai mandar o POST

    try {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${authToken}`
        },
        body: JSON.stringify(body)
      });

      // se o servidor retornar 200, redireciona
      if (response.status === 200) {
        window.location.href = "http://localhost:8180/paciente/HomePage.html";
      } else {
        // se não for 200
        const texto = await response.text();
        alert("Login falhou: " + response.status);
        console.log("Resposta do servidor:", texto);
      }
    } catch (error) {
      console.error("Erro na requisição:", error);
      alert("Um erro ocorreu. Tente novamente.");
    }
  });
});


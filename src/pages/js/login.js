const authToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJub3ZvLmFkbWluQHZydW0uY29tIiwicm9sZXMiOlsiUk9MRV9BRE1JTiJdLCJpYXQiOjE3NjMzMzk0NzAsImV4cCI6MTc2MzQyNTg3MH0.ivG3eh1aKHa-M3MWNtzndl0izMy_pakzOIik_kRrFU5kTIGnanI-kkKJDL7-tupB6QRxZKGkqMdZZ03rkrJT_w";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.querySelector("form");

  form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

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
      password: password
    };

    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        mode: "cors",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${authToken}`
        },
        body: JSON.stringify(bodyData)
      });

      // Agora verifica a resposta
      if (response.status === 200) {
        const responseJson = await response.json(); // se o backend retornar JSON
        console.log("Resposta do servidor:", responseJson);

        alert("Login bem sucedido!");
        // Aqui você pode redirecionar para outra página
        window.location.href = "../user/HomePage.html";
      } else {
        const errorText = await response.text();
        console.error("Erro no login:", response.status, errorText);
        alert("Falha no login: " + response.status);
      }
    } catch (error) {
      console.error("Erro na requisição:", error);
      alert("Um erro aconteceu. Verifique sua conexão ou tente novamente.");
    }
  });
});

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

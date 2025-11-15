// Espera o carregamento completo do DOM
document.addEventListener("DOMContentLoaded", () => {

  // Botões de necessidade especial
  const btnSim = document.getElementById("btn-sim");
  const btnNao = document.getElementById("btn-nao");
  const menuFlutuante = document.getElementById("menu-flutuante");

  // Mostra o campo "Qual?" quando clicar em "Sim"
  btnSim.addEventListener("click", () => {
    menuFlutuante.style.display = "block";
    btnSim.classList.add("ativo");
    btnNao.classList.remove("ativo");
  });

  // Esconde quando clicar em "Não"
  btnNao.addEventListener("click", () => {
    menuFlutuante.style.display = "none";
    btnNao.classList.add("ativo");
    btnSim.classList.remove("ativo");
  });

  // Validação do formulário
  const form = document.querySelector("form");

  form.addEventListener("submit", (event) => {
    event.preventDefault(); // Evita o envio imediato

    const firstname = document.getElementById("firstname").value.trim();
    const lastname = document.getElementById("lastname").value.trim();
    const cpf = document.getElementById("cpf").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const confirmpassword = document.getElementById("confirmpassword").value;

    // Verificação de campos obrigatórios
    if (!firstname || !lastname || !cpf || !email || !password || !confirmpassword) {
      alert("Por favor, preencha todos os campos obrigatórios.");
      return;
    }

    // Verificação de senha
    if (password !== confirmpassword) {
      alert("As senhas não coincidem!");
      return;
    }

    // Exemplo de sucesso (futuro backend pode ser conectado aqui)
    alert(`Cadastro realizado com sucesso, ${firstname}!`);
    form.reset();
    menuFlutuante.style.display = "none";
    btnSim.classList.remove("ativo");
    btnNao.classList.remove("ativo");
  });
});
